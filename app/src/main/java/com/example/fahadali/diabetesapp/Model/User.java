package com.example.fahadali.diabetesapp.Model;

//User singleton

import com.example.fahadali.diabetesapp.Model.Reminders.Reminder;

import java.util.ArrayList;

public class User {


    private static User userInstance;
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private ArrayList<BloodSugar> bsList = new ArrayList<>();
    private ArrayList<Reminder> reminderList = new ArrayList<>();
    public  ArrayList <Runnable> observers = new ArrayList<>();

    //Empty constructor for google firebase
    private User(){

    }

    private User(String id, String firstName, String lastName, String mail){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = mail;

    }


    public ArrayList<BloodSugar> getBsList() {

        return bsList;
    }

    public void setBsList(ArrayList<BloodSugar> testArray) {

        bsList = testArray;
    }

    public ArrayList<Reminder> getReminderList() {
        return reminderList;
    }

    public void setReminderList(ArrayList<Reminder> reminderList) {
        this.reminderList = reminderList;
    }

    public void addBloodSugarNotation(BloodSugar bs) {

        bsList.add(bs);
    }

    public static User getUserInstance(){
        if (userInstance == null) {
            userInstance = new User();
            System.out.println("User was null XX");
        }
        return userInstance;
    }

    public void nullifyUser(){
        if(userInstance != null) {
            userInstance = null;
        }
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = id;
    }

    public String getFirstName() {

        return firstName;
    }

    public void setFirstName(String firstName) {

        this.firstName = firstName;
    }

    public String getLastName() {

        return lastName;
    }

    public void setLastName(String lastName) {

        this.lastName = lastName;
    }


    public String getMail() {

        return email;
    }

    public void setMail(String mail) {

        this.email = mail;
    }

    public void setUser(String id, String firstName, String lastName, String email, ArrayList<BloodSugar> bsList){

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.bsList = bsList;

    }

    public String toString(){
        return ("ID: "+id + " - NAVN: "+firstName + " - MAIL: "+email);
    }

}
