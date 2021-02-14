package com.example.palcarwasher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.Serializable;

public class SelectPaymentMethodActivity extends AppCompatActivity {


    RadioGroup rg;
    String selectedPaymentMethod=null;
    Button next;
    Orders order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_payment_method);
        order=(Orders) getIntent().getSerializableExtra("order");

        next=findViewById(R.id.next1);


        rg = (RadioGroup) findViewById(R.id.rg);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.paypal:

                        selectedPaymentMethod="PayPal";


                        break;
                    case R.id.cash:

                        selectedPaymentMethod="Cash";



                        break;

                }
            }
        });









        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedPaymentMethod.equals(null)){

                    Toast.makeText(SelectPaymentMethodActivity.this,"You Have To select Payment Method!",Toast.LENGTH_LONG).show();

                }

                else if (selectedPaymentMethod.equals("Cash")){
                    // go to final page
                    order.setPaymentType(selectedPaymentMethod);
                    Intent intent = new Intent(SelectPaymentMethodActivity.this,ActivityFinalOrderToDB.class);
                    intent.  putExtra("order",(Serializable) order);
                    startActivity(intent);

                }

                else if (selectedPaymentMethod.equals("PayPal")){
                    // go to paypal payment



                    order.setPaymentType(selectedPaymentMethod);
                    Intent intent = new Intent(SelectPaymentMethodActivity.this,ActivityPayPal.class);
                    intent.  putExtra("order",(Serializable) order);
                    startActivity(intent);



                }

            }
        });



    }
}