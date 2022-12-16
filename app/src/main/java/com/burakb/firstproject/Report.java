package com.burakb.firstproject;

import android.os.Build;

import java.time.LocalDate;

public class Report {
    private String description;
    private String userMail;
    private String date;
    static int count = 0;

    public Report(){

    }
    public Report(String description, String userMail){
        this.description = description;
        this.userMail = userMail;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.date = LocalDate.now().toString();
        }
        count++;
    }



    public String getDescription() {
        return description;
    }

    public String getUserMail() {
        return userMail;
    }

    public static int getCount() {
        return count;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }
}
