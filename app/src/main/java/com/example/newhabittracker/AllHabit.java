package com.example.newhabittracker;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.newhabittracker.Adapter.HabitItemAdapter;
import com.example.newhabittracker.Database.HabitItemDAO;
import com.example.newhabittracker.Database.HabitItemViewModel;
import com.example.newhabittracker.Model.HabitItem;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AllHabit extends Fragment implements View.OnClickListener{
    private ChipGroup choose_days;
    private RecyclerView recyclerView0;
    private HabitItemViewModel habitItemViewModel;
    HabitItemDAO habitItemDAO;
    private ChipGroup chipGroup_selectDays;
    private int num;
    private String name;
    private ImageButton home_btn;
    private FloatingActionButton add_fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_all_habit, container, false);

        home_btn=v.findViewById(R.id.imgButton_today);
        home_btn.setOnClickListener((View.OnClickListener) this);
        add_fab=v.findViewById(R.id.floating_addButton);
        add_fab.setOnClickListener((View.OnClickListener)this);


        //RecyclerView
        recyclerView0 = v.findViewById(R.id.recyclerView_all);
        recyclerView0.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView0.setHasFixedSize(true);
        final HabitItemAdapter adapter0=new HabitItemAdapter();
        recyclerView0.setAdapter(adapter0);
        habitItemViewModel= new ViewModelProvider(this).get(HabitItemViewModel.class);
        chipGroup_selectDays=v.findViewById(R.id.chipGroup_daysView);
        chipGroup_selectDays.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(ChipGroup group, int checkedId) {
                    switch(checkedId){
                        case R.id.chipAll_sunday:
                            num=0;
                            habitItemViewModel.getChosenDay(num).observe(getViewLifecycleOwner(), new Observer<List<HabitItem>>() {
                                @Override
                                public void onChanged(List<HabitItem> habitItems) {
                                    adapter0.setHabitItem(habitItems);
                                }
                            });
                            break;
                        case R.id.chipAll_monday:
                            num =1;
                            habitItemViewModel.getChosenDay(num).observe(getViewLifecycleOwner(), new Observer<List<HabitItem>>() {
                                @Override
                                public void onChanged(List<HabitItem> habitItems) {
                                    adapter0.setHabitItem(habitItems);
                                }
                            });
                            break;
                        case R.id.chipAll_tuesday:
                            num=2;
                            habitItemViewModel.getChosenDay(num).observe(getViewLifecycleOwner(), new Observer<List<HabitItem>>() {
                                @Override
                                public void onChanged(List<HabitItem> habitItems) {
                                    adapter0.setHabitItem(habitItems);
                                }
                            });
                            break;
                        case R.id.chipAll_wednesday:
                            num=3;
                            habitItemViewModel.getChosenDay(num).observe(getViewLifecycleOwner(), new Observer<List<HabitItem>>() {
                                @Override
                                public void onChanged(List<HabitItem> habitItems) {
                                    adapter0.setHabitItem(habitItems);
                                }
                            });
                            break;
                        case R.id.chipAll_thursday:
                            num=4;
                            habitItemViewModel.getChosenDay(num).observe(getViewLifecycleOwner(), new Observer<List<HabitItem>>() {
                                @Override
                                public void onChanged(List<HabitItem> habitItems) {
                                    adapter0.setHabitItem(habitItems);
                                }
                            });
                            break;
                        case R.id.chipAll_friday:
                            num=5;
                            habitItemViewModel.getChosenDay(num).observe(getViewLifecycleOwner(), new Observer<List<HabitItem>>() {
                                @Override
                                public void onChanged(List<HabitItem> habitItems) {
                                    adapter0.setHabitItem(habitItems);
                                }
                            });
                            break;
                        case R.id.chipAll_saturday:
                            num =6;
                            habitItemViewModel.getChosenDay(num).observe(getViewLifecycleOwner(), new Observer<List<HabitItem>>() {
                                @Override
                                public void onChanged(List<HabitItem> habitItems) {
                                    adapter0.setHabitItem(habitItems);

                                }
                            });
                            break;
                    }
                }

        });



        MainActivity main=(MainActivity)getActivity();


       return v;
    }


    public String name(){
        name="Writing";
        return name;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgButton_today:
                FragmentManager fm=getActivity().getSupportFragmentManager();
                if(fm.getBackStackEntryCount()>0){
                    fm.popBackStackImmediate();
                }
                break;
            case R.id.floating_addButton:
                Intent intent=new Intent(getActivity(),AddHabit.class);
                startActivity(intent);
        }


    }
}