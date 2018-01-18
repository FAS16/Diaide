package com.example.fahadali.diabetesapp.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fahadali.diabetesapp.Activities.AddReminderActivity;
import com.example.fahadali.diabetesapp.Adapters.MeasurementAdapter;
import com.example.fahadali.diabetesapp.Adapters.ReminderAdapter;
import com.example.fahadali.diabetesapp.Model.Reminder;
import com.example.fahadali.diabetesapp.Model.User;
import com.example.fahadali.diabetesapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Observer;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReminderFragment extends Fragment implements View.OnClickListener, com.example.fahadali.diabetesapp.Model.ObserverPattern.Observer {

    private List<Reminder> reminderList;
    private RecyclerView recyclerView;
    private ReminderAdapter adapter;
    private FloatingActionButton addReminder_FAB;

    private FirebaseDatabase db;
    private FirebaseAuth fireBaseAuth;
    private FirebaseUser fireBaseUser;
    private DatabaseReference ref;
    private TextView noReminders_IV;
    private boolean editMode;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reminder, container, false);
        User.getUserInstance().registerObserver(this);

        noReminders_IV = view.findViewById(R.id.noReminders_TV);
        if(!User.getUserInstance().getMeasurements().isEmpty()) {
            noReminders_IV.setVisibility(View.INVISIBLE);
        }

        reminderList = User.getUserInstance().getReminders();
        recyclerView = view.findViewById(R.id.reminder_recyclerview);
        recyclerView.setHasFixedSize(true); //TEST
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ReminderAdapter(getActivity(),reminderList);
        recyclerView.setAdapter(adapter);
        db = FirebaseDatabase.getInstance();
        fireBaseAuth = FirebaseAuth.getInstance();
        fireBaseUser = fireBaseAuth.getCurrentUser();
        ref = db.getReference();
        editMode = false;

        addReminder_FAB = view.findViewById(R.id.addReminder_FAB);
        addReminder_FAB.setOnClickListener(this);


        update();
        User.getUserInstance().registerObserver(this);

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        update();
       editMode = false;

        if(!User.getUserInstance().getMeasurements().isEmpty()) {
            noReminders_IV.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        User.getUserInstance().removeObserver(this);

    }



    @Override
    public void onClick(View view) {

        if(view == addReminder_FAB){

            Intent i = new Intent(getActivity(), AddReminderActivity.class);
            startActivity(i);



        }

    }

    @Override
    public void update() {

        adapter = new ReminderAdapter(getActivity(), reminderList);
        recyclerView = view.findViewById(R.id.reminder_recyclerview);
        ref.child("users").child(fireBaseUser.getUid()).child("reminders").setValue(User.getUserInstance().getReminders());
        if(User.getUserInstance().getMeasurements().isEmpty()) noReminders_IV.setVisibility(View.VISIBLE);
    }
}
