package com.example.newhabittracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.newhabittracker.Adapter.HabitCompletedAdapter;
import com.example.newhabittracker.Database.HabitItemViewModel;
import com.example.newhabittracker.Model.HabitList;

import java.util.List;

public class HabitStatsActivities extends AppCompatActivity {

    private TextView title, testHabitName;
    private ImageButton back;
    private HabitItemViewModel habitItemViewModel;
    public static final String EXTRA_HABITNAME=
            "com.example.myapplication.EXTRA_HABITNAME";
    public static final String EXTRA_TOTALCOMPLETED=
            "com.example.myapplication.EXTRA_TOTALCOMPLETED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_stats);
        title=findViewById(R.id.txtView_statsTitle);
        back=findViewById(R.id.btn_return);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HabitStatsActivities.this,MainActivity.class);
                startActivity(intent);
            }
        });
        final RecyclerView recyclerViewStats=findViewById(R.id.recyclerViewStats);
        recyclerViewStats.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewStats.setHasFixedSize(true);
        final HabitCompletedAdapter adapterStats=new HabitCompletedAdapter();
        recyclerViewStats.setAdapter(adapterStats);
        habitItemViewModel = new ViewModelProvider(this).get(HabitItemViewModel.class);
        habitItemViewModel.getHabitList().observe(this, new Observer<List<HabitList>>() {
            @Override
            public void onChanged(List<HabitList> habitLists) {
                adapterStats.setHabitCompleted(habitLists);
            }
        });

        adapterStats.setOnItemClickListener(new HabitCompletedAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(HabitList habitLists) {
                Intent intent=new Intent(HabitStatsActivities.this,StatsDetails.class);
                intent.putExtra(HabitStatsActivities.EXTRA_HABITNAME,habitLists.getHabitName_column());
                intent.putExtra(HabitStatsActivities.EXTRA_TOTALCOMPLETED,habitLists.getTotalCompleted_column());
                startActivity(intent);
            }

        });


    }

}