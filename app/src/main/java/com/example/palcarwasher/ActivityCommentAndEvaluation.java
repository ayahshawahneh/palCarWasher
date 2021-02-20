package com.example.palcarwasher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ActivityCommentAndEvaluation extends AppCompatActivity {
EditText comment;
RatingBar rate;
Orders orderItem;
Button RatingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_and_evaluation);

        orderItem=(Orders) getIntent().getSerializableExtra("orderItem");
        comment=findViewById(R.id.et_comment);
        rate = (RatingBar)findViewById(R.id.ratingBar3);
RatingButton=findViewById(R.id.ratingbutton);
        RatingButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

              ////////////update company rate


                final DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference().child("PalCarWasher")
                        .child("ServiceProvider");

                final DatabaseReference reference2= FirebaseDatabase.getInstance().getReference().child("PalCarWasher")
                        .child("ServiceProvider");
                Query query2=reference2.orderByChild("providerId").equalTo(orderItem.getProviderId());
                query2.addListenerForSingleValueEvent(new ValueEventListener(){

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){
                            for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                                ServiceProvider s=snapshot.getValue(ServiceProvider.class);

                                float r = Float.parseFloat(s.getEvaluationLevel());
                                r+=rate.getRating();

                                s.setEvaluationLevel((r/2)+"");
                                databaseReference2.push().setValue(s); //new child !!!!!
                                reference2.child(snapshot.getKey()).removeValue();

                            }

                        }



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });














///////////////////////////////////////////////////////update order rate
                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("PalCarWasher")
                        .child("Orders");

                final DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("PalCarWasher")
                        .child("Orders");
                Query query=reference.orderByChild("orderId").equalTo(orderItem.getOrderId());
                query.addListenerForSingleValueEvent(new ValueEventListener(){

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){
                            for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                                Orders o=snapshot.getValue(Orders.class);

                               o.setEvaluationLevel(rate.getRating()+"");
                               o.setComment(comment.getText()+"");

                                databaseReference.push().setValue(o); //new child !!!!!
                                reference.child(snapshot.getKey()).removeValue();

                                Toast.makeText(ActivityCommentAndEvaluation.this,"Evaluated Successfully! ",Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(ActivityCommentAndEvaluation.this,OrdersActivity.class);
                                intent.putExtra("customerId",o.getCustomerId());
                                    startActivity(intent);

                            }

                        }



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
























            }
        });




    }
}