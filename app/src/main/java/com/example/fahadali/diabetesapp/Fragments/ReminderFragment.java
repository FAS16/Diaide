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

import com.example.fahadali.diabetesapp.Activities.AddReminderActivity;
import com.example.fahadali.diabetesapp.Adapters.ReminderAdapter;
import com.example.fahadali.diabetesapp.Model.Reminder;
import com.example.fahadali.diabetesapp.Model.User;
import com.example.fahadali.diabetesapp.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReminderFragment extends Fragment implements View.OnClickListener {

    private List<Reminder> reminderList;
    private RecyclerView recyclerView;
    private ReminderAdapter adapter;
    private FloatingActionButton addReminder_FAB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reminder, container, false);

//        User.getUserInstance().getReminders().add(new Reminder("16/01-2017", "15:30", "Lægeaftale", "once", "high"));
//        User.getUserInstance().getReminders().add(new Reminder("16/01-2017", "15:30", "Lægeaftale", "once", "high"));
//        User.getUserInstance().getReminders().add(new Reminder("16/01-2017", "15:30", "Lægeaftale", "once", "high"));
//        User.getUserInstance().getReminders().add(new Reminder("16/01-2017", "15:30", "Lægeaftale", "once", "high"));
//        User.getUserInstance().getReminders().add(new Reminder("16/01-2017", "15:30", "Lægeaftale", "once", "high"));

        reminderList = User.getUserInstance().getReminders();
        recyclerView = view.findViewById(R.id.reminder_recyclerview);
        recyclerView.setHasFixedSize(true); //TEST
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ReminderAdapter(getActivity(),reminderList);
        recyclerView.setAdapter(adapter);

        addReminder_FAB = view.findViewById(R.id.addReminder_FAB);
        addReminder_FAB.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View view) {

        if(view == addReminder_FAB){

            Intent i = new Intent(getActivity(), AddReminderActivity.class);
            startActivity(i);



        }

    }
}
