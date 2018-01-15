package com.example.fahadali.diabetesapp.Model;

//User singleton

import com.example.fahadali.diabetesapp.Model.ObserverPattern.Observer;
import com.example.fahadali.diabetesapp.Model.ObserverPattern.Subject;
import com.example.fahadali.diabetesapp.Model.Reminders.Reminder;
import com.example.fahadali.diabetesapp.Other.MedicineCard;

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
    private ArrayList<Measurement> bloodList = new ArrayList<>();
    private ArrayList<Reminder> reminderList = new ArrayList<>();
    private  ArrayList <Observer> observers = new ArrayList<>();
    private ArrayList <MedicineCard> medicinecardList = new ArrayList<>();

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
    private User(String id, String firstName, String lastName, String mail,  ArrayList<BloodSugar> bloodList, ArrayList<MedicineCard> medicinecardList){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = mail;
        this.bloodList = bloodList;
        this.medicinecardList = medicinecardList;

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

    public ArrayList<Measurement> getBloodList() {
        return bloodList;
    }

    public int getSizeOfList(){

        return bloodList.size();
    }

    public void setBloodList(ArrayList<Measurement> bloodList) {
        this.bloodList = bloodList;
    }


    /**
     * Method for adding bloodsugarnotation
     * @param bs
     */
    public void addBloodSugarNotation(Measurement bs) {

        bloodList.add(bs);
    }

    public ArrayList<MedicineCard> getMedicinecardList() { return medicinecardList;}
    public void setMedicinecardList(MedicineCard medicinecardList){this.medicinecardList = medicinecardList;}

    public void addMedicineCard(MedicineCard mc) {
        medicinecardList.add(mc);
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
    public String getEmail() {

        return email;
    }

    /**
     * Method for setting mail
     * @param email
     */
    public void setEmail(String email) {

        this.email = email;
    }

    /**
     * Method for setting the current user.
     * @param id
     * @param firstName
     * @param lastName
     * @param email
     * @param bloodList
     */
    public void setUser(String id, String firstName, String lastName, String email, ArrayList<Measurement> bloodList){

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.bloodList = bloodList;

    }

    public String toString() {
        return ("ID: "+id + " - NAVN: "+firstName + " - MAIL: "+email+ " - BSLIST: "+ bloodList);
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
