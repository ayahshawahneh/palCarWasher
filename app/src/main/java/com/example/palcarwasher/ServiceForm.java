package com.example.palcarwasher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ServiceForm extends AppCompatActivity {
    FloatingActionButton button;
    ImageView imageView2 ;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_form);

        imageView2 = findViewById(R.id.imageView2);
/*
        databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("PalCarWasher").child("VehicleType");
      String vehicleId = databaseReference.push().getKey();
      VehicleType vehicleType1 = new VehicleType(vehicleId,"Truck","Internal washing and polishing");
       databaseReference.push().setValue(vehicleType1);

        VehicleType vehicleType2 = new VehicleType(vehicleId,"Truck","External Washing and polishing");
        databaseReference.push().setValue(vehicleType2);

        VehicleType vehicleType3 = new VehicleType(vehicleId,"Truck","Internal and external washing and polishing");
        databaseReference.push().setValue(vehicleType3);

        VehicleType vehicleType5 = new VehicleType(vehicleId,"Truck","Change wheels");
        databaseReference.push().setValue(vehicleType5);

        VehicleType vehicleType4 = new VehicleType(vehicleId,"Motor Cycle","Internal and external washing and polishing");
        databaseReference.push().setValue(vehicleType4);


        VehicleType vehicleType6 = new VehicleType(vehicleId,"Motor Cycle","Change wheels");
        databaseReference.push().setValue(vehicleType4);*/


        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder alertBuilder = new AlertDialog.Builder((Context) ServiceForm.this);
                LayoutInflater myinflater = getLayoutInflater();
                final View myView = myinflater.inflate(R.layout.alert_dialog_assign_services, null);

                Spinner spinner1 = myView.findViewById(R.id.spinnerVehicle);
                Spinner spinner2 = myView.findViewById(R.id.spinnerService);
                final ArrayList<String> arrayList = new ArrayList<>();


                DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("PalCarWasher")
                        .child("VehicleType");
                Query query=reference.orderByChild("size");
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String vehicleType = snapshot.getValue(VehicleType.class).getSize();
                            arrayList.add(vehicleType);

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });











                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ServiceForm.this,
                        android.R.layout.simple_spinner_item, arrayList);
                spinner1.setAdapter(arrayAdapter);
                spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String tutorialsName = parent.getItemAtPosition(position).toString();
                        ArrayList<String> arrayList2 = new ArrayList<>();
                        Spinner spinner2 = myView.findViewById(R.id.spinnerService);

                          /*if(tutorialsName=="Buss(5-pass)") {
                           arrayList2.add("Internal washing and polishing");
                           arrayList2.add("External Washing and polishing");
                           arrayList2.add("Internal and external washing and polishing");
                           arrayList2.add("internal washing and polishing with chairs");
                           arrayList2.add("Change wheels");
                       }

 public void getVehicleType(){


        FirebaseDatabase.getInstance().getReference().child("PalCarWasher")
                .child("VehicleType").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    String vehicleType=snapshot.getValue(VehicleType.class).getSize();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }


                      else  if (tutorialsName == "Car(7-pass)") {
                               arrayList2.add("Internal washing and polishing");
                               arrayList2.add("External Washing and polishing");
                               arrayList2.add("Internal and external washing and polishing");
                               arrayList2.add("internal washing and polishing with chairs");
                               arrayList2.add("Change wheels");

                       }

                     else if (tutorialsName == "Car(20-pass)") {
                               arrayList2.add("Internal washing and polishing");
                               arrayList2.add("External Washing and polishing");
                               arrayList2.add("Internal and external washing and polishing");
                               arrayList2.add("internal washing and polishing with chairs");
                               arrayList2.add("Change wheels");

                           }




                         else  if (tutorialsName == "Truck") {
                               arrayList2.add("Internal washing and polishing");
                               arrayList2.add("External Washing and polishing");
                               arrayList2.add("Internal and external washing and polishing");
                               arrayList2.add("internal washing and polishing with chairs");
                               arrayList2.add("Change wheels");

                           }


                           else if (tutorialsName == "Motor Cycle") {
                               arrayList2.add("Internal and external washing and polishing");
                               arrayList2.add("Change wheels");


                       }
                           ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(ServiceForm.this,
                                   android.R.layout.simple_spinner_item, arrayList2);
                           spinner2.setAdapter(arrayAdapter2);*/


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });



                alertBuilder.setView(myView);
                alertBuilder.create();
                alertBuilder.create().show();

            }
        });

      /*  button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertBuilder = new AlertDialog.Builder((Context) ServiceForm.this);
                LayoutInflater myinflater = getLayoutInflater();
                final View myView = myinflater.inflate(R.layout.alert_dialog_add_new_service_or_vehicle, null);
                alertBuilder.setView(myView);
                alertBuilder.create();
                alertBuilder.create().show();

            }
        });*/



    }











}