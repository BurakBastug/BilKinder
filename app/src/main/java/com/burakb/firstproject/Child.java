package com.burakb.firstproject;

import java.util.ArrayList;

public class Child extends User{

    private String teacherName;
    private String parentName;
    private String bloodType;
    private String contactNumber;
    private String contactMail;
    private String address;
    private String medicalCondition;
    private ArrayList<Event> allowedEvents;

    public Child(){

    }
    public Child(String username,String password, String email, String teacherName, String parentName, String bloodType,
                 String contactNumber, String contactMail, String address, String medicalCondition){
        super(username,password,email);
        setTeacherName(teacherName);
        setParentName(parentName);
        setBloodType(bloodType);
        setContactNumber(contactNumber);
        setContactMail(contactMail);
        setAddress(address);
        setMedicalCondition(medicalCondition);

        this.allowedEvents = new ArrayList<>();
    }
    public String getTeacherName() { return this.teacherName; }

    public String getParentName() { return this.parentName; }

    public String getBloodType(){
        return this.bloodType;
    }

    public String getContactNumber() { return this.contactNumber; }

    public String getContactMail() { return this.contactMail; }

    public String getAddress() { return this.address; }

    public String getMedicalCondition(){
        return this.medicalCondition;
    }

    public ArrayList<Event> getAllowedEvents() { return this.allowedEvents; }

    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }

    public void setParentName(String parentName){
        this.parentName = parentName;
    }

    public void setContactNumber(String contactNumber) {
        if(isCorrectFormOfContactNumber(contactNumber)) {
            this.contactNumber = contactNumber;
        }
    }

    public void setContactMail(String contactMail) { this.contactMail = contactMail; }

    public void setAddress(String address) { this.address = address; }

    public void setBloodType(String bloodType){
        if(isCorrectFormOfBloodType(bloodType)) {
                this.bloodType = bloodType;
            }
        }
    }

    public void setMedicalCondition(String medicalCondition){
        this.medicalCondition = medicalCondition;
    }

    public void givePermission(Event event, boolean decision){
        if(decision){
            this.allowedEvents.add(event);
        }
    }
    public void isSick(boolean decision){
        if(decision){
            this.medicalCondition = "Special Care is needed.";
        }
        else{
            this.medicalCondition = "There is no problem.";
        }
    }

    public static boolean isCorrectFormOfBloodType(String bloodType) {
        return bloodType.length() == 4 && (bloodType.charAt(0) == 'A'|| bloodType.charAt(0) == 'B' || bloodType.charAt(0) == '0'
                    || bloodType.charAt(1) == 'A'|| bloodType.charAt(1) == 'B' || bloodType.charAt(1) == '0'
                    && bloodType.substring(2).equalsIgnoreCase("rh"));
    }

    public static boolean isCorrectFormOfContactNumber(String contactNumber) {
        return (contactNumber.length() == 10 && contactNumber.charAt(0) != '0') ||
                (contactNumber.length() == 11 && contactNumber.charAt(0) == '0');
    }

    public String toString(){
        String formatted = "";

        formatted += "Full Name: " + this.getFirstname() + " " + this.getLastname() + "\n";
        formatted += "Blood Type: " + this.getBloodType() + "\n";
        formatted += "Medical Condition: " + this.getMedicalCondition() + "\n";
        formatted += "Parent: " + this.parentName + "\n";

        return formatted;
    }
}
