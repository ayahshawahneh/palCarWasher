package com.example.palcarwasher;

import android.content.ClipData;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class SobspDetailsAdapter extends RecyclerView.Adapter<SobspDetailsAdapter.serviceDetailesHolder>{
    List<ServicesOfferedByServiceProviders> sobspList;


 List<String> finalSobspList=new ArrayList<>();


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
            holder.checkbox.setText(sobspItem.getServiceName());
            holder.price.setText(sobspItem.getPrice());
            holder.description.setText(sobspItem.getDescription());
        final String offerId=sobspItem.getOfferId();

        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    finalSobspList.add(offerId);
                }else{
                    finalSobspList.remove(offerId);
                }
            }
        });

    }

    @Override
    public int getItemCount() {


        return sobspList.size();



    }

   public List<String> getSelectedSobsp() {
      return finalSobspList;
    }




    public class serviceDetailesHolder extends RecyclerView.ViewHolder{

        CheckBox checkbox;
        TextView price;
        TextView description;

        public serviceDetailesHolder(@NonNull View itemView) {
            super(itemView);
            checkbox = itemView.findViewById(R.id.service_name_checkBox);
            price = itemView.findViewById(R.id.price);
            description = itemView.findViewById(R.id.description);

        }


        public void setOnClickListener(View.OnClickListener onClickListener) {
            itemView.setOnClickListener(onClickListener);
        }

    }








}
