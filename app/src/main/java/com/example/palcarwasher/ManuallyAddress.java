package com.example.palcarwasher;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ManuallyAddress extends AppCompatActivity implements OnMapReadyCallback {
   String  ProviderId;
    GoogleMap gMap;
    String latitudeX;
    String longitudeY;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manually_address);
       ProviderId=getIntent().getStringExtra("ProviderId");
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
                latitudeX=latLng.latitude+"";
                longitudeY=latLng.longitude+"";
                gMap.clear();
                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
                gMap.addMarker(markerOptions);


            }
        });

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    public void onClickSaveLocation(View view){

        databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("PalCarWasher").child("ProviderLocation");

     Intent intent = new Intent(ManuallyAddress.this, UploadLogo.class);
       // Intent intent = new Intent(ManuallyAddress.this, MainActivity.class);




        String locationId = databaseReference.push().getKey();
        ProviderLocation pl=new ProviderLocation(locationId,ProviderId,latitudeX,longitudeY);
        databaseReference.push().setValue(pl);


        intent.putExtra("ProviderId", ProviderId);
        startActivity(intent);

    }


}
