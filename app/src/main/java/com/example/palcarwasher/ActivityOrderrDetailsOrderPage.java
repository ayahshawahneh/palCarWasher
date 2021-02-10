package com.example.palcarwasher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ActivityOrderrDetailsOrderPage extends AppCompatActivity {
    Orders orderItem;

    DatabaseReference databaseReference;
    ImageView copy;
    TextView customerName;
    TextView phone;
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


    private ClipboardManager myClipboard;
    private ClipData myClip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderr_details_order_page);



        orderItem=(Orders) getIntent().getSerializableExtra("orderItem");



        copy=findViewById(R.id.imageCopy30);
        customerName=findViewById(R.id.customer_name30);
        phone=findViewById(R.id.phone_number30);
        status=findViewById(R.id.status30);
        dateTime=findViewById(R.id.date_time30);
        totalPrice=findViewById(R.id.total_price30);
        orderType=findViewById(R.id.order_type30);
        orderId=findViewById(R.id.order_id30);
        vehicle=findViewById(R.id.vehicle30);
        paymentType=findViewById(R.id.payment_type30);
        cleanAddress=findViewById(R.id.clean_address30);
        services=findViewById(R.id.recycler_view_services30);

        cancelOrder=findViewById(R.id.cancel_button10);

//////////////////////////copy///////////////////////
        myClipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = customerName.getText().toString();
                myClip = ClipData.newPlainText("text", text);
                myClipboard.setPrimaryClip(myClip);
                Toast.makeText(ActivityOrderrDetailsOrderPage.this, "Phone number Copied!",
                        Toast.LENGTH_SHORT).show();
            }
        });




        /////////////  customerName ////////////////////
        databaseReference= FirebaseDatabase.getInstance().getReference().child("PalCarWasher").child("Customer");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        final Customer c = ds.getValue(Customer.class);

                        if(c.getCustomerId().equals(orderItem.getCustomerId())){
                            // Log.v("DataOB",s.getCompanyName()+"");
                            customerName.setText(c.getName());
                            phone.setText(c.getPhoneNumber());
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
    /*    if(orderItem.getStatus().equals("confirmed")){
            cancelOrder.setVisibility(View.VISIBLE);


            cancelOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ///  Log.v("DataOB", "cancel order");
                    //AlertDialog dialog=
                    new AlertDialog.Builder(ActivityOrderDetailsFormPROVIDER.this)
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



                                                    Toast.makeText(ActivityOrderDetailsFormPROVIDER.this,"Cancellation is Done! ",Toast.LENGTH_LONG).show();
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

*/
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




    }
}