package com.example.palcarwasher;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderAdapterPROVIDER  extends RecyclerView.Adapter<OrderAdapterPROVIDER.orderHolder>{
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    List<Orders> ordersList;
    DatabaseReference databaseReference;
    Context context;

    private ClipboardManager myClipboard;
    private ClipData myClip;
    public OrderAdapterPROVIDER(List<Orders> ordersList,Context context) {
        this.ordersList = ordersList;
        this.context=context;
    }

    @NonNull
    @Override
    public orderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_form_orders_provider,parent,false);
        return new OrderAdapterPROVIDER.orderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final orderHolder holder, final int position) {

        final Orders orderItem=ordersList.get(position);

        /////////////  customerName ////////////////////
        databaseReference= FirebaseDatabase.getInstance().getReference().child("PalCarWasher").child("Customer");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        final Customer c = ds.getValue(Customer.class);

                        if(c.getCustomerId().equals(orderItem.getCustomerId())){
                            // Log.v("DataOB",s.getCompanyName()+"");
                            holder.customerName.setText(c.getName());
                            holder.phone.setText(c.getPhoneNumber());
                        }


                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });


///////////////////////////copy///////////////////////
        /*

         */
        myClipboard = (ClipboardManager)context.getSystemService(context.CLIPBOARD_SERVICE);
        holder.copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = holder.phone.getText().toString();
                myClip = ClipData.newPlainText("text", text);
                myClipboard.setPrimaryClip(myClip);
                Toast.makeText(context, "Phone number Copied!",
                        Toast.LENGTH_SHORT).show();
            }
        });
        /////////////////////vehicle

        holder.vehicleType.setText(orderItem.getVehicleSize());


        ////////////dateTime//////////////
        holder.dateTime.setText(orderItem.getFullTime());

        /////////////  status ////////////////////

        holder.status.setText(orderItem.getStatus());


        /////////////  totalPrice ////////////////////

        holder.totalPrice.setText(orderItem.getTotalPrice()+"$");

        //////////////////////////

        holder.show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),ActivityOrderrDetailsOrderPage.class);
                intent.putExtra("orderItem", (Serializable) orderItem);
                v.getContext().startActivity(intent);
            }
        });





    }








    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    public class orderHolder extends RecyclerView.ViewHolder {




        TextView status;
        ImageView copy;
        TextView customerName;
        TextView phone;
        TextView dateTime;
        TextView totalPrice;
        TextView orderType;
        TextView vehicleType;
        TableRow orderTypeRow;

        Button show;










        public orderHolder(@NonNull View itemView) {
            super(itemView);


            status=itemView.findViewById(R.id.status10);
            copy=itemView.findViewById(R.id.imageCopy);
            customerName=itemView.findViewById(R.id.customer_name20);
            phone=itemView.findViewById(R.id.phone_number20);
            dateTime=itemView.findViewById(R.id.date_time20);
            totalPrice=itemView.findViewById(R.id.total_price20);
            orderType=itemView.findViewById(R.id.order_type20);
            vehicleType=itemView.findViewById(R.id.vehicle20);
            orderTypeRow=itemView.findViewById(R.id.order_type_row20);
            show=itemView.findViewById(R.id.show_details);
        }


    }

}
