package com.burakb.firstproject;

public class User {
    private String username;
    private String firstname;
    private String lastname;
    private String password;
    private String email;
    private String telNum;
    private String address;
    private String contactNum;

    public User(){

    }

    public User(String username,String email,String password){
        this.username = username;
        this.email = email;
        this.password = password;
        this.telNum = "";
        this.address = "";
        this.contactNum = "";
        setFirstAndLastName(username);
    }

    private void setFirstAndLastName(String username) {
        firstname = username.substring(0, username.indexOf(" "));
        lastname = username.substring(username.indexOf(" ") + 1);
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

    public String getAddress() { return address; }

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

    public String getContactNum() { return contactNum; }

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

    public void setContactNum(String contactNum) {
        if(isCorrectFormOfContactNumber(contactNum)) {
            this.contactNum = contactNum;
        }
    }
    public static boolean isCorrectFormOfContactNumber(String contactNumber) {
        return (contactNumber.length() == 10 && contactNumber.charAt(0) != '0') ||
                (contactNumber.length() == 11 && contactNumber.charAt(0) == '0');
    }
}

