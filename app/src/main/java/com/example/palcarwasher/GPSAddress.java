package com.example.palcarwasher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GPSAddress extends FragmentActivity implements OnMapReadyCallback {

    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    String latitudeX;
    String longitudeY;
    DatabaseReference databaseReference;
    String ProviderId;
    private static final int REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g_p_s_address);
        ProviderId=getIntent().getStringExtra("ProviderId");
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();
    }

    

    private void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                    currentLocation = location;
                    Toast.makeText(getApplicationContext(), currentLocation.getLatitude()+""+
                            currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                   latitudeX=currentLocation.getLatitude()+"";
                   longitudeY=currentLocation.getLongitude()+"";

                    SupportMapFragment supportMapFragment=(SupportMapFragment)getSupportFragmentManager()
                            .findFragmentById(R.id.google_map);
                    supportMapFragment.getMapAsync(GPSAddress.this);
                }

            }
        });

    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        LatLng latLng = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).position(latLng)
                .title("Current Location");
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);// to make streetMapType (if i remove it its will be ok)
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
        googleMap.addMarker(markerOptions);



        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(" I Am Here. ").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                latitudeX=latLng.latitude+"";
                longitudeY= latLng.longitude+"";
                Toast.makeText(getApplicationContext(), "The coordinates of your location are : "+latLng.latitude+" , "+
                        latLng.longitude, Toast.LENGTH_SHORT).show();
                googleMap.clear();
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
                googleMap.addMarker(markerOptions);
            }
        });




    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_CODE :
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    fetchLastLocation();
                }
                else {
                    Toast.makeText(this,"This app required permission to be granted in order to work properly",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }


    }

    public void onClickSaveLocation(View view){

        databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("PalCarWasher").child("ProviderLocation");

         Intent intent = new Intent(GPSAddress.this,UploadLogo.class);
      //  Intent intent = new Intent(GPSAddress.this, MainActivity.class);




            String locationId = databaseReference.push().getKey();
             ProviderLocation pl=new ProviderLocation(locationId,ProviderId,latitudeX,longitudeY);
            databaseReference.push().setValue(pl);


        intent.putExtra("ProviderId", ProviderId);
        startActivity(intent);

    }



}