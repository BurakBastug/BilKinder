package com.burakb.firstproject;

import android.media.Image;

import java.util.ArrayList;

public class Teacher extends User {

    private String age;
    private ArrayList<String> studentList;
    private ArrayList<Event> createdEvents;

    public Teacher(){

    }

    public Teacher(String username, String password, String email){
        super(username,password,email);
        this.studentList = new ArrayList<String>();
        this.createdEvents = new ArrayList<Event>();
        this.studentList.add("");
        this.age = "";
    }

    public void setTeacherData(String age, String address, String contactNum, String contactMail) {
        setAge(age);
        setAddress(address);
        setTelNum(contactNum);
        setEmail(contactMail);
    }

    public void setStudentList(ArrayList<String> studentList) {
        this.studentList = studentList;
    }

    public ArrayList<String> getStudentList(){
        return this.studentList;
    }

    public String getAge(){return this.age;}

    public void setAge(String age) {
        this.age = age;
    }

    public void createEvent(String name, String description, Image image){
        Event event = new Event(name,description);
        this.createdEvents.add(event);
    }

    public String toString(){
        return this.getUsername();
    }

    public void addStudent(String child){
        this.studentList.add(child);
    }
}
