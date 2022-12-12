package com.burakb.firstproject;

import android.media.Image;

import java.util.ArrayList;

public class Teacher extends User {
    private ArrayList<String> studentList;
    private int age = 0;
    private ArrayList<Event> createdEvents;


    public Teacher(){

    }

    public Teacher(String username, String password, String email){
        super(username,password,email);
        this.studentList = new ArrayList<String>();
        this.createdEvents = new ArrayList<Event>();
        this.studentList.add("");
    }




    public void setStudentList(ArrayList<String> studentList) {
        this.studentList = studentList;
    }


    public ArrayList<String> getStudentList(){
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



    public String toString(){
        return this.getUsername();
    }

    public void addStudent(String child){
        this.studentList.add(child);
    }
}
