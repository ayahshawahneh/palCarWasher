package com.example.palcarwasher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ActivityWalletPROVIDER extends AppCompatActivity {


    String providerId;
    FloatingActionButton floatingButtonFilter ;


    TextView allTotal,thisMonth,lastMonth;
    double sumTotal,sumthis,sumLast;
    List<Orders> ordersList =new ArrayList<Orders>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_p_r_o_v_i_d_e_r);
      //////////////
        providerId=getIntent().getStringExtra("providerId");
       // providerId = "-MRC4TAdUkYXdnRp0thN";


        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_home);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.nav_home:
                        Intent intent1 = new Intent(ActivityWalletPROVIDER.this, ActivityHomePROVIDER.class);
                        intent1.putExtra("providerId", providerId.toString());//new
                        startActivity(intent1);
                        overridePendingTransition(0, 0);
                        return true;


                    case R.id.nav_orders:
                        Intent intent2 = new Intent(ActivityWalletPROVIDER.this, ActivityOrdersPROVIDER.class);
                        intent2.putExtra("providerId", providerId.toString());//new
                        startActivity(intent2);
                        overridePendingTransition(0, 0);
                        return true;


                    case R.id.nav_wallet:

                        return true;


                    case R.id.nav_profile:
                        Intent intent3 = new Intent(ActivityWalletPROVIDER.this, ActivityProfilePROVIDER.class);
                        intent3.putExtra("providerId", providerId.toString());//new
                        startActivity(intent3);
                        overridePendingTransition(0, 0);
                        return true;


                }


                return false;
            }
        });





        ////////////////////////

        allTotal=findViewById(R.id.all_total);
        thisMonth=findViewById(R.id.this_month);
        lastMonth=findViewById(R.id.last_month);


        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("PalCarWasher")
                .child("Orders");

        reference.addListenerForSingleValueEvent(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ordersList.clear();
                sumTotal=0;
                sumthis=0;
                sumLast=0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Orders o = snapshot.getValue(Orders.class);

                    if (o.getProviderId().equals(providerId)) {

                        if (o.getStatus().equals("canceled"))
                            sumTotal += Double.parseDouble(o.getTotalPrice())/2;
                        else
                            sumTotal += Double.parseDouble(o.getTotalPrice());
                     /////////////////////////

                        Date orderDate;
                        Date startOfThisMonth;
                        Date lastDayOfMonth;





                        try {

                            Calendar c = Calendar.getInstance();   // this takes current date
                            c.set(Calendar.DAY_OF_MONTH, 1);
                            startOfThisMonth=c.getTime();


                            Calendar cal = Calendar.getInstance();
                            cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
                            lastDayOfMonth = cal.getTime();





                            Calendar aCalendar = Calendar.getInstance();
                            aCalendar.add(Calendar.MONTH, -1);
                            aCalendar.set(Calendar.DATE, 1);
                            Date firstDateOfPreviousMonth = aCalendar.getTime();
                            aCalendar.set(Calendar.DATE,     aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                            Date lastDateOfPreviousMonth = aCalendar.getTime();






                            String o11=o.getFullTime().substring(4,14);
                            SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");
                            orderDate = sdf.parse(o11);







                          //  Log.v("DataOB",lastDayOfMonth+" "+startOfThisMonth+"  "+orderDate);
                        if(!orderDate.after(lastDayOfMonth)&&!orderDate.before(startOfThisMonth)){
                                if(o.getStatus().equals("canceled"))
                                    sumthis+=Double.parseDouble(o.getTotalPrice())/2;
                                 else
                                   sumthis+=Double.parseDouble(o.getTotalPrice());

                        }


                            if(!orderDate.after(lastDateOfPreviousMonth)&&!orderDate.before(firstDateOfPreviousMonth)){
                                if(o.getStatus().equals("canceled"))
                                    sumLast+=Double.parseDouble(o.getTotalPrice())/2;
                                else
                                    sumLast+=Double.parseDouble(o.getTotalPrice());

                            }


                        } catch (ParseException e) {

                        }




///////////////////////////////////////////////////
                    }

                    thisMonth.setText(sumthis+"$");
                    allTotal.setText(sumTotal+"$");
                    lastMonth.setText(sumLast+"$");
                }


                //dept.setText(sumDept+"$");
               // total.setText(sumTotal+"$");





            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




























  /*
    Date dateObj1;
                            Date dateObj2;

                            try {

                                String o11=o1.getFullTime().substring(4,14);
                                String o22=o2.getFullTime().substring(4,14);
                                SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");


                                dateObj1 = sdf.parse(o11);
                                dateObj2 = sdf.parse(o22);
                                return dateObj1.compareTo( dateObj2);



                            } catch (ParseException e) {
                                return 0;
                            }
   */





    }
}