package com.burakb.firstproject;

public class Event {
    private String name;
    private String description;
    private String teacherName;
    private String imageDestination;
    private String eventDestination;
    private boolean isVisible;

    public Event(){

    }

    public Event(String name, String description, String teacherName){
        this.name = name;
        this.description = description;
        this.teacherName = teacherName;
        this.imageDestination = "default_event_photo";
        this.eventDestination = "";
        this.isVisible = true;
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
}
