package com.example.fahadali.diabetesapp.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.fahadali.diabetesapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * An activity that displays a Google map with a marker (pin) to indicate a particular location.
 */
public class MapsActivity extends AppCompatActivity
        implements OnMapReadyCallback, View.OnClickListener {

    Button search_BTN;
    EditText search_ET;
    LatLng helsingør = new LatLng(56.035759, 12.612638);
    LatLng lyngby = new LatLng(55.791101,12.526556);
    GoogleMap googleMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_maps);

        search_BTN = findViewById(R.id.Search);
        search_ET = findViewById(R.id.Search_ET);



        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        search_BTN.setOnClickListener(this);

    }

    /**
     * Manipulates the map when it's available.
     * The API invokes this callback when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user receives a prompt to install
     * Play services inside the SupportMapFragment. The API invokes this method after the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMinZoomPreference(6.0f);
        googleMap.setMaxZoomPreference(14.0f);
        setPosition(googleMap);
        // Add a marker in Denmark,
        // and move the map's camera to the same location.
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(55.712, 10.673)));
    }

    @Override
    public void onClick(View v) {
        if(v == search_BTN){
           if(search_ET.getText().toString() == 2800 +""){
               googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(55.712, 10.673)));
           }
        }
    }

    public void setPosition(GoogleMap googleMap){
        googleMap.addMarker(new MarkerOptions().position(helsingør)
                .title("helsingør"));
        googleMap.addMarker(new MarkerOptions().position(lyngby)
                .title("Kongens lyngby"));
    }
}
