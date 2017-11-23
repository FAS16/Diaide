package com.example.fahadali.diabetesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BSugarOverview_activity extends AppCompatActivity implements View.OnClickListener {

    Button addBloodSugar_BTN;



    FirebaseDatabase db = FirebaseDatabase.getInstance();

    DatabaseReference ref = db.getReference("test");
    DatabaseReference ref2 = db.getReference();


    public BSugarOverview_activity() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        BloodSugarAdapter adapter = new BloodSugarAdapter(this,BloodSugar.getList());
        ListView overview = findViewById(R.id.BloodSugar_LV);
        overview.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bsugar_overview);

//       BloodSugar bs = new BloodSugar(12.3, "does it work?", "today");
      // ref.setValue("Test123");
      // ref.setValue("Test124");
       ref.child("test3").setValue("???");
      // ref2.setValue(bs);


        BloodSugarAdapter adapter = new BloodSugarAdapter(this,BloodSugar.getList());
        addBloodSugar_BTN = (Button) findViewById(R.id.addBloodSugar_BTN);

        addBloodSugar_BTN.setOnClickListener(this);

        ListView overview = findViewById(R.id.BloodSugar_LV);
        overview.setAdapter(adapter);



    }

    @Override
    public void onClick(View v) {
        if(v == addBloodSugar_BTN){
            Intent intent = new Intent(this, BSugarNotation_activity.class);
            startActivity(intent);

        }

    }




}
