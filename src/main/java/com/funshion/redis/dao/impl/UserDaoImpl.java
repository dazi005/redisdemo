package com.funshion.redis.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.Assert;

import com.funshion.redis.dao.AbstractBaseRedisDao;
import com.funshion.redis.dao.UserDao;
import com.funshion.redis.entity.User;
  
  
public class UserDaoImpl extends AbstractBaseRedisDao<String, User> implements UserDao {  
  
    /**  
     * ���� 
     * @param user 
     * @return 
     */  
    public boolean save(final User user) {  
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
            	return connection.setNX(redisTemplate.getStringSerializer().serialize("redisdemo.user.uid."+user.getId()),
            			redisTemplate.getStringSerializer().serialize(user.toString())); 
            }  
        });  
        return result;  
    }
      
    /** 
     * �������� ʹ��pipeline��ʽ   
     *@param list 
     *@return 
     * ��list�ж����й���,��ȡ������key.�޹��Ե�ʱ��ֻ�ܵ����洢
     */  
    public boolean save(final List<User> list) {  
        Assert.notEmpty(list);  
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
            	/**
            	 *  list ֱ��ת json
            	 * */
/*            	JSONArray usr_lis = JSONArray.fromObject(list);
            	System.out.println(usr_lis.toString());*/
            	
                for (User user : list) {  
                	connection.setNX(redisTemplate.getStringSerializer().serialize("redisdemo.user.uid."+user.getId()),
                		redisTemplate.getStringSerializer().serialize(user.toString()));
                }  
                return true;  
            }  
        }, false, true);  
        return result;  
    }  
      
    /**  
     * ɾ�� 
     * @param key 
     */  
    public void delete(String key) {  
        List<String> list = new ArrayList<String>();  
        list.add(key);  
        delete(list);  
    }  
  
    /** 
     * ɾ����� 
     * @param keys 
     */  
    public void delete(List<String> keys) {  
        redisTemplate.delete(keys);  
    }  
  
    /** 
     * �޸�  
     * @param user 
     * @return  
     */  
    public boolean update(final User user) {  
        String key = user.getId();  
        if (get(key) == null) {  
            throw new NullPointerException("�����в�����, key = " + key);  
        }  
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = getRedisSerializer();  
                byte[] key  = serializer.serialize(user.getId());  
                byte[] name = serializer.serialize(user.getName());  
                connection.set(key, name);  
                return true;  
            }  
        });  
        return result;  
    }  
  
    /**  
     * ͨ��key��ȡ 
     * @param userId 
     * @return 
     */  
    public User get(final String userId) {  
        User result = redisTemplate.execute(new RedisCallback<User>() {  
            public User doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
            	
            	 byte[] value = connection.get(
            			 redisTemplate.getStringSerializer().serialize("redisdemo.user.uid."+userId));
            	 
                if (value == null) {  
                    return null;  
                }   
                return User.toUser(redisTemplate.getStringSerializer().deserialize(value));  
            }  
        });  
        return result;  
    }  
}  
