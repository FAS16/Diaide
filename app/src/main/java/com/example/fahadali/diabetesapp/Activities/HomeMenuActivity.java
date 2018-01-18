package com.example.fahadali.diabetesapp.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.fahadali.diabetesapp.Fragments.BloodSugarFragment2;
import com.example.fahadali.diabetesapp.Model.ObserverPattern.Observer;
import com.example.fahadali.diabetesapp.Model.User;
import com.example.fahadali.diabetesapp.Fragments.GraphFragment;

import com.example.fahadali.diabetesapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class HomeMenuActivity extends AppCompatActivity implements Observer, NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    /**
     * Variables for the HomeMenuActivity
     */


    private final String TAG = "BloodSugarFragment2";

    protected FirebaseAuth firebaseAuth;
    protected FirebaseUser firebaseUser;
    protected DatabaseReference db_userReference;

    //Objects in current activity
    private ProgressBar pBar;

    //Objects in side drawer
    private TextView name_TV, email_TV;



    /**
     * Oncreate method, to tell the program what to do on create.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_menu);
        Log.i(TAG, "Running onCreate in "+TAG);
        setTitle("Blodsukker");
        User.getUserInstance().registerObserver(this);

        //Handling data retrieval from firebase
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser!= null){
        firebaseUser.getUid();
        db_userReference = FirebaseDatabase.getInstance().getReference().child("users").child(firebaseUser.getUid());
        setFirebaseListener();

        }


        if(savedInstanceState == null) {

            Fragment start = new BloodSugarFragment2();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.navigation_container, start)
                    .commit();

        }

        //instantiation of bottom menu
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //instantiation of toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //instantiation of drawer/side menu
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //If user is not authenticated
        if(firebaseUser.isAnonymous()) {
            Menu menu = navigationView.getMenu();
            MenuItem settings = menu.findItem(R.id.nav_settings);
            settings.setTitle("Opret bruger");

            MenuItem signOut = menu.findItem(R.id.nav_signOut);
            signOut.setVisible(false);
            signOut.setEnabled(false);
        }


        //Objects in current activity
        pBar = findViewById(R.id.homeProgressBar);

        //Objects in side drawer
        name_TV = navigationView.getHeaderView(0).findViewById(R.id.name_TV_drawer);
        email_TV= navigationView.getHeaderView(0).findViewById(R.id.email_TV_drawer);




    }

    /**
     * What to do when activity is destroyed.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        User.getUserInstance().removeObserver(this);

    }


    @Override
    protected void onResume() {
        super.onResume();


        Fragment start = new BloodSugarFragment2();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.navigation_container, start)
                .commit();
    }

    /**
     * What to do when the back button is pressed.
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

        else if(id == R.id.action_help){
            String blood_sugar_info = "Blodsukkeret skal optimalt være: \n\n• Før måltider: mellem 4-7 mmol/l.\n\n• Efter måltider: 10 mmol/l (ca. 1,5 time efter).\n\n• Ved sengetid: omkring 6-8 mmol/l.\n\n\nDet betyder at dit blodsukker må helst ikke på noget tidspunkt af døgnet overskride 10 mmol/l, eller komme under 4 mmol/l.";

            final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Hvad bør dit blodsukkerniveau være?");
            alertDialog.setMessage(blood_sugar_info);

            alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    alertDialog.cancel();
                }
            });

            alertDialog.show();
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


        if (id == R.id.nav_homeMenu) {
            // Handle the camera action
        } else if (id == R.id.nav_medicin) {
            Intent i = new Intent (this, MedicineCardActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_motivationGroup) {
            Intent i = new Intent (this, MapsActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_tips) {

        } else if (id == R.id.nav_print) {

        }else if (id == R.id.nav_settings) {

            if(firebaseUser.isAnonymous()){

                Intent i = new Intent (this, SignUpActivity.class);
                startActivity(i);
                finish();

            }

        } else if (id == R.id.nav_signOut) {

            firebaseAuth.signOut();
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            Log.i("CURRENT USER: ", "After sign out: "+firebaseUser); //Should be null

            User.getUserInstance().nullifyUser();
            System.out.println("SNAPSHOT2: "+ User.getUserInstance()); //Should be null

            Intent i = new Intent (this, LoginActivity.class);
            startActivity(i);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Handles click events
     * @param v
     */
    @Override
    public void onClick(View v) {


    }

    public BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment = null;

                switch (item.getItemId()) {

                case R.id.navigation_bloodsugar:
                    setTitle("Blodsukker");
                    fragment = new BloodSugarFragment2();
                   break;

                case R.id.navigation_reminders:
                    setTitle("Påmindelser");
                    fragment = new Fragment();
                    break;

                case R.id.navigation_overview:
                    setTitle("Overblik");
                    fragment = new Fragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.navigation_container,fragment)
                    .commit();
            return true;
        }
    };

    /**
     * Method for listening to changes to the database, and update if changes occur.
     */
    private void setFirebaseListener(){


        System.out.println("Link to current user: " + FirebaseDatabase.getInstance().getReference().child("users").child(firebaseUser.getUid()).toString()); //Prints link to the current user
        db_userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Create User object with DataSnapShot

                User u = dataSnapshot.getValue(User.class);
                System.out.println("SNAPSHOT: "+ u);
                System.out.println("SINGLETON FØR HENTNING FRA FIREBASE: "+ User.getUserInstance());

                if(!firebaseUser.isAnonymous()) {
                    User.getUserInstance().setUser(u.getId(), u.getFirstName(), u.getLastName(), u.getEmail(), u.getBloodList(), u.getMedicinecardList());
                    User.getUserInstance().notifyAllObservers();
                }

                else {} //Husk at rette

                System.out.println("SINGLETON EFTER HENTNING FRA FB: "+ User.getUserInstance());

                pBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {



            }
        });

    }

    @Override
    public void update() {

        if(!firebaseUser.isAnonymous()){

            name_TV.setText(User.getUserInstance().getFirstName()+" "+User.getUserInstance().getLastName());
            email_TV.setText(User.getUserInstance().getEmail());

        }
        System.out.println("User updated  - HomeMenuActivity "+ User.getUserInstance());

    }

}
