package com.funshion.redis.dao;

import java.util.List;

import com.funshion.redis.entity.User;
   
public interface UserDao {  
      
    /** 
     * ���� 
     * @param user 
     * @return 
     */  
    boolean add(User user);  
      
    /** 
     * �������� ʹ��pipeline��ʽ 
     * @param list 
     * @return 
     */  
    boolean add(List<User> list);  
      
    /** 
     * ɾ�� 
     * @param key 
     */  
    void delete(String key);  
      
    /** 
     * ɾ����� 
     * @param keys 
     */  
    void delete(List<String> keys);  
      
    /** 
     * �޸� 
     * @param user 
     * @return  
     */  
    boolean update(User user);  
  
    /** 
     * ͨ��key��ȡ 
     * @param keyId 
     * @return  
     */  
    User get(String keyId);  
}  