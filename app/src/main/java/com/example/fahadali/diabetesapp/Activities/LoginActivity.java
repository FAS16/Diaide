package com.example.fahadali.diabetesapp.Activities;

import android.app.ProgressDialog;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
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

import com.example.fahadali.diabetesapp.Model.Measurement;
import com.example.fahadali.diabetesapp.Model.MedicineCard;
import com.example.fahadali.diabetesapp.Model.User;
import com.example.fahadali.diabetesapp.Other.App;
import com.example.fahadali.diabetesapp.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    /**
     * variables - fields:
     * email_ET: Username input - EditText
     * password_ET: password input - EditText
     * login_BTN: login button - Button
     */

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private EditText email_ET, password_ET;
    private Button login_BTN, loginFB_BTN, createUser_BTN;
    private TextView notNow_TV, forgotLogin_TV;
    private ProgressBar pBar;
    private CheckBox checkBox;

    public DatabaseReference db;
     private static final String TAG = "LoginActivity";
    CallbackManager callbackManager = CallbackManager.Factory.create();


    /**
     * Oncreate method, to tell the program what to do on create.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        db = FirebaseDatabase.getInstance().getReference();
        if(firebaseUser != null){


            Intent intent = new Intent(LoginActivity.this, HomeMenuActivity.class);
            startActivity(intent);
            finish();


        }

        else {

            email_ET = findViewById(R.id.userName_ET);
            password_ET = findViewById(R.id.password_ET);
            forgotLogin_TV = findViewById(R.id.forgotLogin_TW);
            login_BTN = findViewById(R.id.login_BTN);
            //Facebook LogIn
            loginFB_BTN = findViewById(R.id.loginFB_BTN);
            LoginManager.getInstance().registerCallback(callbackManager,
                    new FacebookCallback<LoginResult>() {
                        @Override
                        public void onSuccess(LoginResult loginResult) {
                            fbLogin(loginResult.getAccessToken());
                        }

                        @Override
                        public void onCancel() {
                            // App code
                        }

                        @Override
                        public void onError(FacebookException exception) {
                            // App code
                        }
                    });

            createUser_BTN = findViewById(R.id.createUser_BTN);
            notNow_TV = findViewById(R.id.notNow_TV);
            pBar = findViewById(R.id.loginProgressBar);
            checkBox = findViewById(R.id.login_checkBox);

            email_ET.requestFocus();
            login_BTN.setOnClickListener(this);
            forgotLogin_TV.setOnClickListener(this);
            loginFB_BTN.setOnClickListener(this);
            createUser_BTN.setOnClickListener(this);
            notNow_TV.setOnClickListener(this);
            pBar.setVisibility(View.GONE);
            checkBox.setOnCheckedChangeListener(this);

            displaySavedLoginCred();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
    /**
     * Resume method, for when the acitivity gets resumed
     */
    @Override
    protected  void onResume() {
        super.onResume();
        enableScreen();
        notNow_TV.setTypeface(Typeface.DEFAULT);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(App.dialogIsVisible) App.showNeutralDialog("Ingen internetforbindelse", "Tilslut til internettet, og prøv igen.",this);
    }

    /**
     * Method for handling what happens when you click in the activity
     * @param v
     */
    @Override
    public void onClick(View v) {

        if( v == createUser_BTN){
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        }


        else if(App.isOnline()) {

            if (v == login_BTN) {

                signIn(email_ET.getText().toString().trim(), password_ET.getText().toString());
                setSavedLoginCred();

            } 
        else if(v == loginFB_BTN){
            LoginManager.getInstance().logInWithReadPermissions(
                    this,
                    Arrays.asList("email", "public_profile")
            );
        }


            } else if (v == notNow_TV) {
                notNow_TV.setTypeface(Typeface.DEFAULT_BOLD);
                signInAnonymously();
               }

              else if (v == forgotLogin_TV){
            System.out.println("Forgot Password TV Knap virker");
            forgotLogin();
        }
        else {
            App.showNeutralDialog("Ingen internetforbindelse", "Tilslut til internettet, og prøv igen.",this);
        }


    }

    private void fbLogin(AccessToken token){

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success
                            Log.d(TAG, "signInWithCredential:success");
                            firebaseUser = firebaseAuth.getCurrentUser();
                            String id = Profile.getCurrentProfile().getId();
                            String firstName = Profile.getCurrentProfile().getFirstName();
                            String lastName = Profile.getCurrentProfile().getLastName();
                            String email = firebaseAuth.getCurrentUser().getEmail();
                            ArrayList <Measurement> bloodList = User.getUserInstance().getMeasurements();
                            ArrayList <MedicineCard> medicineCardsList = User.getUserInstance().getMedicinecardList();

                            User.getUserInstance().setUser(id, firstName, lastName, email, bloodList, medicineCardsList);

                            db.child("users").child(firebaseUser.getUid()).setValue(User.getUserInstance());
                            Intent intent = new Intent(LoginActivity.this, HomeMenuActivity.class);
                            startActivity(intent);
                            finish();


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
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
                                    Intent intent = new Intent(LoginActivity.this, HomeMenuActivity.class);
                                    startActivity(intent);
                                    finish();
                                    Log.i(TAG, "USER (AFTER SIGN IN): " + firebaseUser.getEmail());

                                } else if (!task.isSuccessful()) {

                                    //if login fails
                                    App.shortToast(LoginActivity.this, "Fejl, kan ikke genkende e-mail/password");
                                    Log.i(TAG, "No such user: " + firebaseUser);
                                    enableScreen();

                                }

                            }

                        });
             }



     private void signInAnonymously(){
         firebaseAuth.signInAnonymously()
                 .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {
                         if (task.isSuccessful()) {
                             // Sign in success, update UI with the signed-in user's information
                             Log.d(TAG, "signInAnonymously:success");
                             firebaseUser = firebaseAuth.getCurrentUser();
                             Intent intent = new Intent(LoginActivity.this, HomeMenuActivity.class);
                             startActivity(intent);
                             finish();

                         } else {
                             // If sign in fails, display a message to the user.
                             Log.d(TAG, "signInAnonymously:failure", task.getException());
                             App.shortToast(LoginActivity.this, "Fejl, bruger ikke oprettet. \nTjek din internetforbindelse og prøv igen.");

                         }


                     }
                 });

     }


/*
    public void forgottenPassword(View v) {
            userMail = email_ET.getText().toString();
            FirebaseAuth.getInstance().sendPasswordResetEmail(userMail)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("Glemt kode: ", "Email sent.");
                                App.shortToast(LoginActivity.this, "Email sendt");
                            }
                            else if(email_ET == null){
                                App.shortToast(LoginActivity.this, "Indtast email");
                            }
                            else{
                                App.shortToast(LoginActivity.this, "Email findes ikke");
                            }
                        }
                    });
        }
*/



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
            createUser_BTN.setEnabled(false);
            notNow_TV.setEnabled(false);
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
            createUser_BTN.setEnabled(true);
            notNow_TV.setEnabled(true);
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

    private AlertDialog forgotLogin(){

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(LoginActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.forgot_login, null);
        final EditText mEmail = mView.findViewById(R.id.email_dialog);

        mBuilder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final String userMail = mEmail.getText().toString();
                FirebaseAuth.getInstance().sendPasswordResetEmail(userMail)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("Glemt kode: ", "Email sent.");
                                    App.shortToast(LoginActivity.this, "Email sendt");
                                }
                                else if(email_ET == null){
                                    App.shortToast(LoginActivity.this, "Indtast email");
                                }
                                else{
                                    App.shortToast(LoginActivity.this, "Email findes ikke");
                                }
                            }
                        });
            }
        });

        mBuilder.setNegativeButton("Annuller", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        mBuilder.setView(mView).show();
        final AlertDialog dialog = mBuilder.create();
        return dialog;
    }

}


