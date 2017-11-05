package com.example.fahadali.diabetesapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;


/**
 * Created by emiljorgensen on 02/11/2017.
 */

public class MedicinReminderActivity extends AppCompatActivity implements View.OnClickListener{

    int reminderState = 0;
    Button next_BTN = (Button) findViewById(R.id.next_BTN);
    Button cancel_BTN = (Button) findViewById(R.id.cancel_BTN);
    Fragment step1 = new MedicinReminderStep1Frag();
    Fragment step2 = new MedicinReminderStep2Frag();
    Fragment step3 = new MedicinReminderStep3Frag();
    Fragment step4 = new MedicinReminderStep4Frag();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicin_reminder);
        next_BTN.setOnClickListener(this);
        cancel_BTN.setOnClickListener(this);

        if (savedInstanceState == null) {


            getSupportFragmentManager().beginTransaction()
                    .add(R.id.medicinReminder_FRM, step1)
                    .commit();

        }
    }

    public void update(){
               switch (reminderState){
                   case 1: {
                       getSupportFragmentManager().beginTransaction()
                               .add(R.id.medicinReminder_FRM, step2)
                               .commit();
                   }
                   break;
                   case 2: {
                               getSupportFragmentManager().beginTransaction()
                                       .add(R.id.medicinReminder_FRM, step3)
                                       .commit();
                   }
                   break;
                   case 3: {
                               getSupportFragmentManager().beginTransaction()
                                         .add(R.id.medicinReminder_FRM, step4)
                                        .commit();
                   };
               }


    }

    @Override
    public void onClick(View view) {
                if(view == next_BTN){
                    reminderState++;
                    update();
                }

                if(view == next_BTN){
                    reminderState--;
                    update();
                }
    }
}



