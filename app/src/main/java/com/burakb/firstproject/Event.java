package com.burakb.firstproject;

import android.os.Build;

import java.time.LocalDateTime;

public class Event implements Comparable{
    private String name;
    private String description;
    private String teacherName;
    private String imageDestination;
    private String eventDestination;
    private boolean isVisible;
    private String dateTime;

    public Event(){

    }

    public Event(String name, String description, String teacherName){
        this.name = name;
        this.description = description;
        this.teacherName = teacherName;
        this.imageDestination = "default_event_photo";
        this.eventDestination = "";
        this.isVisible = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.dateTime = LocalDateTime.now().toString();
        }
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getName(){
        return this.name;
    }

    public String getDescription(){
        return this.description;
    }

    public boolean getVisibility(){return this.isVisible;}

    public String getImageDestination() {return this.imageDestination;}

    public String getEventDestination() {return eventDestination;}

    public void setName(String name){
        this.name = name;
    }

    public void setDescription(String description){
        this.description =description;
    }

    public void setTeacherName(String teacherName) { this.teacherName = teacherName;}

    public void setVisible(boolean visible){
        this.isVisible = visible;
    }

    public void setImageDestination(String imageDestination) {this.imageDestination = imageDestination;}

    public void setEventDestination(String eventDestination) {this.eventDestination = eventDestination;}

    public String toString(){
        String formatted = "";
        formatted += "Event Name: " + this.name + "\n";
        formatted += "Event Description: " + this.description + "\n";
        return formatted;
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof Event){
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                LocalDateTime current = LocalDateTime.parse(this.dateTime);
                LocalDateTime oL = LocalDateTime.parse(((Event)o).getDateTime());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if(current.isBefore(oL)){
                        return -1;
                    }
                    else if(current.isAfter(oL)){
                        return 1;
                    }
                    else {
                        return 0;
                    }
                }
            }
        }
        return 0;
    }
}
