package com.example.fahadali.diabetesapp;


import com.example.fahadali.diabetesapp.Model.User;
import com.google.firebase.auth.FirebaseUser;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {
    @Test
    public void createUserTest() {

        User user = User.getUserInstance();
        assertEquals("Martin", user.getFirstName());
        assertEquals("martindahljen@gmail.com", user.getMail());
        assertEquals("1", user.getId());

        user.setFirstName("Aleksander");
        user.setMail("awie@gmail.com");

        assertEquals("Aleksander", user.getFirstName());
        assertEquals("awie@gmail.com", user.getMail());

    }
}

