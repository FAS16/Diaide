package com.example.fahadali.diabetesapp.Other;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.example.fahadali.diabetesapp.Login_activity;
import com.google.firebase.database.FirebaseDatabase;

public class App extends Application {

    public static App appInstance;
    public static SharedPreferences prefs;


    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("Running onCreate - Application App");

        appInstance = this;
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Firebase - enbale disk persistence - caching locally
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

    }

    public static void shortToast(Context context, String msg){

        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

    }

    public static void longToast(Context context, String msg){

        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();

    }



}