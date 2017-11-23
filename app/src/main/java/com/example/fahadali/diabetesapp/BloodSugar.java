package com.example.fahadali.diabetesapp;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by emiljorgensen on 21/11/2017.
 */

public class BloodSugar {
    double BloodSugar;
    String comment;
    String timeString;
    String formatted = new SimpleDateFormat("HH:mm dd-MM").format(new Date(System.currentTimeMillis()));

 //   Date currentTime = (Date) Calendar.getInstance().getTime();

    static ArrayList<BloodSugar> testArray = new ArrayList<>();

    public BloodSugar(double bloodSugar, String comment, String Time) {
        BloodSugar = bloodSugar;
        this.comment = comment;
        timeString = formatted;
    }

    public double getBloodSugar() {
        return BloodSugar;
    }

    public String getComment() {
        return comment;
    }

    public String getTime() {
        return timeString;
    }

    public void setBloodSugar(int bloodSugar) {
        BloodSugar = bloodSugar;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setTimestamp(String Time) {
        this.timeString = Time;
    }

    public static void createList(){

        testArray.add(new BloodSugar(12.2,"Spiste bananer","21/11 09:20"));
        testArray.add(new BloodSugar(13.5,"","21/11 18:15"));
        testArray.add(new BloodSugar(13.0,"Drak juice","22/11 08:10"));
        testArray.add(new BloodSugar(14.8,"","22/11 19:12"));

    }

    public static ArrayList getList(){

        if(testArray.size()==0){
        createList();}

        return testArray;

    }

}
