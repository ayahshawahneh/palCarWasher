package com.example.palcarwasher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ServiceDetailsActivity extends AppCompatActivity {

  //  ProviderDetailsAdapter providerDetailsAdapter;
    RecyclerView recyclerViewsSecond;
    ServiceProvider providerItem;
    String selectedVehicle;
    String selectedCompanyType;
    TextView CompanyName;
    ImageView CompanyLogo;
    TextView TotalPrice;
    String totalPrice;

    RecyclerView Sobsp_checkBox_recycler_view;
    Button Next;
    List<ServicesOfferedByServiceProviders> sobspList;
    SobspDetailsAdapter sobspDetailsAdapter;
    DatabaseReference databaseReference;
   String providerId;
    ArrayList<String> finalServicesList = new ArrayList<String>();
    List<String> selectedid;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);

/////////////////////////////////////////////////
     providerId=getIntent().getStringExtra("providerId");
        String  companyName=getIntent().getStringExtra("companyName");
        String  companyType=getIntent().getStringExtra("companyType");
        String  logo=getIntent().getStringExtra("logo");
        String  name=getIntent().getStringExtra("name");
        String  email=getIntent().getStringExtra("email");
        String  password=getIntent().getStringExtra("password");
        String  phoneNumber=getIntent().getStringExtra("phoneNumber");
        String  address=getIntent().getStringExtra("address");
        String  gender=getIntent().getStringExtra("gender");
        String  bankAccount=getIntent().getStringExtra("bankAccount");
        String  workingStatus=getIntent().getStringExtra("workingStatus");
        String  evaluationLevel=getIntent().getStringExtra("evaluationLevel");
        final String  customerId=getIntent().getStringExtra("customerId");
        selectedVehicle=getIntent().getStringExtra("selectedVehicle");
        selectedCompanyType=getIntent().getStringExtra("selectedCompanyType");
       providerItem =new ServiceProvider(providerId,companyName,companyType,logo,name,email,password,phoneNumber,address,gender,bankAccount,workingStatus,evaluationLevel);


   // Log.v("DataOB",providerId);


////////////////////////////////////////////////

       CompanyName =findViewById(R.id.company_name_name);
       CompanyLogo=findViewById(R.id.company_logo_logo);
        TotalPrice=findViewById(R.id.total_price);

CompanyName.setText(companyName);






        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();

        StorageReference imageRef1 = storageReference.child("logo/"+providerItem.getProviderId());
        long MAXBYTE = 1024*1024;
        imageRef1.getBytes(MAXBYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // convert byte to Bitmap :
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                CompanyLogo.setImageBitmap(bitmap);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
////////////////////////////////////////////////////////


       Sobsp_checkBox_recycler_view=findViewById(R.id.sobsp_checkBox_recycler_view);

        Sobsp_checkBox_recycler_view.setLayoutManager(new LinearLayoutManager(this));




        sobspList=new ArrayList<ServicesOfferedByServiceProviders>();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("PalCarWasher").child("ServicesOfferedByServiceProviders");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {

                    ServicesOfferedByServiceProviders sobspItem=ds.getValue(ServicesOfferedByServiceProviders.class);
                    if(providerId.equals(sobspItem.getProviderId())&&sobspItem.getVehicleName().equals(selectedVehicle)) {
                        sobspList.add(sobspItem);

                    }

                }




                sobspDetailsAdapter=new SobspDetailsAdapter(sobspList,TotalPrice);
                selectedid =  sobspDetailsAdapter.getSelectedSobsp();

            Sobsp_checkBox_recycler_view.setAdapter(sobspDetailsAdapter);





            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });






        b=findViewById(R.id.select);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogTimSlot cdd=new AlertDialogTimSlot(ServiceDetailsActivity.this,b,providerId);

                cdd.show();
                Window window = cdd.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            }
        });





Next=findViewById(R.id.next);
Next.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        totalPrice = TotalPrice.getText()+"";

        if(selectedid.size()==0&&b.getText().equals("Select Arrival Time"))
            Toast.makeText(ServiceDetailsActivity.this,"You have to select service and Arrival Time!", Toast.LENGTH_LONG).show();

        else  if(b.getText().equals("Select Arrival Time")){

            Toast.makeText(ServiceDetailsActivity.this,"You have to select Arrival Time!", Toast.LENGTH_LONG).show();}
        else if(selectedid.size()==0)
                Toast.makeText(ServiceDetailsActivity.this,"You have to select Service!", Toast.LENGTH_LONG).show();


        else{
       //   Toast.makeText(ServiceDetailsActivity.this,totalPrice+"", Toast.LENGTH_LONG).show();
            //Toast.makeText(ServiceDetailsActivity.this, selectedid.get(0), Toast.LENGTH_LONG).show();


            final String selectedArrivalTime=b.getText()+"";


            if(selectedCompanyType.equals("mobile")){

                //go to find my location then payment method

                Orders order =new Orders(providerId,null,customerId,
                        null,selectedCompanyType,null,null,selectedArrivalTime,
                        "confirmed",totalPrice+"",selectedVehicle,selectedid,null,null);

                Intent i =new Intent(ServiceDetailsActivity.this,CleanAddressForCustomerActivity.class);
                i.putExtra("order",(Serializable) order);
                startActivity(i);

            }

            else
            {
                //go to payment method

////////////////////////////////
                databaseReference= FirebaseDatabase.getInstance().getReference().child("PalCarWasher").child("ProviderLocation");


                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                final ProviderLocation s = dataSnapshot.getValue(ProviderLocation.class);
                                if (s.providerId.equals(providerId)) {


                                   // Toast.makeText(ServiceDetailsActivity.this,s.latitudeX + "," + s.longitudeY, Toast.LENGTH_LONG).show();

                                Orders order = new Orders(providerId, null, customerId,
                                        null, selectedCompanyType, s.latitudeX + "," + s.longitudeY, null, selectedArrivalTime,
                                        "confirmed", totalPrice + "", selectedVehicle, selectedid,null,null);

                                Intent i = new Intent(ServiceDetailsActivity.this, SelectPaymentMethodActivity.class);
                                i.putExtra("order", (Serializable) order);
                                startActivity(i);


                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        throw databaseError.toException();
                    }
                });

///////////////////////////////////



            }


        }

    }
});






    }







}