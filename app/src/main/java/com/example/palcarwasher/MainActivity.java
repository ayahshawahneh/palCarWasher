package com.example.palcarwasher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");

        Toast.makeText(this,"Aosome",Toast.LENGTH_SHORT).show();

        Toast.makeText(this,"Hi from shatha1",Toast.LENGTH_SHORT).show();
        Toast.makeText(this,"Hi from shatha2",Toast.LENGTH_SHORT).show();

    }
}
