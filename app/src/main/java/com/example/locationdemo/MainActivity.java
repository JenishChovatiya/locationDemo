package com.example.locationdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    //Initialize Variables.
    Button btlocation;
    TextView longitudeTF,letitudeTF,addressTF,countryTF,cityTF;
    FusedLocationProviderClient fusedLocationProviderClient;


    private final static  int request_code = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Assigning Variables.
        btlocation = findViewById(R.id.getDataBtn);
        longitudeTF = findViewById(R.id.longitudeTV);
        letitudeTF = findViewById(R.id.letitudeTV);
        addressTF = findViewById(R.id.addressTV);
        cityTF = findViewById(R.id.cityTV);
        countryTF = findViewById(R.id.cityTV);

        //Initialize fusedfusedLocationProviderClient for better accuracy.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        btlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check Permission.
                if (ActivityCompat.checkSelfPermission(MainActivity.this
                        , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    //When Permission granted.
                    getLocation();
                } else {
                    //When permission Denied.
                    ActivityCompat.requestPermissions(MainActivity.this
                            , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });



    }



    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions

            return;
        }



        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                //Initialize Location.
                Location location = task.getResult();
                if (location != null)
                {
                    //Initialize getCoder
                    Geocoder geocoder = new Geocoder(MainActivity.this,
                            Locale.getDefault());
                    //Initialize address list
                    try {
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1
                        );
                        //Set Latitude on TextView
                        letitudeTF.setText(Html.fromHtml(
                                "<font color='#6200EE'><b>Latitude:</b><br></font>"
                                        + addresses.get(0).getLatitude()
                        ));
                        //Set Longitude.
                        longitudeTF.setText(Html.fromHtml(
                                "<font color='#6200EE'><b>Longitude:</b><br></font>"
                                        + addresses.get(0).getLongitude()
                        ));
                        //Set Country nmae.
                        countryTF.setText(Html.fromHtml(
                                "<font color='#6200EE'><b>Country:</b><br></font>"
                                        + addresses.get(0).getCountryName()
                        ));
                        //Set Address
                        addressTF.setText(Html.fromHtml(
                                "<font color='#6200EE'><b>Address :</b><br></font>"
                                        + addresses.get(0).getAddressLine(0)
                        ));
                        //Set Address
                        cityTF.setText(Html.fromHtml(
                                "<font color='#6200EE'><b>Address :</b><br></font>"
                                        + addresses.get(0).getLocality()
                        ));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }
}



