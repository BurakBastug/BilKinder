package com.burakb.firstproject;

public class Message {
    private String receiverUid;
    private String senderUid;
    private String message;
    private String dateTime;

    public Message(){

    }

    public Message(String receiverUid, String senderUid, String message, String dateTime){
        this.receiverUid = receiverUid;
        this.senderUid = senderUid;
        this.message = message;
        this.dateTime = dateTime;
    }

    public String getReceiverUid(){
        return this.receiverUid;
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

    public void setReceiverUid(String receiverUid) { this.receiverUid = receiverUid; }

    public void setSenderUid(String senderUid) { this.senderUid = senderUid; }

    public void setMessage(String message){this.message = message;}

    public void setDateTime(String dateTime){this.dateTime = dateTime;}

}
