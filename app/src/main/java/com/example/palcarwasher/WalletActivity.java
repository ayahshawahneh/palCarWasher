package com.example.palcarwasher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class WalletActivity extends AppCompatActivity {
    String customerId;
    TextView total,dept;
    double sumTotal,sumDept;
List<Orders> ordersList =new ArrayList<Orders>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        customerId=getIntent().getStringExtra("customerId");
      //  customerId="-MPQBYHkwU501cMmJC3p";


        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_wallet);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.nav_home:

                        Intent intent = new Intent(WalletActivity.this,ActivityHome.class);
                        intent.putExtra("customerId",customerId.toString());//new
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;


                    case R.id.nav_orders:
                        Intent intent1 = new Intent(WalletActivity.this,OrdersActivity.class);
                        intent1.putExtra("customerId",customerId.toString());//new
                        startActivity(intent1);
                        overridePendingTransition(0,0);
                        return true;



                    case R.id.nav_wallet:

                        return true;


                    case R.id.nav_profile:
                        Intent intent2 = new Intent(WalletActivity.this,ProfileActivity.class);
                        intent2.putExtra("customerId",customerId.toString());//new
                        startActivity(intent2);
                        overridePendingTransition(0,0);
                        return true;


                }



                return false;
            }
        });
/////////////////////////////////////////////////////////////////
     total=findViewById(R.id.all_total);
     dept=findViewById(R.id.dept);




        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("PalCarWasher")
                .child("Orders");

        reference.addListenerForSingleValueEvent(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ordersList.clear();
                sumTotal=0;
                sumDept=0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Orders o = snapshot.getValue(Orders.class);

                    if (o.getCustomerId().equals(customerId)) {

                            if (o.getStatus().equals("canceled"))
                                sumTotal += Double.parseDouble(o.getTotalPrice())/2;
                            else
                                sumTotal += Double.parseDouble(o.getTotalPrice());


                        if (o.getStatus().equals("canceled")&&o.getPaymentType().equals("Cash"))
                            sumDept+=Double.parseDouble(o.getTotalPrice())/2;



                    }


                }

                dept.setText(sumDept+"$");
                total.setText(sumTotal+"$");





            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });











    }
}