package com.example.newhabittracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newhabittracker.Adapter.HabitItemAdapter;
import com.example.newhabittracker.Database.HabitItemViewModel;
import com.example.newhabittracker.Model.HabitCompleted;
import com.example.newhabittracker.Model.HabitItem;
import com.example.newhabittracker.Model.HabitList;
import com.example.newhabittracker.Service.ResetService;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton add_fab;
    private HabitItemViewModel habitItemViewModel;
    private BottomAppBar bottomAppBar;
    private TextView todayDate, test;
    private ImageButton btn_allHabit,btn_stats;
    private String habit_name,test_name;
    private int count, completed, num;
    private AlarmManager alarmManager;
    public static final int ADD_HABIT_REQUEST=1;
    public static final int UPDATE_HABIT_VALUE_REQUEST=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setSupportActionBar(bottomAppBar);
        //get today date
        SimpleDateFormat today_sdf=new SimpleDateFormat("EEEE"+", "+"dd "+"MMMM "+"yyyy");
        String currentDate= today_sdf.format(new Date());
        todayDate.setText(currentDate);
        //RecyclerView
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final HabitItemAdapter adapter=new HabitItemAdapter();
        recyclerView.setAdapter(adapter);
        habitItemViewModel= new ViewModelProvider(this).get(HabitItemViewModel.class);
        habitItemViewModel.getTodayData().observe(this, new Observer<List<HabitItem>>() {
            @Override
            public void onChanged(List<HabitItem> habitItems) {
                adapter.setHabitItem(habitItems);
            }
        });

        add_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAdd();
            }
        });

        adapter.setOnItemClickListener(new HabitItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(HabitItem habitItem) {
                Intent intent=new Intent(MainActivity.this,HabitClicked.class);
                intent.putExtra(AddHabit.EXTRA_ID,habitItem.getId());
                intent.putExtra(AddHabit.EXTRA_HABITNAME,habitItem.getHabitName_column());
                intent.putExtra(AddHabit.EXTRA_SELECTEDDAYS,habitItem.getSelectedDays_column());
                intent.putExtra(AddHabit.EXTRA_HOUR,habitItem.getTargetedHour_column());
                intent.putExtra(AddHabit.EXTRA_GOALVALUE,habitItem.getTargetedValue_column());
                intent.putExtra(AddHabit.EXTRA_GOALUNIT,habitItem.getValueUnit_column());
                intent.putExtra(AddHabit.EXTRA_ISDURATION,habitItem.getIsDuration_column());
                intent.putExtra(AddHabit.EXTRA_GOALREACHED,habitItem.getGoalReached_column());
                intent.putExtra(AddHabit.EXTRA_TOTALCOMPLETED,habitItem.getTotalCompleted_column());
                intent.putExtra(AddHabit.EXTRA_ISTIMECOMPLETED,habitItem.getIsTimeComplete_column());
                intent.putExtra(AddHabit.EXTRA_TIMECREATED,habitItem.getTimeCreated_column());

                startActivityForResult(intent,UPDATE_HABIT_VALUE_REQUEST);
            }
        });

        btn_allHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager=getFragmentManager();


                Fragment allHabit=new AllHabit();
                replaceFragment(allHabit);
            }
        });

        btn_stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               openStat();
            }
        });

       resetProgress();



    }

    private void resetProgress(){
        Intent resetIntent=new Intent(MainActivity.this, ResetService.class);
        PendingIntent pi=PendingIntent.getService(this,0,resetIntent,0);
        alarmManager=(AlarmManager)this.getSystemService(ALARM_SERVICE);
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK,1);
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        calendar.set(Calendar.MILLISECOND,0);

        if(alarmManager!=null){
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY*7,pi);
        }
    }

    public void replaceFragment(Fragment fragment){
        androidx.fragment.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.nav_default_enter_anim,R.anim.nav_default_exit_anim,
                R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim);
        fragmentTransaction.replace(R.id.frame1,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void openAdd(){
        Intent add=new Intent(this, AddHabit.class);
        startActivityForResult(add,ADD_HABIT_REQUEST);// kalo error hilangin return true n gnti bgian ini
    }

    public void openStat(){
        Intent intent=new Intent(MainActivity.this, HabitStatsActivities.class);
        startActivity(intent);
    }


    void initView(){
        add_fab=findViewById(R.id.floating_addButton);
        bottomAppBar =findViewById(R.id.bottomAppBar);
        todayDate=findViewById(R.id.today_date);
        btn_allHabit=findViewById(R.id.btn_all);
        test=findViewById(R.id.title_all);
        btn_stats=findViewById(R.id.btn_stats);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ADD_HABIT_REQUEST && resultCode==RESULT_OK){
            habit_name=data.getStringExtra(AddHabit.EXTRA_HABITNAME);
            ArrayList<Integer> habit_selectedDays=data.getIntegerArrayListExtra(AddHabit.EXTRA_SELECTEDDAYS);
            int habit_isDuration=data.getIntExtra(AddHabit.EXTRA_ISDURATION,0);
            int habit_hour=data.getIntExtra(AddHabit.EXTRA_HOUR,0);
            int habit_goalValue=data.getIntExtra(AddHabit.EXTRA_GOALVALUE,0);
            String habit_unit=data.getStringExtra(AddHabit.EXTRA_GOALUNIT);
            int habit_goalReached=data.getIntExtra(AddHabit.EXTRA_GOALREACHED,0);
            int habit_totalCompleted=data.getIntExtra(AddHabit.EXTRA_TOTALCOMPLETED,0);
            int habit_isTimeCompleted=data.getIntExtra(AddHabit.EXTRA_ISTIMECOMPLETED,0);
            String habit_TimeCreated=data.getStringExtra(AddHabit.EXTRA_TIMECREATED);

            for(int i=0;i<habit_selectedDays.size();i++){
                HabitItem habitItem=new HabitItem(habit_name,habit_selectedDays.get(i),habit_isDuration,habit_hour,
                        habit_goalValue,habit_unit,habit_goalReached,habit_totalCompleted,habit_isTimeCompleted,habit_TimeCreated);
                habitItemViewModel.insert(habitItem);
            }
            HabitList habitList=new HabitList(habit_name,habit_totalCompleted);
            habitItemViewModel.insert(habitList);

            Toast.makeText(this,"Habit saved",Toast.LENGTH_SHORT).show();
        }
        else if(requestCode==UPDATE_HABIT_VALUE_REQUEST && resultCode==RESULT_OK){
            int tid=data.getIntExtra(AddHabit.EXTRA_ID,-1);
            if(tid==-1){
                Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show();
                return;
            }
            String thabit_name=data.getStringExtra(AddHabit.EXTRA_HABITNAME);
            int thabit_selectedDays=data.getIntExtra(AddHabit.EXTRA_SELECTEDDAYS,0);
            int thabit_isDuration=data.getIntExtra(AddHabit.EXTRA_ISDURATION,0);
            int thabit_hour=data.getIntExtra(AddHabit.EXTRA_HOUR,0);
            int thabit_goalValue=data.getIntExtra(AddHabit.EXTRA_GOALVALUE,0);
            String thabit_unit=data.getStringExtra(AddHabit.EXTRA_GOALUNIT);
            int thabit_reachedValue=data.getIntExtra(AddHabit.EXTRA_GOALREACHED,0);
            int thabit_totalCompleted=data.getIntExtra(AddHabit.EXTRA_TOTALCOMPLETED,0);
            int thabit_isTimeCompleted=data.getIntExtra(AddHabit.EXTRA_ISTIMECOMPLETED,0);
            String thabit_timeCreated=data.getStringExtra(AddHabit.EXTRA_TIMECREATED);
            if(thabit_reachedValue>=thabit_goalValue&&thabit_isTimeCompleted==0){
                HabitItemViewModel habitItemViewModel=new HabitItemViewModel(getApplication());
                thabit_totalCompleted+=1;
                SimpleDateFormat today_sdf=new SimpleDateFormat( "yyyy"+"-"+"MM"+"-"+"dd");
                String currentDate= today_sdf.format(new Date());
                HabitCompleted habitCompleted=new HabitCompleted(thabit_name,thabit_totalCompleted,currentDate,0);
                habitItemViewModel.insert(habitCompleted);
                HabitList habitList= new HabitList(thabit_name,thabit_totalCompleted+1);
                habitItemViewModel.update(habitList);
            }
            HabitItem habitItem=new HabitItem(thabit_name,thabit_selectedDays,thabit_isDuration,thabit_hour,thabit_goalValue,
                  thabit_unit,thabit_reachedValue,thabit_totalCompleted, thabit_isTimeCompleted,thabit_timeCreated);
            habitItem.setId(tid);
            habitItemViewModel.update(habitItem);

            Toast.makeText(this,"Value updated",Toast.LENGTH_SHORT).show();


        }
    }
}
