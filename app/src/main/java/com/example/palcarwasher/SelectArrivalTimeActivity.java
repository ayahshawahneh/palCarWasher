package com.example.palcarwasher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SelectArrivalTimeActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Calendar> daysList;
    DaysAdapter daysAdapter;
    GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_arrival_time);

        String providerId="-MPq8uV089ZVlR0gDrnW";

        gridView=findViewById(R.id.timeSlot_gridView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView=findViewById(R.id.days_recyclerView);
        recyclerView.setLayoutManager(layoutManager);

        SimpleDateFormat dateFormat= new SimpleDateFormat("EEEE dd.MM");
        daysList=new ArrayList<Calendar>();


        for (int i=0;i<=6;i++){
            Calendar day= Calendar.getInstance();
             day.add(Calendar.DATE, i);
             daysList.add(day);
             String toDate=dateFormat.format(day.getTime());
             Log.v("DataOB",toDate);

        }
        daysAdapter=new DaysAdapter(daysList,providerId,gridView);
        recyclerView.setAdapter(daysAdapter);




    }
}