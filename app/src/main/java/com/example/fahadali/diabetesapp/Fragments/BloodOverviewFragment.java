package com.example.fahadali.diabetesapp.Fragments;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.androidplot.xy.XYPlot;
import com.example.fahadali.diabetesapp.Activities.NotationActivity;
import com.example.fahadali.diabetesapp.Adapters.BloodSugarAdapter;
import com.example.fahadali.diabetesapp.Model.ObserverPattern.Observer;
import com.example.fahadali.diabetesapp.Model.User;
import com.example.fahadali.diabetesapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BloodOverviewFragment extends Fragment implements View.OnClickListener, Observer {

    /**
     * Variables for the BsugarOverview
     */

    private View view;
    private FloatingActionButton addBloodSugar_BTN;
    private FirebaseDatabase db;
    private FirebaseAuth fireBaseAuth;
    private FirebaseUser fireBaseUser;
    private DatabaseReference ref;
    private BloodSugarAdapter adapter;
    private XYPlot xyPlot;


    @Override
    public void onResume() {
        super.onResume();
        update();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_bsugar_overview,container,false);
        addBloodSugar_BTN = view.findViewById(R.id.addBloodSugar_BTN);
        db = FirebaseDatabase.getInstance();
        fireBaseAuth = FirebaseAuth.getInstance();
        fireBaseUser = fireBaseAuth.getCurrentUser();
        ref = db.getReference();
        adapter = new BloodSugarAdapter(getActivity(), User.getUserInstance().getBloodList());
        xyPlot = view.findViewById(R.id.plot);

        addBloodSugar_BTN.setOnClickListener(this);
        User.getUserInstance().registerObserver(this);




//        XYSeries series1 = new SimpleXYSeries(

//                User.getUserInstance().getSortedBloodSugars(), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series1");

//        LineAndPointFormatter series1Format = new LineAndPointFormatter(Color.RED, Color.GREEN, Color.BLUE, null);

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

            Intent intent = new Intent(getActivity(), NotationActivity.class);
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
