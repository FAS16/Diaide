package com.example.fahadali.diabetesapp.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fahadali.diabetesapp.Adapters.MeasurementAdapter;
import com.example.fahadali.diabetesapp.Model.Measurement;
import com.example.fahadali.diabetesapp.Model.ObserverPattern.Observer;
import com.example.fahadali.diabetesapp.Model.User;
import com.example.fahadali.diabetesapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;

public class MeasurementOverviewActivity extends AppCompatActivity implements View.OnClickListener, Observer, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    /**
     * Variables for the BsugarOverview
     */

    private FloatingActionButton addBloodSugar_BTN;
    private FirebaseDatabase db;
    private FirebaseAuth fireBaseAuth;
    private FirebaseUser fireBaseUser;
    private DatabaseReference ref;
    private MeasurementAdapter adapter;
    private ListView overview;
    private TextView noMeasurements_TV;
    private boolean editMode;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Blodsukkermålinger");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.fragment_bsugar_overview);

        noMeasurements_TV = findViewById(R.id.noMeasurements_TV);
        if(!User.getUserInstance().getMeasurements().isEmpty()) noMeasurements_TV.setVisibility(View.INVISIBLE);
        editMode = false;
        addBloodSugar_BTN = findViewById(R.id.addMeasurement_BTN);
        addBloodSugar_BTN.setOnClickListener(this);
        db = FirebaseDatabase.getInstance();
        fireBaseAuth = FirebaseAuth.getInstance();
        fireBaseUser = fireBaseAuth.getCurrentUser();
        ref = db.getReference();


        overview = findViewById(R.id.BloodSugar_LV);
        adapter = new MeasurementAdapter(this, User.getUserInstance().getMeasurements());
        overview.setOnItemClickListener(this);
        overview.setOnItemLongClickListener(this);

        update();
        User.getUserInstance().registerObserver(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        User.getUserInstance().removeObserver(this);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        overview.onSaveInstanceState();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void onResume() {
        super.onResume();
        update();
        editMode = false;
        if(!User.getUserInstance().getMeasurements().isEmpty()) noMeasurements_TV.setVisibility(View.INVISIBLE);

    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    /**
     * Method for what the acitivity does when it is clicked.
     * @param v
     */

    @Override
    public void onClick(View v) {
        if(v == addBloodSugar_BTN){

            Intent intent = new Intent(this, AddMeasurementActivity.class);
            startActivity(intent);

        }

    }


    private ArrayList<Measurement> descendingOrder(ArrayList<Measurement> list){

        final ArrayList <Measurement> tempList = list;

        Collections.reverse(tempList);

        System.out.println("TEMPLIST: " +tempList.toString());

        return tempList;
    }

    @Override
    public void update() {

        MeasurementAdapter adapter = new MeasurementAdapter(this, User.getUserInstance().getMeasurements());
        overview = findViewById(R.id.BloodSugar_LV);
        ref.child("users").child(fireBaseUser.getUid()).child("measurements").setValue(User.getUserInstance().getMeasurements());
        overview.setAdapter(adapter);
        if(User.getUserInstance().getMeasurements().isEmpty()) noMeasurements_TV.setVisibility(View.VISIBLE);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        editMode = true;
        Measurement m = User.getUserInstance().getMeasurements().get(i);

        Intent intent = new Intent(this, AddMeasurementActivity.class);
        intent.putExtra("editMode", editMode);
        intent.putExtra("seekbarValue", m.getBloodSugar());
        intent.putExtra("chosenTag", m.getTag());
        intent.putExtra("comment", m.getComment());
        intent.putExtra("index",i);
        startActivity(intent);


    }






    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int pos, long l) {


        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setMessage("Vil du slette denne måling? ");


        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Annuller", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                alertDialog.cancel();

            }
        });

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Slet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                User.getUserInstance().getMeasurements().remove(pos);
                update();

            }
        });

        alertDialog.show();
        return true;
    }



}
