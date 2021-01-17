package com.example.palcarwasher;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AlertDialogTimSlot extends Dialog implements
        android.view.View.OnClickListener {

    RecyclerView recyclerView;
    List<Calendar> daysList;
    DaysAdapter daysAdapter;
    GridView gridView;
Button selectArrivalTime;
Button save;

String finalSelection;
String FinalSelectionDB;
List <String> finalSelections;
List<String> finalSelectionsDB;
List<String> finalSlots;

    public Activity activity;
    public AlertDialogTimSlot(Activity a,Button b ) {
        super(a);
        this.activity = a;
        this.selectArrivalTime=b;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.custom_dialog_time_slot);





        String providerId="-MPq8uV089ZVlR0gDrnW";

        gridView=findViewById(R.id.timeSlot_gridView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
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
        finalSelections=daysAdapter.getFinalSelections();
        finalSlots=daysAdapter.getFinalSlots();
        finalSelectionsDB=daysAdapter.getFinalSelectionsDB();
        recyclerView.setAdapter(daysAdapter);




        save=findViewById(R.id.save);
        save.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {


if(v.getId()==R.id.save){
   // Log.v("DataOB",finalSelection);

 selectArrivalTime.setText(finalSelections.get(0)+" "+finalSlots.get(0));
    dismiss();
}



    }
}
