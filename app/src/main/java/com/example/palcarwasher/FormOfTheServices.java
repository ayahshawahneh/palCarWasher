package com.example.palcarwasher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FormOfTheServices extends AppCompatActivity {
    LinearLayout parentLinearLayout;
   /// View rowView = null;
    DatabaseReference vehicleReference;
    DatabaseReference serviceReference;
   // EditText price;
   // EditText description;


    Spinner vehicleSpinner;
   // String vehicleSize;
    ArrayList<String> vehicleList = new ArrayList<String>();
    ArrayAdapter<String> vehicleArrayAdapter ;


   // String service;
    Spinner serviceSpinner;
    ArrayList<String> serviceList = new ArrayList<>();
    ArrayAdapter<String> serviceArrayAdapter ;


    ArrayList<ServicesOfferedByServiceProviders> finalServicesList = new ArrayList<ServicesOfferedByServiceProviders>();

   // ArrayList<Integer> countArray = new ArrayList<>();

   int count=0;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_of_the_services);



        View mainView = getLayoutInflater().inflate(R.layout.activity_form_of_the_services, null);
        //View parentView =new View();
       // price=findViewById(R.id.price);
       // description=findViewById(R.id.description);

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
       //fillVehicleSpinner(mainView);
      // fillServiceSpinner(mainView);


    }//oncreate











    public void onAddField(View v) {
        LayoutInflater inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       View rowView=inflater.inflate(R.layout.add_new_service, null,false);
        // Add the new row before the add field button.

        fillDynamicVehicleSpinner(rowView);
        fillDynamicServiceSpinner(rowView);

       /* EditText price = (EditText)rowView.findViewById(R.id.price);
        EditText description = (EditText)rowView.findViewById(R.id.description);
        Spinner spinnerVehicle = (Spinner)rowView.findViewById(R.id.vehicleSpinner);
        Spinner spinnerService = (Spinner)rowView.findViewById(R.id.serviceSpinner);*/

       // LinearLayout addedRow=(LinearLayout)rowView.findViewById(R.id.form);
        //addedRow.setId(count);
        //countArray.add(count);
        count++;
      parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 2);

    }

    public void onDelete(View v) {
        parentLinearLayout.removeView((View) ((ViewGroup) v.getParent()).getParent());
        count--;

    }




    public void onSubmit(View v) {
        if(checkIfValidAndRead()){

            /*Intent intent = new Intent(MainActivity.this,ActivityCricketers.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("list",cricketersList);
            intent.putExtras(bundle);
            startActivity(intent);*/


            for(int i=0;i<finalServicesList.size();i++)
                Log.v("DataOB",finalServicesList.get(i).getVehicleName()+" "+finalServicesList.get(i).getServiceName()+" "+finalServicesList.get(i).getPrice()+" "+finalServicesList.get(i).getDescription());


            Toast.makeText(this, "Every thing is Correct!", Toast.LENGTH_LONG).show();


        }
    }


/*
    public void fillVehicleSpinner(View view){

        vehicleSpinner = (Spinner) findViewById(R.id.vehicleSpinner);

        vehicleSpinner.setAdapter(vehicleArrayAdapter);
        vehicleArrayAdapter.notifyDataSetChanged();
        vehicleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedVehicleSize = parent.getItemAtPosition(position).toString();
                setVehicleSize(selectedVehicleSize);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




    }



    public void fillServiceSpinner(View view){

         serviceSpinner = (Spinner) findViewById(R.id.serviceSpinner);


        serviceSpinner.setAdapter(serviceArrayAdapter);
        serviceArrayAdapter.notifyDataSetChanged();
        serviceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedService = parent.getItemAtPosition(position).toString();
                setService(selectedService);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




    }*/








    public void fillDynamicVehicleSpinner(View v){

        vehicleSpinner = v.findViewById(R.id.vehicleSpinner);
       vehicleSpinner.setAdapter(vehicleArrayAdapter);
        vehicleArrayAdapter.notifyDataSetChanged();


        /*vehicleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedVehicleSize = parent.getItemAtPosition(position).toString();
                setVehicleSize(selectedVehicleSize);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/




    }








    public void fillDynamicServiceSpinner(View v){

        serviceSpinner = v.findViewById(R.id.serviceSpinner);
        serviceSpinner.setAdapter(serviceArrayAdapter);
        serviceArrayAdapter.notifyDataSetChanged();
       /* serviceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedService = parent.getItemAtPosition(position).toString();
                setService(selectedService);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/




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













}