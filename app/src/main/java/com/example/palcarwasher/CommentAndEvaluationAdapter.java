package com.example.palcarwasher;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class CommentAndEvaluationAdapter extends RecyclerView.Adapter<CommentAndEvaluationAdapter.commentHolder> {

   List <Orders> ordersList;

    public CommentAndEvaluationAdapter(List<Orders> ordersList) {
        this.ordersList = ordersList;
    }

    @NonNull
    @Override
    public commentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_view,parent,false);
        return new CommentAndEvaluationAdapter.commentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final commentHolder holder, int position) {

        final Orders order=ordersList.get(position);
      //  Log.v("DataOB",order.getComment());


        ////////////////////customer name///////////


        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("PalCarWasher")
                .child("Customer");

        reference.addListenerForSingleValueEvent(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Customer s= snapshot.getValue(Customer.class);

                        if (s.getCustomerId().equals(order.getCustomerId())) {

                            Log.v("DataOB",s.getName());
                           holder.customerName.setText(s.getName());


                        }


                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//////////////////rate///////////////////////

        holder.rate.setRating(Float.parseFloat(order.getEvaluationLevel()));
///////////////////////comment////////////
        holder.comment.setText(order.getComment());

    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    public class commentHolder extends RecyclerView.ViewHolder{


        TextView customerName;
        TextView comment;
        RatingBar rate ;





        public commentHolder(@NonNull View itemView) {
            super(itemView);

            customerName=itemView.findViewById(R.id.customer_name);
            comment=itemView.findViewById(R.id.comment);
            rate=itemView.findViewById(R.id.ratingBar);


        }



    }


}
