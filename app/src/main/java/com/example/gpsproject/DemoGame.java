package com.example.gpsproject;


import static android.content.ContentValues.TAG;

import static com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY;
import static com.google.android.gms.location.LocationRequest.PRIORITY_LOW_POWER;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DemoGame extends AppCompatActivity {
    TextView username;
    FirebaseDatabase db;
    DatabaseReference reference;
    ImageView jediside, sithside, charac;

    boolean isjedi, issith;
    String userName, gps;

    Button btnreg;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    private TextView locationTextView;
    private FusedLocationProviderClient fusedLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_game);

        jediside = findViewById(R.id.jcircle);
        username = findViewById(R.id.username);
        charac = findViewById(R.id.charac);
        jediside.setVisibility(View.INVISIBLE);
        btnreg = findViewById(R.id.retreg);
        locationTextView = findViewById(R.id.locationTextView);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // Permission is already granted, proceed to get location
            requestLocation();
        }


        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DemoGame.this, "log out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DemoGame.this, MainActivity.class);
                startActivity(intent);
            }
        });
        DatabaseReference rootRef = db.getInstance().getReference();
        DatabaseReference usersRef = rootRef.child("Users");
        DatabaseReference data = usersRef.child("username");

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                isjedi = dataSnapshot.child("Users").child("username").child("jedi").getValue(Boolean.class);
                issith = dataSnapshot.child("Users").child("username").child("sith").getValue(Boolean.class);
                userName = dataSnapshot.child("Users").child("username").child("userName").getValue(String.class);
                if (isjedi) {
                    jediside.setVisibility(View.VISIBLE);
                    charac.setImageResource(R.drawable.jedi);
                    username.setTextColor(Color.parseColor("#FFFF00"));
                }
                if (issith) {
                    jediside.setImageResource(R.drawable.sithcircle);
                    jediside.setVisibility(View.VISIBLE);
                    charac.setImageResource(R.drawable.sith);
                    username.setTextColor(Color.parseColor("#FF0000"));

                }
                username.setText("welcome to the galaxy " + userName);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage()); //Don't ignore errors!
            }
        };
        rootRef.addListenerForSingleValueEvent(valueEventListener);

    }

    private void requestLocation() {
        // Get last known location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            // Get the last known location
                            Location lastLocation = task.getResult();
                            double latitude = lastLocation.getLatitude();
                            double longitude = lastLocation.getLongitude();

                            // Display the location
                            locationTextView.setText("Latitude: " + latitude + "\nLongitude: " + longitude);
                        } else {
                            // Failed to get location
                            Toast.makeText(DemoGame.this, "Failed to get location.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed to get location
                requestLocation();
            } else {
                // Permission denied, show a message
                Toast.makeText(this, "Location permission denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}