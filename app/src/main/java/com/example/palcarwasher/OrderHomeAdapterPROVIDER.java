package com.example.palcarwasher;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import org.w3c.dom.Text;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderHomeAdapterPROVIDER extends RecyclerView.Adapter<OrderHomeAdapterPROVIDER.orderHolder1>{
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    List<Orders> ordersList;
    DatabaseReference databaseReference;
    Context context;
    private ClipboardManager myClipboard;
    private ClipData myClip;
    public OrderHomeAdapterPROVIDER(List<Orders> ordersList,Context context) {
        this.ordersList = ordersList;
        this.context=context;
    }

    @NonNull
    @Override
    public orderHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_form_home_provider,parent,false);
        return new OrderHomeAdapterPROVIDER.orderHolder1(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final orderHolder1 holder, final int position) {

        final Orders orderItem=ordersList.get(position);


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
                            holder.customerName.setText(c.getName());
                            holder.phone.setText(c.getPhoneNumber());
                        }


                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });


///////////////////////////copy///////////////////////
/*

 */
        myClipboard = (ClipboardManager)context.getSystemService(context.CLIPBOARD_SERVICE);
      holder.copy.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              String text = holder.phone.getText().toString();
              myClip = ClipData.newPlainText("text", text);
              myClipboard.setPrimaryClip(myClip);
              Toast.makeText(context, "Phone number Copied!",
                      Toast.LENGTH_SHORT).show();
          }
      });



        ////////////dateTime//////////////
        holder.dateTime.setText(orderItem.getFullTime());



        /////////////  totalPrice ////////////////////

        holder.totalPrice.setText(orderItem.getTotalPrice()+"$");

        //////////////////////////



       /////////////////////vehicle

       holder.vehicleType.setText(orderItem.getVehicleSize());

       ////////////////ordertype
        holder.orderType.setText(orderItem.getOrderType());
       //////////////////get company from firebase

        databaseReference= FirebaseDatabase.getInstance().getReference().child("PalCarWasher").child("ServiceProvider");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        final ServiceProvider s= ds.getValue(ServiceProvider.class);
                        if (s.getProviderId().equals(orderItem.getProviderId())) {


                            if(s.getCompanyType().equals("both")){


                                holder.orderTypeRow.setVisibility(View.VISIBLE);


                            }else
                                holder.orderTypeRow.setVisibility(View.GONE);




                        }

                    }
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });




        ///////////////////////////

        holder.show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),ActivityOrderDetailsFormPROVIDER.class);
                intent.putExtra("orderItem", (Serializable) orderItem);
                v.getContext().startActivity(intent);
            }
        });
/////////////////////////////
        //    Log.v("DataOB",Calendar.getInstance().getTime()+"" );
        Date dateObj,dateObj2, nowPlus20;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");


        //Log.v("DataOB","heello");
        String orderTime = orderItem.getFullTime().substring(4, 23);
        String t1 = orderItem.getFullTime().substring(4, 14);
        String t2 = orderItem.getFullTime().substring(24, 32);
        String endOrderTime=t1+" "+t2;

        Calendar currentTime = Calendar.getInstance();
        currentTime.add(Calendar.MINUTE, 20);
        nowPlus20 = currentTime.getTime();
           Log.v("DataOB",nowPlus20 +"" );
        try {
            dateObj2=sdf.parse(endOrderTime);
           Log.v("DataOB",dateObj2 +"" );


            dateObj = sdf.parse(orderTime);
            // Log.v("DataOB",dateObj +"" );



            //  Log.v("DataOB",Calendar.getInstance().getTime()+"" );
            if (!nowPlus20.before(dateObj)&&!nowPlus20.after(dateObj2)&&orderItem.getOrderType().equals("mobile")) {

                holder.startTrip.setVisibility(View.VISIBLE);

                holder.startTrip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {




                        String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?&daddr="+orderItem.getCleanAddress());
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        intent.setPackage("com.google.android.apps.maps");

                       context.startActivity(intent);


                    }
                });


            }else
                holder.startTrip.setVisibility(View.GONE);


        } catch (ParseException e) {
            e.printStackTrace();
        }






//////////////////////////////////////







    }








    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    public class orderHolder1 extends RecyclerView.ViewHolder {



        ImageView copy;
        TextView customerName;
        TextView phone;
        TextView dateTime;
        TextView totalPrice;
        TextView orderType;
        TextView vehicleType;
        TableRow orderTypeRow;

        Button show,startTrip;



        public orderHolder1(@NonNull View itemView) {
            super(itemView);

            copy=itemView.findViewById(R.id.imageCopy);
            customerName=itemView.findViewById(R.id.customer_name20);
            phone=itemView.findViewById(R.id.phone_number20);
            dateTime=itemView.findViewById(R.id.date_time20);
            totalPrice=itemView.findViewById(R.id.total_price20);
            orderType=itemView.findViewById(R.id.order_type20);
            vehicleType=itemView.findViewById(R.id.vehicle20);
            orderTypeRow=itemView.findViewById(R.id.order_type_row20);
            show=itemView.findViewById(R.id.show_details);
            startTrip=itemView.findViewById(R.id.start_trip);
        }


    }

}
