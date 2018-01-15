package com.example.fahadali.diabetesapp.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.androidplot.xy.XYPlot;
import com.example.fahadali.diabetesapp.Activities.NotationActivity;
import com.example.fahadali.diabetesapp.Adapters.BloodSugarAdapter;
import com.example.fahadali.diabetesapp.Model.Measurement;
import com.example.fahadali.diabetesapp.Model.ObserverPattern.Observer;
import com.example.fahadali.diabetesapp.Model.User;
import com.example.fahadali.diabetesapp.Other.App;
import com.example.fahadali.diabetesapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BloodOverviewFragment extends AppCompatActivity implements View.OnClickListener, Observer, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, AbsListView.OnScrollListener {

    /**
     * Variables for the BsugarOverview
     */

    private FloatingActionButton addBloodSugar_BTN;
    private FirebaseDatabase db;
    private FirebaseAuth fireBaseAuth;
    private FirebaseUser fireBaseUser;
    private DatabaseReference ref;
    private BloodSugarAdapter adapter;
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
        if(!User.getUserInstance().getBloodList().isEmpty()) noMeasurements_TV.setVisibility(View.INVISIBLE);

        editMode = false;
        addBloodSugar_BTN = findViewById(R.id.addBloodSugar_BTN);
        db = FirebaseDatabase.getInstance();
        fireBaseAuth = FirebaseAuth.getInstance();
        fireBaseUser = fireBaseAuth.getCurrentUser();
        ref = db.getReference();
        adapter = new BloodSugarAdapter(this, User.getUserInstance().getBloodList());
        update();

        addBloodSugar_BTN.setOnClickListener(this);
        overview.setOnItemClickListener(this);
        overview.setOnItemLongClickListener(this);
        overview.setOnScrollListener(this);


        User.getUserInstance().registerObserver(this);

    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        User.getUserInstance().removeObserver(this);
    }

    @Override
    public void onResume() {

        super.onResume();
        update();
        editMode = false;
        if(!User.getUserInstance().getBloodList().isEmpty()) noMeasurements_TV.setVisibility(View.INVISIBLE);

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

            Intent intent = new Intent(this, NotationActivity.class);
            startActivity(intent);

        }

    }

    @Override
    public void update() {

        BloodSugarAdapter adapter = new BloodSugarAdapter(this, User.getUserInstance().getBloodList());
        overview = findViewById(R.id.BloodSugar_LV);
        ref.child("users").child(fireBaseUser.getUid()).child("bloodList").setValue(User.getUserInstance().getBloodList());
        overview.setAdapter(adapter);
        if(User.getUserInstance().getBloodList().isEmpty()) noMeasurements_TV.setVisibility(View.VISIBLE);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        editMode = true;
        Measurement m = User.getUserInstance().getBloodList().get(i);

        Intent intent = new Intent(this, NotationActivity.class);
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
        alertDialog.setMessage("Vil du slette denne måling?");


        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Annuller", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                alertDialog.cancel();

            }
        });

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Slet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                User.getUserInstance().getBloodList().remove(pos);
                update();

            }
        });

        alertDialog.show();
        return true;
    }


    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {



    }
}
