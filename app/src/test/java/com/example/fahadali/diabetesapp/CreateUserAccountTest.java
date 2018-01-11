package com.example.fahadali.diabetesapp;

import com.example.fahadali.diabetesapp.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreateUserAccountTest {

    FirebaseAuth firebaseAuth;

    @Before
    public void setUp(){
       firebaseAuth = FirebaseAuth.getInstance();
    }



    @Test
    public void createUserAccount(){

        User user = new User("1","User","usertest@mail.dk","123456");
        createUserAccount ca = new createUserAccount();

        ca.createUserAccountMockup(user.getEmail(),user.getPassword());

        FirebaseUser fbUser = firebaseAuth.getCurrentUser();

        assertEquals(fbUser.getEmail(), user.getEmail());

    }


  public class createUserAccount {



      private void createUserAccountMockup(String email, String password){

          firebaseAuth.createUserWithEmailAndPassword(email, password);



      }

    }
}
