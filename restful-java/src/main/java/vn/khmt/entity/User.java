/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.khmt.entity;

/**
 *
 * @author huy
 */
public class User {
    /*
     "id": 1,
    "username": "admin",
    "password": "admin",
    "email": "admin@email.com",
    "status": 7,
    "name": "Admin"
    */    
    private int id;
    private String username;
    private String password;
    private String email;
    private int status;
    private String name;
  
    public void setId(int id){
        this.id = id;
    }
    
    public void setUsername(String username){
        this.username = username;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public void setStatus(int status){
        this.status = status;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public int getId(){
        return id;
    }
    
    public String getUsername(){
        return username;
    }
    
    public String getPassword(){
        return password;
    }
    
    public String getEmail(){
        return email;
    }
    
    public int getStatus(){
        return status;
    }
    
    public String getName(){
        return name;
    }
}
