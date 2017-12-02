package com.example.fahadali.diabetesapp.Model;

import java.sql.Date;
import java.text.SimpleDateFormat;


public class BloodSugar {
    double bloodSugar;
    String comment;
    String timeString;
    String formatted = new SimpleDateFormat("dd/MM - HH:mm").format(new Date(System.currentTimeMillis()));

 //   Date currentTime = (Date) Calendar.getInstance().getTime();



    public BloodSugar(double bloodSugar, String comment) {
        this.bloodSugar = bloodSugar;
        this.comment = comment;
        timeString = formatted;
    }

    public BloodSugar(){

    }

    public double getBloodSugar() {
        return bloodSugar;
    }

    public String getComment() {
        return comment;
    }

    public String getTime() {
        return timeString;
    }

    public void setBloodSugar(double bloodSugar) {
        this.bloodSugar = bloodSugar;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setTimestamp(String time) {
        this.timeString = time;
    }

}
