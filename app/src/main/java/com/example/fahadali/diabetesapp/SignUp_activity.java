package com.example.fahadali.diabetesapp;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp_activity extends AppCompatActivity implements View.OnClickListener {

    private EditText firstName_ET, lastName_ET, email_ET, password_ET;
    private Button signUp_BTN;
    private TextView backToLogin_TV;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseAuth = FirebaseAuth.getInstance();

        firstName_ET = findViewById(R.id.createFirstName_ET);
        lastName_ET = findViewById(R.id.createLastName_ET);
        email_ET = findViewById(R.id.createEmail_ET);
        password_ET = findViewById(R.id.createPassword_ET);

        signUp_BTN = findViewById(R.id.signUp_BTN);
        signUp_BTN.setOnClickListener(this);

        backToLogin_TV = findViewById(R.id.backToLogin_TV);
        backToLogin_TV.setOnClickListener(this);


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        updateUI(currentUser);
    }



    @Override
    public void onClick(View view) {

        if(view == backToLogin_TV){
            finish();
        }

        if(view == signUp_BTN){
            createUserAccount(email_ET.getText().toString(), password_ET.getText().toString());

        }
    }

    private void createUserAccount(String email, String password){

        if(!userInputValidation()) return;



        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {


                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // User is logged in, and the UI updates with the specific user values
                            Log.d("EMAIL -------", "createUserWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Toast.makeText(SignUp_activity.this, "Bruger oprettet", Toast.LENGTH_SHORT).show();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                           // Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUp_activity.this, "FEJL, bruger ikke oprettet", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        //hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }



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

    private void updateUI(FirebaseUser currentUser) {
    }

}
