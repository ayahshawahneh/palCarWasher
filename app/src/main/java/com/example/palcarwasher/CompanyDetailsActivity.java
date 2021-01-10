package com.example.palcarwasher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class CompanyDetailsActivity extends AppCompatActivity {



  ImageView companyLogo;
    TextView CompanyName;
    TextView companyOwner;
    TextView companyPhoneNumber;
    TextView companyAddress;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_details);



      //  recyclerViewsSecond=findViewById(R.id.secondRecyclerView);


       // Intent intent=this.getIntent();
        //Bundle bundle=intent.getExtras();
        //ServiceProvider fetchProvider=(ServiceProvider) bundle.getSerializable("key");

        //  String PhoneNumber = getIntent().getStringExtra("phonenumber");
        String providerId=getIntent().getStringExtra("providerId");
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

  ServiceProvider fetchProvider=new ServiceProvider(providerId,companyName,companyType,logo,name,email,password,phoneNumber,address,gender,bankAccount,workingStatus);





        companyLogo=findViewById(R.id.company_logo2);
        CompanyName= findViewById(R.id.company_name22);
        companyOwner=findViewById(R.id.company_owner);
        companyPhoneNumber=findViewById(R.id.company_phone_number);
        companyAddress=findViewById(R.id.company_address);


        CompanyName.setText(companyName);
        companyOwner.setText(name);
        companyPhoneNumber.setText(phoneNumber);
        companyAddress.setText(address);



        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();

        StorageReference imageRef1 = storageReference.child("logo/"+providerId);
        long MAXBYTE = 1024*1024;
        imageRef1.getBytes(MAXBYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // convert byte to Bitmap :
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                companyLogo.setImageBitmap(bitmap);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }





}
