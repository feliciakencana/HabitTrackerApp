package com.example.newhabittracker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.applikeysolutions.cosmocalendar.settings.lists.connected_days.ConnectedDays;
import com.applikeysolutions.cosmocalendar.view.CalendarView;
import com.example.newhabittracker.Database.HabitItemViewModel;
import com.example.newhabittracker.Model.HabitDate;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class StatsDetails extends AppCompatActivity {
    TextView habit_name, total_completed;
    ImageButton btn_return;
    String habitName;
    int totalCompleted;
    CalendarView calendarView;
    List<HabitDate> dateList;
    Set<Long>days;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_details);
        habit_name=findViewById(R.id.habitName);
        total_completed=findViewById(R.id.habitTotalCompleted);
        btn_return=findViewById(R.id.btnReturn);
        calendarView=findViewById(R.id.cosmoCalendar);
        Intent intent=getIntent();
        if(intent!=null){
            habitName=intent.getStringExtra(HabitStatsActivities.EXTRA_HABITNAME);
            totalCompleted= intent.getIntExtra(HabitStatsActivities.EXTRA_TOTALCOMPLETED,0);
        }
        habit_name.setText(habitName);
        total_completed.setText(Integer.toString(totalCompleted));

        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnMain();
            }
        });

        calendarView.setFirstDayOfWeek(Calendar.MONDAY);

        calendarView.setWeekendDays(new HashSet(){{
            add(Calendar.SATURDAY);
            add(Calendar.SUNDAY);
        }});
        calendarView.setCalendarOrientation(0);


        HabitItemViewModel habitItemViewModel=new HabitItemViewModel(getApplication());
        dateList= null;
        try {
            dateList = habitItemViewModel.getDate(habitName);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(dateList!=null){
            Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
        }

        Calendar calendar=Calendar.getInstance();
        days=new HashSet<>();

        for(int i=0;i<dateList.size();i++){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            try {
                date=sdf.parse(dateList.get(i).getHabitDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long timeInMillis = date.getTime();
            days.add(timeInMillis);
        }

        int textColor = Color.parseColor("#ff0000");
        int selectedTextColor = Color.parseColor("#ff4000");
        int disabledTextColor = Color.parseColor("#ff8000");

        ConnectedDays connectedDays = new ConnectedDays(days, textColor, selectedTextColor, disabledTextColor);

//Connect days to calendar
        calendarView.addConnectedDays(connectedDays);

    }
    void returnMain(){
        for(int i=0;i<dateList.size();i++){
            days.clear();
        }
        Intent return_main=new Intent(this,HabitStatsActivities.class);
        startActivity(return_main);
    }
}