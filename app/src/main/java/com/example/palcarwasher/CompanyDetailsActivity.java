package com.example.palcarwasher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
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

import java.util.ArrayList;
import java.util.List;

public class CompanyDetailsActivity extends AppCompatActivity {



    ImageView companyLogo;
    TextView CompanyName;
    TextView companyOwner;
    TextView companyPhoneNumber;
    TextView companyAddress;
    TextView numOrders;
    RatingBar rate ;


    TextView sat,sun,mon,tue,wed,thu,fri,commentText;
    RecyclerView recyclerView;
    CommentAndEvaluationAdapter adapter;
    int count =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_details);


        companyLogo=findViewById(R.id.company_logo2);
        CompanyName= findViewById(R.id.company_name22);
        companyOwner=findViewById(R.id.company_owner);
        companyPhoneNumber=findViewById(R.id.company_phone_number);
        companyAddress=findViewById(R.id.company_address);
        rate=findViewById(R.id.ratingBar);
        numOrders=findViewById(R.id.number_of_orders);



if(getIntent().getStringExtra("companyName") != null){



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
        String  rating=getIntent().getStringExtra("rating");

 // ServiceProvider fetchProvider=new ServiceProvider(providerId,companyName,companyType,logo,name,email,password,phoneNumber,address,gender,bankAccount,workingStatus);




    CompanyName.setText(companyName);
    companyOwner.setText(name);
    companyPhoneNumber.setText(phoneNumber);
    companyAddress.setText(address);
    rate.setRating(Float.parseFloat(rating));




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



        ///////////////...................................////////////////////////////
}else{
final String providerID= getIntent().getStringExtra("providerId");
DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("PalCarWasher").child("ServiceProvider");
    databaseReference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    final ServiceProvider s = ds.getValue(ServiceProvider.class);

                    if(s.getProviderId().equals(providerID)){
                        CompanyName.setText(s.getCompanyName());
                        companyOwner.setText(s.getName());
                        companyPhoneNumber.setText(s.getPhoneNumber());
                        companyAddress.setText(s.getAddress());
                        rate.setRating(Float.parseFloat(s.getEvaluationLevel()));


                    }


                }


            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            throw databaseError.toException();
        }
    });




    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    StorageReference storageReference = firebaseStorage.getReference();

    StorageReference imageRef1 = storageReference.child("logo/"+providerID);
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




}//else







      DatabaseReference  databaseReference= FirebaseDatabase.getInstance().getReference().child("PalCarWasher").child("Orders");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                count=0;
                if(dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        final Orders o = ds.getValue(Orders.class);
                        if (o.getProviderId().equals(getIntent().getStringExtra("providerId"))) {

                            count++;



                        }

                    }
                }


                numOrders.setText(count+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });


//////////////////////////days
        final String providerId=getIntent().getStringExtra("providerId");

        sat=findViewById(R.id.sat);viewWorkingHours("WorkingTimeOnSaturdy",  sat, providerId);
        sun=findViewById(R.id.sun);viewWorkingHours("WorkingTimeOnSunday", sun, providerId);
        mon=findViewById(R.id.mon);viewWorkingHours("WorkingTimeOnMonday", mon,  providerId);
        tue=findViewById(R.id.tue);viewWorkingHours("WorkingTimeOnTueseday", tue, providerId);
        wed=findViewById(R.id.wed);viewWorkingHours("WorkingTimeOnWednesday",wed, providerId);
        thu=findViewById(R.id.thu);viewWorkingHours("WorkingTimeOnThuresday", thu,  providerId);
        fri=findViewById(R.id.fri);viewWorkingHours("WorkingTimeOnFriday", fri,  providerId);


commentText=findViewById(R.id.commentandfeedback);
        ///////////////////////////////

final List<Orders> ordersList=new <Orders> ArrayList();
        recyclerView=findViewById(R.id.recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(0, 0);
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("PalCarWasher")
                .child("Orders");

        reference.addListenerForSingleValueEvent(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                     ordersList.clear();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                       Orders o = snapshot.getValue(Orders.class);

                        if (o.getProviderId().equals(providerId)&&!o.getComment().equals("")) {

                     //      Log.v("DataOB",o.getComment()+"here");
                            ordersList.add(o);


                        }


                    }

if(ordersList.size()==0)
              //  Log.v("DataOB","here");
                 commentText.setVisibility(View.VISIBLE);
   else {

                adapter=new CommentAndEvaluationAdapter(ordersList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();


}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });











    }


void viewWorkingHours(String child, final TextView Day, final String providerId){



    DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("PalCarWasher")
            .child(child);

    reference.addListenerForSingleValueEvent(new ValueEventListener(){

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            if(dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    WorkingTimeOnSaturdy day = snapshot.getValue(WorkingTimeOnSaturdy.class);

                    if (day.getPrviderId().equals(providerId)) {


                        if (!day.getFrom().equals("")) {
                            Day.setText(day.getFrom() + "-" + day.getTo());
                            if (!day.getPartTimeFrom().equals(""))
                                Day.setText(day.getFrom() + "-" + day.getTo() + " , " + day.getPartTimeFrom() + "-" + day.getPartTimeTo());

                        } else {
                            Day.setText("---");
                        }


                    }


                }

            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });








}


}
