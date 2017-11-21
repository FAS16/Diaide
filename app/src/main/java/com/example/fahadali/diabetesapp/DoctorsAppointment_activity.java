package com.example.fahadali.diabetesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class DoctorsAppointment_activity extends AppCompatActivity {

    Button cancel_BTN, add_BTN;

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
