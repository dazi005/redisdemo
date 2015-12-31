package com.funshion.redis.dao;

import java.util.List;

import com.funshion.redis.entity.User;
   
public interface UserDao {  
      
    /** 
     * 新增 
     * @param user 
     * @return 
     */  
    boolean save(User user);  
      
    /** 
     * 批量新增 使用pipeline方式 
     * @param list 
     * @return 
     */  
    boolean save(List<User> list);  
      
    /** 
     * 删除 
     * @param key 
     */  
    void delete(String key);  
      
    /** 
     * 删除多个 
     * @param keys 
     */  
    void delete(List<String> keys);  
      
    /** 
     * 修改 
     * @param user 
     * @return  
     */  
    boolean update(User user);  
  
    /** 
     * 通过key获取 
     * @param keyId 
     * @return  
     */  
    User get(String keyId);
    
    /** 保存list对象，抽取list中所有对象的共性作为key
     *  @param list
     *  @return
     */
    boolean saveList(List<User> userlist);
}  