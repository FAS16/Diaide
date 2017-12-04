package com.example.fahadali.diabetesapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.fahadali.diabetesapp.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeMenu_activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, Runnable {

    /**
     * Variables for the HomeMenuActivity
     */
    private Button bloodSugar_BTN;
    private Button reminders_BTN;
    private Button testLogUd_BTN;
    private ProgressBar pBar;
    private TextView tv;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference db_userReference;

    /**
     * Oncreate method, to tell the program what to do on create.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_menu);

        pBar = findViewById(R.id.homeProgressBar);




        user = User.getUserInstance();


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        setListener();
        User.getUserInstance().observers.add(this);

        System.out.println("SNAPSHOT3: "+ User.getUserInstance());

        tv = findViewById(R.id.nuværendeUser);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        bloodSugar_BTN = (Button) findViewById(R.id.bloodSugar_BTN);
        reminders_BTN = (Button) findViewById(R.id.reminders_BTN);
        testLogUd_BTN = findViewById(R.id.logUd_BTN);


        bloodSugar_BTN.setOnClickListener(this);
        reminders_BTN.setOnClickListener(this);
        testLogUd_BTN.setOnClickListener(this);

    }

    /**
     * Method for telling the activity what to do on destroy.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        User.getUserInstance().observers.remove(this);

    }

    /**
     * Method for telling the activity what to do when the back button is pressed.
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Method for creating the optionsmenu oncreate
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    /**
     * Method for handle action bar item clicks.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Method for navigating view item clicks
     * @param item
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Method for handling what happens when you click different places.
     * @param v
     */
    @Override
    public void onClick(View v) {
        if(v == bloodSugar_BTN){
            Intent intent = new Intent(this, BSugarOverview_activity.class);
            startActivity(intent);
        }

        if(v == reminders_BTN){
            Intent intent = new Intent(this, ReminderTypeSelector_activity.class);
            startActivity(intent);
        }

        if(v == testLogUd_BTN){
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.signOut();
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

            Log.i("CURRENT USER: ", "After sign out: "+firebaseUser);

            User.getUserInstance().nullifyUser();
            System.out.println("SNAPSHOT2: "+ User.getUserInstance());
            Intent i = new Intent (this, Login_activity.class);
            startActivity(i);
            finish();

        }

    }

    /**
     * Run method, handling the ???????
     */
    @Override
    public void run() {
        tv.setText(User.getUserInstance().getFirstName());
        System.out.println("SNAPSHOT3X: "+ User.getUserInstance());
    }

    /**
     * Method for listening to changes to the database, and update if changes occur.
     */
    private void setListener (){


        db_userReference = FirebaseDatabase.getInstance().getReference().child("users").child(firebaseUser.getUid());
        //Prints link to the current user
        System.out.println("Link to current user: " + FirebaseDatabase.getInstance().getReference().child("users").child(firebaseUser.getUid()).toString());

        db_userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Get User object.
                User u = dataSnapshot.getValue(User.class);
                System.out.println("SNAPSHOT: "+ u);
                System.out.println("SINGLETON FØR HENTNING FRA FB: "+ User.getUserInstance());

                User.getUserInstance().setUser(u.getId(),u.getFirstName(),u.getLastName(),u.getMail(),u.getBsList());
                System.out.println("SINGLETON EFTER HENTNING FRA FB: "+ User.getUserInstance());

                for(Runnable r: User.getUserInstance().observers) r.run();
                System.out.println("OBSERVERS 2: "+ User.getUserInstance().observers.toString());

                pBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });



    }
}
