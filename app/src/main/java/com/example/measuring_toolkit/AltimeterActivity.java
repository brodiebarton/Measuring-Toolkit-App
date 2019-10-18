package com.example.measuring_toolkit;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class AltimeterActivity extends AppCompatActivity implements LocationListener {

    private static final int MY_PERMISSIONS_REQUEST_READ_LOCATION = 0;
    private Button backButton;
    private TextView alt, lat, lon;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.altimeter_tool);

        alt = findViewById(R.id.alt);
        lat = findViewById(R.id.lat);
        lon = findViewById(R.id.lon);
        backButton = findViewById(R.id.backButton_altimeter);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_READ_LOCATION);
        }
        else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    1000, 0, this);
        }
        Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
        onLocationChanged(location);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Quit Altimeter Activity
                finish();
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                            2000, 1, this);

                } else {
                    alt.setText("err");
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        double a = location.getAltitude();
        double la = location.getLatitude();
        double lo = location.getLongitude();

        alt.setText(a + "m");
        lat.setText(String.format("%.5f", la));
        lon.setText(String.format("%.5f", lo));
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}

