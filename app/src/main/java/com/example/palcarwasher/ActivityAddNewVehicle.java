package com.example.palcarwasher;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ActivityAddNewVehicle extends AppCompatActivity {
    Button addButton;
    EditText newVehicleType;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_vehicle);

        addButton = findViewById(R.id.addButton);
        newVehicleType = findViewById(R.id.newVehicleType);

        databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("PalCarWasher").child("VehicleType");


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value= newVehicleType.getText().toString();

                if(value.isEmpty()){
                    newVehicleType.setError("fill this field !");
                    newVehicleType.requestFocus();
                    return;
                }else {

                    String vehicleId = databaseReference.push().getKey();
                    Toast.makeText(getApplicationContext(),"Added successfully",Toast.LENGTH_LONG).show();
                    VehicleType vehicleType = new VehicleType(vehicleId, newVehicleType.getText().toString());
                    databaseReference.push().setValue(vehicleType);

                    newVehicleType.setText("");
                }

            }
        });
    }
}