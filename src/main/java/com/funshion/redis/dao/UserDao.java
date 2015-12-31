package com.funshion.redis.dao;

import java.util.List;

import com.funshion.redis.entity.User;
   
public interface UserDao {  
      
    /** 
     * ���� 
     * @param user 
     * @return 
     */  
    boolean save(User user);  
      
    /** 
     * �������� ʹ��pipeline��ʽ 
     * @param list 
     * @return 
     */  
    boolean save(List<User> list);  
      
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
    
    /** ����list���󣬳�ȡlist�����ж���Ĺ�����Ϊkey
     *  @param list
     *  @return
     */
    boolean saveList(List<User> userlist);
}  