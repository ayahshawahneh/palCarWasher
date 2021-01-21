package com.example.palcarwasher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;
public class SendCodeVerificationCompany extends AppCompatActivity {
DatabaseReference databaseReference;
    private String verificationId;
    private FirebaseAuth mAuth;
    private EditText editText;
    private ProgressBar progressBar;
     String ProviderId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_code_verification_company);

        mAuth = FirebaseAuth.getInstance();
        mAuth.setLanguageCode("en");

        editText = findViewById(R.id.editTextCode);
        progressBar = findViewById(R.id.progressBar2);


        String phonenumber = getIntent().getStringExtra("phonenumber");

        sendVerficationCode(phonenumber);

        findViewById(R.id.buttonSignIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                String code = editText.getText().toString();
                //.trim();
                if(code.isEmpty()){
                    progressBar.setVisibility(View.INVISIBLE);
                    editText.setError(" Enter code ...");
                    editText.requestFocus();

                    return;
                }
                if(code.length()<6){
                    progressBar.setVisibility(View.INVISIBLE);
                    editText.setError("The code must be 6 digit");
                    editText.requestFocus();

                    return;
                }

                verifiyCode(code);

            }
        });
    }




    private void sendVerficationCode(String number){


        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(number)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this) // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        //  Toast.makeText(getApplicationContext(),"from send method",Toast.LENGTH_LONG).show();
        PhoneAuthProvider.verifyPhoneNumber(options);


    }


    public  PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallbacks = new PhoneAuthProvider.
            OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s ;


            Toast.makeText(getApplicationContext(),"on code sent",Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);

        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            Toast.makeText(getApplicationContext(),"completed",Toast.LENGTH_LONG).show();
            if(code !=null){
                editText.setText(code);
                verifiyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

            Toast.makeText(SendCodeVerificationCompany.this,"onVerificationFailed",Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
            return;
        }
    };











    private void verifiyCode(String codeByUser){
        //here we will pass the code that entered or recive otomaticlly
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId,codeByUser);
        signInWithPhoneAuthCredential(credential);

    }





    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential){
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithCredential(credential).addOnCompleteListener(SendCodeVerificationCompany.this,new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);

                    String PhoneNumber = getIntent().getStringExtra("phonenumber");
                    String ownerName = getIntent().getStringExtra("ownername");
                    String email = getIntent().getStringExtra("email");
                    String password = getIntent().getStringExtra("password");
                    String Birthdaydate = getIntent().getStringExtra("Birthdaydate");
                    String Gender = getIntent().getStringExtra("Gender");
                    String companyName = getIntent().getStringExtra("companyname");
                    String companyType = getIntent().getStringExtra("companyType");
                    String address = getIntent().getStringExtra("Address");
                    Intent intent = new Intent(getApplicationContext(),
                            LaundaryLicencePicture.class);


                    databaseReference = FirebaseDatabase.getInstance().getReference()
                            .child("PalCarWasher").child("ServiceProvider");



                    ProviderId = databaseReference.push().getKey();
                    //(String providerId, String companyName, String companyType, String logo, String name, String email, String password, String phoneNumber, String address, String gender, String bankAccount, String workingStatus)
                    ServiceProvider company = new ServiceProvider( ProviderId, companyName, companyType,null, ownerName, email, password, PhoneNumber,address,Gender,null,null,"0");
                    databaseReference.push().setValue(company);

                   // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("ProviderId", ProviderId);
                    startActivity(intent);

                }else {
                    Toast.makeText(SendCodeVerificationCompany.this,"incorrect code "
                            ,Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);

                }
            }
        });
    }






    public void clickBack(View view){

        Intent intent = new Intent(getApplicationContext(),
                RegisterAsCompany.class);
        startActivity(intent);

    }

}