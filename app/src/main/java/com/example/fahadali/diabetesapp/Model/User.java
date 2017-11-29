package com.example.fahadali.diabetesapp.Model;

public class User {

    private static User userInstance;
    String ID;
    String name;
    String mail;
    String password;


    public User(){

    }
    public User(String ID, String name, String mail){
        this.ID = ID;
        this.name = name;
        this.mail = mail;
    }

    public static User getUserInstance(){
        if (null == userInstance) {
            userInstance = new User();
        }
        return userInstance;
    }

    public String getID() {

        return ID;
    }

    public void setID(String ID) {

        this.ID = ID;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
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

    public void setPassword(String password) {

        this.password = password;
    }
}
