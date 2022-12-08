package com.burakb.firstproject;

import java.util.ArrayList;

public class Classroom {
    private String teacherName;
    private ArrayList<Child> studentList;
    private String[][] weeklySchedule;
    private String[][] weeklyMenu;
    private ArrayList<Event> eventList;

    public Classroom(){

    }
    public Classroom(String teacherName, ArrayList<Child> studentList){
        this.teacherName = teacherName;
        this.studentList = studentList;
        createFeed();
        this.weeklyMenu = new String[7][10];
        this.weeklySchedule = new String[7][10];
    }

    public String getTeacherName(){
        return this.teacherName;
    }
    public ArrayList<Child> getStudentList(){
        return this.studentList;
    }
    public String[][] getWeeklySchedule(){
        return this.weeklySchedule;
    }
    public String[][] getWeeklyMenu(){
        return this.weeklyMenu;
    }

    public ArrayList<Event> getEventList() {
        return this.eventList;
    }

    public void setEventList(ArrayList<Event> eventList) {
        this.eventList = eventList;
    }

    public void setStudentList(ArrayList<Child> studentList) {
        this.studentList = studentList;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public void setWeeklyMenu(String[][] weeklyMenu) {
        this.weeklyMenu = weeklyMenu;
    }

    public void setWeeklySchedule(String[][] weeklySchedule) {
        this.weeklySchedule = weeklySchedule;
    }

    public String showWeeklSchedule(){
        String formatted = "";
        for(int i = 0; i < this.weeklySchedule.length; i++){
            if(i == 0){formatted += "Monday" + "\n";}
            if(i == 1){formatted += "Tuesday" + "\n";}
            if(i == 2){formatted += "Wednesday" + "\n";}
            if(i == 3){formatted += "Thursday" + "\n";}
            if(i == 4){formatted += "Friday" + "\n";}
            if(i == 5){formatted += "Saturday" + "\n";}
            if(i == 6){formatted += "Sunday" + "\n";}
            for(int j = 0; j < this.weeklySchedule[i].length; j++){
                formatted += this.weeklySchedule[i][j] + "\n";
            }
        }
        return formatted;

    }
    public String showWeeklMenu(){
        String formatted = "";
        for(int i = 0; i < this.weeklyMenu.length; i++){
            if(i == 0){formatted += "Monday" + "\n";}
            if(i == 1){formatted += "Tuesday" + "\n";}
            if(i == 2){formatted += "Wednesday" + "\n";}
            if(i == 3){formatted += "Thursday" + "\n";}
            if(i == 4){formatted += "Friday" + "\n";}
            if(i == 5){formatted += "Saturday" + "\n";}
            if(i == 6){formatted += "Sunday" + "\n";}
            for(int j = 0; j < this.weeklyMenu[i].length; j++){
                formatted += this.weeklyMenu[i][j] + "\n";
            }
        }
        return formatted;

    }

    public void createFeed(){
        this.eventList = new ArrayList<>();
    }
}
