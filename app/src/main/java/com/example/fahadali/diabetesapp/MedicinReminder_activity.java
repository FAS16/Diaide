package com.example.fahadali.diabetesapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;


public class MedicinReminder_activity extends AppCompatActivity implements View.OnClickListener {
    /**
     * Variables for the Medicin Reminder activity
     */

    Button next_BTN = (Button) findViewById(R.id.done_BTN);
    Button cancel_BTN = (Button) findViewById(R.id.cancel_BTN);
    Fragment step1 = new MedicinReminder_frag();


    /**
     * On create method, for letting the activity know what to do, on create.
     *
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicin_reminder);
        next_BTN.setOnClickListener(this);
        cancel_BTN.setOnClickListener(this);

    }

    /**
     * Method for letting the activity know what to do when clicked.
     *
     * @param view
     */
    @Override
    public void onClick(View view) {

    }
}
