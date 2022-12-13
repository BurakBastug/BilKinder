package com.burakb.firstproject;

public class User {
    private String username;
    private String firstname;
    private String lastname;
    private String password;
    private String email;
    private String telNum;
    private String address;

    public User(){

    }

    public User(String username,String email,String password){
        this.username = username;
        this.email = email;
        this.password = password;
        firstname = "";
        lastname = "";
        telNum = "";
        address = "";
    }

    public static boolean isCorrectFormOfContactNumber(String contactNumber) {
        return (contactNumber.length() == 10 && contactNumber.charAt(0) != '0') ||
                (contactNumber.length() == 11 && contactNumber.charAt(0) == '0');
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
        if(isCorrectFormOfContactNumber(telNum))
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

    public String getAddress() { return this.address;}

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public void setAddress(String address) { this.address = address; }
}

