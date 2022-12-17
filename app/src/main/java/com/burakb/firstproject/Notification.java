package com.burakb.firstproject;

import java.util.ArrayList;

public class Notification {
    private String notifName;
    private String notifDetails;
    private ArrayList<Child> allowedList;
    private String teacher;

    public Notification(){

    }

    public Notification(String name, String details, String teacher){
        allowedList = new ArrayList<Child>();

        allowedList.add(new Child("","",""));

        this.notifName = name;
        this.notifDetails = details;
        this.teacher = teacher;
    }


    public ArrayList<Child> getAllowedList() {
        return allowedList;
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
