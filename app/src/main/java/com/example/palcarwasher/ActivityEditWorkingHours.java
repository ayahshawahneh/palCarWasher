package com.example.palcarwasher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ActivityEditWorkingHours extends AppCompatActivity {


    DatabaseReference reference;

    DatabaseReference databaseReference,databaseReference2;
    EditText satOpen;
    EditText satClose;
    EditText satPartOpen;
    EditText satPartClose;


    EditText sunOpen;
    EditText sunClose;
    EditText sunPartOpen;
    EditText sunPartClose;


    EditText monOpen;
    EditText monClose;
    EditText monPartOpen;
    EditText monPartClose;



    EditText tusOpen;
    EditText tusClose;
    EditText tusPartOpen;
    EditText tusPartClose;


    EditText wedOpen;
    EditText wedClose;
    EditText wedPartOpen;
    EditText wedPartClose;


    EditText thuOpen;
    EditText thuClose;
    EditText thuPartOpen;
    EditText thuPartClose;


    EditText friOpen;
    EditText friClose;
    EditText friPartOpen;
    EditText friPartClose;


    WorkingTimeOnSaturdy sat;
    WorkingTimeOnSaturdy sun;
    WorkingTimeOnSaturdy mon;
    WorkingTimeOnSaturdy tus;
    WorkingTimeOnSaturdy wed;
    WorkingTimeOnSaturdy thu;
    WorkingTimeOnSaturdy fri;

    String providerId;

String compStatus;

RadioGroup rg;
RadioButton closed;
RadioButton opened;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_working_hours);
        providerId = getIntent().getStringExtra("providerId");
 ////////////////////////////////////

closed=findViewById(R.id.closed);
opened=findViewById(R.id.opened);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("PalCarWasher").child("ServiceProvider");


        reference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ServiceProvider serviceProvider = snapshot.getValue(ServiceProvider.class);

                        if (serviceProvider.getProviderId().equals(providerId)) {





                        if(serviceProvider.getWorkingStatus().equals("closed"))
                            closed.setChecked(true);

                     else
                           opened.setChecked(true);



                        }


                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







//////////////////////////radio button//////////////

        rg = (RadioGroup) findViewById(R.id.rg);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.opened:

                        compStatus = "opened";


                        break;
                    case R.id.closed:

                        compStatus= "closed";

                        break;


                }
            }
        });







//////////////////////////////fill boxes/////////////////////////

        satOpen = (EditText) findViewById(R.id.satOpen);
        satClose = (EditText) findViewById(R.id.satClose);
        satPartOpen = (EditText) findViewById(R.id.satPartOpen);
        satPartClose = (EditText) findViewById(R.id.satPartClose);
        viewData(satOpen, satClose, satPartOpen, satPartClose, "WorkingTimeOnSaturdy");


        sunOpen = (EditText) findViewById(R.id.sunOpen);
        sunClose = (EditText) findViewById(R.id.sunClose);
        sunPartOpen = (EditText) findViewById(R.id.sunPartOpen);
        sunPartClose = (EditText) findViewById(R.id.sunPartClose);
        viewData(sunOpen, sunClose, sunPartOpen, sunPartClose, "WorkingTimeOnSunday");

        monOpen = (EditText) findViewById(R.id.monOpen);
        monClose = (EditText) findViewById(R.id.monClose);
        monPartOpen = (EditText) findViewById(R.id.monPartOpen);
        monPartClose = (EditText) findViewById(R.id.monPartClose);
        viewData(monOpen, monClose, monPartOpen, monPartClose, "WorkingTimeOnMonday");

        tusOpen = (EditText) findViewById(R.id.tusOpen);
        tusClose = (EditText) findViewById(R.id.tusClose);
        tusPartOpen = (EditText) findViewById(R.id.tusPartOpen);
        tusPartClose = (EditText) findViewById(R.id.tusPartClose);
        viewData(tusOpen, tusClose, tusPartOpen, tusPartClose, "WorkingTimeOnTueseday");

        wedOpen = (EditText) findViewById(R.id.wedOpen);
        wedClose = (EditText) findViewById(R.id.wedClose);
        wedPartOpen = (EditText) findViewById(R.id.wedPartOpen);
        wedPartClose = (EditText) findViewById(R.id.wedPartClose);

        viewData(wedOpen, wedClose, wedPartOpen, wedPartClose, "WorkingTimeOnWednesday");

        thuOpen = (EditText) findViewById(R.id.thuOpen);
        thuClose = (EditText) findViewById(R.id.thuClose);
        thuPartOpen = (EditText) findViewById(R.id.thuPartOpen);
        thuPartClose = (EditText) findViewById(R.id.thuPartClose);
        viewData(thuOpen, thuClose, thuPartOpen, thuPartClose, "WorkingTimeOnThuresday");


        friOpen = (EditText) findViewById(R.id.friOpen);
        friClose = (EditText) findViewById(R.id.friClose);
        friPartOpen = (EditText) findViewById(R.id.friPartOpen);
        friPartClose = (EditText) findViewById(R.id.friPartClose);
        viewData(friOpen, friClose, friPartOpen, friPartClose, "WorkingTimeOnFriday");


/////////////////time pikker/////////////////////////


/////////////////////////////////////////////////////////////////////////////////////////////////////


    }


    void viewData(final TextView open, final TextView close, final TextView openPart, final TextView closePart, String child) {


        reference = FirebaseDatabase.getInstance().getReference().child("PalCarWasher")
                .child(child);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    WorkingTimeOnSaturdy day = dataSnapshot.getValue(WorkingTimeOnSaturdy.class);
                    if (day.getPrviderId().equals(providerId)) {
                        open.setText(day.getFrom());
                        Log.v("DataOB",day.getFrom());
                        close.setText(day.getTo());
                        openPart.setText(day.getPartTimeFrom());
                        closePart.setText(day.getPartTimeTo());

                        open.setTextColor(Color.BLACK);
                        close.setTextColor(Color.BLACK);
                        openPart.setTextColor(Color.BLACK);
                        closePart.setTextColor(Color.BLACK);
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    public void showHourPicker(View v) {
        // EditText e=(EditText)v.getId();
        // View vi = getLayoutInflater().inflate(R.layout.activity_working_hours_per_day, null);
        final EditText editText = (EditText) v;

        final Calendar myCalender = Calendar.getInstance();
        int hour = myCalender.get(Calendar.HOUR_OF_DAY);
        int minute = myCalender.get(Calendar.MINUTE);

        int TIME_PICKER_INTERVAL = 30;


        CustomTimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {


            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                int hour = hourOfDay;
                int minutes = minute;
                String timeSet = "";
                if (hour > 12) {
                    hour -= 12;
                    timeSet = "PM";
                } else if (hour == 0) {
                    hour += 12;
                    timeSet = "AM";
                } else if (hour == 12) {
                    timeSet = "PM";
                } else {
                    timeSet = "AM";
                }

                String min = "";

                if (minutes < 10)
                    min = "0" + minutes;
                else
                    min = String.valueOf(minutes);


                // Append in a StringBuilder
                String aTime = new StringBuilder().append(hour).append(':')
                        .append(min).append(" ").append(timeSet).toString();

                editText.setText(aTime);

            }
        };
        TimePickerDialog timePickerDialog = new CustomTimePickerDialog(ActivityEditWorkingHours.this, myTimeListener, hour, minute, false);
        timePickerDialog.setTitle("Select Time:");

        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();
    }



////////////////////////////////////////////////////////////

    boolean  fieldValidation(String day,EditText open, EditText close, EditText openpart, EditText closepart){



        String pattern = "hh:mm aa";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date1;
        Date date2;
        Date date11;
        Date date22;
        long difference ;


            if((!open.getText().toString().equals("")&&close.getText().toString().equals(""))){

                Toast.makeText(this, "Please fill closing time!", Toast.LENGTH_SHORT).show();
                return false;
            }

        if((open.getText().toString().equals("")&&!close.getText().toString().equals(""))){

            Toast.makeText(this, "Please fill opening time!", Toast.LENGTH_SHORT).show();
            return false;
        }



        if((!openpart.getText().toString().equals("")&&closepart.getText().toString().equals(""))){


            Toast.makeText(this, "Please fill closing part time!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if((openpart.getText().toString().equals("")&&!closepart.getText().toString().equals(""))){


            Toast.makeText(this, "Please fill opening part time!", Toast.LENGTH_SHORT).show();
            return false;
        }



if(!open.getText().toString().equals("")&&!close.getText().toString().equals("")) {
    try {
        date1 = sdf.parse(open.getText().toString());
        date2 = sdf.parse(close.getText().toString());

        if (!date1.before(date2)) {
            Toast.makeText(this, " Opening time can't be in or after closing", Toast.LENGTH_LONG).show();

            return false;
        }


        difference = (date2.getTime() - date1.getTime());
        double hours = difference / (1000 * 60 * 60);
        // long hours = difference % (24 * 3600) / 3600; // Calculating Hours
        if (hours < 1) {

            Toast.makeText(this, " The opening time must be more than one hour", Toast.LENGTH_LONG).show();

            return false;
        }


    } catch (ParseException e) {
        e.printStackTrace();
    }


}

        if(!openpart.getText().toString().equals("")&&!closepart.getText().toString().equals("")) {
            try {

                date1 = sdf.parse(open.getText().toString());
                date2 = sdf.parse(close.getText().toString());
                date11 = sdf.parse(openpart.getText().toString());
                date22 = sdf.parse(closepart.getText().toString());
                if (!date11.before(date22)) {
                    Toast.makeText(this, " Part time start can't be in or after the end part time", Toast.LENGTH_LONG).show();

                    return false;
                }


                if (!date11.after(date2)) {
                    Toast.makeText(this, " Can't start part time at or before the main close time!", Toast.LENGTH_LONG).show();

                    return false;
                }


                difference = (date22.getTime() - date11.getTime()) / 1000;
                long hours = difference % (24 * 3600) / 3600; // Calculating Hours
                if (hours < 1) {

                    Toast.makeText(this, " The opening part time must be more than one hour", Toast.LENGTH_LONG).show();

                    return false;
                }


            } catch (ParseException e) {
                e.printStackTrace();
            }

        }



        if(day.equals("sat")){

            sat = new WorkingTimeOnSaturdy(providerId,open.getText().toString(), close.getText().toString(), openpart.getText().toString(),closepart.getText().toString());

        }

        else if(day.equals("sun")){

            sun = new WorkingTimeOnSaturdy(providerId,open.getText().toString(), close.getText().toString(), openpart.getText().toString(),closepart.getText().toString());

        }


        else if(day.equals("mon")){

            mon = new WorkingTimeOnSaturdy(providerId,open.getText().toString(), close.getText().toString(), openpart.getText().toString(),closepart.getText().toString());

        }

        else if(day.equals("tus")){

            tus = new WorkingTimeOnSaturdy(providerId,open.getText().toString(), close.getText().toString(), openpart.getText().toString(),closepart.getText().toString());

        }



        else if(day.equals("wed")){

            wed = new WorkingTimeOnSaturdy(providerId,open.getText().toString(), close.getText().toString(), openpart.getText().toString(),closepart.getText().toString());

        }


        else if(day.equals("thu")){

            thu= new WorkingTimeOnSaturdy(providerId,open.getText().toString(), close.getText().toString(), openpart.getText().toString(),closepart.getText().toString());

        }


        else if(day.equals("fri")){

            fri = new WorkingTimeOnSaturdy(providerId,open.getText().toString(), close.getText().toString(), openpart.getText().toString(),closepart.getText().toString());

        }




        return true;

    }











    public void ocClickAndSubmit(View view) {












        boolean d1,d2,d3,d4,d5,d6,d7;





        d1=fieldValidation("sat",satOpen,satClose,satPartOpen,satPartClose);
        d2=fieldValidation("sun",sunOpen,sunClose,sunPartOpen,sunPartClose);
        d3=fieldValidation("mon",monOpen,monClose,monPartOpen,monPartClose);
        d4=fieldValidation("tus",tusOpen,tusClose,tusPartOpen,tusPartClose);
        d5=fieldValidation("wed",wedOpen,wedClose,wedPartOpen,wedPartClose);
        d6=fieldValidation("thu",thuOpen,thuClose,thuPartOpen,thuPartClose);
        d7=fieldValidation("fri",friOpen,friClose,friPartOpen,friPartClose);

        if(d1&d2&d3&d4&d5&d6&d7){

/////////////////



            final DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference().child("PalCarWasher").child("ServiceProvider");
            final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("PalCarWasher").child("ServiceProvider");


            reference.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            ServiceProvider serviceProvider = snapshot.getValue(ServiceProvider.class);

                            if (serviceProvider.getProviderId().equals(providerId)) {

                                serviceProvider.setWorkingStatus(compStatus);

                                reference2.push().setValue(serviceProvider); //new child !!!!!
                                reference.child(snapshot.getKey()).removeValue();

                            }


                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });



            ////////////////

            toDB("WorkingTimeOnSaturdy",sat);

            toDB("WorkingTimeOnSunday",sun);

            toDB("WorkingTimeOnMonday",mon);

            toDB("WorkingTimeOnTueseday",tus);

            toDB("WorkingTimeOnWednesday",wed);

            toDB("WorkingTimeOnThuresday",thu);

            toDB("WorkingTimeOnFriday",fri);




        }









    }





    void toDB(String child , final WorkingTimeOnSaturdy day2){


        DatabaseReference  databaseReference4 = FirebaseDatabase.getInstance().getReference().child("PalCarWasher").child(child);


        final DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("PalCarWasher")
                .child(child);
        reference.addListenerForSingleValueEvent(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        WorkingTimeOnSaturdy day =snapshot.getValue(WorkingTimeOnSaturdy.class);




                  if(day.getPrviderId().equals(providerId)){
                       day.setFrom(day2.getFrom());
                       day.setTo(day2.getTo());
                       day.setPartTimeFrom(day2.getPartTimeFrom());
                       day.setPartTimeTo(day2.getPartTimeTo());
                        databaseReference4.push().setValue(day);

                        //new child !!!!!
                       reference.child(snapshot.getKey()).removeValue();
                        Toast.makeText(ActivityEditWorkingHours.this,"Updated Successfully! ",Toast.LENGTH_SHORT).show();
                        // Toast.makeText(getApplicationContext(),snapshot.getKey(),Toast.LENGTH_LONG).show();
                        // Toast.makeText(getApplicationContext(),"Done ",Toast.LENGTH_LONG).show();


                    }}

                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






        Toast.makeText(this, "Updated Successfully! ", Toast.LENGTH_SHORT).show();

    }









} ///






