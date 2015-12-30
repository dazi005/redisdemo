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
     * 新增 
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
     * 批量新增 使用pipeline方式   
     *@param list 
     *@return 
     * 若list中对象有共性,提取共性做key.无共性的时候只能单个存储
     */  
    public boolean save(final List<User> list) {  
        Assert.notEmpty(list);  
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
            	/**
            	 *  list 直接转 json
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
     * 删除 
     * @param key 
     */  
    public void delete(String key) {  
        List<String> list = new ArrayList<String>();  
        list.add(key);  
        delete(list);  
    }  
  
    /** 
     * 删除多个 
     * @param keys 
     */  
    public void delete(List<String> keys) {  
        redisTemplate.delete(keys);  
    }  
  
    /** 
     * 修改  
     * @param user 
     * @return  
     */  
    public boolean update(final User user) {  
        String key = user.getId();  
        if (get(key) == null) {  
            throw new NullPointerException("数据行不存在, key = " + key);  
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
     * 通过key获取 
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
