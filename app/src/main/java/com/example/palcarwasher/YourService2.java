package com.example.palcarwasher;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class YourService2 extends Service {
    public int counter=0;
    String providerId;
    boolean flag=false;
    int lastNotificationTime=0,id=201;
    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT  >=Build.VERSION_CODES.O)
            startTimer();
//        else
//            startForeground(2, new Notification());
    }

    @RequiresApi(Build.VERSION_CODES.O)


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        providerId = intent.getStringExtra("providerId");
        // startMyOwnForeground();
        startTimer();
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        stoptimertask();

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("restartservice2");
        broadcastIntent.setClass(this, Restarter2.class);
        this.sendBroadcast(broadcastIntent);
    }



    private Timer timer;
    private TimerTask timerTask;
    public void startTimer() {
        timer = new Timer();

        timerTask = new TimerTask() {
            public void run() {
                // Log.i("Count", "=========  "+ (counter++));

                counter++;
                //Log.v("DataOB","cout"+counter);

                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("PalCarWasher").child("Orders");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                final Orders o = ds.getValue(Orders.class);
                                if(o.getProviderId().equals(providerId)&&o.getStatus().equals("confirmed")){




                                    Date dateObj,dateObj2, nowPlus20,now;
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




                                        if (!nowPlus20.before(dateObj)&&!nowPlus20.after(dateObj2)&&!flag){
                                            flag=true;
                                            lastNotificationTime=counter;
                                            //   Log.v("DataOB","lastNotificationTime"+lastNotificationTime);

    CharSequence name = "LemubitReminderChanel";
    String description = "Chanel for Lemubit Reminder";
    int importance = NotificationManager.IMPORTANCE_DEFAULT;
    NotificationChannel channel = new NotificationChannel("notifyLemubit", name, importance);
    channel.setDescription(description);

    Intent resultIntent = new Intent(getApplicationContext(), ActivityHomePROVIDER.class);
    resultIntent.putExtra("providerId", providerId);
    PendingIntent resultPendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);


    NotificationManager notificationManager = getSystemService(NotificationManager.class);
    notificationManager.createNotificationChannel(channel);
    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "notifyLemubit")
            .setSmallIcon(R.drawable.ic_notify_name)
            .setContentTitle("An order will start soon!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(resultPendingIntent);


    notificationManager.notify(200, builder.build());



                                        }


                                        if(counter-lastNotificationTime==2400){
                                            // Log.v("DataOB","dif"+( counter-lastNotificationTime));
                                            flag=false;
                                            lastNotificationTime=0;

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
















            }
        };
        timer.schedule(timerTask, 1000, 1000); //
    }

    public void stoptimertask() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }
}