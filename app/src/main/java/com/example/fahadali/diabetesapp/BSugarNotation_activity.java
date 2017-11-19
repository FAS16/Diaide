package com.example.fahadali.diabetesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BSugarNotation_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bsugar_notation);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.BSnotation_frag_container,new BSugarNotation_frag())
                    .commit();
        }
    }
}
