package com.example.fahadali.diabetesapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class BSugarOverview_activity extends Fragment implements View.OnClickListener, Observer {

    /**
     * Variables for the BsugarOverview activity
     */

    private View view;
    private FloatingActionButton addBloodSugar_BTN;
    private FirebaseDatabase db;
    private FirebaseAuth fireBaseAuth;
    private FirebaseUser fireBaseUser;
    private DatabaseReference ref;
    private BloodSugarAdapter adapter;


    @Override
    public void onResume() {
        super.onResume();
        update();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_bsugar_overview,container,false);
        addBloodSugar_BTN = view.findViewById(R.id.addBloodSugar_BTN);
        db = FirebaseDatabase.getInstance();
        fireBaseAuth = FirebaseAuth.getInstance();
        fireBaseUser = fireBaseAuth.getCurrentUser();
        ref = db.getReference();
        adapter = new BloodSugarAdapter(getActivity(), User.getUserInstance().getBloodList());

        addBloodSugar_BTN.setOnClickListener(this);
        User.getUserInstance().registerObserver(this);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        User.getUserInstance().removeObserver(this);
    }

    /**
     * Method for what the acitivity does when it is clicked.
     * @param v
     */

    @Override
    public void onClick(View v) {
        if(v == addBloodSugar_BTN){

            Intent intent = new Intent(getActivity(), BSugarNotation_activity.class);
            startActivity(intent);

        }

    }

    @Override
    public void update() {

        BloodSugarAdapter adapter = new BloodSugarAdapter(getActivity(), User.getUserInstance().getBloodList());
        ListView overview = view.findViewById(R.id.BloodSugar_LV);
        ref.child("users").child(fireBaseUser.getUid()).child("bloodList").setValue(User.getUserInstance().getBloodList());
        overview.setAdapter(adapter);

    }
}
