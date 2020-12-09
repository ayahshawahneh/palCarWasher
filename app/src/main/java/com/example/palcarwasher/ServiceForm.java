package com.example.palcarwasher;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ServiceForm extends AppCompatActivity {
FloatingActionButton button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_form);
/*button.findViewById(R.id.floatingActionButton)

        button.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater layoutInflaterAndroid = LayoutInflater.from(this);
            View view2 = layoutInflaterAndroid.inflate(R.layout.alert_dialog_add_new_service_or_vehicle, null);
            builder.setView(view2);
            builder.setCancelable(false);
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();

          view2.findViewById(R.id.addButton).setOnClickListener(v1 -> onBackPressed());
            view2.findViewById(R.id.cancelButton).setOnClickListener(v12 -> alertDialog.dismiss());
        });

*/


    }






}