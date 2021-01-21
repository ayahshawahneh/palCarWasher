package com.example.palcarwasher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {



    String customerId;
    Intent intentToEditCustomerProfile;


    TableRow itemLogout;
    TableRow appRate;
    TableRow  editProfile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);



        customerId=getIntent().getStringExtra("customerId");

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_profile);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.nav_home:

                        Intent intent = new Intent(ProfileActivity.this,ActivityHome.class);
                        intent.putExtra("customerId",customerId.toString());//new
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;


                    case R.id.nav_orders:
                        Intent intent1 = new Intent(ProfileActivity.this,OrdersActivity.class);
                        intent1.putExtra("customerId",customerId.toString());//new
                        startActivity(intent1);
                        overridePendingTransition(0,0);
                        return true;



                    case R.id.nav_wallet:
                        Intent intent2 = new Intent(ProfileActivity.this,WalletActivity.class);
                        intent2.putExtra("customerId",customerId.toString());//new
                        startActivity(intent2);
                        overridePendingTransition(0,0);
                        return true;


                    case R.id.nav_profile:

                        return true;


                }



                return false;
            }
        });






        itemLogout = findViewById(R.id.itemLogout);
        appRate = findViewById(R.id.appRate);
        editProfile = findViewById(R.id.editProfile);



////////////////////////////////////////////////////////////

    editProfile.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("PalCarWasher").child("Customer");
            Query query = reference.orderByChild("customerId").equalTo(customerId );
            query.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Customer customer = snapshot.getValue(Customer.class);
                            intentToEditCustomerProfile = new Intent(ProfileActivity.this, EditCustomerProfile.class);
                            intentToEditCustomerProfile.putExtra("phonenumber", customer.getPhoneNumber());
                            intentToEditCustomerProfile.putExtra("fullname", customer.getName());
                            intentToEditCustomerProfile.putExtra("email", customer.getEmail());
                            intentToEditCustomerProfile.putExtra("password",customer.getPassword() );
                            intentToEditCustomerProfile.putExtra("Birthdaydate", customer.getBirthday());
                            intentToEditCustomerProfile.putExtra("gender", customer.getGender());
                            intentToEditCustomerProfile.putExtra("Id", customer.getCustomerId());
                            startActivity(intentToEditCustomerProfile);
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }
    });

////////////////////////////////////////////////////////////









        appRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,AppRate.class);
                startActivity(intent);
            }
        });
        itemLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ProfileActivity.this,login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });







    }








}