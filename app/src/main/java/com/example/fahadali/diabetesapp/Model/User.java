package com.example.fahadali.diabetesapp.Model;

//User singleton

import com.example.fahadali.diabetesapp.Model.ObserverPattern.Observer;
import com.example.fahadali.diabetesapp.Model.ObserverPattern.Subject;
import com.example.fahadali.diabetesapp.Model.Reminders.Reminder;

import java.util.ArrayList;

public class User implements Subject {

    /**
     * field variables for the User class
     */
    private static User userInstance;
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private ArrayList<BloodSugar> bsList = new ArrayList<>();
    private ArrayList<Reminder> reminderList = new ArrayList<>();
    private  ArrayList <Observer> observers = new ArrayList<>();

    //Empty constructor for google firebase
    private User(){

    }

    /**
     * Constructor for the User class
     * @param id
     * @param firstName
     * @param lastName
     * @param mail
     */
    private User(String id, String firstName, String lastName, String mail){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = mail;

    }

    /**
     * Method for getting the user instance
     * @return
     */
    public static User getUserInstance(){
        if (userInstance == null) {
            userInstance = new User();
            System.out.println("User was null XX");
        }
        return userInstance;
    }

    /**
     * Method for getting bsList
     * @return
     */
    public ArrayList<BloodSugar> getBsList() {

        return bsList;
    }

    /**
     * Method for setting bsList
     * @param testArray
     */
    public void setBsList(ArrayList<BloodSugar> testArray) {

        bsList = testArray;
    }

    /**
     * Method for adding bloodsugarnotation
     * @param bs
     */
    public void addBloodSugarNotation(BloodSugar bs) {

        bsList.add(bs);
    }

    /**
     * Method for getting reminderslist
     * @return
     */
    public ArrayList<Reminder> getReminderList() {
        return reminderList;
    }

    /**
     * Method for setting reminderslist
     * @param reminderList
     */
    public void setReminderList(ArrayList<Reminder> reminderList) {
        this.reminderList = reminderList;
    }

    /**
     * Method for nullifying the User instance
     */
    public void nullifyUser(){
        if(userInstance != null) {
            userInstance = null;
        }
    }

    /**
     * Method for getting ID
     * @return
     */
    public String getId() {

        return id;
    }

    /**
     * Method for setting ID
     * @param id
     */
    public void setId(String id) {

        this.id = id;
    }

    /**
     * Method for getting firstname
     * @return
     */
    public String getFirstName() {

        return firstName;
    }

    /**
     * Method for setting firstname
     * @param firstName
     */
    public void setFirstName(String firstName) {

        this.firstName = firstName;
    }

    /**
     * Method for setting firstname
     * @return
     */
    public String getLastName() {

        return lastName;
    }

    /**
     * Method for setting lastname
     * @param lastName
     */
    public void setLastName(String lastName) {

        this.lastName = lastName;
    }

    /**
     * Method for getting mail
     * @return
     */
    public String getMail() {

        return email;
    }

    /**
     * Method for setting mail
     * @param mail
     */
    public void setMail(String mail) {

        this.email = mail;
    }

    /**
     * Method for setting the current user.
     * @param id
     * @param firstName
     * @param lastName
     * @param email
     * @param bsList
     */
    public void setUser(String id, String firstName, String lastName, String email, ArrayList<BloodSugar> bsList){

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.bsList = bsList;

    }

    /**
     * Method to convert to string
     * @return
     */
    public String toString(){
        return ("ID: "+id + " - NAVN: "+firstName + " - MAIL: "+email);
    }


    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);

    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);

    }

    @Override
    public void notifyAllObservers() {
        for (Observer obs: observers) {
            obs.update();
        }
    }
}
