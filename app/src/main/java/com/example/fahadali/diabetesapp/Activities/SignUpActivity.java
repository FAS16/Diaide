package com.example.fahadali.diabetesapp.Activities;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
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

import com.example.fahadali.diabetesapp.Model.Measurement;
import com.example.fahadali.diabetesapp.Model.User;
import com.example.fahadali.diabetesapp.Other.App;
import com.example.fahadali.diabetesapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * variables for the SignUp activity
     */
    private EditText firstName_ET, lastName_ET, email_ET, password_ET;
    private Button signUp_BTN;
    private TextView backToLogin_TV;
    private ProgressBar pBar;

    private FirebaseAuth firebaseAuth;
    private SharedPreferences prefs;
    private FirebaseUser firebaseUser;
    public DatabaseReference db;
    private DatabaseReference db_userReference;

    /**
     * Oncreate method, to tell the program what to do on create.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        db = FirebaseDatabase.getInstance().getReference();

        firstName_ET = findViewById(R.id.createFirstName_ET);
        lastName_ET = findViewById(R.id.createLastName_ET);
        email_ET = findViewById(R.id.createEmail_ET);
        password_ET = findViewById(R.id.createPassword_ET);
        pBar = findViewById(R.id.signProgressBar);


        signUp_BTN = findViewById(R.id.signUp_BTN);
        signUp_BTN.setOnClickListener(this);
        backToLogin_TV = findViewById(R.id.backToLogin_TV);
        backToLogin_TV.setOnClickListener(this);
        pBar.setVisibility(View.GONE);
        firstName_ET.requestFocus();

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

    }

    /**
     * On start method, to tell the program what to do on start.
     */
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
       // updateUI(currentUser);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(App.dialogIsVisible) App.showNeutralDialog("Ingen internetforbindelse", "Tilslut til internettet, og prøv igen.",this);
    }


    /**
     * Method for handling what happens when you click in the activity
     * @param view
     */
    @Override
    public void onClick(View view) {

        if(view == backToLogin_TV){
            backToLogin_TV.setTypeface(Typeface.DEFAULT_BOLD);
            finish();
        }

        if(view == signUp_BTN && App.isOnline()){

            if(firebaseUser == null) createUserAccount(email_ET.getText().toString(), password_ET.getText().toString());
            else if(firebaseUser.isAnonymous()) convertAnonymousUser(email_ET.getText().toString(), password_ET.getText().toString());
        }

        else App.showNeutralDialog("Ingen internetforbindelse", "Tilslut til internettet, og prøv igen.",this);
    }

    /**
     * Method for creating a new user locally, and saving it in the database
     */
    private void createNewUser(){

        String id = firebaseUser.getUid();
        String firstName = firstName_ET.getText().toString();
        String lastName = lastName_ET.getText().toString();
        String email = firebaseUser.getEmail();
        ArrayList <Measurement> bloodList = User.getUserInstance().getMeasurements();

        User.getUserInstance().setUser(id, firstName, lastName, email, bloodList);

        db.child("users").child(firebaseUser.getUid()).setValue(User.getUserInstance());
    }

    /**
     * Method for creating the user account in firebase.
     * @param email
     * @param password
     */
    protected void createUserAccount(String email, String password){

        if(!passwordValidation()) return;
        if(!userInputValidation()) return;
        disableScreen();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // User is logged in, and the UI updates with the specific user values
                            Log.d("SUCCESSFULL LOGIN", "createUserWithEmail: success");
                            firebaseUser = firebaseAuth.getCurrentUser();
                            createNewUser();
                            showAlertDialog();

//                            Toast.makeText(SignUpActivity.this, "Bruger oprettet", Toast.LENGTH_SHORT).show();

                        }
                        else if (!task.isSuccessful()) {
                            // If sign in fails, display a message to the user.
                            Log.w("FAILED LOGIN", "createUserWithEmail: failure", task.getException());
                            App.shortToast(SignUpActivity.this, "fejl, bruger ikke oprettet");
                            enableScreen();
                        }
                    }
                });
        }

        public  void convertAnonymousUser(String email, String password){

            AuthCredential credential = EmailAuthProvider.getCredential(email, password);

            firebaseAuth.getCurrentUser().linkWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d("Convert anon user: ", "linkWithCredential:success");
                                firebaseUser = task.getResult().getUser();
                                createNewUser();
                                showAlertDialog();

                            } else {
                                Log.w("Convert anon user: ", "linkWithCredential:failure", task.getException());
                                App.shortToast(SignUpActivity.this, "Fejl, bruger ikke oprettet. Tjek din internetforbindelse og prøv igen.");
                                enableScreen();

                            }

                        }
                    });

        }

    /**
     * Method for sending email verification.
     */
    private void sendEmailVerification() {


        // Send verification email
         FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user != null){
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "Verificérings e-mail er sendt til dig e-mail ", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("FAILED TO SEND EMAIL", "sendEmailVerification method", task.getException());
                            Toast.makeText(SignUpActivity.this, "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });}
    }

    /**
     * Method for checking that the user is entering the right information
     * @return
     */
    private boolean userInputValidation() {
        boolean valid = true;

        String email = email_ET.getText().toString();
        if (TextUtils.isEmpty(email)) {
            email_ET.setError("Udfyld venligst.");
            valid = false;

        } else {
            email_ET.setError(null);
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
     * Method for checking if the password is correct, min 6 chars, a number and a capital letter
     * @return
     */
    public boolean passwordValidation(){
        boolean valid = true;

        String password = password_ET.getText().toString();

        if(password.length() < 6) {
            password_ET.setError("Adgangskoden skal være på minimum 6 tegn.");
            valid = false;

        }

        else if(!password.matches(".*[0-9].*")) {
            password_ET.setError("Adgangskoden skal indeholde minimum ét tal.");
            valid = false;

        }

        else  if(!password.matches(".*[A-Z].*")){
            password_ET.setError("Adgangskoden skal indeholde minimum ét stort bogstav.");
            valid = false;
        }

        return valid;


    }

    /**
     * Method for showing the alerts, eg when they are created as users.
     */
    public void showAlertDialog(){

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Du er oprettet som bruger");
        alertDialog.setMessage("Du vil nu blive ført til din hovedmenu");
        alertDialog.setCanceledOnTouchOutside(false); //the dialog will note close when touch is outside of it
        alertDialog.setCancelable(false);

        alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent = new Intent(SignUpActivity.this, HomeMenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });

        alertDialog.show();
    }



    /**
     * Method for disabling the screen.
     */
    public void disableScreen(){
            pBar.setVisibility(View.VISIBLE);
            firstName_ET.setEnabled(false);
            lastName_ET.setEnabled(false);
            email_ET.setEnabled(false);
            password_ET.setEnabled(false);
            signUp_BTN.setEnabled(false);
            backToLogin_TV.setEnabled(false);

    }

    /**
     * Method for enabling the screen.
     */
    public void enableScreen(){
        pBar.setVisibility(View.GONE);
        firstName_ET.setEnabled(true);
        lastName_ET.setEnabled(true);
        signUp_BTN.setEnabled(true);
        email_ET.setEnabled(true);
        password_ET.setEnabled(true);
    }



}
