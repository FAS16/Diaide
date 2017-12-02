package com.example.fahadali.diabetesapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fahadali.diabetesapp.Model.BloodSugar;
import com.example.fahadali.diabetesapp.Model.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class BSugarNotation_frag extends Fragment implements View.OnClickListener, TextWatcher{

    Button cancel_BTN, add_BTN;
    EditText firstNum_ET, secondNum_ET, thirdNum_ET, comment_ET;
    TextView comma_TV;
    User user = User.getUserInstance();




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rod = inflater.inflate(R.layout.fragment_bsugar_notation,container,false);

        add_BTN = rod.findViewById(R.id.done_BTN);
        add_BTN.setText("Tilføj");
        add_BTN.setOnClickListener(this);
        cancel_BTN = rod.findViewById(R.id.cancel_BTN);
        cancel_BTN.setText("Annuller");
        cancel_BTN.setOnClickListener(this);

        firstNum_ET = rod.findViewById(R.id.firstNum_ET);
        firstNum_ET.requestFocus();
        firstNum_ET.addTextChangedListener(this);
        secondNum_ET = rod.findViewById(R.id.secondNum_ET);
        secondNum_ET.addTextChangedListener(this);
        thirdNum_ET = rod.findViewById(R.id.thirdNum_ET);
        thirdNum_ET.addTextChangedListener(this);

        comma_TV = rod.findViewById(R.id.comma_TV);
        comment_ET = rod.findViewById(R.id.comment_ET);


        return rod;
    }


    @Override
    public void onClick(View v) {
        if(v == add_BTN){

            user.addBloodSugarNotation(new BloodSugar(Double.parseDouble(firstNum_ET.getText().toString()+ secondNum_ET.getText().toString()+ "."+thirdNum_ET.getText().toString()), comment_ET.getText().toString()));

                 getActivity().finish();
        }
        if(v == cancel_BTN){
            getActivity().finish();
        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {


    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        focusHandler();

    }



    @Override
    public void afterTextChanged(Editable s) {

    }
    public void focusHandler(){

        int maxLength = 1;
        comma_TV.setTextColor(getResources().getColor(R.color.hintColor));
        if(firstNum_ET.getText().toString().length() == maxLength){
            secondNum_ET.requestFocus();
            if(secondNum_ET.getText().toString().length() == maxLength){
                thirdNum_ET.requestFocus();
                if(thirdNum_ET.getText().toString().length() == maxLength){
                    comma_TV.setTextColor(getResources().getColor(R.color.TextOnWhite));
                    comment_ET.requestFocus();

                }
            }
        }
    }
}
