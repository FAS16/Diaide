package com.example.fahadali.diabetesapp.Activities;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.fahadali.diabetesapp.Alarm.AlarmReciever;
import com.example.fahadali.diabetesapp.Model.Reminder;
import com.example.fahadali.diabetesapp.Model.User;
import com.example.fahadali.diabetesapp.Other.App;
import com.example.fahadali.diabetesapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddReminderActivity extends AppCompatActivity implements View.OnClickListener {

    private SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
    private final String TAG = "AddReminderActivity";
    private TimePicker timePicker;
    private ImageView add_BTN;
    private View date, time, repeat, type, note, priority;
    private View parentLayout;
    private Calendar currentTime;
    private boolean defineAlarmByDays;
    private DatePickerDialog datePicker;
    private TimePickerDialog timePickerDialog;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;
    private int mYear, mMonth, mDayOfMonth, mHour, mMinute;
    private String inputDate;
    private String inputTime;
    private ImageButton done_BTN;
    private ImageView dateIcon_IV;
    private ImageView timeIcon_IV;
    private ImageView repeatIcon_IV;
    private ImageView typeIcon_IV;
    private ImageView noteIcon_IV;
    private ImageView prioIcon_IV;
    private TextView chosenDate_TV;
    private TextView chosenTime_TV;
    private TextView chosenReapeat_TV;
    private TextView chosenType_TV;
    private TextView chosenNote_TV;
    private TextView chosenPrio_TV;
    private Reminder reminder;

    private Intent intent;
    private boolean editMode;


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
        currentTime = Calendar.getInstance();
        done_BTN = findViewById(R.id.done_BTN);
        dateIcon_IV = findViewById(R.id.dateIcon);
        timeIcon_IV = findViewById(R.id.timeIcon);
        repeatIcon_IV = findViewById(R.id.repeatIcon);
        typeIcon_IV = findViewById(R.id.typeIcon);
        noteIcon_IV = findViewById(R.id.noteIcon);
        prioIcon_IV = findViewById(R.id.prioIcon);
        chosenDate_TV = findViewById(R.id.chosenDate);
        chosenTime_TV = findViewById(R.id.chosenTime);
        chosenReapeat_TV = findViewById(R.id.chosenRepeat);
        chosenType_TV = findViewById(R.id.chosenType);
        chosenNote_TV = findViewById(R.id.chosenNote);
        chosenPrio_TV = findViewById(R.id.chosenPrio);
        reminder = new Reminder();

        chosenDate_TV.setText(sdfDate.format(currentTime.getTime()));

        date.setOnClickListener(this);
        time.setOnClickListener(this);
        repeat.setOnClickListener(this);
        type.setOnClickListener(this);
        note.setOnClickListener(this);
        priority.setOnClickListener(this);
        done_BTN.setOnClickListener(this);
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                mYear = year;
                mMonth = month;
                mDayOfMonth = day;
                inputDate = mDayOfMonth+"/"+mMonth+1+"/"+mYear;
                Log.i(TAG, "Chosen date: " + inputDate);
                doneFillingOut(dateIcon_IV, chosenDate_TV, inputDate);
                reminder.setDate(inputDate);
            }
        };

        datePicker = new DatePickerDialog(this,
                mDateSetListener,
                currentTime.get(Calendar.YEAR),
                currentTime.get(Calendar.MONTH),
                currentTime.get(Calendar.DAY_OF_MONTH));


        mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                mHour = hour;
                mMinute = minute;
                inputTime = mHour+":"+mMinute;
                Log.i(TAG, "Chosen time: " + inputTime);
                doneFillingOut(timeIcon_IV, chosenTime_TV, inputTime);
                reminder.setTime(inputTime);

            }
        };
        timePickerDialog = new TimePickerDialog(this,
                mTimeSetListener,
                currentTime.get(Calendar.HOUR),
                currentTime.get(Calendar.MINUTE),
                true);

        intent = getIntent();

        if(intent.hasExtra("editMode")){
            editMode = getIntent().getExtras().getBoolean("editMode", false);
            if(editMode) initializeEditMode();
        }
    }


    private void initializeEditMode() {

        setTitle("Rediger påmindelse");

        String type = intent.getExtras().getString("type");
        String date = intent.getExtras().getString("date");
        String time = intent.getExtras().getString("time");
        String note = intent.getExtras().getString("note");
        String repeat = intent.getExtras().getString("repeat");
        String priority = intent.getExtras().getString("priority");
        String index = intent.getExtras().getString("index");

        doneFillingOut(typeIcon_IV, chosenType_TV, type);
        doneFillingOut(dateIcon_IV, chosenDate_TV, date);
        doneFillingOut(timeIcon_IV, chosenTime_TV, time);
        doneFillingOut(noteIcon_IV, chosenNote_TV, note);
        doneFillingOut(repeatIcon_IV, chosenReapeat_TV, repeat);
        doneFillingOut(prioIcon_IV, chosenPrio_TV,priority);


        }




    private void editReminder() {



        }



    @Override
    public void onClick(View v) {

        if( v == date) datePicker.show();


        else if( v == time) timePickerDialog.show();

        else if( v == repeat) repeatDialog().show();

        else if( v == type) typeDialog().show();

        else if ( v == note) noteDialog().show();

        else if ( v == priority) prioDialog().show();

        else if(v == done_BTN){

            //If alt nødvendigt er udfyldt
            User.getUserInstance().getReminders().add(reminder);
            setAlarm();
            finish();
        }

    }



    private void setAlarm(){

        Calendar alarmDate = Calendar.getInstance();
        alarmDate.setTimeInMillis(System.currentTimeMillis());
        alarmDate.set(mYear, mMonth, mDayOfMonth,mHour,mMinute);

        App.shortToast(this, alarmDate.toString());

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT); //Could be zero - update so that the Alarms can be edited
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmDate.getTimeInMillis(), pendingIntent); //Repeatable
        App.shortToast(this, "Påmindelse oprettet");

    }



    private void setAlarm(long timeMillis) {

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT); //Could be zero - update so that the Alarms can be edited
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeMillis, AlarmManager.INTERVAL_DAY, pendingIntent); //Repeatable
        Snackbar.make(parentLayout, "Alarm oprettet", Snackbar.LENGTH_LONG).show();

    }

    private void doneFillingOut(ImageView icon, TextView txt, String out){

        txt.setTypeface(Typeface.DEFAULT_BOLD);
        icon.setColorFilter(Color.parseColor("#6AC259"));
        txt.setText(out);
    }

    private AlertDialog repeatDialog(){

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddReminderActivity.this);
        mBuilder.setTitle("Gentag");
        View mView = getLayoutInflater().inflate(R.layout.repeat_dialog, null);
        final RadioGroup rg = mView.findViewById(R.id.rg_rep);

        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int id = rg.getCheckedRadioButtonId();

                if(id == R.id.on_days_rep){
                    chooseDaysDialog().show();

                } else if(id == R.id.once_rep ){
                    reminder.setRepeat("En gang");
                    doneFillingOut(repeatIcon_IV, chosenReapeat_TV,reminder.getRepeat());

                } else if(id == R.id.hourly_rep){
                    reminder.setRepeat("Hver time");

                    doneFillingOut(repeatIcon_IV, chosenReapeat_TV, reminder.getRepeat());

                } else if(id == R.id.daily_rep){
                    reminder.setRepeat("Dagligt");
                    doneFillingOut(repeatIcon_IV, chosenReapeat_TV, reminder.getRepeat());

                }


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
        final CheckBox mon = mView.findViewById(R.id.mon_rep);
        final CheckBox tue = mView.findViewById(R.id.tue_rep);
        final CheckBox wed = mView.findViewById(R.id.wed_rep);
        final CheckBox thu = mView.findViewById(R.id.thu_rep);
        final CheckBox fri = mView.findViewById(R.id.fri_rep);
        final CheckBox sat = mView.findViewById(R.id.sat_rep);
        final CheckBox sun = mView.findViewById(R.id.sun_rep);
        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if(mon.isChecked()){

                } else if(tue.isChecked()){

                } else if(wed.isChecked()){

                } else if (thu.isChecked()){

                } else if (fri.isChecked()){

                } else if(sat.isChecked()){

                } else if(sun.isChecked()){


                } else {
                    //Nothing checked
                }

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



    private AlertDialog typeDialog(){

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddReminderActivity.this);
        mBuilder.setTitle("Vælg type");
        View mView = getLayoutInflater().inflate(R.layout.type_dialog, null);
        final RadioGroup rg = mView.findViewById(R.id.rg_type);

        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int id = rg.getCheckedRadioButtonId();

                if(id == R.id.med_type){
                    reminder.setType("Medicin");
                    doneFillingOut(typeIcon_IV, chosenType_TV, reminder.getType());

                } else if(id == R.id.doc_type ){
                    reminder.setType("Lægeaftale");
                    doneFillingOut(typeIcon_IV, chosenType_TV, reminder.getType());

                } else if(id == R.id.foot_type){
                    reminder.setType("Fodterapeut");
                    doneFillingOut(typeIcon_IV, chosenType_TV, reminder.getType());

                } else if(id == R.id.none_type){
                    reminder.setType("Anden");
                    doneFillingOut(typeIcon_IV, chosenType_TV, reminder.getType());

                }

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



    private AlertDialog noteDialog(){

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddReminderActivity.this);
        mBuilder.setTitle("Skriv en note");
        mBuilder.setCancelable(false);
        View mView = getLayoutInflater().inflate(R.layout.note_dialog, null);
        final TextInputLayout note = mView.findViewById(R.id.note_reminder);

        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                reminder.setNote(note.getEditText().getText().toString());

                if(!note.getEditText().getText().toString().isEmpty())
                doneFillingOut(noteIcon_IV, chosenNote_TV, "Tilføjet");
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
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }



    private AlertDialog prioDialog(){

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddReminderActivity.this);
        mBuilder.setTitle("Vælg prioritet");
        View mView = getLayoutInflater().inflate(R.layout.prio_dialog, null);
        final RadioGroup rg = mView.findViewById(R.id.rg_prio);

        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int id = rg.getCheckedRadioButtonId();

                if(id == R.id.none_prio){
                    chosenPrio_TV.setText(" —");

                } else if(id == R.id.low_prio ){
                    reminder.setPriority("Lav");
                    doneFillingOut(prioIcon_IV, chosenPrio_TV, reminder.getPriority());

                } else if(id == R.id.medium_prio){
                    reminder.setPriority("Mellem");
                    doneFillingOut(prioIcon_IV, chosenPrio_TV, reminder.getPriority());
                } else if(id == R.id.high_prio){
                    reminder.setPriority("Høj");
                    doneFillingOut(prioIcon_IV, chosenPrio_TV, reminder.getPriority());

                }


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

}
