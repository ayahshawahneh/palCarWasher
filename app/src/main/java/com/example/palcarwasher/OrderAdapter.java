package com.example.palcarwasher;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
import java.util.Date;
import java.util.List;

public class OrderAdapter  extends RecyclerView.Adapter<OrderAdapter.orderHolder>{
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    List<Orders> ordersList;
    DatabaseReference databaseReference;
     Context context;
    public OrderAdapter(List<Orders> ordersList,Context context) {
        this.ordersList = ordersList;
       this.context=context;
    }

    @NonNull
    @Override
    public orderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_form,parent,false);
        return new OrderAdapter.orderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final orderHolder holder, final int position) {

        final Orders orderItem=ordersList.get(position);
///////////////////////////logo///////////////////////
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();

        StorageReference imageRef1 = storageReference.child("logo/"+orderItem.getProviderId());
        long MAXBYTE = 1024*1024;
        imageRef1.getBytes(MAXBYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // convert byte to Bitmap :
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                holder.logo.setImageBitmap(bitmap);

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
                            holder.companyName.setText(s.getCompanyName()+"");
                        }


                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });

        ////////////dateTime//////////////
        holder.dateTime.setText(orderItem.getFullTime());

        /////////////  status ////////////////////

         holder.status.setText(orderItem.getStatus());


        /////////////  totalPrice ////////////////////

        holder.totalPrice.setText(orderItem.getTotalPrice()+"$");
        //////////////////////////
        holder.orderType.setText(orderItem.getOrderType());

        //////////////////////////

holder.show.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(v.getContext(),ActivityOrderDetailsForm.class);
        intent.putExtra("orderItem", (Serializable) orderItem);
        v.getContext().startActivity(intent);
    }
});




/////////////////////////////
    //    Log.v("DataOB",Calendar.getInstance().getTime()+"" );
    Date dateObj,dateObj2, nowPlus20;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm aa");


        //Log.v("DataOB","heello");
        String orderTime = orderItem.getFullTime().substring(4, 23);
        String t1 = orderItem.getFullTime().substring(4, 14);
        String t2 = orderItem.getFullTime().substring(24, 32);
        String endOrderTime=t1+" "+t2;

        Calendar currentTime = Calendar.getInstance();
        currentTime.add(Calendar.MINUTE, 20);
        nowPlus20 = currentTime.getTime();
       // Log.v("DataOB",nowPlus20 +"" );
        try {
            dateObj2=sdf.parse(endOrderTime);
          //  Log.v("DataOB",dateObj2 +"" );


         dateObj = sdf.parse(orderTime);
           // Log.v("DataOB",dateObj +"" );



      //  Log.v("DataOB",Calendar.getInstance().getTime()+"" );
if (!nowPlus20.before(dateObj)&&!nowPlus20.after(dateObj2)&&orderItem.getOrderType().equals("stationary")&&orderItem.getStatus().equals("confirmed")) {

            holder.startTrip.setVisibility(View.VISIBLE);

            holder.startTrip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(context, "go to map", Toast.LENGTH_SHORT).show();

                }
            });


        }else
            holder.startTrip.setVisibility(View.GONE);


    } catch (ParseException e) {
        e.printStackTrace();
    }






//////////////////////////////////////

holder.logo.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(v.getContext(),CompanyDetailsActivity.class);
        intent.putExtra("providerId", orderItem.getProviderId());

        v.getContext().startActivity(intent);
    }
});


        holder.companyName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),CompanyDetailsActivity.class);
                intent.putExtra("providerId", orderItem.getProviderId());

                v.getContext().startActivity(intent);
            }
        });

    }








    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    public class orderHolder extends RecyclerView.ViewHolder {



        ImageView logo;
        TextView companyName;
        TextView status;
        TextView dateTime;
        TextView totalPrice;
        TextView orderType;



        Button show,startTrip;



        public orderHolder(@NonNull View itemView) {
            super(itemView);

           logo=itemView.findViewById(R.id.imageLogo);
           companyName=itemView.findViewById(R.id.company_name10);
           status=itemView.findViewById(R.id.status10);
           dateTime=itemView.findViewById(R.id.date_time10);
           totalPrice=itemView.findViewById(R.id.total_price10);
           orderType=itemView.findViewById(R.id.order_type10);

           show=itemView.findViewById(R.id.show_details);
           startTrip=itemView.findViewById(R.id.start_trip);
        }


    }

}
