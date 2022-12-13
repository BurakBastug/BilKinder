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

    public User(String username,String email,String password){
        this.username = username;
        this.email = email;
        this.password = password;

    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getTelNum() {
        return telNum;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setTelNum(String telNum) {
        this.telNum = telNum;
    }

    public String getUsername(){
        return this.username;
    }


    public String getPassword(){
        return this.password;
    }
    public String getEmail(){
        return this.email;
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


}

