package com.example.palcarwasher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ActivityHome extends AppCompatActivity {

    DatabaseReference reference;

    Spinner vehicleSpinner;
    ArrayList<String> vehicleList = new ArrayList<String>();
    ArrayAdapter<String> vehicleArrayAdapter ;

    Spinner compTypeSpinner;
    ArrayList<String> compTypeList = new ArrayList<>();
    ArrayAdapter<String> compTypeArrayAdapter ;




    RecyclerView recyclerView;
    ProviderAdapter providerAdapter;
    DatabaseReference databaseReference2;

   String selectedCompanyType;
   String selectedVehicle;
   String customerId;


   FloatingActionButton floatingActionButton ;
   FloatingActionButton floatingButtonFilter ;
   String filtering;


     List<ServiceProvider> providerList=new ArrayList<>();
     List<ServiceProviderAndAvgPrice> providerPriceList=new ArrayList<>();
     double sum=0;
     double count=0;

     EditText search;
    int r;
    List<String> stringSlotList=new ArrayList<String>();
    List<String> stringSlotListFull=new ArrayList<String>();
    List<ProviderTimeSlot> providerTime=new ArrayList<ProviderTimeSlot>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


       // Toast.makeText(getApplicationContext(),customerId, Toast.LENGTH_LONG).show();

        filtering ="none";

        customerId=getIntent().getStringExtra("customerId");
       // customerId="-MPQBYHkwU501cMmJC3p";
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_home);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.nav_home:

                        return true;


                    case R.id.nav_orders:
                        Intent intent1 = new Intent(ActivityHome.this,OrdersActivity.class);
                        intent1.putExtra("customerId",customerId.toString());//new
                        startActivity(intent1);
                        overridePendingTransition(0,0);
                        return true;



                    case R.id.nav_wallet:
                        Intent intent2 = new Intent(ActivityHome.this,WalletActivity.class);
                        intent2.putExtra("customerId",customerId.toString());//new
                        startActivity(intent2);
                        overridePendingTransition(0,0);
                        return true;


                    case R.id.nav_profile:
                        Intent intent3 = new Intent(ActivityHome.this,ProfileActivity.class);
                        intent3.putExtra("customerId",customerId.toString());//new
                        startActivity(intent3);
                        overridePendingTransition(0,0);
                        return true;


                }



                return false;
            }
        });


///////////////////fill vehicle spinner////////////////////




      vehicleList.add("Car(7-pass)");
        reference= FirebaseDatabase.getInstance().getReference().child("PalCarWasher")
                .child("VehicleType");
        Query query=reference.orderByChild("size");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String vehicleType = snapshot.getValue(VehicleType.class).getSize();

                    if(!vehicleType.equals("Car(7-pass)"))
                        vehicleList.add(vehicleType);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        vehicleArrayAdapter = new ArrayAdapter<String>(ActivityHome.this,
                android.R.layout.simple_spinner_dropdown_item, vehicleList);
        vehicleArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        vehicleSpinner = findViewById(R.id.vehicleSpinner);
        vehicleSpinner.setAdapter(vehicleArrayAdapter);
        vehicleArrayAdapter.notifyDataSetChanged();

////////////////////////fill company type//////////////////////////////////////////



        compTypeList.add("stationary");
        compTypeList.add("mobile");
        compTypeArrayAdapter = new ArrayAdapter<String>(ActivityHome.this,
                android.R.layout.simple_spinner_dropdown_item, compTypeList);
        compTypeArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        compTypeSpinner = findViewById(R.id.compTypeSpinner);
        compTypeSpinner.setAdapter(compTypeArrayAdapter);
        compTypeArrayAdapter.notifyDataSetChanged();

//////////////////////////initial view of companies//////////////////////////////////////////////////////////////



        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
      selectedCompanyType=compTypeSpinner.getSelectedItem()+"";
      selectedVehicle=vehicleSpinner.getSelectedItem()+"";




        databaseReference2= FirebaseDatabase.getInstance().getReference().child("PalCarWasher").child("ServiceProvider");
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    providerList.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {

                    final ServiceProvider prov=ds.getValue(ServiceProvider.class);
                    if(prov.getCompanyType().equals(selectedCompanyType)||prov.getCompanyType().equals("both")){


                        providerList.add(prov);

                    }

                }




                providerAdapter=new ProviderAdapter(providerList,selectedVehicle,selectedCompanyType,customerId);
                recyclerView.setAdapter(providerAdapter);
                providerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });

/////////////////////////////////////////////////////////
        floatingActionButton=findViewById(R.id.floating_location);


////////////////////////////click listener on compSpinner////////////////////////////
       compTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView adapter, View v, int i, long lng) {

                filtering ="none";


                selectedCompanyType =  compTypeSpinner.getSelectedItem()+"";
                changeView(selectedVehicle,selectedCompanyType,filtering );

////////........................
             if(selectedCompanyType.equals("stationary")){
                    floatingActionButton.setVisibility(View.VISIBLE);
                 floatingActionButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent =new Intent(ActivityHome.this,ShowAllCompanyOnMap.class);
                            startActivity(intent);
                        }
                    });
                }
                else{

                    floatingActionButton.setVisibility(View.GONE);


                }


//////////////////////...............................



            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView)
            {

            }
        });



/////************************************************************************/////////

      vehicleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView adapter, View v, int i, long lng) {


                filtering ="none";

                selectedVehicle =  vehicleSpinner.getSelectedItem()+"";

                changeView(selectedVehicle,selectedCompanyType,filtering );


            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView)
            {

            }
        });


///////////////////////////////////////////**********************************/////////////////////////


        search=findViewById(R.id.search);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
              searchView(selectedVehicle,selectedCompanyType,s.toString());
            }
        });

        ///////////////////////////////////////////////////////







floatingButtonFilter=findViewById(R.id.floating_filter);
floatingButtonFilter.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        AlertDialogFilter cdd=new AlertDialogFilter();

        cdd.show();
        Window window = cdd.getWindow();
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);




    }
});



///////////////


//        YourService   mYourService = new YourService();
//        Intent mServiceIntent = new Intent(this, mYourService.getClass());
//        mServiceIntent.putExtra("customerId",customerId);
//            startService(mServiceIntent);




    }


//    @Override
//    protected void onDestroy() {
//        //stopService(mServiceIntent);
//        Intent broadcastIntent = new Intent();
//        broadcastIntent.setAction("restartservice");
//        broadcastIntent.setClass(this, Restarter.class);
//        this.sendBroadcast(broadcastIntent);
//        super.onDestroy();
//    }





    void changeView(final String selecteVehicle, final String selecteCompanyType, final String filter ){




        databaseReference2= FirebaseDatabase.getInstance().getReference().child("PalCarWasher").child("ServiceProvider");
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                providerList.clear();
                providerPriceList.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {

                    final ServiceProvider prov=ds.getValue(ServiceProvider.class);
                    if(prov.getCompanyType().equals(selecteCompanyType)||prov.getCompanyType().equals("both")){

                        providerList.add(prov);

                    }

                }

                if(filter.equals("rating")) { ///from high rate and down
                    Collections.sort(providerList, new Comparator<ServiceProvider>() {
                        @Override
                        public int compare(ServiceProvider o1, ServiceProvider o2) {
                            Double rate1 = Double.valueOf(o1.getEvaluationLevel());
                            Double rate2 = Double.valueOf(o2.getEvaluationLevel());

                            return rate1 > rate2 ? -1 : (rate1 < rate2) ? 1 : 0;


                        }


                    });


                    providerAdapter=new ProviderAdapter(providerList,selecteVehicle,selectedCompanyType,customerId);
                    recyclerView.setAdapter(providerAdapter);
                    providerAdapter.notifyDataSetChanged();

                }

                else if(filter.equals("ascending")) {

//////////////////////////////////////////////////////





               reference= FirebaseDatabase.getInstance().getReference().child("PalCarWasher").child("ServicesOfferedByServiceProviders");
               reference.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                       sum=0;
                       count=0;

                       for (int i=0;i<providerList.size();i++){

                       final ServiceProvider prov = providerList.get(i);


                       for(DataSnapshot ds2: snapshot.getChildren())
                       {
                           final ServicesOfferedByServiceProviders sobsp=ds2.getValue(ServicesOfferedByServiceProviders.class) ;

                           if(prov.getProviderId().equals(sobsp.getProviderId())&&selecteVehicle.equals(sobsp.getVehicleName())){

                               sum+= Double.parseDouble(sobsp.getPrice());
                               count++;



                           }




                       }


                       ServiceProviderAndAvgPrice pp=new ServiceProviderAndAvgPrice(prov,sum/count);
                       providerPriceList.add(pp);

                       }
     //////////////////////************************sorting

                       Collections.sort(providerPriceList, new Comparator<ServiceProviderAndAvgPrice>() {
                           @Override
                           public int compare(ServiceProviderAndAvgPrice o1, ServiceProviderAndAvgPrice o2) {
                               Double price1 = Double.valueOf(o1.getAvg());
                               Double price2 = Double.valueOf(o2.getAvg());

                               return price1 > price2 ? -1 : (price1 < price2) ? 1 : 0;


                           }


                       });






                       providerList.clear();


                       for (int i=providerPriceList.size()-1;i>=0;i--){
                    //    Log.v("DataOB",providerPriceList.get(i).getAvg()+"");
                       providerList.add(providerPriceList.get(i).getSp());
                     //  Log.v("DataOB",providerList.get(i).getCompanyName()+"");

                   }


                       providerAdapter=new ProviderAdapter(providerList,selecteVehicle,selectedCompanyType,customerId);
                       recyclerView.setAdapter(providerAdapter);
                       providerAdapter.notifyDataSetChanged();

                       ////////////////////////////////****************

                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               });












                    //////////////////////////////////////////////////////

                }

                else if(filter.equals("descending")) {


//////////////////////////////////////////////////////





                    reference= FirebaseDatabase.getInstance().getReference().child("PalCarWasher").child("ServicesOfferedByServiceProviders");
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            sum=0;
                            count=0;

                            for (int i=0;i<providerList.size();i++){

                                final ServiceProvider prov = providerList.get(i);


                                for(DataSnapshot ds2: snapshot.getChildren())
                                {
                                    final ServicesOfferedByServiceProviders sobsp=ds2.getValue(ServicesOfferedByServiceProviders.class) ;

                                    if(prov.getProviderId().equals(sobsp.getProviderId())&&selecteVehicle.equals(sobsp.getVehicleName())){

                                        sum+= Double.parseDouble(sobsp.getPrice());
                                        count++;
                                       // Log.v("DataOB",sum+"");


                                    }







                                }


                                ServiceProviderAndAvgPrice pp=new ServiceProviderAndAvgPrice(prov,sum/count);

                          //   Log.v("DataOB",sum/count+"");

                                providerPriceList.add(pp);

                            }
                            //////////////////////************************sorting

                            Collections.sort(providerPriceList, new Comparator<ServiceProviderAndAvgPrice>() {
                                @Override
                                public int compare(ServiceProviderAndAvgPrice o1, ServiceProviderAndAvgPrice o2) {
                                    Double price1 = Double.valueOf(o1.getAvg());
                                    Double price2 = Double.valueOf(o2.getAvg());

                                    return price1 > price2 ? -1 : (price1 < price2) ? 1 : 0;


                                }


                            });






                            providerList.clear();


                            for (int i=0;i<providerPriceList.size();i++){
                           //  Log.v("DataOB",providerPriceList.get(i).getAvg()+"");
                                providerList.add(providerPriceList.get(i).getSp());
                          // Log.v("DataOB",providerList.get(i).getCompanyName()+"");

                            }


                            providerAdapter=new ProviderAdapter(providerList,selecteVehicle,selectedCompanyType,customerId);
                            recyclerView.setAdapter(providerAdapter);
                            providerAdapter.notifyDataSetChanged();




                            ////////////////////////////////****************

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });












                    //////////////////////////////////////////////////////

                }

     ///////////////////////////////////////
       ////////////////////////
          /////////////////////////


                ////////////////////////
                ///////////////////////////////////
                ///////////////////////
                 else{
                    providerAdapter=new ProviderAdapter(providerList,selecteVehicle,selectedCompanyType,customerId);
                    recyclerView.setAdapter(providerAdapter);
                    providerAdapter.notifyDataSetChanged();
                    }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });






    }


 ////////////////////////??????????????????????????????????????????   //////////////////////


    void getFromDatabase(String databaseChild, final String fullDate,String providerId){

        reference= FirebaseDatabase.getInstance().getReference().child("PalCarWasher")
                .child(databaseChild);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    WorkingTimeOnSaturdy fri = dataSnapshot.getValue(WorkingTimeOnSaturdy.class);
                    if(fri.getPrviderId().equals(providerId)){
                       // Log.v("DataOB",providerId);

                        String start=fri.getFrom();
                        String end=fri.getTo();
                        String  partStart=fri.getPartTimeFrom();
                        String  partEnd=fri.getPartTimeTo();

                       // Log.v("DataOB",start);
                        ///////////////////////////


                        if(start!=null){

                         genarateTimeSlots(start,end,partStart,partEnd,fullDate,providerId);



                        }


                        /////////////////////////


                    }




                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }


/////////////////////////////////////////

void genarateTimeSlots(String start,String end,String partStart,String partEnd,String fullDate,String providerId){


    Log.v("DataOB", "genarate");
    String format = "hh:mm aa";
    SimpleDateFormat sdf = new SimpleDateFormat(format);





    Date dateObj1 = null;
    Date dateObj2 = null;
    Date dateObj11 = null;
    Date dateObj22 = null;
    Date  nowPlus30  =null;

    try {
        dateObj1 = sdf.parse(start);
        dateObj2 = sdf.parse(end);

        Calendar currentTime = Calendar.getInstance();
        currentTime.add(Calendar.MINUTE, 30);




        String s=  sdf.format(currentTime.getTime());
        // Log.v("DataOB",s);

        nowPlus30 =sdf.parse(s);

        //  Log.v("DataOB",nowPlus30+"");
        // Log.v("DataOB",dateObj1+"");
        //Log.v("DataOB",dateObj2+"");


        long dif = dateObj1.getTime();
        while (dif < dateObj2.getTime()) {
            Date slot = new Date(dif);
            if (slot.after( nowPlus30)){
                Date nextSlot =new Date( dif + 3600000);
                if(!nextSlot.after(dateObj2))
                { String fullSlot = sdf.format(slot)+"-"+sdf.format(nextSlot);
                    // Log.v("DataOB",fullSlot+"");
                    stringSlotList.add(fullSlot);
                }
            }
            dif += 3600000;
        }



        dateObj11 = sdf.parse( partStart);
        dateObj22 = sdf.parse(partEnd);
        if(partStart!=null){
            long dif2 = dateObj11.getTime();
            while (dif2 < dateObj22.getTime()) {
                Date slot = new Date(dif2);
                if (slot.after( nowPlus30)) {
                    Date nextSlot = new Date(dif2 + 3600000);
                    if (!nextSlot.after(dateObj22)) {
                        String fullSlot = sdf.format(slot) + "-" + sdf.format(nextSlot);
                        //   Log.v("DataOB",fullSlot+"");
                        stringSlotList.add(fullSlot);

                    }
                }
                dif2 += 3600000;
            }

        }



    } catch (ParseException e) {
        e.printStackTrace();
    }
    getReservedTimeSlots(fullDate,providerId);




}








/////////////////////
    void getReservedTimeSlots(final String fullDate,String providerId){

        Log.v("DataOB", "reserved");


        //Log.v("DataOB", providerId);

        //reservedTimeSlots.clear();
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("PalCarWasher")
                .child("Orders");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
             //   Log.v("DataOB", "2");

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Orders order = dataSnapshot.getValue(Orders.class);
                       // Log.v("DataOB", order.getFullTime());
                        if (order.getProviderId().equals(providerId)&&order.getStatus().equals("confirmed")) {


                              // deleteTimeSlot(order.getFullTime(),fullDate,providerId);
                          //  Log.v("DataOB", order.getFullTime());


                            for(int i=0;i<stringSlotList.size();i++){
                                String fullTimeSlot=fullDate+" "+stringSlotList.get(i);
                                if(fullTimeSlot.equals(order.getFullTime()))
                                    stringSlotList.remove(i);


                            }



                        }// if

                    }


/////////////////////////


////////////////////////
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





//Log.v("DataOB",reservedTimeSlots.get(0));




        for(int i=0;i<stringSlotList.size();i++){
            String fullTimeSlot=fullDate+" "+stringSlotList.get(i);
            stringSlotListFull.add(fullTimeSlot);
         //   Log.v("DataOB",   stringSlotListFull.get(i));


        }



//return reservedTimeSlots2;
    }

////////////////////////////////////////////////








////////////////
    void searchView(final String selecteVehicle, final String selecteCompanyType, final String text ){




        databaseReference2= FirebaseDatabase.getInstance().getReference().child("PalCarWasher").child("ServiceProvider");
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                providerList.clear();
                providerPriceList.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {

                    final ServiceProvider prov=ds.getValue(ServiceProvider.class);
                    if(prov.getCompanyType().equals(selecteCompanyType)||prov.getCompanyType().equals("both")){

                        providerList.add(prov);

                    }

                }


ArrayList<ServiceProvider> searchProvider=new ArrayList<>();
for(ServiceProvider s : providerList){

    if(s.getCompanyName().toLowerCase().contains(text.toLowerCase()))

        searchProvider.add(s);

                            }






               providerAdapter.searchedList(searchProvider);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });






    }




















    public class AlertDialogFilter extends Dialog implements
            android.view.View.OnClickListener {

        Button aply;
        RadioGroup rg;



        public AlertDialogFilter( ) {
            super(ActivityHome.this);

        }
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            setContentView(R.layout.filter_view_home_activity);





            aply=findViewById(R.id.aply);




             rg = (RadioGroup) findViewById(R.id.radioGroup1);

            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
            {
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch(checkedId){
                        case R.id.ratting:
                            // do operations specific to this selection
                            filtering="rating";
                            break;
                        case R.id.ascending:
                            // do operations specific to this selection
                            filtering="ascending";


                            break;
                        case R.id.descending:
                            // do operations specific to this selection

                            filtering="descending";

                            break;
//                        case R.id.timeSlot:
//                            // do operations specific to this selection
//
//                            filtering="timeSlot";
//
//                            break;
                    }
                }
            });

        Log.v("DataOB","filtering"+filtering);

           aply.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

           changeView(selectedVehicle,selectedCompanyType,filtering);
            dismiss();

        }






    }











}