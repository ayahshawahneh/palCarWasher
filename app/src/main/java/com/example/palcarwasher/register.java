package com.example.palcarwasher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import static com.example.palcarwasher.R.id.spinnerCountries;

public class register extends AppCompatActivity {

    private EditText PhoneNum;
    private EditText FullName;
    private EditText Email;
    private EditText Password;
    private EditText RePassword;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

   spinner = findViewById(R.id.spinnerCountries);

        spinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,
                CountryData.countryNames));

        PhoneNum = findViewById(R.id.phone_number);
        FullName = findViewById(R.id.et_name);
        Email = findViewById(R.id.et_email);
        Password = findViewById(R.id.et_password);
        RePassword = findViewById(R.id.et_repassword);


        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];
                String number = PhoneNum.getText().toString().trim();
                String fullName = FullName.getText().toString();
                String email = Email.getText().toString();
                String password = Password.getText().toString();
                String repassword = RePassword.getText().toString();

                if(fullName.isEmpty()){
                    FullName.setError("Please enter your Name");
                    FullName.requestFocus();
                    return;
                }
                if(email.isEmpty()){
                    Email.setError("Please enter your Email");
                    Email.requestFocus();
                    return;
                }
                if(password.isEmpty()){
                    Password.setError("Please enter your Password");
                    Password.requestFocus();
                    return;
                }
                if(repassword.isEmpty()){
                   RePassword.setError("Please enter your Password again");
                    RePassword.requestFocus();
                    return;
                }

                if ( number.isEmpty() || number.length()<9 ){
                    PhoneNum.setError("Valid Number is required");
                    PhoneNum.requestFocus();
                    return;
                }
                if(number.isEmpty() || password.isEmpty()||email.isEmpty()|| repassword.isEmpty()||fullName.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please fill all information",Toast.LENGTH_SHORT).show();
                    return;
                }
               if(repassword.equals(password)==false){
                    RePassword.setError("Please enter The same password");
                  RePassword.requestFocus();
                   return;

                }

                String PhoneNumber = "+" + code + number;

               Intent intent = new Intent(register.this, sendCodeVrification.class);
                intent.putExtra("phonenumber", PhoneNumber);
                intent.putExtra("fullname", fullName);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                startActivity(intent);
            }
        });


    }
    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() !=null){
            Intent intent = new Intent(this,insideOurProject.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
