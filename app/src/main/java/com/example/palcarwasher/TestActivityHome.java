package com.example.palcarwasher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TestActivityHome extends AppCompatActivity {


    RecyclerView recyclerView;

    List<ServiceProvider> providerList;
    ProviderAdapter providerAdapter;
    DatabaseReference databaseReference;
    SobspAdapter sobspAdapter;
    DatabaseReference databaseReference2;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_home);

        final String selectedCompanyType="stationary";
        final String selectedVehicle="Car(5-pass)";





        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));





        providerList=new ArrayList<>();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("PalCarWasher").child("ServiceProvider");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {

                    final ServiceProvider prov=ds.getValue(ServiceProvider.class);
                    if(prov.getCompanyType().equals(selectedCompanyType)||prov.getCompanyType().equals("both")){


                        providerList.add(prov);

                    }

                }


                ;

                providerAdapter=new ProviderAdapter(providerList,selectedVehicle);
                recyclerView.setAdapter(providerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });

        //Log.v("DataOB",providerList.get(2).getCompanyName());
        //Toast.makeText(this, providerList.get(2).getCompanyName(), Toast.LENGTH_LONG).show();
        //  Toast.makeText(this, "iam from test home activity", Toast.LENGTH_LONG).show();
    }
}