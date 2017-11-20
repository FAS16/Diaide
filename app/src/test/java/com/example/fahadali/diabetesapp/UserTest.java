package com.example.fahadali.diabetesapp;

import org.junit.Test;

import static org.junit.Assert.*;
/**
 * Created by aleks on 19-11-2017.
 */ 

public class UserTest {
    @Test
    public void createUserTest(){

        User user = new User("1", "Martin", "martindahljen@gmail.com", "qwerty12");
        assertEquals("Martin", user.getName());
        assertEquals("martindahljen@gmail.com", user.getMail());
        assertEquals("qwerty12", user.getPW());
        assertEquals("1", user.getID());

        user.setName("Aleksander");
        user.setMail("awie@gmail.com");
        user.setPW("qwerty21");

        assertEquals("Aleksander", user.getName());
        assertEquals("awie@gmail.com", user.getMail());
        assertEquals("qwerty21", user.getPW());

        }


    }
