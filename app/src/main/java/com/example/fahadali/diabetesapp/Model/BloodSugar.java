package com.example.fahadali.diabetesapp.Model;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class BloodSugar {
    /**
     * Variables for the Bloodsugar class
     */
    double bloodSugar;
    String comment;
    String timeString;
    String formatted = new SimpleDateFormat("dd/MM - HH:mm").format(new Date(System.currentTimeMillis()));

 //   Date currentTime = (Date) Calendar.getInstance().getTime();


    /**
     * Constructor for BloodSugar
     * @param bloodSugar
     * @param comment
     */
    public BloodSugar(double bloodSugar, String comment) {
        this.bloodSugar = bloodSugar;
        this.comment = comment;
        timeString = formatted;
    }

    public BloodSugar(){

    }

    /**
     * Method for getting bloodsugar
     * @return
     */
    public double getBloodSugar() {
        return bloodSugar;
    }

    /**
     * Method for getting comment
     * @return
     */
    public String getComment() {
        return comment;
    }

    /**
     * Method for getting Time
     * @return
     */
    public String getTime() {
        return timeString;
    }

    /**
     * Method for setting bloodsugar
     * @param bloodSugar
     */
    public void setBloodSugar(int bloodSugar) {
        this.bloodSugar = bloodSugar;
    }

    /**
     * Method for setting the comment
     * @param comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Method for setting a timestamp
     * @param time
     */
    public void setTimestamp(String time) {
        this.timeString = time;
    }

}
