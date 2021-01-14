package com.example.palcarwasher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class OrdersActivity extends AppCompatActivity {

    String customerId;



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


    }
}