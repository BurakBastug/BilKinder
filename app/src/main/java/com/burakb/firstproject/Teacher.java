package com.burakb.firstproject;

import java.util.ArrayList;
import java.util.HashMap;

public class Teacher extends User {

    private String age;
    private ArrayList<String> studentList;
    private HashMap<String,String> weeklySchedule;
    private HashMap<String,String> weeklyMenu;
    private Notification firstNotif = new Notification("","","");
    private Event firstEvent = new Event("","","");

    public Teacher(){

    }

    public Teacher(String username, String password, String email){
        super(username,password,email);
        this.studentList = new ArrayList<String>();
        this.studentList.add("");
        this.age = "";
        this.weeklySchedule = new HashMap<>();
        this.weeklyMenu = new HashMap<>();
        this.weeklyMenu.put("Monday","");
        this.weeklyMenu.put("Tuesday","");
        this.weeklyMenu.put("Wednesday","");
        this.weeklyMenu.put("Thursday","");
        this.weeklyMenu.put("Friday","");
    }

    public void setTeacherData(String age, String address, String contactNum) {
        setAge(age);
        setAddress(address);
        setTelNum(contactNum);
    }

    public void setWeeklyMenu(HashMap<String, String> weeklyMenu) {
        this.weeklyMenu = weeklyMenu;
    }

    public void setWeeklySchedule(HashMap<String, String> weeklySchedule) {
        this.weeklySchedule = weeklySchedule;
    }

    public HashMap<String,String> getWeeklySchedule(){
        return this.weeklySchedule;
    }

    public HashMap<String,String> getWeeklyMenu(){
        return this.weeklyMenu;
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

    public String toString(){
        return this.getUsername();
    }

    public void addStudent(String child){
        this.studentList.add(child);
    }

    public static boolean isCorrectFormOfAge(String age) {
        int intAge = Integer.parseInt(age);
        return intAge > 18 && intAge < 100;
    }
}
