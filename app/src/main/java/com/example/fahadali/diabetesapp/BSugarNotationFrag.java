package com.example.fahadali.diabetesapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;



/**
 * A simple {@link Fragment} subclass.
 */
public class BSugarNotationFrag extends Fragment implements View.OnClickListener{

    Button cancel_BTN, add_BTN;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rod = inflater.inflate(R.layout.fragment_bsugar_notation,container,false);

        add_BTN = rod.findViewById(R.id.done_BTN);
        add_BTN.setText("Tilføj");

        cancel_BTN = rod.findViewById(R.id.cancel_BTN);
        cancel_BTN.setText("Annuller");

        add_BTN.setOnClickListener(this);
        cancel_BTN.setOnClickListener(this);

        return rod;
    }


    @Override
    public void onClick(View v) {
        if(v == add_BTN){
            // TODO: 02/11/2017: Needs backend to get Userinput and place it in BSoverview
        }
        if(v == cancel_BTN){
            getActivity().finish();
        }

    }
}
