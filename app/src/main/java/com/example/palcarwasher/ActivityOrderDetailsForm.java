package com.example.palcarwasher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.util.List;

public class ActivityOrderDetailsForm extends AppCompatActivity {
Orders orderItem;

DatabaseReference databaseReference;
    ImageView logo;
    TextView companyName;
    TextView status;
    TextView dateTime;
    TextView totalPrice;
    TextView orderType;
    TextView paymentType;
    TextView orderId;
    TextView vehicle;
    TextView cleanAddress;
    RecyclerView services;
    Button cancelOrder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details_form);

orderItem=(Orders) getIntent().getSerializableExtra("orderItem");



        logo=findViewById(R.id.imageView);
        companyName=findViewById(R.id.company_name10);
        status=findViewById(R.id.status10);
        dateTime=findViewById(R.id.date_time10);
        totalPrice=findViewById(R.id.total_price10);
        orderType=findViewById(R.id.order_type10);
        orderId=findViewById(R.id.order_id10);
        vehicle=findViewById(R.id.vehicle10);
        paymentType=findViewById(R.id.payment_type10);
        cleanAddress=findViewById(R.id.clean_address10);
        services=findViewById(R.id.recycler_view_services);

        cancelOrder=findViewById(R.id.cancel_button10);

//////////////////////////logo///////////////////////
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();

        StorageReference imageRef1 = storageReference.child("logo/"+orderItem.getProviderId());
        long MAXBYTE = 1024*1024;
        imageRef1.getBytes(MAXBYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // convert byte to Bitmap :
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
               logo.setImageBitmap(bitmap);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });



        /////////////  companyName ////////////////////
        databaseReference= FirebaseDatabase.getInstance().getReference().child("PalCarWasher").child("ServiceProvider");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        final ServiceProvider s = ds.getValue(ServiceProvider.class);

                        if(s.getProviderId().equals(orderItem.getProviderId())){
                            // Log.v("DataOB",s.getCompanyName()+"");
                            companyName.setText(s.getCompanyName()+"");
                        }


                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });


        /////////////  button ////////////////////
        if(orderItem.getStatus().equals("confirmed")){
          cancelOrder.setVisibility(View.VISIBLE);


            cancelOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ///  Log.v("DataOB", "cancel order");
                    //AlertDialog dialog=
                    new AlertDialog.Builder(ActivityOrderDetailsForm.this)
                            .setTitle("Cancel Order")
                            .setMessage("Are you sure that you want to cancel this order?\n\nYou will lose 70% of total amount!\nIf your payment method is cash the cut amount will be calculated with the next order\nIf you cancel two consecutive bookings in cash and without payment, you will be banned from the application! ")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(final DialogInterface dialog, int which) {

                                    ///////////////////
                                    databaseReference = FirebaseDatabase.getInstance().getReference().child("PalCarWasher").child("Orders");
                                    final DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("PalCarWasher")
                                            .child("Orders");
                                    Query query=reference.orderByChild("orderId").equalTo(orderItem.getOrderId());
                                    query.addListenerForSingleValueEvent(new ValueEventListener(){

                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            if(dataSnapshot.exists()){
                                                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                                                    Orders o=snapshot.getValue(Orders.class);


                                                    //ordersList.clear();
                                                    // clear();
                                                    o.setStatus("canceled");
                                                    databaseReference.push().setValue(o); //new child !!!!!
                                                    reference.child(snapshot.getKey()).removeValue();



                                                    Toast.makeText(ActivityOrderDetailsForm.this,"Cancellation is Done! ",Toast.LENGTH_LONG).show();
                                                    dialog.dismiss();
                                                    cancelOrder.setVisibility(View.GONE);
                                                    status.setText("canceled");
                                                }

                                            }



                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });



                                    ///////////////////

                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();


                }
            });

        }


        /////////////  orderId ////////////////////

        orderId.setText(orderItem.getOrderId());


        /////////////  totalPrice ////////////////////

       totalPrice.setText(orderItem.getTotalPrice()+"$");

        //////////////////////////

        /////////////  vehicleSize ////////////////////

        vehicle.setText(orderItem.getVehicleSize());


        /////////////  paymentType ////////////////////

       paymentType.setText(orderItem.getPaymentType());


        /////////////  paymentType ////////////////////

       orderType.setText(orderItem.getOrderType());


        ////////////  cleanAddress ////////////////////

       cleanAddress.setText(orderItem.getCleanAddress());

        ////////////  cleanAddress ////////////////////

       dateTime.setText(orderItem.getFullTime());
/////////////////////////////////////status////////////////
        status.setText(orderItem.getStatus());

        /////////////////////services///////////////////////////////////

        List<String> servicesList=orderItem.getOfferIds();
        // Log.v("DataOB", servicesList.get(0));
        //  Log.v("DataOB", servicesList.get(1));

        final LinearLayoutManager layoutManager = new LinearLayoutManager(
               services.getContext(),
                LinearLayoutManager.VERTICAL,
                false );
      //  layoutManager.setInitialPrefetchItemCount(servicesList.size());
        ServicesInOrderAdapter subItemAdapter = new ServicesInOrderAdapter(servicesList);

        services.setLayoutManager(layoutManager);
        services.setAdapter(subItemAdapter);
        subItemAdapter.notifyDataSetChanged();

/////////////////////////////////////////


        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),CompanyDetailsActivity.class);
                intent.putExtra("providerId", orderItem.getProviderId());
                v.getContext().startActivity(intent);
            }
        });

        companyName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),CompanyDetailsActivity.class);
                intent.putExtra("providerId", orderItem.getProviderId());
                v.getContext().startActivity(intent);
            }
        });

    }
}