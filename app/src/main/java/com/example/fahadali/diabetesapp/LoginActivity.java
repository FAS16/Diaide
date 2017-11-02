package com.example.fahadali.diabetesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Global variables:
     * userName_ET: Username input - EditText
     * password_ET: password input - EditText
     * login_BTN: login button - Button
     */

    EditText userName_ET;
    EditText password_ET;
    Button login_BTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Hides the action bar in current activity
        getSupportActionBar().hide();

        userName_ET = (EditText) findViewById(R.id.userName_ET);
        password_ET = (EditText) findViewById(R.id.userName_ET);
        login_BTN = (Button) findViewById(R.id.login_BTN);
        login_BTN.setOnClickListener(this);

    }

    // TODO: 01/11/2017: User input validation missing
    @Override
    public void onClick(View v) {
        if(v == login_BTN){
            Intent intent = new Intent(this, HomeMenuActivity.class);
            startActivity(intent);

        }
    }
}

