package com.example.palcarwasher;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter  extends RecyclerView.Adapter<OrderAdapter.orderHolder>{
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    List<Orders> ordersList;
    DatabaseReference databaseReference;
     Context context;
    public OrderAdapter(List<Orders> ordersList,Context context) {
        this.ordersList = ordersList;
       this.context=context;
    }

    @NonNull
    @Override
    public orderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_form,parent,false);
        return new OrderAdapter.orderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final orderHolder holder, final int position) {

        final Orders orderItem=ordersList.get(position);




        /////////////  companyName ////////////////////
        databaseReference= FirebaseDatabase.getInstance().getReference().child("PalCarWasher").child("ServiceProvider");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        final ServiceProvider s = ds.getValue(ServiceProvider.class);

                        if(s.getProviderId().equals(orderItem.getProviderId())){
                           // Log.v("DataOB",s.getCompanyName()+"");
                            holder.companyName.setText(s.getCompanyName()+"");
                        }


                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });

        //////////////////////////


        /////////////  status ////////////////////

         holder.status.setText(orderItem.getStatus());
        /////////////  button ////////////////////
if(orderItem.getStatus().equals("confirmed")){
    holder.cancelOrder.setVisibility(View.VISIBLE);


    holder.cancelOrder.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

          ///  Log.v("DataOB", "cancel order");
            //AlertDialog dialog=
            new AlertDialog.Builder(context)
                    .setTitle("Cancel Order")
                    .setMessage("Are you sure that you want to cancel this order?\n\nYou will lose 70% of total amount!\nIf your payment method is cash the cut amount will be calculated with the next order\nIf you cancel two consecutive bookings in cash and without payment, you will be banned from the application! ")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, int which) {

                         ///////////////////
                            databaseReference = FirebaseDatabase.getInstance().getReference().child("PalCarWasher").child("Orders");
                            final DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("PalCarWasher")
                                    .child("Orders");
                            Query query=reference.orderByChild("orderId").equalTo(orderItem.getOrderId());
                            query.addListenerForSingleValueEvent(new ValueEventListener(){

                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    if(dataSnapshot.exists()){
                                        for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                                           Orders o=snapshot.getValue(Orders.class);


                                            //ordersList.clear();
                                           // clear();
                                           o.setStatus("canceled");
                                            databaseReference.push().setValue(o); //new child !!!!!
                                            reference.child(snapshot.getKey()).removeValue();


                                           notifyDataSetChanged();
                                            Toast.makeText(context,"Cancellation is Done! ",Toast.LENGTH_LONG).show();
                                           dialog.dismiss();

                                        }

                                    }



                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });



                         ///////////////////

                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();


        }
    });

}


        /////////////  orderId ////////////////////

        holder.orderId.setText(orderItem.getOrderId());


        /////////////  totalPrice ////////////////////

        holder.totalPrice.setText(orderItem.getTotalPrice()+"$");

        //////////////////////////

        /////////////  vehicleSize ////////////////////

        holder.vehicleSize.setText(orderItem.getVehicleSize());


        /////////////  paymentType ////////////////////

        holder.paymentType.setText(orderItem.getPaymentType());


        /////////////  paymentType ////////////////////

        holder.orderType.setText(orderItem.getOrderType());


        ////////////  cleanAddress ////////////////////

        holder.cleanAddress.setText(orderItem.getCleanAddress());

        ////////////  cleanAddress ////////////////////

        holder.dateTime.setText(orderItem.getFullTime());


        /////////////////////services///////////////////////////////////

        List<String> servicesList=orderItem.getOfferIds();
       // Log.v("DataOB", servicesList.get(0));
      //  Log.v("DataOB", servicesList.get(1));

        final LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.services.getContext(),
                LinearLayoutManager.VERTICAL,
                false );
        layoutManager.setInitialPrefetchItemCount(servicesList.size());
        ServicesInOrderAdapter subItemAdapter = new ServicesInOrderAdapter(servicesList);

        holder.services.setLayoutManager(layoutManager);
       holder.services.setAdapter(subItemAdapter);
        holder.services.setRecycledViewPool(viewPool);

/////////////////////////////////////////

    }




    public void clear() {
        int size = ordersList.size();
        ordersList.clear();
        notifyItemRangeRemoved(0, size);
    }


    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    public class orderHolder extends RecyclerView.ViewHolder {


        TextView companyName;
        TextView status;
        TextView orderId;
        TextView dateTime;
        TextView totalPrice;
        TextView vehicleSize;
        RecyclerView services;
        TextView paymentType;
        TextView orderType;
        TextView cleanAddress;
        Button cancelOrder;



        public orderHolder(@NonNull View itemView) {
            super(itemView);

           companyName=itemView.findViewById(R.id.company_name10);
           status=itemView.findViewById(R.id.status10);
            dateTime=itemView.findViewById(R.id.date_time10);
            orderId=itemView.findViewById(R.id.order_id10);
           totalPrice=itemView.findViewById(R.id.total_price10);
            vehicleSize=itemView.findViewById(R.id.vehicle10);
           services=itemView.findViewById(R.id.recycler_view_services);
             paymentType=itemView.findViewById(R.id.payment_type10);
           orderType=itemView.findViewById(R.id.order_type10);
           cleanAddress=itemView.findViewById(R.id.clean_address10);
            cancelOrder=itemView.findViewById(R.id.cancel_button10);
        }


    }

}
