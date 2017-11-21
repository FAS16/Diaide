package com.example.fahadali.diabetesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BSugarOverview_activity extends AppCompatActivity implements View.OnClickListener {

    Button addBloodSugar_BTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bsugar_overview);

        addBloodSugar_BTN = (Button) findViewById(R.id.addBloodSugar_BTN);

        addBloodSugar_BTN.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == addBloodSugar_BTN){
            Intent intent = new Intent(this, BSugarNotation_activity.class);
            startActivity(intent);

        }

    }
}
