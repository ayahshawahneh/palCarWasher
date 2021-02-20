package com.example.palcarwasher;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddnewService extends AppCompatActivity {
    Button addButton;
    //EditText addNewService;
    Spinner spinnerVehicles;
    Spinner spinnerServices;
    EditText price;
    EditText description;
    String spinnerSel;
    String spinnerSel2;
    // DatabaseReference databaseReference;
    String ProviderId;
    DatabaseReference databaseReference2;
    DatabaseReference databaseReference3;
    DatabaseReference databaseReferenceSer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnew_service);


        addButton = findViewById(R.id.addButton);
        price = findViewById(R.id.price);
        description = findViewById(R.id.description);
        spinnerVehicles = findViewById(R.id.spinnerVehicle);
        spinnerServices = findViewById(R.id.spinnerService1);

        ProviderId = getIntent().getStringExtra("providerId");




        databaseReference2 = FirebaseDatabase.getInstance().getReference()
                .child("PalCarWasher").child("ServicesOfferedByServiceProviders");


        /////////////////////////////retrive all vehicle type from firebase//////////////////////

        final ArrayList<String> arrayList = new ArrayList<>();
        databaseReference3 = FirebaseDatabase.getInstance().getReference()
                .child("PalCarWasher").child("VehicleType");
        Query query=databaseReference3.orderByChild("vehicleId");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        VehicleType vehicleType =snapshot.getValue(VehicleType.class);
                        String Type = vehicleType.getSize();
                        arrayList.add(Type);

                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddnewService.this
                            ,android.R.layout.simple_spinner_item,arrayList);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerVehicles.setAdapter(arrayAdapter);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        /////////////////////////////retrive all services from firebase//////////////////////

        final ArrayList<String> listOfServices = new ArrayList<>();

        databaseReferenceSer = FirebaseDatabase.getInstance().getReference()
                .child("PalCarWasher").child("Services");
        Query query2 = databaseReferenceSer.orderByChild("serviceId");

        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Services services = snapshot.getValue(Services.class);
                        String serviceName = services.getServiceName();
                        listOfServices.add(serviceName);

                    }
                    ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(AddnewService.this
                            ,android.R.layout.simple_spinner_item,listOfServices);
                    arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerServices.setAdapter(arrayAdapter2);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
////////////////////////////////////////////////////////////////////////////
        spinnerServices.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                spinnerSel2 = parent.getItemAtPosition(position).toString();
                //Toast.makeText(parent.getContext(), "Selected: " + spinnerSel2,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



///////////////////////////////////////////////////////////////////////////

        spinnerVehicles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                spinnerSel = parent.getItemAtPosition(position).toString();
                // Toast.makeText(parent.getContext(), "Selected: " + spinnerSel,Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ////////////////////////////////////////////////////////////////////
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if( price.getText().toString().isEmpty() || description.getText().toString().isEmpty()) {
                    if (price.getText().toString().isEmpty())
                    {
                        price.setError("fill this field !");
                        price.requestFocus();
                        return;
                    }
                    if (description.getText().toString().isEmpty())
                    {
                        description.setError("fill this field !");
                        description.requestFocus();
                        return;
                    }
                }else {

                    //send to Services
                    /*String serviceId = databaseReference.push().getKey();
                    Services services = new Services(serviceId, addNewService.getText().toString());
                    databaseReference.push().setValue(services);*/

                    /////////////////////////////////////////

                    //send to ServicesOfferedByServiceProvider:

                    String offerId = databaseReference2.push().getKey();
                    String serviceName = spinnerSel2;
                    String vehicleName=spinnerSel;
                    String Description = description.getText().toString();
                    String Price = price.getText().toString();
                    boolean discountOnOff = false;
                    String discountId=databaseReference2.push().getKey();

                    ServicesOfferedByServiceProviders sobsp = new ServicesOfferedByServiceProviders(offerId,ProviderId,serviceName
                            ,vehicleName,Description,Price,discountOnOff,discountId);
                    databaseReference2.push().setValue(sobsp);
                    Toast.makeText(getApplicationContext(),"Added successfully",Toast.LENGTH_LONG).show();
                    // addNewService.setText("");
                    price.setText("");
                    description.setText("");

                }

            }
        });
    }


}