package com.example.palcarwasher;

 import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;

        import android.app.Activity;
        import android.content.Intent;
        import android.net.Uri;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.Toast;

        import com.paypal.android.sdk.payments.PayPalAuthorization;
        import com.paypal.android.sdk.payments.PayPalConfiguration;
        import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
        import com.paypal.android.sdk.payments.PayPalPayment;
        import com.paypal.android.sdk.payments.PayPalService;
        import com.paypal.android.sdk.payments.PaymentActivity;
        import com.paypal.android.sdk.payments.PaymentConfirmation;

        import org.json.JSONException;
        import org.json.JSONObject;

 import java.io.Serializable;
 import java.math.BigDecimal;

public class ActivityPayPal extends AppCompatActivity  {
    //  private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_NO_NETWORK;
    private static final String CONFIG_CLIENT_ID = "AS8JzfB3ZhS0fR3Y2FG66dOq3sbOx91v9VrU0asCzXObJaXBCeI42U34e1-znNoj5BsXZha_FxjjETlS";
    //private static final String CONFIG_RECEIVER_EMAIL = "test-facilitator@officialgates.com";
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;

    private static final int REQUEST_CODE_PAYMENT = 1;
    private static final String TAG = "paymentExample";
    Button orderButton ;
    Orders order;
    String paymentId;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID)
            .merchantName("Store");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_pal);

        order=(Orders) getIntent().getSerializableExtra("order");


        orderButton = findViewById(R.id.order);
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBuyPressed(v);

                order.setVisaId(paymentId);
                Intent intent = new Intent(ActivityPayPal.this,ActivityFinalOrderToDB.class);
                intent.putExtra("order",(Serializable) order);
                startActivity(intent);


            }
        });

    }


    public void onBuyPressed(View pressed) {
        PayPalPayment thingToBuy = getThingToBuy(PayPalPayment.PAYMENT_INTENT_SALE);


        Intent intent = new Intent(ActivityPayPal.this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
        startActivityForResult(intent, REQUEST_CODE_PAYMENT);
    }

    private PayPalPayment getThingToBuy(String paymentIntent) {
        return new PayPalPayment(new BigDecimal(order.getTotalPrice()), "USD", "my product", paymentIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm =
                        data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        Log.i(TAG, confirm.toJSONObject().toString(4));
                        Log.i(TAG, confirm.getPayment().toJSONObject().toString(4));


                        JSONObject jsonObj = new JSONObject(confirm.toJSONObject().toString());

                       paymentId = jsonObj.getJSONObject("response").getString("id");
                        System.out.println("payment id:-==" + paymentId);


                        Toast.makeText(
                                getApplicationContext(),
                                "payment id" + paymentId, Toast.LENGTH_LONG)
                                .show();


                        Toast.makeText(
                                getApplicationContext(),
                                "PaymentConfirmation info received from PayPal", Toast.LENGTH_LONG)
                                .show();

                    } catch (JSONException e) {
                        Log.e(TAG, "an extremely unlikely failure occurred: ", e);
                        Toast.makeText(getApplicationContext(),"an extremely unlikely failure occurred: ",Toast.LENGTH_LONG).show();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i(TAG, "The user canceled.");
                Toast.makeText(getApplicationContext(),"The user canceled. ",Toast.LENGTH_LONG).show();

            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i(TAG, "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
                Toast.makeText(getApplicationContext(),"An invalid Payment or PayPalConfiguration was submitted. Please see the docs. ",Toast.LENGTH_LONG).show();
            }
        }

    }
    @Override
    public void onDestroy() {
        // Stop service when done
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }
}