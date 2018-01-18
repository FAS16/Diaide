package com.example.fahadali.diabetesapp.Alarm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

import com.example.fahadali.diabetesapp.Activities.AddReminderActivity;
import com.example.fahadali.diabetesapp.R;

/**
 * Created by fahadali on 17/01/2018.
 */

public class AlarmReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) { //Will be executed whenever Alarm is fired

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent directToOnClick = new Intent(context, AddReminderActivity.class);
        directToOnClick.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //Replace even if the acitivty specified in the intent already is open og running in the background

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, directToOnClick, PendingIntent.FLAG_UPDATE_CURRENT );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentTitle("Diaide")
                .setContentText("Påmindelse")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setVibrate(new long[] { 0, 1000, 1000, 1000, 1000 })
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setAutoCancel(true);

        notificationManager.notify(100, builder.build() );


    }
}
