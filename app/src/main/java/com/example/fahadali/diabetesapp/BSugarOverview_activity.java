package com.example.fahadali.diabetesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.fahadali.diabetesapp.Model.BloodSugar;
import com.example.fahadali.diabetesapp.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BSugarOverview_activity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Variables for the BsugarOverview activity
     */
    Button addBloodSugar_BTN;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    FirebaseAuth fireBaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser fireBaseUser = fireBaseAuth.getCurrentUser();
    DatabaseReference ref = db.getReference();
    User user = User.getUserInstance();

    /**
     * Constructor for BsugarOverview
     */
    public BSugarOverview_activity() {

    }

    /**
     * Method for what to do when the activity resumes.
     */
    @Override
    protected void onResume() {
        super.onResume();
        BloodSugarAdapter adapter = new BloodSugarAdapter(this, user.getBsList());
        ListView overview = findViewById(R.id.BloodSugar_LV);
        ref.child("users").child(fireBaseUser.getUid()).child("bsList").setValue(user.getBsList());
        overview.setAdapter(adapter);
    }

    /**
     * Method for what to do when activity is created.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bsugar_overview);

        BloodSugarAdapter adapter = new BloodSugarAdapter(this,user.getBsList());
        addBloodSugar_BTN = findViewById(R.id.addBloodSugar_BTN);

        addBloodSugar_BTN.setOnClickListener(this);

        ListView overview = findViewById(R.id.BloodSugar_LV);
        overview.setAdapter(adapter);



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




}
