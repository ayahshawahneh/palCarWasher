package com.example.palcarwasher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

public class ActivityTester extends AppCompatActivity {
Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tester);


        b=findViewById(R.id.select);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              AlertDialogTimSlot cdd=new AlertDialogTimSlot(ActivityTester.this,b);
                cdd.show();
                Window window = cdd.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            }
        });


    }
}