package com.example.fahadali.diabetesapp;


import com.example.fahadali.diabetesapp.Model.User;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {
    @Test
    public void createUserTest() {

        User user = User.getUserInstance();
        assertEquals("Martin", user.getFirstName());
        assertEquals("martindahljen@gmail.com", user.getEmail());
        assertEquals("1", user.getId());

        user.setFirstName("Aleksander");
        user.getEmail("awie@gmail.com");

        assertEquals("Aleksander", user.getFirstName());
        assertEquals("awie@gmail.com", user.getEmail());

    }
}

