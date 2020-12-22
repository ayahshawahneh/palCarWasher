package com.example.palcarwasher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;

public class LaundryLicencePic extends AppCompatActivity {
    private Button UploadPic;
    StorageReference mStorage=null  ;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laundry_licence_pic);
        //implementation 'com.google.firebase:firebase-storage:19.2.0'
        //implementation 'com.google.firebase:firebase-core:16.0.4'



        //go to gallary to select picture
        UploadPic=(Button)findViewById(R.id.UploadPic);
        mStorage = FirebaseStorage.getInstance().getReference();// the root
        UploadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ImageIntent = new Intent(Intent.ACTION_PICK);
                ImageIntent.setType("image/*");
                startActivityForResult(ImageIntent, 3);


            }
        });

    }

    //select picture
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode==3 && resultCode==RESULT_OK){
            final Uri uriImage = data.getData();

            StorageReference filePath =  mStorage.child(Calendar.getInstance().getTime().toString());//the name of the child
            if (uriImage!=null) {
                filePath.putFile(uriImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getApplicationContext(),"upload image done", Toast.LENGTH_LONG).show();
                        imageView = (ImageView)findViewById(R.id.imageView3);
                        imageView.setImageURI(uriImage);

                    }
                });
            }
        }
    }


}