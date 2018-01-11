package com.example.fahadali.diabetesapp.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.fahadali.diabetesapp.Other.App;
import com.example.fahadali.diabetesapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BloodSugarFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {


    private View view;
    private Spinner filterSpinner;
    private ArrayAdapter <CharSequence> filterAdapter;
    private TextView lowestBloodSugar, highestBloodSugar;
    private Button go_BTN;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_blood_sugar_2, container, false);
        addGraph();

        filterSpinner = view.findViewById(R.id.filter_SPNR);
        filterAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.blood_sugar_filters, android.R.layout.simple_spinner_dropdown_item);
////        lowestBloodSugar = view.findViewById(R.id.bsLowVal_TV);
////        highestBloodSugar = view.findViewById(R.id.bsHighVal_TV);
////        lowestBloodSugar = view.findViewById(R.id.bsLowVal_TV);

//        go_BTN = view.findViewById(R.id.go_BTN);
//        go_BTN.setOnClickListener(this);
////
        filterSpinner.setAdapter(filterAdapter);
        filterSpinner.setOnItemSelectedListener(this);

        return view;
    }

    public  void addGraph(){
//    Nested fragment
        getChildFragmentManager().beginTransaction()
                .add(R.id.graph_containerNew, new GraphFragment())
                .commit();

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        String itemSelected = adapterView.getItemAtPosition(i).toString();

        if(itemSelected.equals("I dag")){

            App.shortToast(getActivity(),"I dag");

        }

        else if(itemSelected.equals("Seneste 7 dage")){

            App.shortToast(getActivity(),"Seneste 7 dag");

        }

        else if(itemSelected.equals("Seneste 30 dage")){

            App.shortToast(getActivity(),"Seneste 30 dage");

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {


        Intent i = new Intent(getContext(), BloodOverviewFragment.class);

        startActivity(i);

    }
}