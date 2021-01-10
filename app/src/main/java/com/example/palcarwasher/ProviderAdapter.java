package com.example.palcarwasher;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProviderAdapter extends RecyclerView.Adapter<ProviderAdapter.providerHolder> {


private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
List<ServiceProvider> providersList;
List<ServicesOfferedByServiceProviders> sobspList;
Context context;
int servicCount;
final String selectedVehicle;



    public ProviderAdapter(List<ServiceProvider> providersList, String selectedVehicle) {
        this.providersList = providersList;
        this.selectedVehicle = selectedVehicle;
    }


  /* public ProviderAdapter(List<ServiceProvider> providersList, List<ServicesOfferedByServiceProviders> sobspList) {
        this.providersList = providersList;
        this.sobspList = sobspList;
    }*/

    @NonNull
    @Override
    public providerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.company_view,parent,false);
        return new providerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final providerHolder holder, int position) {

        final ServiceProvider providerItem=providersList.get(position);

        holder.companyName.setText(providerItem.getCompanyName());

     ////////////////////////////////////////////////////////////////


        String providerId = providerItem.getProviderId();


        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();

        StorageReference imageRef1 = storageReference.child("logo/"+providerId);
        long MAXBYTE = 1024*1024;
        imageRef1.getBytes(MAXBYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // convert byte to Bitmap :
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                holder.companyLogo.setImageBitmap(bitmap);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
////////////////////////////////////////////////////////







      final  LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.sobspRecyclerView.getContext(),
                LinearLayoutManager.VERTICAL,
                false );

        servicCount=0;
        DatabaseReference databaseReference2;
        databaseReference2= FirebaseDatabase.getInstance().getReference().child("PalCarWasher").child("ServicesOfferedByServiceProviders");
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                sobspList=new ArrayList<ServicesOfferedByServiceProviders>();
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    ServicesOfferedByServiceProviders sobspItem=ds.getValue(ServicesOfferedByServiceProviders.class);
                    if(providerItem.getProviderId().equals(sobspItem.getProviderId())&&sobspItem.getVehicleName().equals(selectedVehicle)) {
                        sobspList.add(sobspItem);
                        servicCount++;
                    }




                }

                layoutManager.setInitialPrefetchItemCount(sobspList.size());
                SobspAdapter subItemAdapter = new SobspAdapter(sobspList,providerItem,selectedVehicle);

                holder.sobspRecyclerView.setLayoutManager(layoutManager);
                holder.sobspRecyclerView.setAdapter(subItemAdapter);
                holder.sobspRecyclerView.setRecycledViewPool(viewPool);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }


        });




/////////////////////////////////////////


    holder.companyLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           String providerId= providerItem.getProviderId();
           String companyNamee =providerItem.getCompanyName();
           String companyType =providerItem.getCompanyType();
           String logo =providerItem.getLogo();
           String name=providerItem.getName();
           String email= providerItem.getEmail();
           String password=providerItem.getPassword();
           String phoneNumber=providerItem.getPhoneNumber();
           String address =providerItem.getAddress();
           String gender =providerItem.getGender();
           String bankAccount=providerItem.getBankAccount();
           String workingStatus=providerItem.getWorkingStatus();



                Intent intent=new Intent(view.getContext(),CompanyDetailsActivity.class);
                intent.putExtra("providerId", providerId);
                intent.putExtra("companyName", companyNamee);
                intent.putExtra("companyType", companyType);
                intent.putExtra("logo", logo);
                intent.putExtra("name", name);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                intent.putExtra("phoneNumber", phoneNumber);
                intent.putExtra("address", address);
                intent.putExtra("gender",gender);
                intent.putExtra("bankAccount", bankAccount);
                intent.putExtra("workingStatus", workingStatus);
                view.getContext().startActivity(intent);


              /*Intent intent=new Intent(view.getContext(),CompanyDetailsActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("key", (Serializable) providerItem);
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);*/
            }
        });



        holder.companyName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String providerId= providerItem.getProviderId();
                String companyNamee =providerItem.getCompanyName();
                String companyType =providerItem.getCompanyType();
                String logo =providerItem.getLogo();
                String name=providerItem.getName();
                String email= providerItem.getEmail();
                String password=providerItem.getPassword();
                String phoneNumber=providerItem.getPhoneNumber();
                String address =providerItem.getAddress();
                String gender =providerItem.getGender();
                String bankAccount=providerItem.getBankAccount();
                String workingStatus=providerItem.getWorkingStatus();



                Intent intent=new Intent(view.getContext(),CompanyDetailsActivity.class);
                intent.putExtra("providerId", providerId);
                intent.putExtra("companyName", companyNamee);
                intent.putExtra("companyType", companyType);
                intent.putExtra("logo", logo);
                intent.putExtra("name", name);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                intent.putExtra("phoneNumber", phoneNumber);
                intent.putExtra("address", address);
                intent.putExtra("gender",gender);
                intent.putExtra("bankAccount", bankAccount);
                intent.putExtra("workingStatus", workingStatus);
                view.getContext().startActivity(intent);


              /*Intent intent=new Intent(view.getContext(),CompanyDetailsActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("key", (Serializable) providerItem);
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);*/
            }
        });






    }


    @Override
    public int getItemCount() {
        return providersList.size();
    }

    public class providerHolder extends RecyclerView.ViewHolder{
            ImageView companyLogo;
            TextView companyName;
            RecyclerView sobspRecyclerView;

        public providerHolder(@NonNull View itemView) {
            super(itemView);
            companyLogo=itemView.findViewById(R.id.company_logo_logo);
            companyName=itemView.findViewById(R.id.company_name_name);
            sobspRecyclerView=itemView.findViewById(R.id.sobsp_recycler_view);
        }
    }



}
