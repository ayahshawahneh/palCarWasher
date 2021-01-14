package com.example.palcarwasher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ServiceDetailsActivity extends AppCompatActivity {

  //  ProviderDetailsAdapter providerDetailsAdapter;
    RecyclerView recyclerViewsSecond;
    ServiceProvider providerItem;
    String selectedVehicle;
    TextView CompanyName;
    ImageView CompanyLogo;
    RecyclerView Sobsp_checkBox_recycler_view;
    Button Next;
    List<ServicesOfferedByServiceProviders> sobspList;
    SobspDetailsAdapter sobspDetailsAdapter;
    DatabaseReference databaseReference;
   String providerId;
    ArrayList<String> finalServicesList = new ArrayList<String>();
    List<String> selectedid;

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
        selectedVehicle=getIntent().getStringExtra("selectedVehicle");
       providerItem =new ServiceProvider(providerId,companyName,companyType,logo,name,email,password,phoneNumber,address,gender,bankAccount,workingStatus);


    Log.v("DataOB",providerId);


////////////////////////////////////////////////

       CompanyName =findViewById(R.id.company_name_name);
       CompanyLogo=findViewById(R.id.company_logo_logo);


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




                sobspDetailsAdapter=new SobspDetailsAdapter(sobspList);
                selectedid =  sobspDetailsAdapter.getSelectedSobsp();
            Sobsp_checkBox_recycler_view.setAdapter(sobspDetailsAdapter);





            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });







Next=findViewById(R.id.next);
Next.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Toast.makeText(ServiceDetailsActivity.this, selectedid.get(0), Toast.LENGTH_LONG).show();
    }
});






    }







}