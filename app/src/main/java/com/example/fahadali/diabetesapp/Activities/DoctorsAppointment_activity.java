package com.example.fahadali.diabetesapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.example.fahadali.diabetesapp.R;

public class DoctorsAppointment_activity extends AppCompatActivity {
    /**
     * Variables for the DoctorsAppointment activity
     */
    Button cancel_BTN, add_BTN;

    /**
     * On create method, for letting the activity know what to do when created.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_appointment);

        cancel_BTN = findViewById(R.id.cancel_BTN);
        cancel_BTN.setText("ANNULLER");

        add_BTN = findViewById(R.id.done_BTN);
        add_BTN.setText("NÆSTE");


    }
}
