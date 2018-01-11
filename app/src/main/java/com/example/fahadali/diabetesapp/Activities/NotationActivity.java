
package com.example.fahadali.diabetesapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.fahadali.diabetesapp.Model.BloodSugar;
import com.example.fahadali.diabetesapp.Model.User;
import com.example.fahadali.diabetesapp.Other.App;
import com.example.fahadali.diabetesapp.R;

public class NotationActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private Button cancel_BTN, add_BTN;
    private EditText firstNum_ET, secondNum_ET, thirdNum_ET, comment_ET, comma_ET;

    /**
     * Method for letting the activity know what to do when created.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bsugar_notation);
        setTitle("Tilføj blodsukker");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        add_BTN = findViewById(R.id.done_BTN);
        cancel_BTN = findViewById(R.id.cancel_BTN);
        firstNum_ET = findViewById(R.id.firstNum_ET);
        secondNum_ET = findViewById(R.id.secondNum_ET);
        thirdNum_ET = findViewById(R.id.thirdNum_ET);
        comma_ET = findViewById(R.id.comma_ET);
        comment_ET = findViewById(R.id.comment_ET);


        add_BTN.setText("Tilføj");
        add_BTN.setOnClickListener(this);
        cancel_BTN.setText("Annuller");
        cancel_BTN.setOnClickListener(this);
        firstNum_ET.requestFocus();
        firstNum_ET.addTextChangedListener(this);
        secondNum_ET.addTextChangedListener(this);
        thirdNum_ET.addTextChangedListener(this);
        comma_ET.setEnabled(false);

    }

    /**
     * Method for letting the fragment know what to do when clicked.
     * @param v
     */

    @Override
    public void onClick(View v) {
        if(v == add_BTN){

            double tempBloodSugar = Double.parseDouble(firstNum_ET.getText().toString()+secondNum_ET.getText().toString()+ "."+thirdNum_ET.getText().toString());
            String tempComment = comment_ET.getText().toString();
            User.getUserInstance().addBloodSugarNotation(new BloodSugar(tempBloodSugar, tempComment));
            App.shortToast(this, "Blodsukker tilføjet");
            finish();

        }
        if(v == cancel_BTN){

            finish();

        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
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

    /**
     * Directs the cursor to next input field, when current input field gets filled
     */
    public void focusHandler() {

        int maxLength = 1;
        comma_ET.setTextColor(getResources().getColor(R.color.hintColor));
        if (firstNum_ET.getText().toString().length() == maxLength) {
            secondNum_ET.requestFocus();
            if (secondNum_ET.getText().toString().length() == maxLength) {
                thirdNum_ET.requestFocus();
                if (thirdNum_ET.getText().toString().length() == maxLength) {
                    comma_ET.setHintTextColor(getResources().getColor(R.color.TextOnWhite));
                    comment_ET.requestFocus();

                }
            }
        }
    }
}
