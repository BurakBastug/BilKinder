package com.burakb.firstproject;

import android.os.Build;

import java.time.LocalDateTime;

public class Message implements Comparable {
    private String senderMail;
    private String receiverMail;
    private String message;
    private String dateTime;

    public Message(){

    }

    public Message(String senderMail,String receiverMail ,String message){
        this.senderMail = senderMail;
        this.message = message;
        this.receiverMail = receiverMail;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.dateTime = LocalDateTime.now().toString();
        }

    }

    public String getReceiverMail() {
        return receiverMail;
    }

    public void setReceiverUid(String receiverMail) {
        this.receiverMail = receiverMail;
    }

    public Message (String message){
        this.message=message;
    }

    public String getSenderMail(){
        return this.senderMail;
    }

    public String getMessage(){
        return this.message;
    }

    public String getDateTime(){
        return this.dateTime;
    }

    public void setSenderUid(String senderMail) { this.senderMail = senderMail; }

    public void setMessage(String message){this.message = message;}

    public void setDateTime(String dateTime){this.dateTime = dateTime;}

    @Override
    public int compareTo(Object o) {
        if(o instanceof Message){
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                LocalDateTime current = LocalDateTime.parse(this.dateTime);
                LocalDateTime oL = LocalDateTime.parse(((Message)o).getDateTime());
                o = (Message) o;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if(current.isBefore(oL)){
                        return -1;
                    }
                    else{
                        return 1;
                    }
                }
            }

        }
        return 0;
    }
}
