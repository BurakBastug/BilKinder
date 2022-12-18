package com.burakb.firstproject;

import java.util.ArrayList;

public class Child extends User{

    private String teacherName;
    private String parentName;
    private String bloodType;
    private String contactNumber;
    private String contactMail;
    private String medicalCondition;
    private String sickOrNot;
    private boolean isSick;
    private ArrayList<Event> allowedEvents;

    public Child(){

    }
    public Child(String username,String password, String email){
        super(username,password,email);
        this.allowedEvents = new ArrayList<>();
        teacherName = "";
        parentName = "";
        bloodType = "";
        contactNumber = "";
        contactMail = "";
        medicalCondition = "";
        sickOrNot = "We don't know yet";
        isSick = false;
    }
    public String getTeacherName() { return this.teacherName; }

    public String getParentName() { return this.parentName; }

    public String getBloodType(){
        return this.bloodType;
    }

    public String getContactNumber() { return this.contactNumber; }

    public String getContactMail() { return this.contactMail; }

    public String getMedicalCondition(){
        return this.medicalCondition;
    }

    public ArrayList<Event> getAllowedEvents() { return this.allowedEvents; }

    public boolean getIsSick() { return this.isSick; }

    public String getSickOrNot() {
        return this.sickOrNot;
    }

    public void setAllData(String userName, String parentName, String bloodType, String contactNumber,
                           String contactMail, String address, String medicalCondition) {
        setUsername(userName);
        setParentName(parentName);
        setBloodType(bloodType);
        setContactNumber(contactNumber);
        setContactMail(contactMail);
        setAddress(address);
        setMedicalCondition(medicalCondition);
    }

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

    public void setBloodType(String bloodType){
        if(isCorrectFormOfBloodType(bloodType)) {
                this.bloodType = bloodType;
        }
    }

    public void setMedicalCondition(String medicalCondition){
        this.medicalCondition = medicalCondition;
    }

    public void setIsSick(boolean isSick) { this.isSick = isSick; }

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
        return bloodType.length() == 5 && (bloodType.charAt(0) == 'A'|| bloodType.charAt(0) == 'B' || bloodType.charAt(0) == '0'
                    || bloodType.charAt(1) == 'A'|| bloodType.charAt(1) == 'B' || bloodType.charAt(1) == '0'
                    && bloodType.substring(2).equalsIgnoreCase("rh") && (bloodType.charAt(4) == '+' || bloodType.charAt(4) == '-'));
    }

    public String toString(){
        String formatted = "";

        formatted += "Blood Type: " + this.getBloodType() + "\n";
        formatted += "Medical Condition: " + this.getMedicalCondition() + "\n";
        formatted += "Parent: " + this.parentName + "\n";

        return formatted;
    }

    public boolean equals(Child child){
        return this.getEmail().equals(child.getEmail());
    }
}
