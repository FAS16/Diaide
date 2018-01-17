package com.example.fahadali.diabetesapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.fahadali.diabetesapp.Model.User;
import com.example.fahadali.diabetesapp.Model.MedicineCard;
import com.example.fahadali.diabetesapp.R;

public class MedicineCardAddActivity extends AppCompatActivity implements View.OnClickListener {

    EditText medicineName_ET, medicineEffect_ET, medicineSideEffect_ET, other_ET;
    Button done_BTN, cancel_BTN;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_card_add);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        medicineName_ET = findViewById(R.id.medicinName_ET);
        medicineEffect_ET = findViewById(R.id.commentNameEffect_ET);
        medicineSideEffect_ET = findViewById(R.id.commentNameSideEffect_ET);
        other_ET = findViewById(R.id.commentNameOther_ET);
        done_BTN = findViewById(R.id.done_BTN);
        cancel_BTN = findViewById(R.id.cancel_BTN);

        done_BTN.setText("Tilføj");
        done_BTN.setOnClickListener(this);
        cancel_BTN.setText("Annuller");
        cancel_BTN.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == done_BTN) {
            User.getUserInstance().addMedicineCard(new MedicineCard(medicineName_ET.getText().toString(), medicineEffect_ET.getText().toString(), medicineSideEffect_ET.getText().toString(), other_ET.getText().toString()));
            finish();
        }
        if (v == cancel_BTN){
            finish();
        }

    }

}
