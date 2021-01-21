package com.example.palcarwasher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityTester extends AppCompatActivity {
DatabaseReference databaseReference;
    RecyclerView recyclerView;
    OrderAdapter orderAdapter;
    List<Orders> ordersList =new ArrayList<Orders>();
    String customerId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tester);

/*
    private String providerId;
    private String orderId;
    private String customerId;
    private String visaId;
    private String orderType;
    private String cleanAddress;
    private String paymentType;
    private String fullTime;
    private String status;
    private String totalPrice;

    List<String> offerIds;

        List<String> offerIds=new ArrayList<String>();
        offerIds.add("-MPq9CMryyaieSLPdAaH");
        offerIds.add("-MPq9CMweziFXu-dSUYa");

          databaseReference = FirebaseDatabase.getInstance().getReference()
                 .child("PalCarWasher").child("Orders");
         final String orderId = databaseReference.push().getKey();

           Orders o=new Orders("-MPq8uV089ZVlR0gDrnW",orderId,"-MPQBYHkwU501cMmJC3p",null,"stationary","main street/selat Al-Harthia/jenin","Cash","Sun 24/01/2021 01:00 PM-02:00 PM","completed","20","Car(5-pass)",offerIds);
        databaseReference.push().setValue(o);*/

     //  ServicesInEachOrder o2 =new ServicesInEachOrder("-MRWqI3MKHw6bxtJrgSR",offerIds);


     // databaseReference.push().setValue(o);

/////////////////////////////////
        customerId="-MPQBYHkwU501cMmJC3p";


 recyclerView=findViewById(R.id.recyclerview_orders);
 recyclerView.setHasFixedSize(true);
recyclerView.setLayoutManager(new LinearLayoutManager(this));





        databaseReference= FirebaseDatabase.getInstance().getReference().child("PalCarWasher").child("Orders");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ordersList.clear();
            if(dataSnapshot.exists()) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                   final Orders o = ds.getValue(Orders.class);
                      if (o.getCustomerId().equals(customerId)) {


                         //  Log.v("DataOB",o.getOfferIds().get(1)) ;

                         ordersList.add(o);

                       }

                }
            }


            orderAdapter=new OrderAdapter(ordersList,ActivityTester.this);
              //  orderAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(orderAdapter);
           orderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });






/////////////////////////////

    }
}