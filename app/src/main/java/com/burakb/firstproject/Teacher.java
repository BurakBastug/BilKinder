package com.burakb.firstproject;

import android.media.Image;

import java.util.ArrayList;

public class Teacher extends User {
    private ArrayList<Child> studentList;
    private int age;
    private ArrayList<Event> createdEvents;

    public Teacher(){

    }

    public Teacher(String username, String password, String email){
        super(username,password,email);
        this.studentList = new ArrayList<>();
        this.createdEvents = new ArrayList<>();
    }

    public ArrayList<Child> getStudentList(){
        return this.studentList;
    }
    public int getAge(){
        return this.age;
    }

    public void setAge(int age){
        this.age = age;
    }

    public void createEvent(String name, String description, Image image){
        Event event = new Event(name,description,image);
        this.createdEvents.add(event);
    }

    public String viewChildrenStatues(){
        String formatted = "";
        for(Child child : this.studentList){
            formatted += child.toString() + "\n";
        }
        return formatted;
    }
}
