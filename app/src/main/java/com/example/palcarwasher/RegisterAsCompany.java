package com.example.palcarwasher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class RegisterAsCompany extends AppCompatActivity {


    private EditText CompanyName;
    private EditText OwnerName;
    private String companyType;
    private RadioButton Mobile;
    private RadioButton Stationary;
    private RadioButton Both;
    private EditText Email;
    private EditText Password;
    private EditText Address;
    private EditText PhoneNum;
    private Spinner spinner;
    private TextView BirthdayDate;
    private RadioButton Male;
    private RadioButton Female;
    private String Gender;
    private ProgressBar progressBar;


    boolean flag=true;
    boolean typeFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_as_company);

        TextView text =(TextView) findViewById(R.id.termms);
        text.setMovementMethod(LinkMovementMethod.getInstance());



        spinner = findViewById(R.id.spinnerCountries);
        spinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,
                CountryData.countryNames));


        ImageView ClickhereForLogin =(ImageView)findViewById(R.id.imageView8);
        ClickhereForLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),login.class);
                startActivity(intent);
            }
        });




        progressBar = findViewById(R.id.progressBar);
        PhoneNum = findViewById(R.id.phone_number);
        OwnerName = findViewById(R.id.et_name);
        CompanyName = findViewById(R.id.company_name);
        Email = findViewById(R.id.et_email);
        Address=findViewById(R.id.address);
        Mobile = (RadioButton)findViewById(R.id.mobile);
        Stationary = (RadioButton)findViewById(R.id.stationary);
        Male = (RadioButton)findViewById(R.id.male);
        Both = (RadioButton)findViewById(R.id.both);
        Password = findViewById(R.id.et_password);
        BirthdayDate = findViewById(R.id.birthday_date);
        Male = (RadioButton)findViewById(R.id.male);
        Female = (RadioButton)findViewById(R.id.female);



        setDatePicker(BirthdayDate);


        findViewById(R.id.btn_verfiy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);

                flag=true;
                typeFlag = true;

                String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];
                String number = PhoneNum.getText().toString().trim();
                String ownerName = OwnerName.getText().toString();
                String email = Email.getText().toString();
                String password = Password.getText().toString();
                String companyName = CompanyName.getText().toString();
                String Birthdaydate = BirthdayDate.getText().toString();
                String address = Address.getText().toString();


                if(Mobile.isChecked()){
                    companyType="mobile";
                    typeFlag=false;
                }


                else if(Stationary.isChecked())
                    companyType="stationary";

                    else {
                    companyType="both";
                }








                if(Male.isChecked()){
                    Gender="male";
                }else {
                    if(Female.isChecked())
                       Gender="female";

                }






                String []array = new String[]{companyName,ownerName,password,number};
                EditText []editArray =new EditText[]{CompanyName,OwnerName,Password,PhoneNum};
                for (int i=0;i<array.length;i++){

                    if(array[i].isEmpty()) {
                        flag=false;
                        progressBar.setVisibility(View.GONE);
                        editArray[i].setError("You have to fill it!");
                        editArray[i].requestFocus();

                    }
                    if(companyType.isEmpty()) {
                        flag=false;
                        progressBar.setVisibility(View.GONE);
                        Both.setError("You have to fill it!");
                        editArray[i].requestFocus();

                    }


                    if(typeFlag && address.isEmpty()){
                        flag=false;
                        progressBar.setVisibility(View.GONE);
                        Address.setError("You have to fill it!");
                        editArray[i].requestFocus();

                    }


                }
                if(!flag) return;



                //////////////////////////////////////////////////////////////////////
                if((!EMAIL_ADDRESS_PATTERN.matcher(email).matches())&&(!email.isEmpty()) ){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(RegisterAsCompany.this,"Invalid Email Address",Toast.LENGTH_LONG).show();
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

                isValidPhoneNumberClassProvider( PhoneNumber);


            }
        });






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




    public void isValidPhoneNumberClassProvider(String PhoneNumber){


        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("PalCarWasher").child("ServiceProvider");
        Query query=reference.orderByChild("phoneNumber").equalTo(PhoneNumber);// or in general i can put the edittext insted of "sham"
        query.addListenerForSingleValueEvent(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    Toast.makeText(getApplicationContext(), "It's a used phone number use another one or you can Login", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);



                }

                else
                    isValidPhoneNumberClassCustomer();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }



    public void isValidPhoneNumberClassCustomer(){
        String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];
        PhoneNum = findViewById(R.id.phone_number);
        String number = PhoneNum.getText().toString().trim();
        String[] numberArray= number.split("");
        if(numberArray[0].equals("0"))
            number = number.substring(1);
        String PhoneNumber ="+" + code + number;

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
                   sendData();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







    }







    void sendData() {


        PhoneNum = findViewById(R.id.phone_number);
        OwnerName = findViewById(R.id.et_name);
        CompanyName = findViewById(R.id.company_name);
        Email = findViewById(R.id.et_email);
        Password = findViewById(R.id.et_password);
        BirthdayDate = findViewById(R.id.birthday_date);
        Address=findViewById(R.id.address);


        spinner = findViewById(R.id.spinnerCountries);
        spinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,
                CountryData.countryNames));



        String number = PhoneNum.getText().toString().trim();
        String ownerName = OwnerName.getText().toString();
        String email = Email.getText().toString();
        String password = Password.getText().toString();
        String companyName = CompanyName.getText().toString();
        String Birthdaydate = BirthdayDate.getText().toString();
        String address = Address.getText().toString();

        String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];
        String PhoneNumber ="+" + code + number;


        Intent intent = new Intent(RegisterAsCompany.this, SendCodeVerificationCompany.class);
        intent.putExtra("phonenumber", PhoneNumber);
        intent.putExtra("ownername", ownerName);
        intent.putExtra("companyname", companyName);
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        intent.putExtra("Birthdaydate", Birthdaydate);
        intent.putExtra("companyType", companyType);
        intent.putExtra("Gender",Gender);
        intent.putExtra("Address", address);
        startActivity(intent);

    }













}