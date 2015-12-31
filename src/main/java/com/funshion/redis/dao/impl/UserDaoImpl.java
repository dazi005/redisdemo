package com.funshion.redis.dao.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
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
    	try{
    		
	        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
	            public Boolean doInRedis(RedisConnection connection)  
	                    throws DataAccessException {  
	            	return connection.setNX(redisTemplate.getStringSerializer().serialize("redisdemo.user.uid."+user.getId()),
	            			redisTemplate.getStringSerializer().serialize(user.toString())); 
	            }  
	        });  
	        return result;  
	        
    	}catch(DataAccessResourceFailureException e){
    		logger.error("Jedis Connection refused");
    		//e.printStackTrace();
    	}
    	return false;
    }
      
    /** 
     * �������� ʹ��pipeline��ʽ   
     *@param list 
     *@return 
     * ��list�ж����й���,��ȡ������key.�޹��Ե�ʱ��ֻ�ܵ����洢
     */  
    public boolean save(final List<User> list) {  
        Assert.notEmpty(list);  
        try{
	        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
	            public Boolean doInRedis(RedisConnection connection)  
	                    throws DataAccessException {   
	            	//list ֱ��ת json 
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
	    }catch(DataAccessResourceFailureException e){
			logger.error("Jedis Connection refused");
			//e.printStackTrace();
		}
		return false;
    }  
      
    /**  
     * ɾ�� 
     * @param key 
     */  
    public void delete(String userid) {  
    	try{
    		redisTemplate.delete("redisdemo.user.uid."+userid);
    		
    	}catch(DataAccessResourceFailureException e){
    		logger.error("Jedis Connection refused");
    		//e.printStackTrace();
    	}
    }  
  
    /** 
     * ɾ����� 
     * @param keys 
     */  
    public void delete(List<String> keys) {
    	try{
	    	List<String> keylist = new ArrayList<String>();
	    	for(String userid:keys){
	    		keylist.add("redisdemo.user.uid."+userid);
	    	}
	        redisTemplate.delete(keylist);  
        
    	}catch(DataAccessResourceFailureException e){
    		logger.error("Jedis Connection refused");
    		//e.printStackTrace();
    	}
    }  
  
    /** 
     * �޸�  
     * @param user 
     * @return  
     */  
    public boolean update(final User user) { 
        if (get(user.getId()) == null) {  
            throw new NullPointerException("�����в�����, key = " + user.getId());  
        }  
        try{
        	
	        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
	            public Boolean doInRedis(RedisConnection connection) throws DataAccessException { 
	                connection.set(redisTemplate.getStringSerializer().serialize("redisdemo.user.uid."+user.getId()),
	                		redisTemplate.getStringSerializer().serialize(user.toString()));  
	                return true;  
	            }  
	        });  
	        return result;  
	    }catch(DataAccessResourceFailureException e){
			logger.error("Jedis Connection refused");
			//e.printStackTrace();
		}
        return false;
    }  
  
    /**  
     * ͨ��key��ȡ 
     * @param userId 
     * @return 
     */  
    public User get(final String userId) { 
    	try{
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
	    }catch(DataAccessResourceFailureException e){
			logger.error("Jedis Connection refused");
			//e.printStackTrace();
		}
	    return null;
    }

    /** 
     * 	����list���󣬳�ȡlist�����ж���Ĺ�����Ϊkey
     *  @param list
     *  @return
     */
	@Override
	public boolean saveList(final List<User> userlist) {
		if(userlist == null||userlist.size()<=0){
			 throw new NullPointerException("the param list is empty"); 
		}
		try{
			
			boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
	            public Boolean doInRedis(RedisConnection connection)  
	                    throws DataAccessException {   
	            	//list ֱ��ת json 
	            	JSONArray usr_list = JSONArray.fromObject(userlist);
	            	
	            	connection.setNX(redisTemplate.getStringSerializer().serialize(
	            			"redisdemo.user.name."+userlist.get(0).getName()),
	                		redisTemplate.getStringSerializer().serialize(usr_list.toString()));
	                return true;  
	            }  
	        }, false, true);  
	        return result;  
			
		}catch(DataAccessResourceFailureException e){
			logger.error("Jedis Connection refused");
		}
		return false;
	} 
    
   
}  
