package com.example.palcarwasher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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


   FloatingActionButton floatingActionButton ;
   FloatingActionButton floatingButtonFilter ;
   String filtering;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

  //customerId=getIntent().getStringExtra("customerId");
       // Toast.makeText(getApplicationContext(),customerId, Toast.LENGTH_LONG).show();

        filtering ="none";


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

/////////////////////////////////////////////////////////
        floatingActionButton=findViewById(R.id.floating_location);


////////////////////////////////////////////////////////
       compTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView adapter, View v, int i, long lng) {

                filtering ="none";


                selectedCompanyType =  compTypeSpinner.getSelectedItem()+"";
                changeView(selectedVehicle,selectedCompanyType,filtering );

////////........................
                if(selectedCompanyType.equals("stationary")){
                    floatingActionButton.setVisibility(View.VISIBLE);
                    floatingActionButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent =new Intent(ActivityHome.this,ShowAllCompanyOnMap.class);
                            startActivity(intent);
                        }
                    });
                }
                else{

                    floatingActionButton.setVisibility(View.GONE);


                }


//////////////////////...............................



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


                filtering ="none";

                selectedVehicle =  vehicleSpinner.getSelectedItem()+"";

                changeView(selectedVehicle,selectedCompanyType,filtering );


            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView)
            {

            }
        });


///////////////////////////////////////////**********************************/////////////////////////










floatingButtonFilter=findViewById(R.id.floating_filter);
floatingButtonFilter.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        AlertDialogFilter cdd=new AlertDialogFilter();

        cdd.show();
        Window window = cdd.getWindow();
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);




    }
});











    }

    void changeView(final String selecteVehicle, final String selecteCompanyType, final String filter ){


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


                if(filter.equals("ascending")){

                    Toast.makeText(ActivityHome.this,"" +
                            "lateron!", Toast.LENGTH_LONG).show();

                }

                else if(filter.equals("descending")){


                    Toast.makeText(ActivityHome.this,"" +
                            "lateron!", Toast.LENGTH_LONG).show();

                }

              else if(filter.equals("rating")){

                    Collections.sort(providerList, new Comparator<ServiceProvider>()
                    {
                        @Override
                        public int compare(ServiceProvider o1, ServiceProvider o2) {
                          //  Double rate1 = Double.valueOf(o1.getEvaluationLevel());
                          //  Double rate2 = Double.valueOf(o2.getEvaluationLevel());
                          /*  if (rate1.compareTo(rate2) < 0) {
                                return 1;
                            } else if (rate1.compareTo(rate2) > 0) {
                                return -1;
                            } else {
                                return 0;
                            }*/

                          //  return rate1 > rate2 ? 1 : (rate1 < rate2 ) ? -1 : 0;


                            return Double.compare(  Double.parseDouble(o1.getEvaluationLevel()),Double.parseDouble(o2.getEvaluationLevel()) );
                        }


                    });


                }

                providerAdapter=new ProviderAdapter(providerList,selecteVehicle,selectedCompanyType,customerId);
                recyclerView.setAdapter(providerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });






    }




    public class AlertDialogFilter extends Dialog implements
            android.view.View.OnClickListener {


        RadioButton ratting;
        RadioButton ascending;
        RadioButton descending;
        Button aply;


        String selection;

        public AlertDialogFilter( ) {
            super(ActivityHome.this);

        }
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            setContentView(R.layout.filter_view_home_activity);




            ratting=findViewById(R.id.ratting);
            ascending=findViewById(R.id.ascending);
            descending=findViewById(R.id.descending);
            aply=findViewById(R.id.aply);


           if(ratting.isChecked())filtering="rating";
           else if(ascending.isChecked())filtering="ascending";
           else if(descending.isChecked())filtering="descending";


           aply.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            changeView(selectedVehicle,selectedCompanyType,filtering);
            dismiss();

        }




        void changeView(final String selecteVehicle, final String selecteCompanyType, final String filter ){


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


                    if(filter.equals("ascending")){

                        Toast.makeText(ActivityHome.this,"" +
                                "lateron!", Toast.LENGTH_LONG).show();

                    }

                    else if(filter.equals("descending")){


                        Toast.makeText(ActivityHome.this,"" +
                                "lateron!", Toast.LENGTH_LONG).show();

                    }

                    else if(filter.equals("rating")){

                        Collections.sort(providerList, new Comparator<ServiceProvider>()
                        {
                            @Override
                            public int compare(ServiceProvider o1, ServiceProvider o2) {
                                //  Double rate1 = Double.valueOf(o1.getEvaluationLevel());
                                //  Double rate2 = Double.valueOf(o2.getEvaluationLevel());
                          /*  if (rate1.compareTo(rate2) < 0) {
                                return 1;
                            } else if (rate1.compareTo(rate2) > 0) {
                                return -1;
                            } else {
                                return 0;
                            }*/

                                //  return rate1 > rate2 ? 1 : (rate1 < rate2 ) ? -1 : 0;


                                return Double.compare(  Double.parseDouble(o1.getEvaluationLevel()),Double.parseDouble(o2.getEvaluationLevel()) );
                            }


                        });


                    }

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











}