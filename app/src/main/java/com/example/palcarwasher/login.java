package com.example.palcarwasher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroupOverlay;
import android.widget.Button;
import android.widget.Toast;

public class login extends AppCompatActivity {
    private Button RegAsCustomer;
    private Button RegAsCompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //welcome logo:
        LayoutInflater myinflater = getLayoutInflater();
        View myView = myinflater.inflate(R.layout.palcarwasherwelcomelogo, null);
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.FILL,0,0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(myView);
        toast.show();
        //end welcome logo


        RegAsCustomer = (Button)findViewById(R.id.reg_as_customer);
        RegAsCompany = (Button)findViewById(R.id.reg_as_company);

        RegAsCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, register.class);
                startActivity(intent);
            }
        });



        RegAsCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, RegisterAsCompany.class);
                startActivity(intent);
            }
        });

    }
}
