package com.burakb.firstproject;

import android.media.Image;

import java.util.ArrayList;

public class Teacher extends User {
    private int age = 0;
    private String className;
    private ArrayList<Event> createdEvents;
    private ArrayList<Child> studentList;

    public Teacher(){

    }

    public Teacher(String username, String password, String email, String className){
        super(username,password,email);
        this.studentList = new ArrayList<>();
        this.createdEvents = new ArrayList<>();
        this.className = className;
    }

    public ArrayList<Child> getStudentList(){
        return this.studentList;
    }

    public int getAge(){
        return this.age;
    }

    public String getClassName() { return this.className; }

    public void setAge(int age){
        this.age = age;
    }

    public void createEvent(String name, String description, Image image){
        Event event = new Event(name,description,image);
        this.createdEvents.add(event);
    }

    public void setClassName(String className) { this.className = className; }

    public String viewChildrenStatues(){
        String formatted = "";
        for(Child child : this.studentList){
            formatted += child.toString() + "\n";
        }
        return formatted;
    }

    public void setAllData(String teacherName, int age, String address, String contactNum, String contactMail) {
        setUsername(teacherName);
        setAge(age);
        setAddress(address);
        setContactNum(contactNum);
        setEmail(contactMail);
    }
}
