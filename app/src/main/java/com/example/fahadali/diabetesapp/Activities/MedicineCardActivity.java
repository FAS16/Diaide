package com.example.fahadali.diabetesapp.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.fahadali.diabetesapp.Adapters.MedicineCardAdapter;
import com.example.fahadali.diabetesapp.Model.ObserverPattern.Observer;
import com.example.fahadali.diabetesapp.Model.User;
import com.example.fahadali.diabetesapp.Model.MedicineCard;
import com.example.fahadali.diabetesapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MedicineCardActivity extends AppCompatActivity implements View.OnClickListener, Observer {
    private RecyclerView rv;
    private FloatingActionButton medicineCardAdd;
    private FirebaseDatabase db;
    private FirebaseAuth fireBaseAuth;
    private FirebaseUser fireBaseUser;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_card);
        setTitle("Medicin Kort");
        db = FirebaseDatabase.getInstance();
        fireBaseAuth = FirebaseAuth.getInstance();
        fireBaseUser = fireBaseAuth.getCurrentUser();
        ref = db.getReference();
        rv= findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
        medicineCardAdd = findViewById(R.id.medicincardAdd);
        User.getUserInstance().registerObserver(this);


        initializeData();
        initializeAdapter();

        medicineCardAdd.setOnClickListener(this);
    }
    private void initializeData(){
        if(User.getUserInstance().getMedicinecardList().size() < 3) {
            User.getUserInstance().addMedicineCard(new MedicineCard("Hexaglucon", "Virkningen indtræder i løbet af 1/2 - 1 time og varer 8-12 timer", "Almindelige bivirkninger: Vægtøgning, Lavt blodsukker", "Her skrives andet"));
            User.getUserInstance().addMedicineCard(new MedicineCard("Gliclatim", "Virkningen varer ca. 24 timer", "Almindelige bivirkninger: Lavt blodsukker", "Her skrives andet"));
            User.getUserInstance().addMedicineCard(new MedicineCard("Minodiab", "Virkningen indtræder i løbet af 1/2 time og varer 4-6 timer", "Almindelige bivirkninger: Diarré, Kvalme, Mavesmerter, Lavt blodsukker", "Her skrives andet"));
        }
        }
    private void initializeAdapter(){
        MedicineCardAdapter adapter = new MedicineCardAdapter(User.getUserInstance().getMedicinecardList());
        rv.setAdapter(adapter);
    }

    public void onClick(View v){
        if (v == medicineCardAdd){
            Intent i = new Intent(this, MedicineCardAddActivity.class);
            startActivity(i);
        }
    }
    public void onResume() {
        super.onResume();
        update();
    }

    public void update(){
        ref.child("users").child(fireBaseUser.getUid()).child("medicinecardList").setValue(User.getUserInstance().getMedicinecardList());
        initializeAdapter();
    }
    @Override
    public void onDestroy() {

        super.onDestroy();
        User.getUserInstance().removeObserver(this);
    }

}
