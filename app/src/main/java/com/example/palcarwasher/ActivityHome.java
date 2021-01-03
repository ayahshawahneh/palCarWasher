package com.example.palcarwasher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActivityHome extends AppCompatActivity {


    DatabaseReference reference;

    String   ProviderId;


    Spinner vehicleSpinner;
    ArrayList<String> vehicleList = new ArrayList<String>();
    ArrayAdapter<String> vehicleArrayAdapter ;



    Spinner compTypeSpinner;
    ArrayList<String> compTypeList = new ArrayList<>();
    ArrayAdapter<String> compTypeArrayAdapter ;




    ArrayList<ServiceProvider> providersList =new ArrayList<ServiceProvider>();
    ArrayList<String> providersIdList =new ArrayList<String>();
    ArrayList<ServicesOfferedByServiceProviders> sobpList = new ArrayList<ServicesOfferedByServiceProviders>();
    int count=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_home);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.nav_home:

                        return true;


                    case R.id.nav_orders:
                        startActivity(new Intent(getApplicationContext(), OrdersActivity.class));
                        overridePendingTransition(0,0);
                        return true;



                    case R.id.nav_wallet:
                        startActivity(new Intent(getApplicationContext(),WalletActivity.class));
                        overridePendingTransition(0,0);
                        return true;


                    case R.id.nav_profile:
                        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;


                }



                return false;
            }
        });



        // ProviderId=getIntent().getStringExtra("ProviderId");


        ///////////////////////////////////////////////////////


      vehicleList.add("Car(5-pass)");
        reference= FirebaseDatabase.getInstance().getReference().child("PalCarWasher")
                .child("VehicleType");
        Query query=reference.orderByChild("size");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String vehicleType = snapshot.getValue(VehicleType.class).getSize();

                    if(!vehicleType.equals("Car(5-pass)"))
                        vehicleList.add(vehicleType);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //vehicleList.remove(0);
        vehicleArrayAdapter = new ArrayAdapter<String>(ActivityHome.this,
                android.R.layout.simple_spinner_dropdown_item, vehicleList);
        vehicleArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        vehicleSpinner = findViewById(R.id.vehicleSpinner);
        vehicleSpinner.setAdapter(vehicleArrayAdapter);
        vehicleArrayAdapter.notifyDataSetChanged();

//////////////////////////////////////////////////////////////////


       // compTypeList.add("Company Type");
        compTypeList.add("Stationary");
        compTypeList.add("Mobile");
        compTypeArrayAdapter = new ArrayAdapter<String>(ActivityHome.this,
                android.R.layout.simple_spinner_dropdown_item, compTypeList);
        compTypeArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        compTypeSpinner = findViewById(R.id.compTypeSpinner);
        compTypeSpinner.setAdapter(compTypeArrayAdapter);
        compTypeArrayAdapter.notifyDataSetChanged();



       reference= FirebaseDatabase.getInstance().getReference().child("PalCarWasher")
                .child("ServiceProvider");
        Query query2=reference;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ServiceProvider provider = snapshot.getValue(ServiceProvider.class);
                    providersList.add(provider);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        String selectedComType=compTypeSpinner.getSelectedItem()+"";
        String selectedVehicle=vehicleSpinner.getSelectedItem()+"";



        reference= FirebaseDatabase.getInstance().getReference().child("PalCarWasher")
                .child("ServicesOfferedByServiceProviders");
        Query query3=reference.orderByChild("providerId");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ServicesOfferedByServiceProviders sobp = snapshot.getValue(ServicesOfferedByServiceProviders.class);
                    sobpList.add(sobp);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




       /* for(int i=0;i<providersList.size();i++){


         providersList.get(i).getProviderId();

        }*/







    }
}