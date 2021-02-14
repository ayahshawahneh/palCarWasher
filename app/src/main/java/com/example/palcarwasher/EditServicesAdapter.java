package com.example.palcarwasher;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditServicesAdapter extends RecyclerView.Adapter<EditServicesAdapter.serviceHolder> {




   List<ServicesOfferedByServiceProviders> sobsp;
   Context context;



    public EditServicesAdapter(List<ServicesOfferedByServiceProviders> sobsp, Context context) {
        this.sobsp = sobsp;
        this.context = context;
    }

    @NonNull
    @Override
    public serviceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_service,parent,false);
        return new EditServicesAdapter.serviceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final serviceHolder holder, final int position) {

        final ServicesOfferedByServiceProviders ss =sobsp.get(position);




holder.service.setText(ss.getServiceName());
holder.vehicle.setText(ss.getVehicleName());
holder.price.setText(ss.getPrice()+"$");
holder.des.setText(ss.getDescription());


holder.edit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        AlertDialogEdit cdd =new AlertDialogEdit(ss);
        cdd.show();
        Window window = cdd.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    }
});

////////////////////////
 holder.delete.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {
         AlertDialog.Builder builder=new AlertDialog.Builder(holder.delete.getContext());
         builder.setTitle("Delete Services");
         builder.setMessage("Are You sure you want to Delete ...?");

         builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialogInterface, int i) {











                 final DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("PalCarWasher")
                         .child("ServicesOfferedByServiceProviders");
                 Query query=reference.orderByChild("offerId").equalTo(ss.getOfferId());
                 query.addListenerForSingleValueEvent(new ValueEventListener(){

                     @Override
                     public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                         if(dataSnapshot.exists()){
                             for (DataSnapshot appleSnapshot: dataSnapshot.getChildren())
                             {
                                 appleSnapshot.getRef().removeValue();


                             }
                                 Toast.makeText(context,"Delete Done! ",Toast.LENGTH_SHORT).show();


                         }



                     }

                     @Override
                     public void onCancelled(@NonNull DatabaseError error) {

                     }
                 });














             }
         });

         builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialogInterface, int i) {

             }
         });

         builder.show();
     }
 });


    }

    @Override
    public int getItemCount() {
        return sobsp.size();
    }



    public class serviceHolder extends RecyclerView.ViewHolder{


        TextView service;
        TextView vehicle;
        TextView price;
        TextView des;
        Button delete;
        Button edit;




        public serviceHolder(@NonNull View itemView) {
            super(itemView);

            service=itemView.findViewById(R.id.service);
            vehicle=itemView.findViewById(R.id.vehicle);
            price=itemView.findViewById(R.id.price);
            des=itemView.findViewById(R.id.description);
            delete=itemView.findViewById(R.id.delete);
            edit=itemView.findViewById(R.id.edit);
        }



    }




    public class AlertDialogEdit extends Dialog implements android.view.View.OnClickListener {
        DatabaseReference reference;


        ArrayList<String> vehicleList = new ArrayList<String>();
        ArrayAdapter<String> vehicleArrayAdapter ;


        ArrayList<String> serviceList = new ArrayList<>();
        ArrayAdapter<String> serviceAdapter ;



        String selectedVehicle,selectedService;
        //////////////////////////////////
        Spinner vehicles,services;
        EditText price,description;
        Button save;
        ServicesOfferedByServiceProviders ss;


        public AlertDialogEdit( ServicesOfferedByServiceProviders ss ) {
            super(context);

            this.ss=ss;
        }
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            setContentView(R.layout.edit_service_inner);



        vehicles= findViewById(R.id.vehicleSpinner);
        services=findViewById(R.id.serviceSpinner);
        price=findViewById(R.id.price);
        description=findViewById(R.id.description);
        save=findViewById(R.id.save);

        save.setOnClickListener(this);


        price.setText(ss.getPrice());
        description.setText(ss.getDescription());


            /////////////////////vehicle//////////////////////////////////


            vehicleList.add(ss.getVehicleName());
            reference= FirebaseDatabase.getInstance().getReference().child("PalCarWasher")
                    .child("VehicleType");
            Query query=reference.orderByChild("size");
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String vehicleType = snapshot.getValue(VehicleType.class).getSize();

                        if(!vehicleType.equals(ss.getVehicleName()))
                            vehicleList.add(vehicleType);

                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            vehicleArrayAdapter = new ArrayAdapter<String>(context,
                    android.R.layout.simple_spinner_dropdown_item, vehicleList);
            vehicleArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
            vehicles.setAdapter(vehicleArrayAdapter);
            vehicleArrayAdapter.notifyDataSetChanged();
           ////////////;;;;;;;;;;;;;;;;;;;;////////////

            vehicles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
            {
                @Override
                public void onItemSelected(AdapterView adapter, View v, int i, long lng) {




                    selectedVehicle = vehicles.getSelectedItem()+"";

                }
                @Override
                public void onNothingSelected(AdapterView<?> parentView)
                {

                }
            });


//////////////////////////service name////////////////////////////////////////


            serviceList.add(ss.getServiceName());
            reference= FirebaseDatabase.getInstance().getReference().child("PalCarWasher")
                    .child("Services");
           query=reference.orderByChild("serviceName");
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String ser = snapshot.getValue(Services.class).getServiceName();

                        if(!ser.equals(ss.getServiceName()))
                            serviceList.add(ser);

                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            serviceAdapter = new ArrayAdapter<String>(context,
                    android.R.layout.simple_spinner_dropdown_item, serviceList);
            serviceAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
            services.setAdapter(serviceAdapter);
            serviceAdapter.notifyDataSetChanged();
            ////////////;;;;;;;;;;;;;;;;;;;;////////////

            services.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
            {
                @Override
                public void onItemSelected(AdapterView adapter, View v, int i, long lng) {




                    selectedService = services.getSelectedItem()+"";

                }
                @Override
                public void onNothingSelected(AdapterView<?> parentView)
                {

                }
            });




            ///////////////////////////////////////////////////
        }

        @Override
        public void onClick(View v) {






            final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("PalCarWasher")
                    .child("ServicesOfferedByServiceProviders");

            final DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("PalCarWasher")
                    .child("ServicesOfferedByServiceProviders");
            Query query=reference.orderByChild("offerId").equalTo(ss.getOfferId());
            query.addListenerForSingleValueEvent(new ValueEventListener(){

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if(dataSnapshot.exists()){
                        for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                            ServicesOfferedByServiceProviders so=snapshot.getValue(ServicesOfferedByServiceProviders.class);

                           so.setServiceName(selectedService);
                           so.setVehicleName(selectedVehicle);
                           so.setPrice(price.getText().toString());
                           so.setDescription(description.getText().toString());
                            databaseReference.push().setValue(so); //new child !!!!!
                            reference.child(snapshot.getKey()).removeValue();

                            Toast.makeText(context,"Updated Successfully! ",Toast.LENGTH_SHORT).show();


                        }

                    }



                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });





















        }


        }




    }







