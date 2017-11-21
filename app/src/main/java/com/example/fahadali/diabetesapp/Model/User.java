package com.example.fahadali.diabetesapp.Model;

/**
 * Created by aleks on 19-11-2017.
 */

public class User {



    String ID;
    String name;
    String mail;
    String PW;


    public User(String ID, String name, String mail, String PW){
        this.ID = ID;
        this.name = name;
        this.mail = mail;
        this.PW = PW;
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

    public String getPW() {
        return PW;
    }

    public void setPW(String PW) {
        this.PW = PW;
    }
}
