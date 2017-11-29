package com.example.fahadali.diabetesapp;

import com.example.fahadali.diabetesapp.Model.User;
import com.google.firebase.auth.FirebaseUser;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {
    @Test
    public void createUserTest(){

        User user = new User("1", "Martin", "martindahljen@gmail.com", "qwerty12");
        assertEquals("Martin", user.getName());
        assertEquals("martindahljen@gmail.com", user.getMail());
        assertEquals("qwerty12", user.getPassword());
        assertEquals("1", user.getId());

        user.setName("Aleksander");
        user.setMail("awie@gmail.com");
        user.setPassword("qwerty21");

        assertEquals("Aleksander", user.getName());
        assertEquals("awie@gmail.com", user.getMail());
        assertEquals("qwerty21", user.getPassword());

        }
    @Test
    public void createUserDBTest(){

        SignUp_activity testSignup = new SignUp_activity();

        testSignup.createUserAccount("test@email.com", "test123");


        FirebaseUser userTest = testSignup.getCurrentUser();

        assertEquals("test@email.com", userTest.getEmail());

        }


    }
