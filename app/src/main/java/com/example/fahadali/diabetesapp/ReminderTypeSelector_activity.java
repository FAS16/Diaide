package com.example.fahadali.diabetesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ReminderTypeSelector_activity extends AppCompatActivity implements View.OnClickListener {
    /**
     * Variables for the reminder type selector class
     */
    Button medicinBT, doctorAppointmentBT, otherBT;

    /**
     * Method for letting the activity know what to do, when it is created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_type_selector);
        medicinBT = (Button) findViewById(R.id.medicinBTN);
        doctorAppointmentBT = (Button) findViewById(R.id.doctorAppointmentBTN);
        otherBT = (Button) findViewById(R.id.otherBTN);
        medicinBT.setOnClickListener(this);
        doctorAppointmentBT.setOnClickListener(this);
        otherBT.setOnClickListener(this);
    }

    /**
     * Method for letting the activity know, what to do when clicked
     * @param v
     */
    public void onClick(View v){
        if (v == medicinBT){

            }
        else if (v == doctorAppointmentBT){
        Intent intent = new Intent(this, DoctorsAppointment_activity.class);
        startActivity(intent);
        }
        else if (v == otherBT){

        }

    }


}
