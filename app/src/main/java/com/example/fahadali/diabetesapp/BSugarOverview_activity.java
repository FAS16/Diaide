package com.example.fahadali.diabetesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.fahadali.diabetesapp.Model.BloodSugar;
import com.example.fahadali.diabetesapp.Model.ObserverPattern.Observer;
import com.example.fahadali.diabetesapp.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BSugarOverview_activity extends AppCompatActivity implements View.OnClickListener, Observer, Runnable {

    /**
     * Variables for the BsugarOverview activity
     */
    private Button addBloodSugar_BTN;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private FirebaseAuth fireBaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser fireBaseUser = fireBaseAuth.getCurrentUser();
    private DatabaseReference ref = db.getReference();
    private DatabaseReference db_userReference;
    private FirebaseUser firebaseUser;
    private User user = User.getUserInstance();
    private GenericTypeIndicator<ArrayList<BloodSugar>> t = new GenericTypeIndicator<ArrayList<BloodSugar>>(){};

    /**
     * Method for what to do when the activity resumes.
     */

    @Override
    protected void onResume() {
        super.onResume();
        run();
        update();

    }

    /**
     * Method for what to do when activity is created.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bsugar_overview);
        firebaseUser = fireBaseAuth.getCurrentUser();

        User.getUserInstance().registerObserver(this);

        BloodSugarAdapter adapter = new BloodSugarAdapter(this,user.getBsList());
        addBloodSugar_BTN = findViewById(R.id.addBloodSugar_BTN);

        addBloodSugar_BTN.setOnClickListener(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       // User.getUserInstance().observers.remove(this);
        User.getUserInstance().removeObserver(this);
    }

    private void setListener (){

        db_userReference = FirebaseDatabase.getInstance().getReference().child("users").child(firebaseUser.getUid()).child("bsList");


        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get User object.

//                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
//                    User.getUserInstance().addBloodSugarNotation(new BloodSugar());
//                }

                Log.d("Før","val = " + dataSnapshot.getValue());
                user.setBsList(dataSnapshot.getValue(t));

                //for(Runnable r: User.getUserInstance().observers) r.run();
                Log.d("Efter","val = " + dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                // Toast.makeText(Login_activity.this, "Fejl - initializeUser", Toast.LENGTH_SHORT).show();
            }
        };

        db_userReference.addListenerForSingleValueEvent(listener);

    }

    /**
     * Method for what the acitivity does when it is clicked.
     * @param v
     */
    @Override
    public void onClick(View v) {
        if(v == addBloodSugar_BTN){
            Intent intent = new Intent(this, BSugarNotation_activity.class);
            startActivity(intent);

        }

    }


    @Override
    public void run() {

    }

    @Override
    public void update() {

        BloodSugarAdapter adapter = new BloodSugarAdapter(this, user.getBsList());
        ListView overview = findViewById(R.id.BloodSugar_LV);
        ref.child("users").child(fireBaseUser.getUid()).child("bsList").setValue(user.getBsList());
        overview.setAdapter(adapter);

    }
}
