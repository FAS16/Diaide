package com.example.fahadali.diabetesapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.fahadali.diabetesapp.Model.User;
import com.example.fahadali.diabetesapp.Other.MedicineCard;
import com.example.fahadali.diabetesapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MedicineCardAddActivity extends AppCompatActivity implements View.OnClickListener {

    EditText medicineName_ET, medicineEffect_ET, medicineSideEffect_ET, other_ET;
    Button done_BTN, cancel_BTN;
    private FirebaseDatabase db;
    private FirebaseAuth fireBaseAuth;
    private FirebaseUser fireBaseUser;
    private DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_card_add);
        db = FirebaseDatabase.getInstance();
        fireBaseAuth = FirebaseAuth.getInstance();
        fireBaseUser = fireBaseAuth.getCurrentUser();
        ref = db.getReference();
        medicineName_ET = findViewById(R.id.medicinName_ET);
        medicineEffect_ET = findViewById(R.id.medicinEffect_ET);
        medicineSideEffect_ET = findViewById(R.id.medicinSideEffect_ET);
        other_ET = findViewById(R.id.other_ET);
        done_BTN = findViewById(R.id.done_BTN);
        cancel_BTN = findViewById(R.id.cancel_BTN);


        done_BTN.setText("Tilføj");
        done_BTN.setOnClickListener(this);
        cancel_BTN.setText("Annuller");
        cancel_BTN.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        User.getUserInstance().addMedicineCard(new MedicineCard(medicineName_ET.getText().toString(), medicineEffect_ET.getText().toString(), medicineSideEffect_ET.getText().toString(), other_ET.getText().toString()));
        System.out.println(fireBaseUser.getUid());
        System.out.println("test");
        System.out.println(User.getUserInstance().getMedicinecardList().get(0).MedicineEffect);
        ref.child("users").child(fireBaseUser.getUid()).child("medicineCardList").setValue(User.getUserInstance().getMedicinecardList());
        finish();

    }

}
