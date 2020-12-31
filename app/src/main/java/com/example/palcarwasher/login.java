package com.example.palcarwasher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {
    private Button RegAsCustomer;
    private Button RegAsCompany;
    private Button Login;
    private EditText PhoneNumber;
    private EditText Password;


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
        Login = (Button)findViewById(R.id.btn_login);
        PhoneNumber = (EditText)findViewById(R.id.phone_number);
        Password = (EditText)findViewById(R.id.et_password);



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

       Login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String phonenum=PhoneNumber.getText().toString();
               String password=Password.getText().toString();
               if(phonenum.isEmpty()){
                   //Toast.makeText(getApplicationContext(),"Please Enter your Phone number",Toast.LENGTH_LONG).show();
                   PhoneNumber.setError("Please Enter your Phone number");
                   PhoneNumber.requestFocus();
                   return;
               }
               if(password.isEmpty()){
                   //Toast.makeText(getApplicationContext(),"Please Enter your Password",Toast.LENGTH_LONG).show();
                   Password.setError("Please Enter your Password");
                   Password.requestFocus();
                   return;

               }
               if(phonenum.isEmpty() && password.isEmpty()){
                   //Toast.makeText(getApplicationContext(),"Please Enter your  Phone number and Password",Toast.LENGTH_LONG).show();
                   PhoneNumber.setError("Please Enter your Phone number");
                   PhoneNumber.requestFocus();
                   Password.setError("Please Enter your Password");
                   Password.requestFocus();
                   return;

               }
               else {
                   String phoneNumberr = "+" + phonenum;
                   isexist1( phoneNumberr);
               }
           }
       });

    }

    private void isexist1(final String phoneNumberr) {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("PalCarWasher")
                .child("Customer");
        Query query=reference.orderByChild("phoneNumber").equalTo(phoneNumberr);
        query.addListenerForSingleValueEvent(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    //Toast.makeText(getApplicationContext(), "your in database1", Toast.LENGTH_LONG).show();
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Customer customer=snapshot.getValue(Customer.class);
                        //Toast.makeText(getApplicationContext(),customer.getPassword(),Toast.LENGTH_LONG).show();
                        if(Password.getText().toString().equals(customer.getPassword())){
                            Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(login.this,insideOurProject.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "your password is not correct !", Toast.LENGTH_LONG).show();
                            Password.setError("Password not correct");
                            Password.requestFocus();
                        }
                    }
                    // progressBar.setVisibility(View.GONE);
                    }

                else
                    isexist2(phoneNumberr);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    private void isexist2(String phoneNumberr){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("PalCarWasher")
                .child("ServiceProvider");
        Query query=reference.orderByChild("phoneNumber").equalTo(phoneNumberr);
        query.addListenerForSingleValueEvent(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    //Toast.makeText(getApplicationContext(), "your in database2", Toast.LENGTH_LONG).show();
                    // progressBar.setVisibility(View.GONE);
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){

                        ServiceProvider serviceProvider=snapshot.getValue(ServiceProvider.class);
                        //Toast.makeText(getApplicationContext(),customer.getPassword(),Toast.LENGTH_LONG).show();

                        if(Password.getText().toString().equals(serviceProvider.getPassword())){
                            Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(login.this,insideOurProject.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                        else {
                               Toast.makeText(getApplicationContext(), "your password is not correct !", Toast.LENGTH_LONG).show();
                            Password.setError("Password not correct");
                            Password.requestFocus();
                            return;
                        }

                    }
                }

                else  Toast.makeText(getApplicationContext(), "you dont have an a account ", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
