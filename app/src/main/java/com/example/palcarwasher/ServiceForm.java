package com.example.palcarwasher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ServiceForm extends AppCompatActivity {
  /*  FloatingActionButton button;
    ImageView imageView2 ;
    DatabaseReference databaseReference;
    EditText price;
    EditText description;
    String vehicleSize;
    String service;
    Button add;
    Button cancel;
    TableLayout table;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_form);



/*
        imageView2 = findViewById(R.id.imageView2);

        databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("PalCarWasher").child("Services");
      String vehicleId = databaseReference.push().getKey();
        Services vehicleType1 = new Services(vehicleId,"Internal washing and polishing");
       databaseReference.push().setValue(vehicleType1);

        Services vehicleType2 = new Services(vehicleId,"External Washing and polishing");
        databaseReference.push().setValue(vehicleType2);

        Services vehicleType3 = new Services(vehicleId,"Internal and external washing and polishing");
        databaseReference.push().setValue(vehicleType3);

        Services vehicleType5 = new Services(vehicleId,"internal washing and polishing with chairs");
        databaseReference.push().setValue(vehicleType5);


        Services vehicleType6 = new Services(vehicleId,"Change wheels");
        databaseReference.push().setValue(vehicleType6);


        Services vehicleType8 = new Services(vehicleId, "Internal and external washing and polishing with polishing");
        databaseReference.push().setValue(vehicleType8);

        Services vehicleType4 = new Services(vehicleId,"Clean motor and gear");
        databaseReference.push().setValue(vehicleType4);


        Services vehicleType7 = new Services(vehicleId, "Change oil");
        databaseReference.push().setValue(vehicleType7);

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder alertBuilder = new AlertDialog.Builder((Context) ServiceForm.this,R.style.my_dialog);
                LayoutInflater myinflater = getLayoutInflater();
                final View myView = myinflater.inflate(R.layout.alert_dialog_assign_services, null);

                alertBuilder.setCancelable(true);

                alertBuilder.setPositiveButton(
                        "ADD",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {





                                price=findViewById(R.id.price);
                                description=findViewById(R.id.description);
                                table=(TableLayout)findViewById(R.id.tableId);
                                String servicePrice=price.getText().toString();
                                String serviceDescription=description.getText().toString();

                                table.setStretchAllColumns(true);
                                table.bringToFront();

                                TableRow tr =  new TableRow(ServiceForm.this);

                                TextView c1 = new TextView(ServiceForm.this);
                                c1.setText(vehicleSize);

                                TextView c2 = new TextView(ServiceForm.this);
                                c2.setText(service);

                                TextView c3 = new TextView(ServiceForm.this);
                                c3.setText(servicePrice);

                                TextView c4 = new TextView(ServiceForm.this);
                                c4.setText(serviceDescription);

                                tr.addView(c1);
                                tr.addView(c2);
                                tr.addView(c3);
                                tr.addView(c4);
                                //  tr.addView(c4);
                                table.addView(tr);













                            }
                        });

                alertBuilder.setNegativeButton(
                        "CANCEL",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });






                Spinner spinner1 = myView.findViewById(R.id.spinnerVehicle);
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



                Spinner spinner2 = myView.findViewById(R.id.spinnerService);
                final ArrayList<String> arrayList2 = new ArrayList<>();

                DatabaseReference reference2= FirebaseDatabase.getInstance()
                        .getReference().child("PalCarWasher")
                        .child("Services");
                Query query2=reference2.orderByChild("serviceName");
                query2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String serviceName= snapshot.getValue(Services.class).getServiceName();
                            arrayList2.add(serviceName);

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });







                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ServiceForm.this,
                        android.R.layout.simple_spinner_dropdown_item, arrayList);
                spinner1.setAdapter(arrayAdapter);
                spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String selectedVehicleSize = parent.getItemAtPosition(position).toString();
                        setVehicleSize(selectedVehicleSize);


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });




                ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(ServiceForm.this,
                        android.R.layout.simple_spinner_item, arrayList2);
                //simple_spinner_dropdown_item
                spinner2.setAdapter(arrayAdapter2);
                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String selectedService = parent.getItemAtPosition(position).toString();
                        setService(selectedService);


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                /*add= myView.findViewById(R.id.addButton);
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        price=findViewById(R.id.price);
                        description=findViewById(R.id.description);
                        table=(TableLayout)findViewById(R.id.tableId);
                        String servicePrice=price.getText().toString();
                        String serviceDescription=description.getText().toString();

                        table.setStretchAllColumns(true);
                        table.bringToFront();

                        TableRow tr =  new TableRow(ServiceForm.this);

                        TextView c1 = new TextView(ServiceForm.this);
                        c1.setText(vehicleSize);

                        TextView c2 = new TextView(ServiceForm.this);
                        c2.setText(service);

                        TextView c3 = new TextView(ServiceForm.this);
                        c3.setText(servicePrice);

                        TextView c4 = new TextView(ServiceForm.this);
                        c4.setText(serviceDescription);

                        tr.addView(c1);
                        tr.addView(c2);
                        tr.addView(c3);
                        tr.addView(c4);
                        //  tr.addView(c4);
                        table.addView(tr);

                    }
                });




                alertBuilder.setView(myView);

                alertBuilder.create();

                alertBuilder.create().show();
               // alertBuilder.getWindow().setLayout(600, 400);
            }
        });











*/





    }



/*
void setVehicleSize(String selectedVehicleSize){

        vehicleSize=selectedVehicleSize;

}

    void setService(String selectedService){

        service=selectedService;

    }




    public void onClickAddButton(View view) {

        //vehicleSize=selectedVehicleSize;
        //service=selectedService;
        price=findViewById(R.id.price);
        description=findViewById(R.id.description);
        table=(TableLayout)findViewById(R.id.tableId);
        String servicePrice=price.getText().toString();
        String serviceDescription=description.getText().toString();

        table.setStretchAllColumns(true);
        table.bringToFront();

        TableRow tr =  new TableRow(this);

        TextView c1 = new TextView(this);
        c1.setText(vehicleSize);

        TextView c2 = new TextView(this);
        c2.setText(service);

        TextView c3 = new TextView(this);
        c3.setText(servicePrice);

        TextView c4 = new TextView(this);
        c4.setText(serviceDescription);

        tr.addView(c1);
        tr.addView(c2);
        tr.addView(c3);
        tr.addView(c4);
        //  tr.addView(c4);
        table.addView(tr);




    }*/


}