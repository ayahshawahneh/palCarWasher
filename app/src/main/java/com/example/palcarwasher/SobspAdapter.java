package com.example.palcarwasher;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SobspAdapter extends RecyclerView.Adapter<SobspAdapter.SobspViewHolder> {
    List<ServicesOfferedByServiceProviders> sobspList;
    ServiceProvider providerItem ;
    String selectedVehicle;

    public SobspAdapter(List<ServicesOfferedByServiceProviders> sobspList, ServiceProvider providerItem, String selectedVehicle) {
        this.sobspList = sobspList;
        this.providerItem = providerItem;
        this.selectedVehicle = selectedVehicle;
    }



    @NonNull
    @Override
    public SobspViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.services_in_company_view, parent, false);
        return new SobspViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SobspViewHolder holder, int position) {


        final ServicesOfferedByServiceProviders sobspItem = sobspList.get(position);
        holder.serviceName.setText(sobspItem.getServiceName());
        holder.price.setText(sobspItem.getPrice());
        holder.description.setText(sobspItem.getDescription());

        /////////////////////////////////////////////////////////

        //Log.v("DataOB",providerItem.getCompanyName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String providerId =providerItem.getProviderId();
                String companyNamee =providerItem.getCompanyName();
                String companyType2 =providerItem.getCompanyType();
                String logo2 =providerItem.getLogo();
                String name2=providerItem.getName();
                String email2= providerItem.getEmail();
                String password2=providerItem.getPassword();
                String phoneNumber2=providerItem.getPhoneNumber();
                String address2 =providerItem.getAddress();
                String gender2 =providerItem.getGender();
                String bankAccount2=providerItem.getBankAccount();
                String workingStatus2=providerItem.getWorkingStatus();


           Intent intent=new Intent(view.getContext(),ServiceDetailsActivity.class);


                intent.putExtra("providerId", providerId);
                intent.putExtra("companyName", companyNamee);
                intent.putExtra("companyType", companyType2);
                intent.putExtra("logo", logo2);
                intent.putExtra("name", name2);
                intent.putExtra("email", email2);
                intent.putExtra("password", password2);
                intent.putExtra("phoneNumber", phoneNumber2);
                intent.putExtra("address", address2);
                intent.putExtra("gender",gender2);
                intent.putExtra("bankAccount", bankAccount2);
                intent.putExtra("workingStatus", workingStatus2);

                intent.putExtra("selectedVehicle",selectedVehicle);

                view.getContext().startActivity(intent);



            }
        });







    }

    @Override
    public int getItemCount() {
        return sobspList.size();
    }

    class SobspViewHolder extends RecyclerView.ViewHolder {
        TextView serviceName;
        TextView price;
        TextView description;

        SobspViewHolder(View itemView) {
            super(itemView);
            serviceName = itemView.findViewById(R.id.serviceName);
            price = itemView.findViewById(R.id.price);
            description = itemView.findViewById(R.id.description);
        }

    }
}
