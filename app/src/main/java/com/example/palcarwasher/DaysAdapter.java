package com.example.palcarwasher;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.HashMap;
import java.util.List;

public class DaysAdapter extends RecyclerView.Adapter<DaysAdapter.DaysHolder> {
    GridView gridView;
    List<Calendar> dayList;
    String providerId;


    DatabaseReference reference;

    List<String> stringSlotList=new ArrayList<String>();

    List<String> finalSelections=new ArrayList <String>();
    List<String> finalSelectionsDB=new ArrayList <String>();
    List<String> finalSlots=new ArrayList <String>();
    //public String reservedItem;
    int count=0;
    int row_index;
//List<String> reservedTimeSlots =new ArrayList<String>();

    public DaysAdapter( List<Calendar> dayList, String providerId,GridView gridView) {
        this.gridView = gridView;
        this.dayList = dayList;
        this.providerId = providerId;


    }


    @NonNull
    @Override
    public DaysHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_buttons,parent,false);
        return new DaysAdapter.DaysHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DaysHolder holder, final int position) {

        final  Calendar dayItem = dayList.get(position);
        SimpleDateFormat dateFormat= new SimpleDateFormat("EEE dd/MM");
        SimpleDateFormat dateFormat2= new SimpleDateFormat("EEE dd/MM/YYYY");
        final String toDate2=dateFormat2.format(dayItem.getTime());
        final String toDate=dateFormat.format(dayItem.getTime());
        holder.dayButton.setText(toDate);


        stringSlotList.clear();
        SimpleDateFormat dayPattern= new SimpleDateFormat("EEEE");
        final String dayName = dayPattern.format(dayItem.getTime());
        String databaseChild=null;

        if (count==0) {
            finalSelections.add(toDate);
            finalSelectionsDB.add(toDate2);
            holder.dayButton.setBackgroundResource(R.drawable.btn_custom);
            if (dayName.equals("Friday")) {

                databaseChild = "WorkingTimeOnFriday";
                getFromDatabase(position,databaseChild,toDate2);
            }
            else if (dayName.equals("Saturday")) {
                databaseChild = "WorkingTimeOnSaturdy";
                getFromDatabase(position,databaseChild,toDate2);
            }


            else if (dayName.equals("Sunday")) {
                databaseChild = "WorkingTimeOnSunday";
                getFromDatabase(position,databaseChild,toDate2);
            }


            else if (dayName.equals("Monday")) {
                databaseChild = "WorkingTimeOnMonday";
                getFromDatabase(position,databaseChild,toDate2);
            }


            else if (dayName.equals("Tuesday")) {
                databaseChild = "WorkingTimeOnTueseday";
                getFromDatabase(position,databaseChild,toDate2);
            }

            else if (dayName.equals("Wednesday")) {
                databaseChild = "WorkingTimeOnWednesday";
                getFromDatabase(position,databaseChild,toDate2);
            }

            else  {
                databaseChild = "WorkingTimeOnThuresday";
                getFromDatabase(position,databaseChild,toDate2);
            }

        }else holder.dayButton.setBackgroundResource(R.color.gray);
        count++;



        holder.dayButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {


                stringSlotList.clear();
                //////////////////////////////////
                finalSlots.clear();
                finalSelections.clear();
                finalSelections.add(toDate);
                finalSelectionsDB.clear();
                finalSelectionsDB.add(toDate2);
///////////////////


                row_index=position;
                notifyDataSetChanged();





                String databaseChild=null;
                if(dayName.equals("Friday")){

                    databaseChild="WorkingTimeOnFriday";
                    getFromDatabase(position,databaseChild,toDate2);
                }
                else if(dayName.equals("Saturday")) {
                    databaseChild="WorkingTimeOnSaturdy";
                    getFromDatabase(position,databaseChild,toDate2);}


                else if(dayName.equals("Sunday")) {
                    databaseChild="WorkingTimeOnSunday";
                    getFromDatabase(position,databaseChild,toDate2);}


                else if(dayName.equals("Monday")) {
                    databaseChild="WorkingTimeOnMonday";
                    getFromDatabase(position,databaseChild,toDate2);}


                else if(dayName.equals("Tuesday")){
                    databaseChild="WorkingTimeOnTueseday";
                    getFromDatabase(position,databaseChild,toDate2);}

                else if(dayName.equals("Wednesday")) {
                    databaseChild="WorkingTimeOnWednesday";
                    getFromDatabase(position,databaseChild,toDate2);}

                else {
                    databaseChild="WorkingTimeOnThuresday";
                    getFromDatabase(position,databaseChild,toDate2);}

            }
        });

        if(row_index==position){
            holder.dayButton.setBackgroundResource(R.drawable.btn_custom);


        }
        else
        {
            holder.dayButton.setBackgroundResource(R.color.gray);



        }

    }

    @Override
    public int getItemCount() {
        return dayList.size();
    }



    public List <String> getFinalSelections(){ return finalSelections; }
    public List <String> getFinalSelectionsDB(){ return finalSelectionsDB; }
    public List <String> getFinalSlots(){ return finalSlots; }




    public class DaysHolder extends RecyclerView.ViewHolder{
        Button dayButton;

        public DaysHolder(@NonNull View itemView) {
            super(itemView);
            dayButton=itemView.findViewById(R.id.day_button);

        }




    }
    //////////////////****************************************************************************
    void getFromDatabase(int position,String databaseChild, final String fullDate) {
        if (position == 0){
            //  final List<String> reservedTimeSlots=getReservedTimeSlots();

            //  reservedTimeSlots.add("Sun 24/01/2021 01:00 PM-02:00 PM");
            reference = FirebaseDatabase.getInstance().getReference().child("PalCarWasher")
                    .child(databaseChild);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                stringSlotList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    WorkingTimeOnSaturdy fri = dataSnapshot.getValue(WorkingTimeOnSaturdy.class);
                    if (fri.getPrviderId().equals(providerId)) {
                        Log.v("DataOB", providerId);

                        String start = fri.getFrom();
                        String end = fri.getTo();
                        String partStart = fri.getPartTimeFrom();
                        String partEnd = fri.getPartTimeTo();


                        ///////////////////////////


                        if (start != null) {
                            Log.v("DataOB", "start" + start);
                            String format = "hh:mm aa";
                            SimpleDateFormat sdf = new SimpleDateFormat(format);


                            Date dateObj1 = null;
                            Date dateObj2 = null;
                            Date dateObj11 = null;
                            Date dateObj22 = null;
                            Date nowPlus30 = null;

                            try {
                                dateObj1 = sdf.parse(start);
                                dateObj2 = sdf.parse(end);

                                Calendar currentTime = Calendar.getInstance();
                                currentTime.add(Calendar.MINUTE, 30);


                                String s = sdf.format(currentTime.getTime());
                                // Log.v("DataOB",s);

                                nowPlus30 = sdf.parse(s);

                                Log.v("DataOB", nowPlus30 + "nowPlus30");
                                Log.v("DataOB", dateObj1 + "dateObj1");
                                Log.v("DataOB", dateObj2 + "dateObj2");


                                long dif = dateObj1.getTime();
                                while (dif < dateObj2.getTime()) {
                                    Date slot = new Date(dif);
                                    if (slot.after(nowPlus30)) {
                                        Date nextSlot = new Date(dif + 3600000);
                                        if (!nextSlot.after(dateObj2)) {
                                            String fullSlot = sdf.format(slot) + "-" + sdf.format(nextSlot);
                                            // Log.v("DataOB",fullSlot+"");
                                            stringSlotList.add(fullSlot);
                                        }
                                    }
                                    dif += 3600000;
                                }


                                dateObj11 = sdf.parse(partStart);
                                dateObj22 = sdf.parse(partEnd);
                                if (partStart != null) {
                                    long dif2 = dateObj11.getTime();
                                    while (dif2 < dateObj22.getTime()) {
                                        Date slot = new Date(dif2);
                                        if (slot.after(nowPlus30)) {
                                            Date nextSlot = new Date(dif2 + 3600000);
                                            if (!nextSlot.after(dateObj22)) {
                                                String fullSlot = sdf.format(slot) + "-" + sdf.format(nextSlot);
                                                // Log.v("DataOB",fullSlot+"");
                                                stringSlotList.add(fullSlot);

                                            }
                                        }
                                        dif2 += 3600000;
                                    }

                                }


                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            getReservedTimeSlots(fullDate);


                        }


                        /////////////////////////


                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }else
    {
        reference = FirebaseDatabase.getInstance().getReference().child("PalCarWasher")
                .child(databaseChild);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                stringSlotList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    WorkingTimeOnSaturdy fri = dataSnapshot.getValue(WorkingTimeOnSaturdy.class);
                    if (fri.getPrviderId().equals(providerId)) {
                        Log.v("DataOB", providerId);

                        String start = fri.getFrom();
                        String end = fri.getTo();
                        String partStart = fri.getPartTimeFrom();
                        String partEnd = fri.getPartTimeTo();


                        ///////////////////////////


                        if (start != null) {
                            Log.v("DataOB", "start" + start);
                            String format = "hh:mm aa";
                            SimpleDateFormat sdf = new SimpleDateFormat(format);


                            Date dateObj1 = null;
                            Date dateObj2 = null;
                            Date dateObj11 = null;
                            Date dateObj22 = null;


                            try {
                                dateObj1 = sdf.parse(start);
                                dateObj2 = sdf.parse(end);

                                Calendar currentTime = Calendar.getInstance();
                                currentTime.add(Calendar.MINUTE, 30);


                                String s = sdf.format(currentTime.getTime());
                                // Log.v("DataOB",s);




                                Log.v("DataOB", dateObj1 + "dateObj1");
                                Log.v("DataOB", dateObj2 + "dateObj2");


                                long dif = dateObj1.getTime();
                                while (dif < dateObj2.getTime()) {
                                    Date slot = new Date(dif);
                                        Date nextSlot = new Date(dif + 3600000);
                                        if (!nextSlot.after(dateObj2)) {
                                            String fullSlot = sdf.format(slot) + "-" + sdf.format(nextSlot);
                                            // Log.v("DataOB",fullSlot+"");
                                            stringSlotList.add(fullSlot);
                                        }

                                    dif += 3600000;
                                }


                                dateObj11 = sdf.parse(partStart);
                                dateObj22 = sdf.parse(partEnd);
                                if (partStart != null) {
                                    long dif2 = dateObj11.getTime();
                                    while (dif2 < dateObj22.getTime()) {
                                        Date slot = new Date(dif2);
                                            Date nextSlot = new Date(dif2 + 3600000);
                                            if (!nextSlot.after(dateObj22)) {
                                                String fullSlot = sdf.format(slot) + "-" + sdf.format(nextSlot);
                                                // Log.v("DataOB",fullSlot+"");
                                                stringSlotList.add(fullSlot);

                                            }

                                        dif2 += 3600000;
                                    }

                                }


                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            getReservedTimeSlots(fullDate);


                        }


                        /////////////////////////


                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







    }





    }



    /////////////////////////////***********************************************************
//List<String>


    void getReservedTimeSlots(final String fullDate){

        //reservedTimeSlots.clear();
        reference= FirebaseDatabase.getInstance().getReference().child("PalCarWasher")
                .child("Orders");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> reservedTimeSlots = new ArrayList<String>();
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Orders order = dataSnapshot.getValue(Orders.class);

                        if (order.getProviderId().equals(providerId) && order.getStatus().equals("confirmed")) {


                            for (int i = 0; i < stringSlotList.size(); i++) {
                                String fullTimeSlot=fullDate+" "+stringSlotList.get(i);

                                //  Log.v("DataOB",fullTimeSlot);
                                if (fullTimeSlot.equals(order.getFullTime()))

                                    stringSlotList.remove(i);



                            }
                            //  Log.v("DataOB",order.getFullTime());





                        }// if

                    }
                }



                gridView.setAdapter(new GridViewAdapter() );

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//Log.v("DataOB",reservedTimeSlots.get(0));

//return reservedTimeSlots2;
    }


/////////////////////////////////////////////////////////

    public class GridViewAdapter extends BaseAdapter {
        int index;
        public HashMap<Integer, Boolean> hashMapSelected;

        public GridViewAdapter() {
            hashMapSelected = new HashMap<>();
            for (int i = 0; i < stringSlotList.size(); i++)
                hashMapSelected.put(i, false);
        }

        @Override
        public int getCount() {
            return stringSlotList.size();
        }

        @Override
        public Object getItem(int position) {

            return stringSlotList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.time_slot_button,parent,false);
            final Button timeSlotButton= view.findViewById(R.id.time_slot);
            //  final Date timeSlotItem=slotTimeList.get(position);
            final String slotItem = stringSlotList.get(position);
            // finalSlots.add(slotItem);
            timeSlotButton.setText(slotItem);

            timeSlotButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    finalSlots.clear();
                    finalSlots.add(slotItem);

                    makeAllUnselect(position);
                    notifyDataSetChanged();



                }
            });

            if (hashMapSelected.get(position)) {
                timeSlotButton.setBackgroundResource(R.drawable.btn_custom3);



            } else {
                timeSlotButton.setBackgroundResource(R.drawable.btn_custom2);

            }

            return view;
        }


        public void makeAllUnselect(int position) {
            hashMapSelected.put(position, true);
            for (int i = 0; i < hashMapSelected.size(); i++) {
                if (i != position)
                    hashMapSelected.put(i, false);
            }
        }



    }







/////////////////////////////////////////////
}