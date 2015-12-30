package com.funshion.redis.entity;

import java.io.Serializable;

import net.sf.json.JSONObject;

import com.funshion.redis.utils.JsonUtils;

public class User implements Serializable{
	
	private static final long serialVersionUID = -6011241820070393952L;   
    private String id;  
    private String name;  
    private String password;  
   
    public User() {  
          
    }  
       
    public User(String id, String name, String password) {  
        super();  
        this.id = id;  
        this.name = name;  
        this.password = password;  
    }  
   
    public String getId() {  
        return id;  
    }  
   
    public void setId(String id) {  
        this.id = id;  
    }  
 
    public String getName() {  
        return name;  
    }  
  
    public void setName(String name) {  
        this.name = name;  
    }  
   
    public String getPassword() {  
        return password;  
    }  

    public void setPassword(String password) {  
        this.password = password;  
    } 
    
    @Override
    public String toString(){
    	return JsonUtils.toJson(this, null);
    }
    
    /**
     * @author zhangxu
     * Transfer json String to bean
     * */
    static public User toUser(String user){
    	if(user == null||user == ""){
    		return null;
    	}
    	return (User)JSONObject.toBean(JSONObject.fromObject(user), User.class);
    }
}
