package com.funshion.redis.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
  

public abstract class AbstractBaseRedisDao<K, V> {  
      
	protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired  
    protected RedisTemplate<K, V> redisTemplate;  
  
    public void setRedisTemplate(RedisTemplate<K, V> redisTemplate) {  
        this.redisTemplate = redisTemplate;  
    }  
      
    /** 
     * ªÒ»° RedisSerializer 
     * 
     */  
    protected RedisSerializer<String> getRedisSerializer() {  
        return redisTemplate.getStringSerializer();  
    }  
}  
