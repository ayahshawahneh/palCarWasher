package com.example.palcarwasher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityHome extends AppCompatActivity {

    DatabaseReference reference;

    Spinner vehicleSpinner;
    ArrayList<String> vehicleList = new ArrayList<String>();
    ArrayAdapter<String> vehicleArrayAdapter ;

    Spinner compTypeSpinner;
    ArrayList<String> compTypeList = new ArrayList<>();
    ArrayAdapter<String> compTypeArrayAdapter ;




    RecyclerView recyclerView;
    ProviderAdapter providerAdapter;
    SobspAdapter sobspAdapter;
    DatabaseReference databaseReference2;

   String selectedCompanyType;
   String selectedVehicle;
   String customerId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

  //customerId=getIntent().getStringExtra("customerId");
       // Toast.makeText(getApplicationContext(),customerId, Toast.LENGTH_LONG).show();

        customerId="-MPQBYHkwU501cMmJC3p";
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_home);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.nav_home:

                        return true;


                    case R.id.nav_orders:
                        Intent intent1 = new Intent(ActivityHome.this,OrdersActivity.class);
                        intent1.putExtra("customerId",customerId.toString());//new
                        startActivity(intent1);
                        overridePendingTransition(0,0);
                        return true;



                    case R.id.nav_wallet:
                        Intent intent2 = new Intent(ActivityHome.this,WalletActivity.class);
                        intent2.putExtra("customerId",customerId.toString());//new
                        startActivity(intent2);
                        overridePendingTransition(0,0);
                        return true;


                    case R.id.nav_profile:
                        Intent intent3 = new Intent(ActivityHome.this,ProfileActivity.class);
                        intent3.putExtra("customerId",customerId.toString());//new
                        startActivity(intent3);
                        overridePendingTransition(0,0);
                        return true;


                }



                return false;
            }
        });






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


        vehicleArrayAdapter = new ArrayAdapter<String>(ActivityHome.this,
                android.R.layout.simple_spinner_dropdown_item, vehicleList);
        vehicleArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        vehicleSpinner = findViewById(R.id.vehicleSpinner);
        vehicleSpinner.setAdapter(vehicleArrayAdapter);
        vehicleArrayAdapter.notifyDataSetChanged();

//////////////////////////////////////////////////////////////////



        compTypeList.add("stationary");
        compTypeList.add("mobile");
        compTypeArrayAdapter = new ArrayAdapter<String>(ActivityHome.this,
                android.R.layout.simple_spinner_dropdown_item, compTypeList);
        compTypeArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        compTypeSpinner = findViewById(R.id.compTypeSpinner);
        compTypeSpinner.setAdapter(compTypeArrayAdapter);
        compTypeArrayAdapter.notifyDataSetChanged();

////////////////////////////////////////////////////////////////////////////////////////



        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
      selectedCompanyType=compTypeSpinner.getSelectedItem()+"";
      selectedVehicle=vehicleSpinner.getSelectedItem()+"";


      final  List<ServiceProvider> providerList;

        providerList=new ArrayList<>();


        databaseReference2= FirebaseDatabase.getInstance().getReference().child("PalCarWasher").child("ServiceProvider");
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
providerList.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {

                    final ServiceProvider prov=ds.getValue(ServiceProvider.class);
                    if(prov.getCompanyType().equals(selectedCompanyType)||prov.getCompanyType().equals("both")){


                        providerList.add(prov);

                    }

                }




                providerAdapter=new ProviderAdapter(providerList,selectedVehicle,selectedCompanyType,customerId);
                recyclerView.setAdapter(providerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });



       compTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView adapter, View v, int i, long lng) {

                selectedCompanyType =  compTypeSpinner.getSelectedItem()+"";
                changeView(selectedVehicle,selectedCompanyType );





            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView)
            {

            }
        });



/////************************************************************************/////////

      vehicleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView adapter, View v, int i, long lng) {

                selectedVehicle =  vehicleSpinner.getSelectedItem()+"";

                changeView(selectedVehicle,selectedCompanyType );


            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView)
            {

            }
        });


///////////////////////////////////////////**********************************/////////////////////////





















    }

    void changeView(final String selecteVehicle, final String selecteCompanyType ){


final  List<ServiceProvider> providerList;

        providerList=new ArrayList<>();

        databaseReference2= FirebaseDatabase.getInstance().getReference().child("PalCarWasher").child("ServiceProvider");
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                providerList.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {

                    final ServiceProvider prov=ds.getValue(ServiceProvider.class);
                    if(prov.getCompanyType().equals(selecteCompanyType)||prov.getCompanyType().equals("both")){


                        providerList.add(prov);

                    }

                }


                ;

                providerAdapter=new ProviderAdapter(providerList,selecteVehicle,selectedCompanyType,customerId);
                recyclerView.setAdapter(providerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });






    }





}