package com.example.palcarwasher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

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

    LinearLayout parentLinearLayout;


    DatabaseReference reference;

    String   ProviderId;


    Spinner vehicleSpinner;
    ArrayList<String> vehicleList = new ArrayList<String>();
    ArrayAdapter<String> vehicleArrayAdapter ;



    Spinner compTypeSpinner;
    ArrayList<String> compTypeList = new ArrayList<>();
    ArrayAdapter<String> compTypeArrayAdapter ;





    ArrayList<ServicesOfferedByServiceProviders> sobpList = new ArrayList<ServicesOfferedByServiceProviders>();
    int count=0;


     List<ServiceProvider> providersList =new ArrayList<ServiceProvider>();
    ListView provdersView;
    ArrayAdapter<ServiceProvider> ProvidersAdapter ;

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

////////////////////////////////////////////////////////////////////////////////////////
        final View mainView = getLayoutInflater().inflate(R.layout.activity_form_of_the_services, null);



        //fillProvidersArray();

       // ServiceProvider nullProvider=new ServiceProvider();
      // nullProvider.setCompanyName("shatha comp");
    //  providersList.add(nullProvider);
       // ArrayAdapter<ServiceProvider> providerAddapt=new ArrayAdapter<ServiceProvider>(this,R.id.parent_linear_layout , vehicleList);
/*
        provdersView=findViewById(R.id.providers_adapter);
        ProvidersAdapter=new ArrayAdapter<ServiceProvider>(this,android.R.layout.simple_list_item_1,providersList);
        provdersView.setAdapter(ProvidersAdapter);

        FirebaseDatabase database =FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=database.getReference();
        databaseReference.child("PalCarWasher").child("ServiceProvider").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                ServiceProvider provider =snapshot.getValue(ServiceProvider.class);
                providersList.add(provider);
                ProvidersAdapter.notifyDataSetChanged();


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ProvidersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


*/



        parentLinearLayout=(LinearLayout) findViewById(R.id.parent_linear_layout);

        String selectedComType=compTypeSpinner.getSelectedItem()+"";
        String selectedVehicle=vehicleSpinner.getSelectedItem()+"";


       /* for(int i=0;i<providersList.size();i++){

         String coType = providersList.get(i).getCompanyType();
         String providerId=providersList.get(i).getProviderId() +"";
        if(coType.equals(selectedComType)||coType.equals("Both")){

            //providersIdList.add(providerId);

            ArrayList<ServicesOfferedByServiceProviders> sobpToViewList = new ArrayList<ServicesOfferedByServiceProviders>();
            for(int j=0;j<sobpList.size();j++){

                if(sobpList.get(j).getProviderId().equals(providerId)&&sobpList.get(j).getVehicleName().equals(selectedVehicle)){

                    sobpToViewList.add(sobpList.get(j));

                }


            }


            onAddCompany(mainView,providersList.get(i),sobpToViewList);


        }

        }*/


      /* for(int i=0;i<providersIdList.size();i++){



           for(int j=0;j<sobpList.size();j++){
                String sobpProviderId=sobpList.get(j).getProviderId();
               if(providersIdList.get(i).equals(sobpProviderId)){




               }



           }



       }*/




      Log.v("DataOB",providersList.get(0).getCompanyName());


 //onAddCompany(mainView,providersList.get(0));
    //onAddCompany(mainView,providersList.get(1));

       // onAddCompany(mainView);
    }


    public void onAddCompany(View v,ServiceProvider provider) {
        LayoutInflater inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View companyView=inflater.inflate(R.layout.company_view, null,false);
        parentLinearLayout.addView(companyView, parentLinearLayout.getChildCount()-0);



        LinearLayout companyLinearLayout;

        companyLinearLayout= parentLinearLayout.findViewById(R.id.company_layout);
        companyLinearLayout.setId(count);



        onAddService(companyLinearLayout,provider);



    }

    public void onAddService( LinearLayout companyLinearLayout,ServiceProvider provider) {

        TextView companyName=companyLinearLayout.findViewById(R.id.company_name_name);
        companyName.setText(provider.getCompanyName());
        LayoutInflater inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View serviceInCompany=inflater.inflate(R.layout.services_in_company_view, null,false);
        companyLinearLayout.addView(serviceInCompany,companyLinearLayout.getChildCount()-0);
        count++;
    }



    void fillProvidersArray(){

        FirebaseDatabase database =FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=database.getReference();
        databaseReference.child("PalCarWasher").child("ServiceProvider").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                providersList.clear();
                for(DataSnapshot child : children){
                    ServiceProvider provider =child.getValue(ServiceProvider.class);
                    //  providersList.add(provider);
                    sendProviderToProvidersList(provider);
                    //onAddCompany(mainView,provider);

                    // Log.v("DataOB",provider.getCompanyName());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }










    void sendProviderToProvidersList(ServiceProvider provider){


        providersList.add(provider);




    }

}