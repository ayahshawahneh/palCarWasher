package com.example.palcarwasher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class insideOurProject extends AppCompatActivity {
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_our_project);

        //send the new user to database


        databaseReference = FirebaseDatabase.getInstance().getReference().child("PalCarWasher").child("Customer");

        String phonenumber = getIntent().getStringExtra("phonenumber");
        String fullname = getIntent().getStringExtra("fullname");
        String email = getIntent().getStringExtra("email");
        String password = getIntent().getStringExtra("password");
        String Birthdaydate = getIntent().getStringExtra("Birthdaydate");
        String gender = getIntent().getStringExtra("gender");

        String CustomerId = databaseReference.push().getKey();

        Customer customer = new Customer(CustomerId,fullname,phonenumber,password,email,Birthdaydate,gender,null);
        databaseReference.push().setValue(customer);

        findViewById(R.id.bu_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(insideOurProject.this,register.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}