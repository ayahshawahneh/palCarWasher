package com.example.palcarwasher;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

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
        spinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,
                CountryData.countryNames));


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

                if(fullName.isEmpty()){
                    progressBar.setVisibility(View.GONE);
                    FullName.setError("Please enter your Name");
                    FullName.requestFocus();
                    return;
                }
                if(email.isEmpty()){
                    progressBar.setVisibility(View.GONE);
                    Email.setError("Please enter your Email");
                    Email.requestFocus();
                    return;
                }
                if(!EMAIL_ADDRESS_PATTERN.matcher(email).matches()){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(register.this,"Invalid Email Address",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.isEmpty()){
                    progressBar.setVisibility(View.GONE);
                    Password.setError("Please enter your Password");
                    Password.requestFocus();
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



                if ( number.isEmpty()){
                    progressBar.setVisibility(View.GONE);
                    PhoneNum.setError("Please Enter Your Phone Number");
                    PhoneNum.requestFocus();
                    return;
                }
                if (number.length() != 9){
                    progressBar.setVisibility(View.GONE);
                    PhoneNum.setError("number must be 9 digit");
                    PhoneNum.requestFocus();
                    return;

                }

                if(number.isEmpty() || password.isEmpty() ||email.isEmpty() ||fullName.isEmpty()){
                    Toast.makeText(getApplicationContext()," Please fill all information",Toast.LENGTH_SHORT).show();
                    return;
                }




                progressBar.setVisibility(View.VISIBLE);
                String PhoneNumber = "+" + code + number;
               Intent intent = new Intent(register.this, sendCodeVrification.class);
                intent.putExtra("phonenumber", PhoneNumber);
                intent.putExtra("fullname", fullName);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                intent.putExtra("Birthdaydate",Birthdaydate);
                intent.putExtra("gender",gender);
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

}
