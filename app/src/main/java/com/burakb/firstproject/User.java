package com.burakb.firstproject;

public class User {
    private String username;
    private String firstname;
    private String lastname;
    private String password;
    private String email;
    private String telNum;

    public User(){

    }

    public User(String username,String password,String email){
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public String getUsername(){
        return this.username;
    }
    public String getFirstname(){
        return this.firstname;
    }
    public String getLastname(){
        return this.lastname;
    }
    public String getPassword(){
        return this.password;
    }
    public String getEmail(){
        return this.email;
    }
    public String getTelNum(){
        return this.telNum;
    }

    public void setUsername(String username){
        this.username = username;
    }
    public void setFirstName(String firstName){
        this.firstname = firstName;
    }
    public void setLastName(String lastName){
        this.lastname = lastName;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setTelNum(String telNum){
        this.telNum = telNum;
    }

}

