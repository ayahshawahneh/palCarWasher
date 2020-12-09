package com.example.palcarwasher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class insideOurProjectCompany extends AppCompatActivity {
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_our_project_company);

        //send the new user to database


        databaseReference = FirebaseDatabase.getInstance().getReference().child("PalCarWasher").child("ServiceProvider");


        String PhoneNumber = getIntent().getStringExtra("phonenumber");
        String ownerName = getIntent().getStringExtra("ownername");
        String email = getIntent().getStringExtra("email");
        String password = getIntent().getStringExtra("password");
        String Birthdaydate = getIntent().getStringExtra("Birthdaydate");
        String Gender = getIntent().getStringExtra("Gender");
        String companyName = getIntent().getStringExtra("companyname");
        String companyType = getIntent().getStringExtra("companyType");

        String ProviderId = databaseReference.push().getKey();
        //(String providerId, String companyName, String companyType, String logo, String name, String email, String password, String phoneNumber, String address, String gender, String bankAccount, String workingStatus)
        ServiceProvider company = new ServiceProvider( ProviderId, companyName, companyType,null, ownerName, email, password, PhoneNumber,null,Gender,null,null);
        databaseReference.push().setValue(company);

        findViewById(R.id.bu_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(insideOurProjectCompany.this,login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}