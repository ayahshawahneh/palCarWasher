package com.example.palcarwasher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityEditServicesPROVIDER extends AppCompatActivity {
    DatabaseReference databaseReference2;
    List<ServicesOfferedByServiceProviders> SobspList=new ArrayList<>();
    EditServicesAdapter adapter;
    RecyclerView recyclerView;
    String providerId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_services_p_r_o_v_i_d_e_r);


       providerId=getIntent().getStringExtra("providerId");

      ///providerId="-MRC4TAdUkYXdnRp0thN";
      //  providerId="-MPq8uV089ZVlR0gDrnW";
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




        databaseReference2= FirebaseDatabase.getInstance().getReference().child("PalCarWasher").child("ServicesOfferedByServiceProviders");
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                SobspList.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {

                    final ServicesOfferedByServiceProviders s=ds.getValue(ServicesOfferedByServiceProviders.class);
                    if(s.getProviderId().equals(providerId)){


                        SobspList.add(s);

                    }

                }




               adapter=new EditServicesAdapter(SobspList,ActivityEditServicesPROVIDER.this);
                recyclerView.setAdapter(adapter);
               adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });

/////////////////////////////////////////////////////////





    }
}