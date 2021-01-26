package com.example.palcarwasher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrdersActivity extends AppCompatActivity {

    String customerId;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    OrderAdapter orderAdapter;
    List<Orders> ordersList =new ArrayList<Orders>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        customerId=getIntent().getStringExtra("customerId");

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_orders);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.nav_home:

                        Intent intent = new Intent(OrdersActivity.this,ActivityHome.class);
                        intent.putExtra("customerId",customerId.toString());//new
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;


                    case R.id.nav_orders:

                        return true;



                    case R.id.nav_wallet:
                        Intent intent2 = new Intent(OrdersActivity.this,WalletActivity.class);
                        intent2.putExtra("customerId",customerId.toString());//new
                        startActivity(intent2);
                        overridePendingTransition(0,0);
                        return true;


                    case R.id.nav_profile:
                        Intent intent1 = new Intent(OrdersActivity.this,ProfileActivity.class);
                        intent1.putExtra("customerId",customerId.toString());//new
                        startActivity(intent1);
                        overridePendingTransition(0,0);
                        return true;


                }



                return false;
            }
        });


        recyclerView=findViewById(R.id.recyclerview_orders);
        //recyclerView.setHasFixedSize(true);
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
                            Collections.reverse(ordersList);
                        }

                    }
                }


                orderAdapter=new OrderAdapter(ordersList,OrdersActivity.this);
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