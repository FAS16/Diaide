package com.example.fahadali.diabetesapp.Other;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import io.fabric.sdk.android.Fabric;

public class App extends Application {

    public static App appInstance;
    public static SharedPreferences prefs;
    private boolean EMULATOR = Build.PRODUCT.contains("sdk") || Build.MODEL.contains("Emulator");

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("Running onCreate -  Application App");


FirebaseAuth auth = FirebaseAuth.getInstance();
auth.signOut();


//        Crashlytics
//        if (!EMULATOR) {
//            Fabric.with(this, new Crashlytics());
//        }

        Fabric.with(null, null);
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