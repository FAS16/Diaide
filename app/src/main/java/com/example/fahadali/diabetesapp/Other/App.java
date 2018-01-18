package com.example.fahadali.diabetesapp.Other;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import io.fabric.sdk.android.Fabric;

public class App extends Application {


    private static boolean landscapeMode;
    private static boolean portraitMode;
    public static App appInstance;
    public static SharedPreferences prefs;
    private boolean EMULATOR = Build.PRODUCT.contains("sdk") || Build.MODEL.contains("Emulator");
    public static ConnectivityManager connectivityManager;
    public static NetworkStatus network;
    public static boolean dialogIsVisible;

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("Running onCreate -  Application App");

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        if (!EMULATOR) Fabric.with(this, new Crashlytics());
        appInstance = this;
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        network = new NetworkStatus();
        registerReceiver(network, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        network.onReceive(this, null);

    }


    public static boolean isLandscapeMode(Context context){

        if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) landscapeMode = true;
        else landscapeMode = false;

        return landscapeMode;
    }

    public static boolean isPortraitMode(Context context){

        if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) portraitMode = true;
        else portraitMode = false;

        return portraitMode;
    }

    public static void shortToast(Context context, String msg){

        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

    }

    public static void longToast(Context context, String msg){

        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();

    }

    public static void showNeutralDialog(String title, String msg, Context context){

        dialogIsVisible = true;
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);

        alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogIsVisible = false;
            }
        });

        alertDialog.show();
    }

    public static boolean isOnline(){
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }


}