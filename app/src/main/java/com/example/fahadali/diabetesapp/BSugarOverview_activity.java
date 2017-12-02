package com.example.fahadali.diabetesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.fahadali.diabetesapp.Model.BloodSugar;
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

public class BSugarOverview_activity extends AppCompatActivity implements View.OnClickListener, Runnable {

    Button addBloodSugar_BTN;



    FirebaseDatabase db = FirebaseDatabase.getInstance();
    FirebaseAuth fireBaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser fireBaseUser = fireBaseAuth.getCurrentUser();
    DatabaseReference ref = db.getReference();
    private DatabaseReference db_userReference;
    private FirebaseUser firebaseUser;
    User user = User.getUserInstance();
    GenericTypeIndicator<ArrayList<BloodSugar>> t = new GenericTypeIndicator<ArrayList<BloodSugar>>(){};


    @Override
    protected void onResume() {
        super.onResume();
        run();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bsugar_overview);
        firebaseUser = fireBaseAuth.getCurrentUser();

        User.getUserInstance().observers.add(this);

        BloodSugarAdapter adapter = new BloodSugarAdapter(this,user.getBsList());
        addBloodSugar_BTN = findViewById(R.id.addBloodSugar_BTN);

        addBloodSugar_BTN.setOnClickListener(this);

        //ListView overview = findViewById(R.id.BloodSugar_LV);
        //overview.setAdapter(adapter);



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        User.getUserInstance().observers.remove(this);
    }

    private void setListener (){

        db_userReference = FirebaseDatabase.getInstance().getReference().child("users").child(firebaseUser.getUid()).child("bsList");


        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get User object.
                Log.d("Før","val = " + dataSnapshot.getValue());
                user.setBsList(dataSnapshot.getValue(t));

                for(Runnable r: User.getUserInstance().observers) r.run();
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

    @Override
    public void onClick(View v) {
        if(v == addBloodSugar_BTN){
            Intent intent = new Intent(this, BSugarNotation_activity.class);
            startActivity(intent);

        }

    }


    @Override
    public void run() {
        BloodSugarAdapter adapter = new BloodSugarAdapter(this, user.getBsList());
        ListView overview = findViewById(R.id.BloodSugar_LV);
        ref.child("users").child(fireBaseUser.getUid()).child("bsList").setValue(user.getBsList());
        overview.setAdapter(adapter);
    }
}
