package com.example.fahadali.diabetesapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fahadali.diabetesapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BloodSugarFragment extends Fragment {


    public BloodSugarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_blood_sugar, container, false);
        addGraph();


        CardView card = view.findViewById(R.id.højesteMåling_CV);




        return view;
    }


    public  void addGraph(){
    //Nested fragment
        getChildFragmentManager().beginTransaction()
                .add(R.id.graph_container, new GraphFragment3())
                .commit();

    }

}
