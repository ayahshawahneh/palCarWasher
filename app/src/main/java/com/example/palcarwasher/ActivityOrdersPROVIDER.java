package com.example.palcarwasher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ActivityOrdersPROVIDER extends AppCompatActivity {
    String customerId;
    String providerId;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    OrderAdapterPROVIDER orderAdapter;
    List<Orders> ordersList =new ArrayList<Orders>();


    FloatingActionButton floatingButtonFilter ;
    String filtering;
    String filtering2;
    String filtering3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_p_r_o_v_i_d_e_r);


        //  providerId=getIntent().getStringExtra("providerId");
        providerId = "-MRC4TAdUkYXdnRp0thN";


        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_home);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.nav_home:
                        Intent intent1 = new Intent(ActivityOrdersPROVIDER.this, ActivityHomePROVIDER.class);
                        intent1.putExtra("providerId", providerId.toString());//new
                        startActivity(intent1);
                        overridePendingTransition(0, 0);
                        return true;


                    case R.id.nav_orders:

                        return true;


                    case R.id.nav_wallet:
                        Intent intent2 = new Intent(ActivityOrdersPROVIDER.this, WalletActivity.class);
                        intent2.putExtra("providerId", providerId.toString());//new
                        startActivity(intent2);
                        overridePendingTransition(0, 0);
                        return true;


                    case R.id.nav_profile:
                        Intent intent3 = new Intent(ActivityOrdersPROVIDER.this, ProfileActivity.class);
                        intent3.putExtra("providerId", providerId.toString());//new
                        startActivity(intent3);
                        overridePendingTransition(0, 0);
                        return true;


                }


                return false;
            }
        });


        recyclerView = findViewById(R.id.recyclerview_orders);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        filtering="all";
        filtering2="timeN";
        filtering3="both";
        changeView();












/////////////////////////////


        floatingButtonFilter=findViewById(R.id.floating_filter);
        floatingButtonFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              AlertDialogFilter cdd= new AlertDialogFilter();

                cdd.show();
                Window window = cdd.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);




            }
        });
    }















    public class AlertDialogFilter extends Dialog implements
            android.view.View.OnClickListener {

        Button aply;
        RadioGroup rg;
        RadioGroup rg2;
        RadioGroup rg3;
        TextView t;

        public AlertDialogFilter( ) {
            super(ActivityOrdersPROVIDER.this);

        }
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            setContentView(R.layout.filter_view_order_activity_provider);




            t=findViewById(R.id.orderTypeText);
            aply=findViewById(R.id.aply);




            rg = (RadioGroup) findViewById(R.id.radioGroup1);

            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
            {
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch(checkedId){
                        case R.id.confirmed:
                            // do operations specific to this selection
                            filtering="confirmed";
                            break;
                        case R.id.completed:
                            // do operations specific to this selection
                            filtering="completed";


                            break;
                        case R.id.canceled:
                            // do operations specific to this selection

                            filtering="canceled";

                            break;




                        case R.id.all:
                            // do operations specific to this selection

                            filtering="all";

                            break;

                    }
                }
            });




            rg2 = (RadioGroup) findViewById(R.id.radioGroup2);

            rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
            {
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch(checkedId){




                        case R.id.timeO:
                            // do operations specific to this selection

                            filtering2="timeO";

                            break;

                        case R.id.timeN:
                            // do operations specific to this selection

                            filtering2="timeN";

                            break;

                    }
                }
            });

            rg3 = (RadioGroup) findViewById(R.id.radioGroup3);

            rg3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
            {
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch(checkedId){




                        case R.id.both:
                            // do operations specific to this selection

                            filtering3="both";

                            break;

                        case R.id.mobile:
                            // do operations specific to this selection

                            filtering3="mobile";

                            break;
                        case R.id.stationary:
                            // do operations specific to this selection

                            filtering3="stationary";

                            break;

                    }
                }
            });

///////////////////////////

            DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("PalCarWasher")
                    .child("ServiceProvider");
            Query query=reference.orderByChild("providerId");
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String type = snapshot.getValue(ServiceProvider.class).getCompanyType();

                        if(type.equals("both")){
                            t.setVisibility(View.VISIBLE);
                            rg3.setVisibility(View.VISIBLE);



                        }


                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });



            ///////////////////////










            // Log.v("DataOB","filtering"+filtering);

            aply.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            changeView();
            dismiss();

        }






    }








    void changeView(){


        databaseReference= FirebaseDatabase.getInstance().getReference().child("PalCarWasher").child("Orders");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ordersList.clear();
                if(dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        final Orders o = ds.getValue(Orders.class);


                        if(!filtering.equals("all")&&!filtering3.equals("both")){
                            if (!o.getStatus().equals("confirmed")&&o.getProviderId().equals(providerId)&&o.getStatus().equals(filtering)&&o.getOrderType().equals(filtering3)) {
                                ordersList.add(o);
                            }   }



                            else if(!filtering.equals("all")&&filtering3.equals("both")){
                                if (!o.getStatus().equals("confirmed")&&o.getProviderId().equals(providerId)&&o.getStatus().equals(filtering)) {
                                    ordersList.add(o);
                                }



                            }

                            else if(filtering.equals("all")&&!filtering3.equals("both")){
                                if (!o.getStatus().equals("confirmed")&&o.getProviderId().equals(providerId)&&o.getOrderType().equals(filtering3)) {
                                    ordersList.add(o);
                                }



                            }


                         else {

                            if (!o.getStatus().equals("confirmed")&&o.getProviderId().equals(providerId)){

                                ordersList.add(o);
                            }}






                    }/////




                        Collections.sort(ordersList, new Comparator<Orders>() {

                            @Override
                            public int compare(Orders o1, Orders o2) {

                                Date dateObj1;
                                Date dateObj2;

                                try {

                                    String o11=o1.getFullTime().substring(4,23);
                                    Log.v("DataOB",o11+"");
                                    String o22=o2.getFullTime().substring(4,23);
                                    SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy HH:mm aa");


                                    dateObj1 = sdf.parse(o11);
                                    dateObj2 = sdf.parse(o22);

                                    Log.v("DataOB",dateObj1+"");
                                    return dateObj1.compareTo( dateObj2);


                                } catch (ParseException e) {
                                    return 0;
                                }
                            }
                        });

                    if(filtering2.equals("timeO")){

                        orderAdapter= new OrderAdapterPROVIDER(ordersList,ActivityOrdersPROVIDER.this);
                        recyclerView.setAdapter(orderAdapter);
                        orderAdapter.notifyDataSetChanged();

                    }





                    else if(filtering2.equals("timeN")){

                        Collections.reverse(ordersList);
                        orderAdapter= new OrderAdapterPROVIDER(ordersList,ActivityOrdersPROVIDER.this);
                        recyclerView.setAdapter(orderAdapter);
                        orderAdapter.notifyDataSetChanged();

                    }


                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });




    }











}
