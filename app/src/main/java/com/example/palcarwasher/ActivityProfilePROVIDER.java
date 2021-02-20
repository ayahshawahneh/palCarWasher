package com.example.palcarwasher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TableRow;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class ActivityProfilePROVIDER extends AppCompatActivity {
    String providerId;
    FloatingActionButton floatingButtonFilter ;



    Intent intentToEditCustomerProfile;


    TableRow itemLogout;
    TableRow appRate;
    TableRow  editProfile;
    TableRow addNewServices;
    TableRow suggestNewVehicle;
    TableRow  suggestNewService;
    TableRow editServices;

    TableRow editWorkingHours;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_p_r_o_v_i_d_e_r);

        //////////////
     providerId=getIntent().getStringExtra("providerId");
      //  providerId = "-MRC4TAdUkYXdnRp0thN";


        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_home);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.nav_home:
                        Intent intent1 = new Intent(ActivityProfilePROVIDER.this, ActivityHomePROVIDER.class);
                        intent1.putExtra("providerId", providerId.toString());//new
                        startActivity(intent1);
                        overridePendingTransition(0, 0);
                        return true;


                    case R.id.nav_orders:
                        Intent intent2 = new Intent(ActivityProfilePROVIDER.this, ActivityOrdersPROVIDER.class);
                        intent2.putExtra("providerId", providerId.toString());//new
                        startActivity(intent2);
                        overridePendingTransition(0, 0);
                        return true;


                    case R.id.nav_wallet:
                        Intent intent3 = new Intent(ActivityProfilePROVIDER.this, ActivityProfilePROVIDER.class);
                        intent3.putExtra("providerId", providerId.toString());//new
                        startActivity(intent3);
                        overridePendingTransition(0, 0);
                        return true;


                    case R.id.nav_profile:

                        return true;


                }


                return false;
            }
        });





        ////////////////////////



        itemLogout = findViewById(R.id.itemLogout);
        appRate = findViewById(R.id.appRate);
        editProfile = findViewById(R.id.editProfile);
        editServices=findViewById(R.id.editServices);
        addNewServices=findViewById(R.id.addNewService);
        suggestNewVehicle=findViewById(R.id.suggestVehicle);
        suggestNewService=findViewById(R.id.suggestNewService);
        editWorkingHours=findViewById(R.id.editWorkingHours);


        appRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityProfilePROVIDER.this,AppRate.class);
                startActivity(intent);
            }
        });
        itemLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ActivityProfilePROVIDER.this,login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

         editProfile.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                Intent intent = new Intent(ActivityProfilePROVIDER.this,
                         EditServiceProviderProfile.class);
                intent.putExtra("providerId",providerId);
                startActivity(intent);
             }
         });

        editServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityProfilePROVIDER.this,
                       ActivityEditServicesPROVIDER.class);
                intent.putExtra("providerId",providerId);
                startActivity(intent);
            }
        });

        addNewServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityProfilePROVIDER.this,
                        AddnewService.class);
                intent.putExtra("providerId",providerId);
                startActivity(intent);
            }
        });
        suggestNewVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityProfilePROVIDER.this,
                       ActivityAddNewVehicle.class);
                intent.putExtra("providerId",providerId);
                startActivity(intent);
            }
        });


        suggestNewService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityProfilePROVIDER.this,
                       SuggestNewService.class);
                intent.putExtra("providerId",providerId);
                startActivity(intent);
            }
        });





        editWorkingHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityProfilePROVIDER.this,
                        ActivityEditWorkingHours.class);
                intent.putExtra("providerId",providerId);
                startActivity(intent);
            }
        });
    }
}