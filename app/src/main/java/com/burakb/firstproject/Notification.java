package com.burakb.firstproject;

import java.util.ArrayList;

public class Notification {
    private String notifName;
    private String notifDetails;
    private ArrayList<Child> allowedList;
    private ArrayList<Child> notAllowedList;
    private Teacher teacher;

    public Notification(){

    }

    public Notification(String name, String details, Teacher teacher){
        allowedList = new ArrayList<>();
        notAllowedList = new ArrayList<>();
        this.notifName = name;
        this.notifDetails = details;
        this.teacher = teacher;
    }

    public ArrayList<Child> getAllowedList() {
        return allowedList;
    }

    public ArrayList<Child> getNotAllowedList() {
        return notAllowedList;
    }

    public String getNotifDetails() {
        return notifDetails;
    }

    public String getNotifName() {
        return notifName;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setAllowedList(ArrayList<Child> allowedList) {
        this.allowedList = allowedList;
    }

    public void setNotAllowedList(ArrayList<Child> notAllowedList) {
        this.notAllowedList = notAllowedList;
    }

    public void setNotifDetails(String notifDetails) {
        this.notifDetails = notifDetails;
    }

    public void setNotifName(String notifName) {
        this.notifName = notifName;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
