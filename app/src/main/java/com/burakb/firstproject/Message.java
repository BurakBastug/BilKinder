package com.burakb.firstproject;

import android.os.Build;

import java.time.LocalDate;
import java.util.Date;

public class Message {
    private Child child;
    private Teacher teacher;
    private LocalDate messageDate;
    private String content;

    public Message(){

    }
    public Message(Child child, Teacher teacher, String cont){
        this.child = child;
        this.teacher = teacher;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.messageDate = LocalDate.now();
        }
        this.content = cont;

    }

    public Child getChild(){
        return this.child;
    }
    public Teacher getTeacher(){
        return this.teacher;
    }
    public LocalDate getDate(){
        return this.messageDate;
    }

    public String getContent(){
        return this.content;
    }
    public void setContent(String message){
        this.content = message;
    }

}
