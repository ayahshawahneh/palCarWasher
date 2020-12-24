package com.example.palcarwasher;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ManuallyAddress extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap gMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manually_address);

        SupportMapFragment supportMapFragment=(SupportMapFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.google_map);
        supportMapFragment.getMapAsync(this);
    }


    public void onMapReady(GoogleMap googleMap){
        gMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(" I Am Here. ");
                Toast.makeText(getApplicationContext(), "The coordinates of your location are : "+latLng.latitude+" , "+
                        latLng.longitude, Toast.LENGTH_SHORT).show();
                gMap.clear();
                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
                gMap.addMarker(markerOptions);


            }
        });

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
