package com.example.palcarwasher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SelectPaymentMethodActivity extends AppCompatActivity {

    RadioButton cash ;
    RadioButton paypal;
    String selectedPaymentMethod=null;
    Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_payment_method);


next=findViewById(R.id.next1);
        cash=findViewById(R.id.cash);
        paypal=findViewById(R.id.paypal);

        if(cash.isChecked())
            selectedPaymentMethod="Cash";
        else
            selectedPaymentMethod="PayPal";



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedPaymentMethod.equals(null)){

                    Toast.makeText(SelectPaymentMethodActivity.this,"You Have To select Payment Method!",Toast.LENGTH_LONG).show();

                }

                else if (selectedPaymentMethod.equals("Cash")){
                    // go to final page


                }

                else if (selectedPaymentMethod.equals("PayPal")){
                    // go to paypal payment


                }

            }
        });



    }
}