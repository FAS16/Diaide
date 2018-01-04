package com.example.fahadali.diabetesapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fahadali.diabetesapp.Other.App;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login_activity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    /**
     * variables - fields:
     * email_ET: Username input - EditText
     * password_ET: password input - EditText
     * login_BTN: login button - Button
     */

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private EditText email_ET, password_ET;
    private Button login_BTN, loginFB_BTN;
    private TextView newUser_TV;
    private ProgressBar pBar;
    private CheckBox checkBox;
    private static final String TAG = "CURRENT USER";

    /**
     * Oncreate method, to tell the program what to do on create.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser != null){

            Intent intent = new Intent(Login_activity.this, HomeMenu_activity.class);
            startActivity(intent);
            finish();

        }

        else {

            email_ET = findViewById(R.id.userName_ET);
            password_ET = findViewById(R.id.password_ET);
            login_BTN = findViewById(R.id.login_BTN);
            loginFB_BTN = findViewById(R.id.loginFB_BTN);
            newUser_TV = findViewById(R.id.newUser_TV);
            pBar = findViewById(R.id.loginProgressBar);
            checkBox = findViewById(R.id.login_checkBox);

            email_ET.requestFocus();
            login_BTN.setOnClickListener(this);
            newUser_TV.setOnClickListener(this);
            pBar.setVisibility(View.GONE);
            checkBox.setOnCheckedChangeListener(this);

            displaySavedLoginCred();
        }
    }

    /**
     * Resume method, for when the acitivity gets resumed
     */
    @Override
    protected  void onResume() {
        super.onResume();
        enableScreen();
        newUser_TV.setTypeface(Typeface.DEFAULT);
    }

    /**
     * Method for handling what happens when you click in the activity
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (v == login_BTN) {

            signIn(email_ET.getText().toString().trim(), password_ET.getText().toString());
            setSavedLoginCred();

        }

        if (v == newUser_TV) {

            newUser_TV.setTypeface(Typeface.DEFAULT_BOLD);
            Intent intent = new Intent(this, SignUp_activity.class);
            startActivity(intent);

        }
    }


    /**
     * Method for handling the sign in process, with firebaseAuth
     * @param email
     * @param password
     */
    private void signIn(String email, String password) {


        if (!userInputValidation()) return;
        disableScreen();

        Log.i(TAG, "USER (BEFORE SIGN IN): "+firebaseUser); //Should be null if signed out
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    //User is now signed in
                                    firebaseUser = firebaseAuth.getCurrentUser();
                                    Intent intent = new Intent(Login_activity.this, HomeMenu_activity.class);
                                    startActivity(intent);
                                    finish();
                                    Log.i(TAG, "USER (AFTER SIGN IN): " + firebaseUser.getEmail());

                                } else if (!task.isSuccessful()) {

                                    //if login fails
                                    App.longToast(Login_activity.this, "Fejl, kan ikke genkende e-mail/password");
                                    Log.i(TAG, "No such user: " + firebaseUser);
                                    enableScreen();

                                }

                            }

                        });
             }


    /**
     * Method for checking if the user has entered anything in the fields.
     * @return
     */
    private boolean userInputValidation() {

        boolean valid = true;

        String email = email_ET.getText().toString();
        if (TextUtils.isEmpty(email)) {

            email_ET.setError("Udfyld venligst.");
            valid = false;

        }
        else email_ET.setError(null);

        String password = password_ET.getText().toString();
        if (TextUtils.isEmpty(password)) {

            password_ET.setError("Udfyld venligst.");
            valid = false;

        }
        else password_ET.setError(null);

        return valid;

        }

    /**
     * Method for disabling the screen.
     */
        public void disableScreen(){
            pBar.setVisibility(View.VISIBLE);
            email_ET.setEnabled(false);
            password_ET.setEnabled(false);
            login_BTN.setEnabled(false);
            loginFB_BTN.setEnabled(false);
            newUser_TV.setEnabled(false);
            checkBox.setEnabled(false);

        }

    /**
     * Method for enabling the screen.
     */
        public void enableScreen(){
            pBar.setVisibility(View.GONE);
            email_ET.setEnabled(true);
            password_ET.setEnabled(true);
            login_BTN.setEnabled(true);
            loginFB_BTN.setEnabled(true);
            newUser_TV.setEnabled(true);
            checkBox.setEnabled(true);
        }

    public void setSavedLoginCred(){

            if(checkBox.isChecked()) {
                App.prefs.edit()
                        .putBoolean("loginCheckBox", checkBox.isChecked())
                        .putString("e-mail", email_ET.getText().toString())
                        .putString("password", password_ET.getText().toString())
                        .apply();
            }

    }

    public void displaySavedLoginCred(){

        boolean checked = App.prefs.getBoolean("loginCheckBox", false);
        String email = App.prefs.getString("e-mail", null);
        String password = App.prefs.getString("password", null);

        if (checked && email != null && password != null) {
            checkBox.setChecked(true);
            email_ET.setText(email);
            password_ET.setText(password);

        }

    }



    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            switch(compoundButton.getId()){
                case  R.id.login_checkBox:
                    if(!b){

                        App.prefs.edit()
                                .remove("loginCheckBox")
                                .remove("e-mail")
                                .remove("password")
                                .commit();
                    }

             }

       }

}


