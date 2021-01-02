package com.example.palcarwasher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BankAccountInformation extends AppCompatActivity {
    DatabaseReference databaseReference;
    String ProviderId;



    EditText FirstName;
    EditText SecondName;
    EditText ThirdName;
    EditText FourthName;
    EditText AccountNumber;
    EditText IdNumber;
    EditText Error;
    Spinner SpinnerBankName;


    String firstName;
    String secondName;
    String thirdName;
    String fourthName;
    String accountNumber;
    String idNumber;
    String bankName;


Button verify;
    boolean flag=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_account_information);


        ProviderId=getIntent().getStringExtra("ProviderId");

         FirstName=findViewById(R.id.first_name);
        SecondName=findViewById(R.id.second_name);
        ThirdName=findViewById(R.id.third_name);
        FourthName=findViewById(R.id.fourth_name);
        AccountNumber=findViewById(R.id.account_number);
        IdNumber=findViewById(R.id.id_number);
        SpinnerBankName=findViewById(R.id.spinnerBankName);


        SpinnerBankName.setAdapter(new ArrayAdapter<String>(this,android.R
                .layout.simple_spinner_dropdown_item,
                BankNamesSpinnerData.bankNames));

        Error=findViewById(R.id.error);


verify=findViewById(R.id.btn_verify);
verify.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        flag=true;
        firstName=FirstName.getText().toString();
        secondName =SecondName.getText().toString();
        thirdName=ThirdName.getText().toString();
        fourthName=FourthName.getText().toString();
        accountNumber=AccountNumber.getText().toString();
        idNumber=IdNumber.getText().toString();
        bankName =SpinnerBankName.getSelectedItem().toString();
        Error.setVisibility(View.GONE);


        String []array = new String[]{firstName,secondName,thirdName,fourthName,accountNumber,idNumber,bankName};
        EditText []editArray =new EditText[]{FirstName,SecondName,ThirdName,FourthName,AccountNumber,IdNumber,Error};
        for (int i=0;i<array.length;i++){

            if(array[i].equals(bankName)&&SpinnerBankName.getSelectedItem().equals("Bank Name")){

                    flag=false;
                editArray[i].setVisibility(View.VISIBLE);
                editArray[i].setError("You have to to choose bank name!");
                editArray[i].requestFocus();


            }


            else if(array[i].isEmpty()) {
                flag=false;
                editArray[i].setError("You have to fill it!");
                editArray[i].requestFocus();

            }



        }
        if(!flag) return;
        else
            sendDataAndNext();
    }
});





    }




    public void sendDataAndNext(){

      //  databaseReference = FirebaseDatabase.getInstance().getReference()
             //   .child("PalCarWasher").child("BankAccounts");

      Intent intent = new Intent(getApplicationContext(),MainActivity.class);

       // String locationId = databaseReference.push().getKey();
       // BankAccounts pa=new BankAccounts();
      //  databaseReference.push().setValue(pa);


        intent.putExtra("ProviderId", ProviderId);
        startActivity(intent);



    }
}