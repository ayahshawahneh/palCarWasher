package com.example.palcarwasher;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class WorkingHoursPerDay extends AppCompatActivity {
    CheckBox satBox;
    TableLayout satTable;
    TableRow satPartTimeRow;
    TextView satAddPartTimeText;
    ImageView closePartSat;




    CheckBox sunBox;
    TableLayout sunTable;
    TableRow sunPartTimeRow;
    TextView sunAddPartTimeText;
    ImageView closePartSun;


    CheckBox monBox;
    TableLayout monTable;
    TableRow monPartTimeRow;
    TextView monAddPartTimeText;
    ImageView closePartMon;



    CheckBox tusBox;
    TableLayout tusTable;
    TableRow tusPartTimeRow;
    TextView tusAddPartTimeText;
    ImageView closePartTus;




    CheckBox wedBox;
    TableLayout wedTable;
    TableRow wedPartTimeRow;
    TextView wedAddPartTimeText;
    ImageView closePartWed;



    CheckBox thuBox;
    TableLayout thuTable;
    TableRow thuPartTimeRow;
    TextView thuAddPartTimeText;
    ImageView closePartThu;




    CheckBox friBox;
    TableLayout friTable;
    TableRow friPartTimeRow;
    TextView friAddPartTimeText;
    ImageView closePartFri;




    boolean satFlag=false;
    boolean satPartFlag=false;
    EditText satOpen;
    EditText satClose;
    EditText satPartOpen;
    EditText satPartClose;





    boolean sunFlag=false;
    boolean sunPartFlag=false;
    EditText sunOpen;
    EditText sunClose;
    EditText sunPartOpen;
    EditText sunPartClose;



    boolean monFlag=false;
    boolean monPartFlag=false;
    EditText monOpen;
    EditText monClose;
    EditText monPartOpen;
    EditText monPartClose;


    boolean tusFlag=false;
    boolean tusPartFlag=false;
    EditText tusOpen;
    EditText tusClose;
    EditText tusPartOpen;
    EditText tusPartClose;



    boolean wedFlag=false;
    boolean wedPartFlag=false;
    EditText wedOpen;
    EditText wedClose;
    EditText wedPartOpen;
    EditText wedPartClose;

    boolean thuFlag=false;
    boolean thuPartFlag=false;
    EditText thuOpen;
    EditText thuClose;
    EditText thuPartOpen;
    EditText thuPartClose;

    boolean friFlag=false;
    boolean friPartFlag=false;
    EditText friOpen;
    EditText friClose;
    EditText friPartOpen;
    EditText friPartClose;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_working_hours_per_day);


        WorkingTimeOnSaturdy sat = new WorkingTimeOnSaturdy();
        WorkingTimeOnSunday sun=new WorkingTimeOnSunday();
        WorkingTimeOnMonday mon= new WorkingTimeOnMonday();
        WorkingTimeOnTueseday tus=new WorkingTimeOnTueseday();
        WorkingTimeOnWednesday wed =new WorkingTimeOnWednesday();
        WorkingTimeOnThuresday thu= new WorkingTimeOnThuresday();
        WorkingTimeOnFriday fri =new WorkingTimeOnFriday();













        satTable=(TableLayout)findViewById(R.id.satTable);
        satTable.setVisibility(View.GONE);

        satPartTimeRow=(TableRow)findViewById(R.id.satPartTimeRow);
        satPartTimeRow.setVisibility(View.GONE);

        satBox=(CheckBox)findViewById(R.id.satBox);
        satBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {


                    satTable.setVisibility(View.VISIBLE);
                    satFlag=true;


                }

                else


                    satTable.setVisibility(View.GONE);
                    satFlag=false;

            }
        });



        satAddPartTimeText=(TextView)findViewById(R.id.satAddPartTimeText);
        satAddPartTimeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                satPartTimeRow.setVisibility(View.VISIBLE);
                satPartFlag=true;
            }
        });






        closePartSat=(ImageView)findViewById(R.id.closePartSat);
        closePartSat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                satPartTimeRow.setVisibility(View.GONE);
                satPartFlag=false;
            }
        });










/////////////////////////////////////////////////////////////////////////////////////////////////////


        sunTable=(TableLayout)findViewById(R.id.sunTable);
        sunTable.setVisibility(View.GONE);

        sunPartTimeRow=(TableRow)findViewById(R.id.sunPartTimeRow);
        sunPartTimeRow.setVisibility(View.GONE);

        sunBox=(CheckBox)findViewById(R.id.sunBox);
        sunBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {


                    sunTable.setVisibility(View.VISIBLE);


                }

                else


                    sunTable.setVisibility(View.GONE);


            }
        });




        sunAddPartTimeText=(TextView)findViewById(R.id.sunAddPartTimeText);
        sunAddPartTimeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                sunPartTimeRow.setVisibility(View.VISIBLE);

            }
        });






        closePartSun=(ImageView)findViewById(R.id.closePartSun);
        closePartSun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sunPartTimeRow.setVisibility(View.GONE);
            }
        });

////////////////////////////////////////////////////////////////////////


        monTable=(TableLayout)findViewById(R.id.monTable);
        monTable.setVisibility(View.GONE);

        monPartTimeRow=(TableRow)findViewById(R.id.monPartTimeRow);
        monPartTimeRow.setVisibility(View.GONE);

        monBox=(CheckBox)findViewById(R.id.monBox);
        monBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {


                    monTable.setVisibility(View.VISIBLE);


                }

                else


                    monTable.setVisibility(View.GONE);


            }
        });




        monAddPartTimeText=(TextView)findViewById(R.id.monAddPartTimeText);
        monAddPartTimeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                monPartTimeRow.setVisibility(View.VISIBLE);

            }
        });






        closePartMon=(ImageView)findViewById(R.id.closePartMon);
        closePartMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monPartTimeRow.setVisibility(View.GONE);
            }
        });
/////////////////////////////////////////////////////////////////

        tusTable=(TableLayout)findViewById(R.id.tusTable);
        tusTable.setVisibility(View.GONE);

        tusPartTimeRow=(TableRow)findViewById(R.id.tusPartTimeRow);
        tusPartTimeRow.setVisibility(View.GONE);

        tusBox=(CheckBox)findViewById(R.id.tusBox);
        tusBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {


                    tusTable.setVisibility(View.VISIBLE);


                }

                else


                    tusTable.setVisibility(View.GONE);


            }
        });




        tusAddPartTimeText=(TextView)findViewById(R.id.tusAddPartTimeText);
        tusAddPartTimeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                tusPartTimeRow.setVisibility(View.VISIBLE);

            }
        });






        closePartTus=(ImageView)findViewById(R.id.closePartTus);
        closePartTus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tusPartTimeRow.setVisibility(View.GONE);
            }
        });
////////////////////////////////////////////////////////////





        wedTable=(TableLayout)findViewById(R.id.wedTable);
        wedTable.setVisibility(View.GONE);

        wedPartTimeRow=(TableRow)findViewById(R.id.wedPartTimeRow);
        wedPartTimeRow.setVisibility(View.GONE);

        wedBox=(CheckBox)findViewById(R.id.wedBox);
        wedBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {


                    wedTable.setVisibility(View.VISIBLE);


                }

                else


                    wedTable.setVisibility(View.GONE);


            }
        });




        wedAddPartTimeText=(TextView)findViewById(R.id.wedAddPartTimeText);
        wedAddPartTimeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                wedPartTimeRow.setVisibility(View.VISIBLE);

            }
        });






        closePartWed=(ImageView)findViewById(R.id.closePartWed);
        closePartWed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wedPartTimeRow.setVisibility(View.GONE);
            }
        });
////////////////////////////////////////////////////////////////


        thuTable=(TableLayout)findViewById(R.id.thuTable);
        thuTable.setVisibility(View.GONE);

        thuPartTimeRow=(TableRow)findViewById(R.id.thuPartTimeRow);
        thuPartTimeRow.setVisibility(View.GONE);

        thuBox=(CheckBox)findViewById(R.id.thuBox);
        thuBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {


                    thuTable.setVisibility(View.VISIBLE);


                }

                else


                    thuTable.setVisibility(View.GONE);


            }
        });




        thuAddPartTimeText=(TextView)findViewById(R.id.thuAddPartTimeText);
        thuAddPartTimeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                thuPartTimeRow.setVisibility(View.VISIBLE);

            }
        });






        closePartThu=(ImageView)findViewById(R.id.closePartThu);
        closePartThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thuPartTimeRow.setVisibility(View.GONE);
            }
        });
/////////////////////////////////////////////////////




        friTable=(TableLayout)findViewById(R.id.friTable);
        friTable.setVisibility(View.GONE);

        friPartTimeRow=(TableRow)findViewById(R.id.friPartTimeRow);
        friPartTimeRow.setVisibility(View.GONE);

        friBox=(CheckBox)findViewById(R.id.friBox);
        friBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {


                    friTable.setVisibility(View.VISIBLE);


                }

                else


                    friTable.setVisibility(View.GONE);


            }
        });




        friAddPartTimeText=(TextView)findViewById(R.id.friAddPartTimeText);
        friAddPartTimeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                friPartTimeRow.setVisibility(View.VISIBLE);

            }
        });






        closePartFri=(ImageView)findViewById(R.id.closePartFri);
        closePartFri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                friPartTimeRow.setVisibility(View.GONE);
            }
        });
////////////////////////////////////////////////////////////////////////





        satOpen = (EditText)findViewById(R.id.satOpen);
        satClose =(EditText)findViewById(R.id.satClose);
        satPartOpen=(EditText)findViewById(R.id.satPartOpen);
        satPartClose=(EditText)findViewById(R.id.satPartClose);




    /*    satOpen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(WorkingHoursPerDay.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        satOpen.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });*/


        sunOpen = (EditText)findViewById(R.id.sunOpen);
        sunClose =(EditText)findViewById(R.id.sunClose);
        sunPartOpen=(EditText)findViewById(R.id.sunPartOpen);
        sunPartClose=(EditText)findViewById(R.id.sunPartClose);


        monOpen = (EditText)findViewById(R.id.monOpen);
        monClose =(EditText)findViewById(R.id.monClose);
        monPartOpen=(EditText)findViewById(R.id.monPartOpen);
        monPartClose=(EditText)findViewById(R.id.monPartClose);


        tusOpen = (EditText)findViewById(R.id.tusOpen);
        tusClose =(EditText)findViewById(R.id.tusClose);
        tusPartOpen=(EditText)findViewById(R.id.tusPartOpen);
        tusPartClose=(EditText)findViewById(R.id.tusPartClose);


        wedOpen = (EditText)findViewById(R.id.wedOpen);
        wedClose =(EditText)findViewById(R.id.wedClose);
        wedPartOpen=(EditText)findViewById(R.id.wedPartOpen);
        wedPartClose=(EditText)findViewById(R.id.wedPartClose);



        thuOpen = (EditText)findViewById(R.id.thuOpen);
        thuClose =(EditText)findViewById(R.id.thuClose);
        thuPartOpen=(EditText)findViewById(R.id.thuPartOpen);
        thuPartClose=(EditText)findViewById(R.id.thuPartClose);



        friOpen = (EditText)findViewById(R.id.friOpen);
        friClose =(EditText)findViewById(R.id.friClose);
        friPartOpen=(EditText)findViewById(R.id.friPartOpen);
        friPartClose=(EditText)findViewById(R.id.friPartClose);


//////////////////////////////////////////




        fieldValidation(satFlag,satOpen,satClose,satPartFlag,satPartOpen,satPartClose);









    }





 void  fieldValidation(boolean flag,EditText open, EditText close,boolean flagPart, EditText openpart, EditText closepart){


      if(flag){




          if(open.getText().toString().equals("")||close.getText().toString().equals("")){

              Toast.makeText(this, "Please fill the time correctly, there is missing fields!", Toast.LENGTH_LONG).show();
          }



      //   else if(open.getText()<=close.getText()){


       //  }










      }



      if(flagPart){
          if(openpart.getText().toString().equals("")||closepart.getText().toString().equals("")){

              Toast.makeText(this, "Please fill the time correctly, there is missing fields in part time sections!", Toast.LENGTH_LONG).show();

          }

      }


//return true;

  }








  /*  public void onClick(View v) {
        // TODO Auto-generated method stub
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(AddReminder.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                eReminderTime.setText( selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }*/




    public void showHourPicker(View v) {
       // EditText e=(EditText)v.getId();
       // View vi = getLayoutInflater().inflate(R.layout.activity_working_hours_per_day, null);


        final Calendar myCalender = Calendar.getInstance();
        int hour = myCalender.get(Calendar.HOUR_OF_DAY);
        int minute = myCalender.get(Calendar.MINUTE);


        TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (view.isShown()) {
                    myCalender.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    myCalender.set(Calendar.MINUTE, minute);
             // vi.setText( hourOfDay + ":" + minute);
                }
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(WorkingHoursPerDay.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, hour, minute, true);
        timePickerDialog.setTitle("Choose hour:");
        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();
    }






}