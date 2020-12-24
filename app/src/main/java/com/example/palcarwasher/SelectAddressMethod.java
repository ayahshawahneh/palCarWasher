package com.example.palcarwasher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectAddressMethod extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address_method);


        Button byGps = (Button)findViewById(R.id.Bygps);
        Button Manually = (Button)findViewById(R.id.manually);

        byGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toGps = new Intent(SelectAddressMethod.this,BeforGPS.class);
                startActivity(toGps);
            }
        });


        Manually.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toManually = new Intent(SelectAddressMethod.this,ManuallyAddress.class);
                startActivity(toManually);
            }
        });


    }
}