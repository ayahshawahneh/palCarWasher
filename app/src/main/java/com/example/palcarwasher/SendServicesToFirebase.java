package com.example.palcarwasher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SendServicesToFirebase extends AppCompatActivity {
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_services_to_firebase);




        databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("PalCarWasher").child("ServicesOfferedByServiceProviders");
        String phonenumber = getIntent().getStringExtra("phonenumber");
        String fullname = getIntent().getStringExtra("fullname");
        String email = getIntent().getStringExtra("email");
        String password = getIntent().getStringExtra("password");
        String Birthdaydate = getIntent().getStringExtra("Birthdaydate");
        String gender = getIntent().getStringExtra("gender");

        /*
private String offerId;
    private String providerId;
    private String vehicleId;
    private String description;
    private String price;
    private boolean discountOnOff;
    private String discountId;*/

        String CustomerId = databaseReference.push().getKey();

        Customer customer = new Customer(CustomerId,fullname,phonenumber,password,email,Birthdaydate,gender,null);
        databaseReference.push().setValue(customer);













    }
}