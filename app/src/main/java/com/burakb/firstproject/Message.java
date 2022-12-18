package com.burakb.firstproject;

public class Message {
    private String senderUid;
    private String message;
    private String dateTime;

    public Message(){

    }

    public Message(String senderUid, String message, String dateTime){
        this.senderUid = senderUid;
        this.message = message;
        this.dateTime = dateTime;
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
