package com.example.fahadali.diabetesapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        userName_ET = findViewById(R.id.userName_ET);
        password_ET = findViewById(R.id.password_ET);
        login_BTN = findViewById(R.id.login_BTN);
        loginFB_BTN = findViewById(R.id.loginFB_BTN);
        newUser_TV = findViewById(R.id.newUser_TV);

        userName_ET.requestFocus();
        login_BTN.setOnClickListener(this);
        newUser_TV.setOnClickListener(this);

    }

    // TODO: 01/11/2017: User input validation missing
    @Override
    public void onClick(View v) {
        if(v == login_BTN){
            signIn(userName_ET.getText().toString(), password_ET.getText().toString());


        }

        if(v == newUser_TV){
            Intent intent = new Intent(this, SignUp_activity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in, and update UI.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
       // updateUI(currentUser);
    }

    private void signIn(String email, String password){
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //User is signed in
                            Toast.makeText(Login_activity.this, "Logged in", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Intent intent = new Intent(Login_activity.this, HomeMenu_activity.class);
                            startActivity(intent);
                            //updateUI(user)
                        }
                        else{
                            //Login fails
                            Toast.makeText(Login_activity.this, "Fejl, kan ikke genkende e-mail/password.", Toast.LENGTH_SHORT).show();
                            //updateUI(null)

                        }
                    }
                });
    }
}

