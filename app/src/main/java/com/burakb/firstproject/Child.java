package com.burakb.firstproject;

import java.util.ArrayList;

public class Child extends User{

    private String bloodType;
    private String medicalCondition;
    private ArrayList<Event> allowedEvents;
    private String parentName;

    public Child(){

    }
    public Child(String username,String password, String email){
        super(username,password,email);
        this.allowedEvents = new ArrayList<>();
    }

    public String getBloodType(){
        return this.bloodType;
    }
    public String getMedicalCondition(){
        return this.medicalCondition;
    }

    public String getParentName(){
        return this.parentName;
    }
    public void setParentName(String name){
        this.parentName = name;
    }

    public void setBloodType(String bloodType){
        this.bloodType = bloodType;
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

    public String toString(){
        String formatted = "";

        formatted += "Full Name: " + this.getFirstname() + " " + this.getLastname() + "\n";
        formatted += "Blood Type: " + this.getBloodType() + "\n";
        formatted += "Medical Condition: " + this.getMedicalCondition() + "\n";
        formatted += "Parent: " + this.parentName + "\n";

        return formatted;
    }
}
