package com.example.palcarwasher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectAddressMethod extends AppCompatActivity {
    String ProviderId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address_method);

     ProviderId=getIntent().getStringExtra("ProviderId");
        Button byGps = (Button)findViewById(R.id.Bygps);


        byGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toGps = new Intent(SelectAddressMethod.this,GPSAddress.class);

        toGps.putExtra("ProviderId", ProviderId);

                startActivity(toGps);
            }
        });





    }
}