package com.example.palcarwasher;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ServicesInOrderAdapter extends RecyclerView.Adapter<ServicesInOrderAdapter.serviceHolder> {

    List<String> servicesList;

    public ServicesInOrderAdapter(List<String> servicesList) {
        this.servicesList = servicesList;
    }

    @NonNull
    @Override
    public serviceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.services_in_order_view,parent,false);
        return new ServicesInOrderAdapter.serviceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final serviceHolder holder, final int position) {

            final String offerIdItem = servicesList.get(position);





        DatabaseReference databaseReference2= FirebaseDatabase.getInstance().getReference().child("PalCarWasher").child("ServicesOfferedByServiceProviders");
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    ServicesOfferedByServiceProviders sobspItem=ds.getValue(ServicesOfferedByServiceProviders.class);
                    if(sobspItem.getOfferId().equals(offerIdItem)) {
                       holder.service.setText(position+1+"- "+sobspItem.getServiceName());
                        holder.price.setText(sobspItem.getPrice()+"$");
                        holder.des.setText("  "+sobspItem.getDescription());
                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }


        });







    }

    @Override
    public int getItemCount() {
        return servicesList.size();
    }


    public class serviceHolder extends RecyclerView.ViewHolder{
        TextView service;
        TextView price;
        TextView des;
        public serviceHolder(@NonNull View itemView) {
            super(itemView);

             service=itemView.findViewById(R.id.service_name10);
            price=itemView.findViewById(R.id.price10);
           des=itemView.findViewById(R.id.description10);
        }



    }

}
