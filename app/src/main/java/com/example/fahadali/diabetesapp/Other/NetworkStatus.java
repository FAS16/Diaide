package com.example.fahadali.diabetesapp.Other;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fahadali on 16/01/2018.
 */

public class NetworkStatus extends BroadcastReceiver {
    private final String TAG = "NetworkStatus";

    public enum Status {
        WIFI, CELLULAR, NONE
    }

    public Status status;
    public List<Runnable> observatører = new ArrayList<Runnable>();

    public boolean isOnline() {
        return status != Status.NONE;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        NetworkInfo networkInfo = App.connectivityManager.getActiveNetworkInfo();

        Status newStatus;

        if (networkInfo == null || !networkInfo.isConnected()) {
            newStatus = Status.NONE;
        } else if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            newStatus = Status.WIFI;
        } else {
            newStatus = Status.CELLULAR;
        }

        if (status != newStatus) {
            status = newStatus;
            Log.d(TAG,"Netvaerksstatus\n" + intent + "\n" + networkInfo);
            for (Runnable o : new ArrayList<Runnable>(observatører)) o.run(); //Hvad gør jacob her?
        }
    }
}