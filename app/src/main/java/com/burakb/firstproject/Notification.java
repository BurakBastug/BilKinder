package com.burakb.firstproject;

import java.util.ArrayList;

public class Notification {
    private String notifName;
    private String notifDetails;
    private ArrayList<String> allowedList;
    private ArrayList<String> notAllowedList;
    private String teacher;

    public Notification(){

    }

    public Notification(String name, String details, String teacher){
        allowedList = new ArrayList<String>();
        notAllowedList = new ArrayList<String>();
        allowedList.add("");
        notAllowedList.add("");
        this.notifName = name;
        this.notifDetails = details;
        this.teacher = teacher;
    }


    public ArrayList<String> getAllowedList() {
        return allowedList;
    }

    public ArrayList<String> getNotAllowedList() {
        return notAllowedList;
    }

    public String getNotifDetails() {
        return notifDetails;
    }

    public String getNotifName() {
        return notifName;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setAllowedList(ArrayList<String> allowedList) {
        this.allowedList = allowedList;
    }

    public void setNotAllowedList(ArrayList<String> notAllowedList) {
        this.notAllowedList = notAllowedList;
    }

    public void setNotifDetails(String notifDetails) {
        this.notifDetails = notifDetails;
    }

    public void setNotifName(String notifName) {
        this.notifName = notifName;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}
