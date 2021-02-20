package com.example.palcarwasher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class OrderReminderCustomer extends BroadcastReceiver {


    String customerId;

    @Override
    public void onReceive(Context context, Intent intent) {


  customerId="-MPQBYHkwU501cMmJC3p";
//intent.getStringExtra("customerId");

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("PalCarWasher").child("Orders");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        final Orders o = ds.getValue(Orders.class);
                        if(o.getCustomerId().equals(customerId)&&o.getStatus().equals("confirmed")&&o.getOrderType().equals("stationary")){




                            Date dateObj,dateObj2, nowPlus20;
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");


                            String orderTime = o.getFullTime().substring(4, 23);
                            String t1 = o.getFullTime().substring(4, 14);
                            String t2 = o.getFullTime().substring(24, 32);
                            String endOrderTime=t1+" "+t2;

                            Calendar currentTime = Calendar.getInstance();
                            currentTime.add(Calendar.MINUTE, 20);
                            nowPlus20 = currentTime.getTime();

                            try {
                                dateObj2=sdf.parse(endOrderTime);



                                dateObj = sdf.parse(orderTime);




                                if (!nowPlus20.before(dateObj)&&!nowPlus20.after(dateObj2)){

                                    NotificationCompat.Builder builder =new NotificationCompat.Builder(context,"notifyLemubit")
                                            .setSmallIcon(R.mipmap.ic_launcher_foreground)
                                            .setContentTitle("The order will start soon, may you have to take off now!")
                                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                                    notificationManager.notify(200,builder.build());









                                }


                            } catch (ParseException e) {
                                e.printStackTrace();
                            }











                        }










                    }/////







                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });











        //////////////////////

    }
}
