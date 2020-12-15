package com.example.palcarwasher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.regex.Pattern;

import static com.example.palcarwasher.R.*;

public class register extends AppCompatActivity {

    private EditText PhoneNum;
    private EditText FullName;
    private EditText Email;
    private EditText Password;

    private Spinner spinner;
    private TextView BirthdayDate;
    private RadioButton male;
    private RadioButton female;
    private String gender;
    private ProgressBar progressBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_register);

       spinner = findViewById(R.id.spinnerCountries);
        spinner.setAdapter(new ArrayAdapter<String>(this,android.R
                .layout.simple_spinner_dropdown_item,
                CountryData.countryNames));


        ImageView ClickhereForLogin =(ImageView)findViewById(id.imageView8);
        ClickhereForLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),login.class);
                startActivity(intent);
            }
        });





        progressBar = findViewById(id.progressBar);
        PhoneNum = findViewById(R.id.phone_number);
        FullName = findViewById(R.id.et_name);
        Email = findViewById(R.id.et_email);
        Password = findViewById(R.id.et_password);
        BirthdayDate = findViewById(R.id.birthday_date);
        male = (RadioButton)findViewById(R.id.male);
        female = (RadioButton)findViewById(R.id.female);

        setDatePicker(BirthdayDate);

        findViewById(id.btn_verfiy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];
                String number = PhoneNum.getText().toString().trim();
                 String fullName = FullName.getText().toString();
                 String email = Email.getText().toString();
                  String password = Password.getText().toString();

                String Birthdaydate = BirthdayDate.getText().toString();


                if(male.isChecked()){
                    gender="male";
                }else {
                    if(female.isChecked())
                        gender="female";

                }





                boolean flag=true;
                String []array = new String[]{fullName,password,number};
                EditText []editArray =new EditText[]{FullName,Password,PhoneNum};
                for (int i=0;i<array.length;i++){

                   if(array[i].isEmpty()) {
                       flag=false;
                       progressBar.setVisibility(View.GONE);
                       editArray[i].setError("You have to fill it!");
                       editArray[i].requestFocus();

                   }
                }
                   if(!flag) return;



                //////////////////////////////////////////////////////////////////////
                if((!EMAIL_ADDRESS_PATTERN.matcher(email).matches())&&(!email.isEmpty()) ){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(register.this,"Invalid Email Address",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(password.length()<6){
                    progressBar.setVisibility(View.GONE);
                    Password.setError("Password must be more than 6 characters");
                    Password.requestFocus();
                    return;
                }
                if(password.length()>12){
                    progressBar.setVisibility(View.GONE);
                    Password.setError("Password must be less than 12 characters");
                    Password.requestFocus();
                    return;
                }


                    String[] numberArray= number.split("");
                      if(numberArray[0].equals("0"))
                       number = number.substring(1);



                if (number.length() != 9){
                    progressBar.setVisibility(View.GONE);
                    PhoneNum.setError("number must be 9 digit");
                    PhoneNum.requestFocus();
                    return;





                }





                String PhoneNumber ="+" + code + number;

                isValidPhoneNumberClassCustomer( PhoneNumber);



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
    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9+._%-+]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
                    "(" +
                    "." +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
                    ")+"
    );


    private void setDatePicker(final TextView textView) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment newFragment = new DatePickerFragment();

                Bundle b = new Bundle();
                b.putInt("customStyle", R.style.CustomDatePickerDialog);
                newFragment.setArguments(b);

                newFragment.setDatePickerListener(new DatePickerFragment.DatePickerListener() {
                    @Override
                    public void onDatePicked(String date) {
                        textView.setText(date);
                    }
                });


                newFragment.show(getSupportFragmentManager(),"dataPicker");
            }
        });
    }




    public void isValidPhoneNumberClassCustomer(String PhoneNumber){


        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("PalCarWasher")
                .child("Customer");
        Query query=reference.orderByChild("phoneNumber").equalTo(PhoneNumber);// or in general i can put the edittext insted of "sham"
        query.addListenerForSingleValueEvent(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    Toast.makeText(getApplicationContext(), "It's a used phone number use another one or you can Login", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);



                }

                else
                    isValidPhoneNumberClassProvider( );

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







    }






    public void isValidPhoneNumberClassProvider(){
        String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];
        PhoneNum = findViewById(R.id.phone_number);
        String number = PhoneNum.getText().toString().trim();
        String[] numberArray= number.split("");
        if(numberArray[0].equals("0"))
            number = number.substring(1);
        String PhoneNumber ="+" + code + number;


        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("PalCarWasher")
                .child("ServiceProvider");
        Query query=reference.orderByChild("phoneNumber").equalTo(PhoneNumber);// or in general i can put the edittext insted of "sham"
        query.addListenerForSingleValueEvent(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    Toast.makeText(getApplicationContext(), "It's a used phone number use another one or you can Login", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);



                }

                else
                    sendData();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }









    void sendData() {

        PhoneNum = findViewById(R.id.phone_number);
        FullName = findViewById(R.id.et_name);
        Email = findViewById(R.id.et_email);
        Password = findViewById(R.id.et_password);
        BirthdayDate = findViewById(R.id.birthday_date);
        spinner = findViewById(R.id.spinnerCountries);
        spinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,
                CountryData.countryNames));


        String number = PhoneNum.getText().toString().trim();
        String fullName = FullName.getText().toString();
        String email = Email.getText().toString();
        String password = Password.getText().toString();
        String Birthdaydate = BirthdayDate.getText().toString();

        String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];
        String PhoneNumber ="+" + code + number;


        Intent intent = new Intent(register.this, sendCodeVrification.class);
        intent.putExtra("phonenumber", PhoneNumber);
        intent.putExtra("fullname", fullName);
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        intent.putExtra("Birthdaydate", Birthdaydate);
        intent.putExtra("gender", gender);
        startActivity(intent);

    }









}
