package com.example.fahadali.diabetesapp.Activities;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import com.example.fahadali.diabetesapp.Alarm.AlarmBroadcastReciever;
import com.example.fahadali.diabetesapp.Other.App;
import com.example.fahadali.diabetesapp.R;

import java.util.Calendar;

public class AddReminderActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, CompoundButton.OnCheckedChangeListener {


    private TimePicker timePicker;
    private ImageView add_BTN;
    private View date, time, repeat, type, note, priority;
    private View parentLayout;
    Calendar c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);
        parentLayout = findViewById(android.R.id.content);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Tilføj påmindelse");

        date = findViewById(R.id.date_rel);
        time = findViewById(R.id.time_rel);
        repeat = findViewById(R.id.repeat_rel);
        type = findViewById(R.id.type_rel);
        note = findViewById(R.id.note_rel);
        priority = findViewById(R.id.prio_rel);
        c = Calendar.getInstance();

        date.setOnClickListener(this);
        time.setOnClickListener(this);
        repeat.setOnClickListener(this);
        type.setOnClickListener(this);
        note.setOnClickListener(this);
        priority.setOnClickListener(this);





//        timePicker = findViewById(R.id.time_picker_rem);
//        add_BTN = findViewById(R.id.done_BTN);
//        cancel_BTN = findViewById(R.id.cancel_BTN);
//
//        add_BTN.setText("Tilføj");
//        add_BTN.setOnClickListener(this);
//        cancel_BTN.setText("Annuller");
//        cancel_BTN.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        if( v == date){

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, AddReminderActivity.this, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();

        }
        else if( v == time){

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, this ,c.get(Calendar.HOUR), c.get(Calendar.MINUTE), true );
            timePickerDialog.show();



        }
        else if( v == repeat){

            repeatDialog().show();

        }
        else if( v == type){

        }
        else if ( v == note){

        }
        else if ( v == priority){

        }


//        if(view == Button b){
//
//            Calendar calendar = Calendar.getInstance();
//
//            if (Build.VERSION.SDK_INT > 21){
//
//                calendar.set(
//                        calendar.get(Calendar.YEAR),
//                        calendar.get(Calendar.MONTH),
//                        calendar.get(Calendar.DAY_OF_MONTH),
//                        timePicker.getHour(),
//                        timePicker.getMinute(),
//                        0);
//            } else {
//
//                calendar.set(
//                        calendar.get(Calendar.YEAR),
//                        calendar.get(Calendar.MONTH),
//                        calendar.get(Calendar.DAY_OF_MONTH),
//                        timePicker.getCurrentHour(),
//                        timePicker.getCurrentMinute(),
//                        0
//                );
//            }
//
//            setAlarm(calendar.getTimeInMillis());
//            finish();
//
//        } else if (view == cancel_BTN){
//            finish();
//        }


    }

    private void setAlarm(long timeMillis) {

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, AlarmBroadcastReciever.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT); //Could be zero - update so that the Alarms can be edited

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeMillis, AlarmManager.INTERVAL_DAY, pendingIntent); //Repeatable

        Snackbar.make(parentLayout, "Alarm oprettet", Snackbar.LENGTH_LONG).show();

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {

    }



    private AlertDialog forgotLogin(){



        AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddReminderActivity.this);

        View mView = getLayoutInflater().inflate(R.layout.forgot_login, null);

        final EditText mEmail = mView.findViewById(R.id.email_dialog);


        mBuilder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        mBuilder.setNegativeButton("Annuller", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        mBuilder.setView(mView);

        final AlertDialog dialog = mBuilder.create();


        return dialog;
    }




    private AlertDialog chooseDaysDialog(){

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddReminderActivity.this);
        mBuilder.setTitle("Vælg dage");
        View mView = getLayoutInflater().inflate(R.layout.choose_days_dialog, null);


        final CheckBox mon = findViewById(R.id.mon_rep);
        final CheckBox tue = findViewById(R.id.tue_rep);
        final CheckBox wed = findViewById(R.id.wed_rep);
        final CheckBox thu = findViewById(R.id.thu_rep);
        final CheckBox fri = findViewById(R.id.fri_rep);
        final CheckBox sat = findViewById(R.id.sat_rep);
        final CheckBox sun = findViewById(R.id.sun_rep);
        mon.setOnCheckedChangeListener(this);
        tue.setOnCheckedChangeListener(this);
        wed.setOnCheckedChangeListener(this);
        thu.setOnCheckedChangeListener(this);
        fri.setOnCheckedChangeListener(this);
        sat.setOnCheckedChangeListener(this);
        sun.setOnCheckedChangeListener(this);

        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        mBuilder.setNegativeButton("Annuller", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();


        return dialog;
    }




    private AlertDialog repeatDialog(){




        AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddReminderActivity.this);
        mBuilder.setTitle("Gentag");
        View mView = getLayoutInflater().inflate(R.layout.repeat_dialog, null);
        final RadioGroup rg = mView.findViewById(R.id.rg_rep);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if(i == R.id.once_rep) {

                } else if(i == R.id.hourly_rep) {

                } else if (i == R.id.daily_rep){

                } else if(i == R.id.on_days_rep) {




                }

            }
        });



        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {





            }
        });

        mBuilder.setNegativeButton("Annuller", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();


        return dialog;
    }






    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        switch(compoundButton.getId()){
            case  R.id.mon_rep:
                if(b){

                }
                break;
            case  R.id.tue_rep:
                if(b){

                }
                break;
            case  R.id.wed_rep:
                if(b){

                }
                break;
            case  R.id.thu_rep:
                if(b){

                }
                break;
            case  R.id.fri_rep:
                if(b){

                }
                break;
            case  R.id.sat_rep:
                if(b){

                }
                break;
            case  R.id.sun_rep:
                if(b){

                }
                break;

        }
    }
}
