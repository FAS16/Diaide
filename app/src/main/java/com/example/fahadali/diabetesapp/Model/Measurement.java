package com.example.fahadali.diabetesapp.Model;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;


public class Measurement {
    /**
     * Variables for the Bloodsugar class
     */
    private double bloodSugar;
    private String comment;
    private String time;
    private String tag;

    //   Date currentTime = (Date) Calendar.getInstance().getTime();


    /**
     * Constructor for Measurement
     *
     * @param bloodSugar
     * @param comment
     */

    public Measurement(double bloodSugar, String comment, String tag) {
        this.bloodSugar = bloodSugar;
        this.comment = comment;
        this.tag = tag;
        time = new SimpleDateFormat("dd/MM/yy HH:mm").format(new Date(System.currentTimeMillis()));


    }

    public Measurement(){

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


    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String toString(){

        return ("["+bloodSugar+", "+time+"]");
    }




}