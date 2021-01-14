package com.example.palcarwasher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class EditCustomerProfile extends AppCompatActivity {
    EditText Name;
    EditText Email;
    TextView Gender;
    TextView BirthdayDate;
    TextView PhoneNumber;
    EditText Password;
    Button Modify;
    ImageView back;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_customer_profile);
        Name = (EditText)findViewById(R.id.name);
        Email = (EditText)findViewById(R.id.email);
        Gender = (TextView)findViewById(R.id.gend);
        BirthdayDate = (TextView)findViewById(R.id.birthdayDate);
        PhoneNumber = (TextView)findViewById(R.id.phoneNub);
        Password = (EditText) findViewById(R.id.passwordd);
        Modify = (Button)findViewById(R.id.Modify);
        back = (ImageView)findViewById(R.id.backkkkkk);


        final String phonenumber = getIntent().getStringExtra("phonenumber");
        final String fullname = getIntent().getStringExtra("fullname");
        final String email = getIntent().getStringExtra("email");
        final String password = getIntent().getStringExtra("password");
        final String Birthdaydate = getIntent().getStringExtra("Birthdaydate");
        final String gender = getIntent().getStringExtra("gender");
        final String customerId = getIntent().getStringExtra("Id");

        Name.setText(fullname);
        Email.setText(email);
        BirthdayDate.setText(Birthdaydate);
        Gender.setText(gender);
        PhoneNumber.setText(phonenumber);
        Password.setText(password);


        Modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference = FirebaseDatabase.getInstance().getReference().child("PalCarWasher").child("Customer");
                final String EditName=Name.getText().toString();
                final String EditEmail = Email.getText().toString();
                final String EditPassword = Password.getText().toString();

                final DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("PalCarWasher")
                        .child("Customer");
                Query query=reference.orderByChild("customerId").equalTo(customerId);
                query.addListenerForSingleValueEvent(new ValueEventListener(){

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){
                            for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                                Customer customer=snapshot.getValue(Customer.class);

                                customer.setEmail(EditEmail);
                                customer.setName(EditName);
                                customer.setPassword(EditPassword);
                                databaseReference.push().setValue(customer); //new child !!!!!
                                reference.child(snapshot.getKey()).removeValue();

                                // Toast.makeText(getApplicationContext(),snapshot.getKey(),Toast.LENGTH_LONG).show();
                                Toast.makeText(getApplicationContext(),"Done ",Toast.LENGTH_LONG).show();


                            }

                        }



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toCustomerProfile = new Intent(EditCustomerProfile.this,ProfileActivity.class);
                startActivity(toCustomerProfile);
            }
        });



    }
}