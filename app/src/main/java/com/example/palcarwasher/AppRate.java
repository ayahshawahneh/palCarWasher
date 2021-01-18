package com.example.palcarwasher;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


public class AppRate extends AppCompatActivity {


    ImageView back ;
    Button RatingButton;
    TextView RatingText;
    RatingBar ratingBar1;
    RatingBar ratingBar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_rate);
        RatingButton = (Button)findViewById(R.id.ratingbutton);
        RatingText = (TextView)findViewById(R.id.ratingtext);
        ratingBar1 = (RatingBar)findViewById(R.id.ratingBar3);
        // ratingBar2 = (RatingBar)findViewById(R.id.rateeeeeee);


        RatingButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                ////to retreive evaluation from database :
               /*  float rate = Float.parseFloat("evaluation");//evaluation that come from firebase
                ratingBar2.setRating(rate);
                //////////////////  */

                //when new customer rate :
                /* rate = Float.parseFloat("evaluation");//evaluation that come from firebase
                 rate += ratingBar1.getRating();
                 float average = rate / 2;
                ////////then we will send the new evaluation(average) to firebase*/


                RatingText.setText(" Your Rating is "+ratingBar1.getRating());

                if(ratingBar1.getRating() > 3.5){
                    Toast.makeText(getApplicationContext(), "     ^ _ ^\n\nThank You", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(), "- _ -\n\nOky !", Toast.LENGTH_SHORT).show();

                RatingButton.setEnabled(false);
                ratingBar1.setEnabled(false);



            }
        });
    }
}