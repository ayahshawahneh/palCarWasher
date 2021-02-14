package com.example.palcarwasher;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SuggestNewService extends AppCompatActivity {

    EditText SuggestNewVehicleType;
    Button addButton;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest_new_service);

        SuggestNewVehicleType = findViewById(R.id.SuggestNewVehicleType);
        addButton = findViewById(R.id.addB);

        databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("PalCarWasher").child("Services");


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"onclick",Toast.LENGTH_LONG).show();

                if(SuggestNewVehicleType.getText().toString().isEmpty()) {
                    SuggestNewVehicleType.setError("fill this field !");
                    SuggestNewVehicleType.requestFocus();
                    return;
                }
                else {

                    String serviceId = databaseReference.push().getKey();
                    Services services = new Services(serviceId, SuggestNewVehicleType.getText().toString());
                    databaseReference.push().setValue(services);
                    Toast.makeText(getApplicationContext(),"Added successfully",Toast.LENGTH_LONG).show();
                    SuggestNewVehicleType.setText("");

                }

            }
        });
    }
}