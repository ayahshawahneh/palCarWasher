package com.example.palcarwasher;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class ShowAllCompanyOnMap extends FragmentActivity implements OnMapReadyCallback {

    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    double distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_company_on_map);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();
    }

    private void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager
                .PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
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
                    Toast.makeText(getApplicationContext(), currentLocation.getLatitude()+" , "+
                            currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                    SupportMapFragment supportMapFragment=(SupportMapFragment)getSupportFragmentManager()
                            .findFragmentById(R.id.google_map);
                    supportMapFragment.getMapAsync(ShowAllCompanyOnMap.this);
                }

            }
        });

    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        //customer current location :
        // implementation 'com.google.maps.android:android-maps-utils:0.4+'

        final LatLng latLng = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).position(latLng)
                .title(" I Am Here. ").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));

        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);// to make streetMapType (if i remove it its will be ok)
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,12));

        googleMap.addMarker(markerOptions);


//////////////////////////////////////////////////////////


        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("PalCarWasher")
                .child("ProviderLocation");
        Query query=reference.orderByChild("providerId");
        query.addListenerForSingleValueEvent(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        final ProviderLocation providerLocation=snapshot.getValue(ProviderLocation.class);

//////////////////////////////////////////////////////////////////////////////////////////////////////

                        DatabaseReference reference2= FirebaseDatabase.getInstance().getReference().child("PalCarWasher")
                                .child("ServiceProvider");
                        Query query2=reference2.orderByChild("providerId");
                        query2.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    for(DataSnapshot dataSnapshot1:snapshot.getChildren()){
                                        ServiceProvider serviceProvider=dataSnapshot1.getValue(ServiceProvider.class);
                                        if(providerLocation.getProviderId().equals(serviceProvider.getProviderId())) {
                                            if (serviceProvider.getCompanyType().equals("stationary")||serviceProvider.getCompanyType().equals("both"))
                                            {

                                                LatLng latLng2 = new LatLng(Double.parseDouble(providerLocation.getLatitudeX()),
                                                        Double.parseDouble(providerLocation.getLongitudeY()));

                                                // Toast.makeText(getApplicationContext(),providerLocation.getLatitudeX()+" , "+providerLocation.getLongitudeY(),Toast.LENGTH_LONG).show();
                                                MarkerOptions markerOptions2 = new MarkerOptions().position(latLng2).position(latLng2)
                                                        .title(serviceProvider.getCompanyName());
                                                googleMap.addMarker(markerOptions2);

                                                //calculate distance:
                                             //   distance = SphericalUtil.computeDistanceBetween(latLng, latLng2);
                                               // Toast.makeText(getApplicationContext(), "The distance to " + serviceProvider.getCompanyName() +
                                                     //   "  is  " + String.valueOf(distance), Toast.LENGTH_LONG).show();

                                            }
                                        }
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //////


        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(" I Am Here. ").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                Toast.makeText(getApplicationContext(), "The coordinates of your location are : "+latLng.latitude+" , "+
                        latLng.longitude, Toast.LENGTH_SHORT).show();

                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
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
                    Toast.makeText(this,"This app requires permission to be granted in " +
                            "order to work properly",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }


    }


}