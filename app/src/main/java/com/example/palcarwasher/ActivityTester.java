package com.example.palcarwasher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ActivityTester extends AppCompatActivity {
DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tester);




        //    databaseReference = FirebaseDatabase.getInstance().getReference()
                 //   .child("PalCarWasher").child("Orders");






           // final String orderId = databaseReference.push().getKey();

          //  Orders o=new Orders("-MPq8uV089ZVlR0gDrnW",orderId,"-MPQBYHkwU501cMmJC3p",null,"stationary",null,"Cash","SUN 24/01/2021 01:00 PM-02:00 PM","confirmed","30" );
            //databaseReference.push().setValue(o);









    }
}