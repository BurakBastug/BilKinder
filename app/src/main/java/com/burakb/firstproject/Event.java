package com.burakb.firstproject;

import android.media.Image;

import java.util.Date;

public class Event {
    private String name;
    private String description;
    private Date date;
    private boolean isVisible;
    //private Image image;
    //private int img;

    public Event(){

    }


    public Event(String name, String description){
        this.name = name;
        this.description = description;
        this.isVisible = true;
    }


    public String getName(){
        return this.name;
    }
    public String getDescription(){
        return this.description;
    }
    public Date getDate(){
        return this.date;
    }
    public boolean getVisibility(){
        return this.isVisible;
    }



    public void setName(String name){
        this.name = name;
    }
    public void setDescription(String description){
        this.description =description;
    }
    public void setDate(Date date){
        this.date = date;
    }
    public void setVisible(boolean visible){
        this.isVisible = visible;
    }

    public String toString(){
        String formatted = "";
        formatted += "Event Name: " + this.name + "\n";
        formatted += "Event Description: " + this.description + "\n";
        //formatted += "Event Date: " + this.date.toString() + "\n";
        //formatted += "Event Visibility: " + this.isVisible + "\n";

        return formatted;
    }
}
