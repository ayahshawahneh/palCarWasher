package com.example.palcarwasher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class EditServiceProviderProfile extends AppCompatActivity {
    EditText companyName;
    EditText FullName;
    EditText Email;
    TextView PhoneNumber;
    TextView Gender;
    EditText Address;
    EditText Password;
    Button Modify;

    DatabaseReference databaseReference;

    String providerId ;


    private RadioButton Mobile;
    private RadioButton Stationary;
    private RadioButton Both;
    RadioGroup rg;
    String newSelectionType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_service_provider_profile);




        providerId=getIntent().getStringExtra("providerId");


        Mobile = (RadioButton) findViewById(R.id.mobile);
        Stationary = (RadioButton) findViewById(R.id.stationary);
        Both = (RadioButton) findViewById(R.id.both);

        companyName = findViewById(R.id.companyName);
        FullName = findViewById(R.id.fullName);
        Email = findViewById(R.id.Email);
        PhoneNumber = findViewById(R.id.PhoneNumber);
        Gender = findViewById(R.id.Gender);
        Address = findViewById(R.id.Address);
        Password = findViewById(R.id.Password);
        Modify = (Button) findViewById(R.id.Modify);


////////////////////////


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("PalCarWasher").child("ServiceProvider");


        reference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ServiceProvider serviceProvider = snapshot.getValue(ServiceProvider.class);

                        if (serviceProvider.getProviderId().equals(providerId)) {



                            companyName.setText(serviceProvider.getCompanyName());
                            FullName.setText(serviceProvider.getName());
                            Email.setText(serviceProvider.getEmail());
                            PhoneNumber.setText(serviceProvider.getPhoneNumber());
                            Gender.setText(serviceProvider.getGender());
                            Address.setText(serviceProvider.getAddress());
                            Password.setText(serviceProvider.getPassword());
                            if (serviceProvider.getCompanyType().equals("mobile"))
                                Mobile.setChecked(true);
                            else if (serviceProvider.getCompanyType().equals("stationary"))
                                Stationary.setChecked(true);
                            else if (serviceProvider.getCompanyType().equals("both"))
                                Both.setChecked(true);



                        }


                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


/////////////////////////////

        //Toast.makeText(getApplicationContext(),companyType,Toast.LENGTH_LONG).show();








        rg = (RadioGroup) findViewById(R.id.rg);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.mobile:

                        newSelectionType = "mobile";





                        break;
                    case R.id.stationary:


                        newSelectionType= "stationary";





                        break;
                    case R.id.both:

                        newSelectionType= "both";




                        break;

                }
            }
        });

        Modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference = FirebaseDatabase.getInstance().getReference().child("PalCarWasher").child("ServiceProvider");

                //field that can modify:

                final String EditName1 = FullName.getText().toString();
                final String EditEmail1 = Email.getText().toString();
                final String EditAddress1 = Address.getText().toString();
                final String EditPassword1 = Password.getText().toString();








                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("PalCarWasher")
                        .child("ServiceProvider");
                Query query = reference.orderByChild("providerId").equalTo(providerId);

                query.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                ServiceProvider serviceProvider = snapshot.getValue(ServiceProvider.class);

                                serviceProvider.setCompanyName(companyName.getText().toString());
                                serviceProvider.setName(EditName1);
                                serviceProvider.setEmail(EditEmail1);
                                serviceProvider.setAddress(EditAddress1);
                                serviceProvider.setPassword(EditPassword1);
                                serviceProvider.setCompanyType(newSelectionType);

                                databaseReference.push().setValue(serviceProvider); //new child !!!!!
                                reference.child(snapshot.getKey()).removeValue();

                                Toast.makeText(getApplicationContext(), "Done ", Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


    }}