package com.burakb.firstproject;

import android.os.Build;

import java.time.LocalDate;

public class Message {
    private String senderUid;
    private String message;
    private String dateTime;

    public Message(){

    }

    public Message(String senderUid, String message){
        this.senderUid = senderUid;
        this.message = message;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.dateTime = LocalDate.now().toString();
        }
    }
    public Message (String message){
        this.message=message;
    }

    public String getSenderUid(){
        return this.senderUid;
    }

    public String getMessage(){
        return this.message;
    }

    public String getDateTime(){
        return this.dateTime;
    }

    public void setSenderUid(String senderUid) { this.senderUid = senderUid; }

    public void setMessage(String message){this.message = message;}

    public void setDateTime(String dateTime){this.dateTime = dateTime;}

}
