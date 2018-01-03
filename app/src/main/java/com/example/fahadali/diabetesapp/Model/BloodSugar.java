package com.example.fahadali.diabetesapp.Model;

import java.sql.Date;
import java.text.SimpleDateFormat;


public class BloodSugar {
    /**
     * Variables for the Bloodsugar class
     */
    private double bloodSugar;
    private String comment;
    private String time;

    //   Date currentTime = (Date) Calendar.getInstance().getTime();


    /**
     * Constructor for BloodSugar
     *
     * @param bloodSugar
     * @param comment
     */

    public BloodSugar(double bloodSugar, String comment) {
        this.bloodSugar = bloodSugar;
        this.comment = comment;
        time = new SimpleDateFormat("dd/MM - HH:mm").format(new Date(System.currentTimeMillis()));
    }

    public  BloodSugar(){

    }

    public double getBloodSugar() {
        return bloodSugar;
    }

    public void setBloodSugar(double bloodSugar) {
        this.bloodSugar = bloodSugar;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}