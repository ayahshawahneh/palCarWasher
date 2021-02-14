package com.example.palcarwasher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.util.List;

public class ActivityFinalOrderToDB extends AppCompatActivity {

Orders orderItem;


    DatabaseReference databaseReference;
    ImageView logo;
    TextView companyName;

    TextView dateTime;
    TextView totalPrice;
    TextView orderType;
    TextView paymentType;
    TextView orderId;
    TextView vehicle;
    TextView cleanAddress;
    RecyclerView services;
    Button confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_order_to_d_b);

        orderItem=(Orders) getIntent().getSerializableExtra("order");



        logo=findViewById(R.id.imageView);
        companyName=findViewById(R.id.company_name10);

        dateTime=findViewById(R.id.date_time10);
        totalPrice=findViewById(R.id.total_price10);
        orderType=findViewById(R.id.order_type10);
        orderId=findViewById(R.id.order_id10);
        vehicle=findViewById(R.id.vehicle10);
        paymentType=findViewById(R.id.payment_type10);
        cleanAddress=findViewById(R.id.clean_address10);
        services=findViewById(R.id.recycler_view_services);




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

        /////////////  orderId ////////////////////




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




        confirm=findViewById(R.id.confirm_order);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String orderId= databaseReference.push().getKey();


                orderItem.setComment("");
                orderItem.setEvaluationLevel("");
                orderItem.setOrderId(orderId);


                databaseReference = FirebaseDatabase.getInstance().getReference()
                        .child("PalCarWasher").child("Orders");


                databaseReference.push().setValue(orderItem);


                Toast.makeText(getApplicationContext(),"Order Done!" ,Toast.LENGTH_SHORT).show();



                Intent intent = new Intent(ActivityFinalOrderToDB.this,ActivityHome.class);
                intent.putExtra("customerId", orderItem.getCustomerId());
                startActivity(intent);





            }
        });




    }
}