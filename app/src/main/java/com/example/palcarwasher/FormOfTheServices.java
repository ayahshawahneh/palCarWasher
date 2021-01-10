package com.example.palcarwasher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FormOfTheServices extends AppCompatActivity {

    DatabaseReference databaseReference;
    String   ProviderId;

    LinearLayout parentLinearLayout;

    DatabaseReference vehicleReference;
    DatabaseReference serviceReference;

    Spinner vehicleSpinner;

    ArrayList<String> vehicleList = new ArrayList<String>();
    ArrayAdapter<String> vehicleArrayAdapter ;



    Spinner serviceSpinner;
    ArrayList<String> serviceList = new ArrayList<>();
    ArrayAdapter<String> serviceArrayAdapter ;


    ArrayList<ServicesOfferedByServiceProviders> finalServicesList = new ArrayList<ServicesOfferedByServiceProviders>();


   int count=0;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_of_the_services);

       ProviderId=getIntent().getStringExtra("ProviderId");



        ////////////////////////////////////////////////////////////////
        View mainView = getLayoutInflater().inflate(R.layout.activity_form_of_the_services, null);

        parentLinearLayout=(LinearLayout) findViewById(R.id.parent_linear_layout);
/////////////////////////////////////
 vehicleList.add("Vehicle Size");
        vehicleReference= FirebaseDatabase.getInstance().getReference().child("PalCarWasher")
                .child("VehicleType");
        Query query=vehicleReference.orderByChild("size");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String vehicleType = snapshot.getValue(VehicleType.class).getSize();
                    vehicleList.add(vehicleType);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        vehicleArrayAdapter = new ArrayAdapter<String>(FormOfTheServices.this,
                android.R.layout.simple_spinner_dropdown_item, vehicleList);
        vehicleArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);


        /////////////////////////service//////////////////////////////////////
        serviceList.add("Service Name");
        serviceReference= FirebaseDatabase.getInstance()
                .getReference().child("PalCarWasher")
                .child("Services");
        Query query2=serviceReference.orderByChild("serviceName");
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String serviceName= snapshot.getValue(Services.class).getServiceName();
                    serviceList.add(serviceName);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        serviceArrayAdapter = new ArrayAdapter<String>(FormOfTheServices.this,
                android.R.layout.simple_spinner_dropdown_item, serviceList);
        serviceArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        ///////////////////////////////////////////////


       onAddField(mainView);

    }//oncreate











    public void onAddField(View v) {
        LayoutInflater inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       View rowView=inflater.inflate(R.layout.add_new_service, null,false);

        fillDynamicVehicleSpinner(rowView);
        fillDynamicServiceSpinner(rowView);

        count++;
      parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 2);

    }

    public void onDelete(View v) {
        parentLinearLayout.removeView((View) ((ViewGroup) v.getParent()).getParent());
        count--;

    }




    public void onSubmit(View v) {
        if(checkIfValidAndRead()){


            sendData();


            Intent intent = new Intent(getApplicationContext(),
            WorkingHoursPerDay.class);
            intent.putExtra("ProviderId", ProviderId);
            startActivity(intent);

        }
    }




    public void fillDynamicVehicleSpinner(View v){

        vehicleSpinner = v.findViewById(R.id.vehicleSpinner);
       vehicleSpinner.setAdapter(vehicleArrayAdapter);
        vehicleArrayAdapter.notifyDataSetChanged();





    }








    public void fillDynamicServiceSpinner(View v){

        serviceSpinner = v.findViewById(R.id.serviceSpinner);
        serviceSpinner.setAdapter(serviceArrayAdapter);
        serviceArrayAdapter.notifyDataSetChanged();

    }







    private boolean checkIfValidAndRead() {
        finalServicesList.clear();
        boolean result = true;

        for(int i=0;i<parentLinearLayout.getChildCount()-2;i++){

          if(count==0)break;


            View rowView = parentLinearLayout.getChildAt(i);
            ServicesOfferedByServiceProviders sobsp = new ServicesOfferedByServiceProviders();

            if(rowView instanceof TableLayout){
                EditText price = (EditText)rowView.findViewById(R.id.price);
                EditText description = (EditText)rowView.findViewById(R.id.description);
                Spinner spinnerVehicle = (Spinner)rowView.findViewById(R.id.vehicleSpinner);
                Spinner spinnerService = (Spinner)rowView.findViewById(R.id.serviceSpinner);


                for(int j=0;j<((TableLayout) rowView).getChildCount();j++){



                    if(!price.getText().toString().equals("")){
                        sobsp.setPrice(price.getText().toString());
                    }else {
                        result = false;
                        break;
                    }


                    if(!description.getText().toString().equals("")){
                        sobsp.setDescription(description.getText().toString());
                    }else {
                        result = false;
                        break;
                    }




                    if(spinnerVehicle.getSelectedItemPosition()!=0){
                        sobsp.setVehicleName(spinnerVehicle.getSelectedItem().toString());
                    }else {
                        result = false;
                        break;
                    }


                    if(spinnerService.getSelectedItemPosition()!=0){
                        sobsp.setServiceName(spinnerService.getSelectedItem().toString());
                    }else {
                        result = false;
                        break;
                    }



                }
                sobsp.setProviderId(ProviderId);
                sobsp.setDiscountOnOff(false);
                finalServicesList.add(sobsp);
            }












        }

        if(finalServicesList.size()==0||count==0){
            result = false;
            Toast.makeText(this, "Add Services that you will provide First!", Toast.LENGTH_LONG).show();
        }else if(!result){
            Toast.makeText(this, "Please Fill All Details Correctly!", Toast.LENGTH_LONG).show();
        }


        return result;
    }







    void sendData() {

        databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("PalCarWasher").child("ServicesOfferedByServiceProviders");
        Intent intent = new Intent(FormOfTheServices.this, WorkingHoursPerDay.class);

        for(int i=0;i<finalServicesList.size();i++){


    String OfferId = databaseReference.push().getKey();
     finalServicesList.get(i).setOfferId(OfferId);
    databaseReference.push().setValue(finalServicesList.get(i));





}

    }



}