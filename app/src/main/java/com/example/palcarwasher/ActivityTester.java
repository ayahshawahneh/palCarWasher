package com.example.palcarwasher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityTester extends AppCompatActivity {
    RecyclerView recyclerView;
    CommentAndEvaluationAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tester);


        final String   providerId="-MRC4TAdUkYXdnRp0thN";
        final List<Orders> ordersList=new <Orders> ArrayList();
        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        ///recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("PalCarWasher")
                .child("Orders");

        reference.addListenerForSingleValueEvent(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ordersList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Orders o = snapshot.getValue(Orders.class);

                    if (o.getProviderId().equals(providerId)&&!o.getComment().equals("")) {

                        //      Log.v("DataOB",o.getComment()+"here");
                        ordersList.add(o);


                    }


                }

               // if(ordersList.size()==0)
                    //  Log.v("DataOB","here");
                  //  commentText.setVisibility(View.VISIBLE);
               // else {

                    adapter=new CommentAndEvaluationAdapter(ordersList);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();


               // }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



/////////////////////////////////




/////////////////////////////

    }
}