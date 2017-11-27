package com.example.fahadali.diabetesapp.Model;

public class User {

    String ID;
    String name;
    String mail;
    String password;


    public User(String ID, String name, String mail, String password){
        this.ID = ID;
        this.name = name;
        this.mail = mail;
        this.password = password;
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
