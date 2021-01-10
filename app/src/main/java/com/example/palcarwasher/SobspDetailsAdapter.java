package com.example.palcarwasher;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class SobspDetailsAdapter extends RecyclerView.Adapter<SobspDetailsAdapter.serviceDetailesHolder>{
    List<ServicesOfferedByServiceProviders> sobspList;

    public SobspDetailsAdapter(List<ServicesOfferedByServiceProviders> sobspList) {
        this.sobspList = sobspList;
    }

    @NonNull
    @Override
    public serviceDetailesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_check_box,parent,false);
        return new SobspDetailsAdapter.serviceDetailesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final serviceDetailesHolder holder, int position) {


        final ServicesOfferedByServiceProviders sobspItem = sobspList.get(position);
        holder.serviceName.setText(sobspItem.getServiceName());
        holder.price.setText(sobspItem.getPrice());
        holder.description.setText(sobspItem.getDescription());




    }

    @Override
    public int getItemCount() {


        return sobspList.size();



    }


    public class serviceDetailesHolder extends RecyclerView.ViewHolder{

        TextView serviceName;
        TextView price;
        TextView description;

        public serviceDetailesHolder(@NonNull View itemView) {
            super(itemView);
            serviceName = itemView.findViewById(R.id.service_name_checkBox);
            price = itemView.findViewById(R.id.price);
            description = itemView.findViewById(R.id.description);

        }
    }








}
