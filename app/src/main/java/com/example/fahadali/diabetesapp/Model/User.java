package com.example.fahadali.diabetesapp.Model;

//User singleton

public class User {

    private static User userInstance;
    String id;
    String firstName;
    String lastName;
    String mail;
    String password;

    //Empty constructor for google firebase
    public User(){

    }

    public User(String id, String firstName, String lastName, String mail){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
    }

    public static User getUserInstance(){
        if (null == userInstance) {
            userInstance = new User();
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

        return mail;
    }

    public void setMail(String mail) {

        this.mail = mail;
    }

    public String getPassword() {

        return password;
    }

}
