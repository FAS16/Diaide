package com.example.fahadali.diabetesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ReminderTypeSelectorActivity extends AppCompatActivity implements View.OnClickListener {
    Button medicinBT, doctorAppointmentBT, otherBT;
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

    public void onClick(View v){
        if (v == medicinBT){
          //  Intent intent = new Intent(this, MedicinReminderActivity.class);
          //  startActivity(intent);
            }
        else if (v == doctorAppointmentBT){
        Intent intent = new Intent(this, DoctorsAppointmentActivity.class);
        startActivity(intent);
        }
        else if (v == otherBT){

        }

    }


}
