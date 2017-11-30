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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fahadali.diabetesapp.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login_activity extends AppCompatActivity implements View.OnClickListener {

    /**
     * variables - fields:
     * userName_ET: Username input - EditText
     * password_ET: password input - EditText
     * login_BTN: login button - Button
     */

    EditText userName_ET;
    EditText password_ET;
    Button login_BTN;
    Button loginFB_BTN;
    TextView newUser_TV;
    protected FirebaseAuth firebaseAuth;
    protected FirebaseUser firebaseUser;

    private static final String TAG = "CURRENT USER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        userName_ET = findViewById(R.id.userName_ET);
        password_ET = findViewById(R.id.password_ET);
        login_BTN = findViewById(R.id.login_BTN);
        loginFB_BTN = findViewById(R.id.loginFB_BTN);
        newUser_TV = findViewById(R.id.newUser_TV);

        userName_ET.requestFocus();
        login_BTN.setOnClickListener(this);
        newUser_TV.setOnClickListener(this);

    }

    @Override
    protected  void onResume() {
        super.onResume();

        newUser_TV.setTypeface(Typeface.DEFAULT);

//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//        String email = prefs.getString("e-mail",null);
//        String password = prefs.getString("password", null);
//
//        if(email != null && password != null){
//            userName_ET.setText(email);
//            password_ET.setText(password);
//
//        }
    }

    // TODO: 01/11/2017: User input validation missing
    @Override
    public void onClick(View v) {
        if (v == login_BTN) {
            signIn(userName_ET.getText().toString(), password_ET.getText().toString());


        }

        if (v == newUser_TV) {
            newUser_TV.setTypeface(Typeface.DEFAULT_BOLD);
            Intent intent = new Intent(this, SignUp_activity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in, and update UI.
        //updateUI(firebaseUser);

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if(currentUser == null) {

            Log.i(TAG, "User is null");
        }
        else{
            Log.i(TAG, "USER (onStart): " + currentUser.getEmail());
        }
    }

    private void signIn(String email, String password) {

        if (!userInputValidation()) {
            return;
        }

        firebaseUser = firebaseAuth.getCurrentUser();

        Log.i(TAG, "USER (BEFORE SIGN IN): "+firebaseUser);

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){


                            firebaseUser = firebaseAuth.getCurrentUser();
                            if(!firebaseUser.isEmailVerified()){

                            //User is signed in
                            firebaseUser = firebaseAuth.getCurrentUser();
                            Toast.makeText(Login_activity.this, "Logged in", Toast.LENGTH_SHORT).show();

                            System.out.println("EMAIL VERIFICATION STATUS: "+ firebaseUser.isEmailVerified());
                            Intent intent = new Intent(Login_activity.this, HomeMenu_activity.class);
                            startActivity(intent);
                            //updateUI(user)
                            Log.i(TAG, "USER (AFTER SIGN IN): "+firebaseUser.getEmail()); //Should show null since the user is signed out



                        }

                            else if(firebaseUser.isEmailVerified()){
                                firebaseAuth.signOut();
                                firebaseUser = firebaseAuth.getCurrentUser();
                                Toast.makeText(Login_activity.this, "Verificér venligst din konto via. e-mail.", Toast.LENGTH_SHORT).show();
                                Log.i(TAG, "USER (AFTER SIGN IN - VERIFY ACCOUNT): "+firebaseUser); //Should show null since the user is signed out

                            }


                        }

                        else{
                            //Login fails
                            Toast.makeText(Login_activity.this, "Fejl, kan ikke genkende e-mail/password.", Toast.LENGTH_SHORT).show();
                            Log.i(TAG, "No such user: "+firebaseUser); //Should show null since the user is signed out
                            //updateUI(null)

                        }
                    }
            });
        }

    private boolean userInputValidation() {
        boolean valid = true;

        String email = userName_ET.getText().toString();
        if (TextUtils.isEmpty(email)) {
            userName_ET.setError("Udfyld venligst.");
            valid = false;

        } else {
            userName_ET.setError(null);
        }

        String password = password_ET.getText().toString();
        if (TextUtils.isEmpty(password)) {
            password_ET.setError("Udfyld venligst.");
            valid = false;
        } else {
            password_ET.setError(null);
        }

        return valid;
    }
    }

