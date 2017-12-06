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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fahadali.diabetesapp.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login_activity extends AppCompatActivity implements View.OnClickListener {

    /**
     * variables - fields:
     * userName_ET: Username input - EditText
     * password_ET: password input - EditText
     * login_BTN: login button - Button
     */

    private EditText userName_ET;
    private EditText password_ET;
    private Button login_BTN;
    private Button loginFB_BTN;
    private TextView newUser_TV;
    private ProgressBar pBar;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    private static final String TAG = "CURRENT USER";

    /**
     * Oncreate method, to tell the program what to do on create.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("OBSERVERS: "+ User.getUserInstance().observers.toString());
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        userName_ET = findViewById(R.id.userName_ET);
        password_ET = findViewById(R.id.password_ET);
        login_BTN = findViewById(R.id.login_BTN);
        loginFB_BTN = findViewById(R.id.loginFB_BTN);
        newUser_TV = findViewById(R.id.newUser_TV);
        pBar = findViewById(R.id.loginProgressBar);

        pBar.setVisibility(View.GONE);
        userName_ET.requestFocus();
        login_BTN.setOnClickListener(this);
        newUser_TV.setOnClickListener(this);


    }

    /**
     * Resume method, for when the acitivity gets resumed
     */
    @Override
    protected  void onResume() {
        super.onResume();
        enableScreen();
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

    /**
     * Method for handling what happens when you click in the activity
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (v == login_BTN) {
            signIn(userName_ET.getText().toString().trim(), password_ET.getText().toString());

        }

        if (v == newUser_TV) {
            newUser_TV.setTypeface(Typeface.DEFAULT_BOLD);
            Intent intent = new Intent(this, SignUp_activity.class);
            startActivity(intent);
        }
    }

    /**
     * Method for handling what happens on start.
     */
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

    /**
     * Method for handling the sign in process, with firebaseAuth
     * @param email
     * @param password
     */
    private void signIn(String email, String password) {


        if (!userInputValidation()) return;
        disableScreen();
        Log.i(TAG, "USER (BEFORE SIGN IN): "+firebaseUser);

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                                //User is signed in
                                firebaseUser = firebaseAuth.getCurrentUser();
                               // setListener();
                                Toast.makeText(Login_activity.this, "Logged in", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Login_activity.this, HomeMenu_activity.class);
                                startActivity(intent);
                                finish();

                                Log.i(TAG, "USER (AFTER SIGN IN): " + firebaseUser.getEmail());

                        }

                        else if(!task.isSuccessful()){

                            //Login fails
                            Toast.makeText(Login_activity.this, "Fejl, kan ikke genkende e-mail/password.", Toast.LENGTH_SHORT).show();
                            //enableScreen();
                            Log.i(TAG, "No such user: "+firebaseUser);
                            enableScreen();

                            //updateUI(null)

                        }


                    }

        db_userReference = FirebaseDatabase.getInstance().getReference().child("users").child(firebaseUser.getUid());
        //Prints link to the current user
        System.out.println(FirebaseDatabase.getInstance().getReference().child("users").child(firebaseUser.getUid()).toString());

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get User object.

                User u = dataSnapshot.getValue(User.class);
                System.out.println("SNAPSHOT: "+ u);
                System.out.println("SINGLETON FØR HENTNING FRA FB: "+ User.getUserInstance());

                User.getUserInstance().setUser(u.getId(),u.getFirstName(),u.getLastName(),u.getMail(),u.getBsList());
                System.out.println("SINGLETON EFTER HENTNING FRA FB: "+ User.getUserInstance());

                for(Runnable r: User.getUserInstance().observers) r.run();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
               // Toast.makeText(Login_activity.this, "Fejl - initializeUser", Toast.LENGTH_SHORT).show();
            }
        };

        db_userReference.addValueEventListener(listener);

                });
        }

    /**
     * Method for checking if the user has entered anything in the fields.
     * @return
     */
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

    /**
     * Method for disabling the screen.
     */
        public void disableScreen(){
            pBar.setVisibility(View.VISIBLE);
            userName_ET.setEnabled(false);
            password_ET.setEnabled(false);
            login_BTN.setEnabled(false);
            loginFB_BTN.setEnabled(false);
            newUser_TV.setEnabled(false);

        }

    /**
     * Method for enabling the screen.
     */
        public void enableScreen(){
            pBar.setVisibility(View.GONE);
            userName_ET.setEnabled(true);
            password_ET.setEnabled(true);
            login_BTN.setEnabled(true);
            loginFB_BTN.setEnabled(true);
            newUser_TV.setEnabled(true);
        }

    }

