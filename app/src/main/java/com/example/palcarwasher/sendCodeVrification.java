package com.example.palcarwasher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class sendCodeVrification extends AppCompatActivity {
    DatabaseReference databaseReference;
    private String verificationId;
    private FirebaseAuth mAuth;
    private EditText editText;
    private ProgressBar progressBar;



   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_code_vrification);




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

            Toast.makeText(sendCodeVrification.this,"onVerificationFailed",Toast.LENGTH_LONG).show();
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
        mAuth.signInWithCredential(credential).addOnCompleteListener(sendCodeVrification.this,new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

try {
    databaseReference = FirebaseDatabase.getInstance().getReference()
            .child("PalCarWasher").child("Customer");

    final String phonenumber = getIntent().getStringExtra("phonenumber");
    String fullname = getIntent().getStringExtra("fullname");
    String email = getIntent().getStringExtra("email");
    String password = getIntent().getStringExtra("password");
    String Birthdaydate = getIntent().getStringExtra("Birthdaydate");
    String gender = getIntent().getStringExtra("gender");

    final String CustomerId = databaseReference.push().getKey();
  //  Toast.makeText(sendCodeVrification.this,fullname+CustomerId+"iam here"
           // ,Toast.LENGTH_LONG).show();
    Customer customer = new Customer(CustomerId, fullname, phonenumber, password, email, Birthdaydate, gender, null);
    databaseReference.push().setValue(customer);


    Intent intent = new Intent(sendCodeVrification.this, ActivityHome.class);
    intent.putExtra("customerId", CustomerId);
    // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(intent);

}catch (Exception exp){

    Toast.makeText(sendCodeVrification.this,exp.getMessage()
            ,Toast.LENGTH_LONG).show();

}


                        }









                           /*{ progressBar.setVisibility(View.GONE);

                            String phonenumber = getIntent().getStringExtra("phonenumber");
                            String fullname = getIntent().getStringExtra("fullname");
                            String email = getIntent().getStringExtra("email");
                            String password = getIntent().getStringExtra("password");
                            String Birthdaydate = getIntent().getStringExtra("Birthdaydate");
                            String gender = getIntent().getStringExtra("gender");

                            Intent intent = new Intent(getApplicationContext(),
                                   ActivityHome.class);
                            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.putExtra("phonenumber", phonenumber);
                            intent.putExtra("fullname", fullname);
                            intent.putExtra("email", email);
                            intent.putExtra("password", password);
                            intent.putExtra("Birthdaydate", Birthdaydate);
                            intent.putExtra("gender", gender);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        }*/
                        else {
                            Toast.makeText(sendCodeVrification.this,"incorrect code "
                                    ,Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);

                        }
                    }
                });
    }






    public void clickBack(View view) {
        Intent intent = new Intent(getApplicationContext(),
                register.class);
        startActivity(intent);
    }
}