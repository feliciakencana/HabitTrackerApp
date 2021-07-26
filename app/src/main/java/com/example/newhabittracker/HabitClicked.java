package com.example.newhabittracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newhabittracker.Database.HabitItemViewModel;
import com.example.newhabittracker.Model.HabitItem;
import com.example.newhabittracker.Service.MyService;
import com.example.newhabittracker.Service.ResetService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.ContentValues.TAG;

public class HabitClicked extends AppCompatActivity {

    private String habit_name;
    private String goal_unit;
    private String time_created;
    private String date;
    private LiveData<String> testDate;
    private int goal_reached, goal_targeted,is_duration,id,selected_days,temp_inputGoal,is_reminder,total_completed,
    habit_hour, is_timeCompleted,streak;
    private TextView txtView_habitName,txtView_habitUnit,txtView_goalOrDuration,
            txtView_goalReached,txtView_goalTarget,txtView_habitUnitInput, txtView_timer, txtView_hourTarget,txtView_hourUnit;
    private EditText inputted_goal;
    private ImageButton btn_submit, btn_playPause, btn_delete, btn_return;
    private LinearLayout display_goal, display_time,input_goal;
    private FrameLayout input_time;

    private long timeLeftMillis, timeEnd;
    private CountDownTimer countDownTimer;
    private boolean timerRunning;
    private HabitItemViewModel habitItemViewModel;
    private AlarmManager alarmManager;
    private PendingIntent pi;



    public static final String EXTRA_TIMERTIME=
            "com.example.myapplication.EXTRA_TIMERTIME";
    public static final String EXTRA_DATE=
            "com.example.myapplication.EXTRA_DATE";
    public static final String EXTRA_STREAK=
            "com.example.myapplication.EXTRA_STREAK";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_clicked);
        initView();
        startService(new Intent(this, MyService.class));
        Log.i(TAG,"Service started");
        habitItemViewModel= new ViewModelProvider(this).get(HabitItemViewModel.class);
        Intent intent=getIntent();
        if(intent.hasExtra(AddHabit.EXTRA_ID)){
            habit_name=intent.getStringExtra(AddHabit.EXTRA_HABITNAME);
            selected_days=intent.getIntExtra(AddHabit.EXTRA_SELECTEDDAYS,0);
            habit_hour=intent.getIntExtra(AddHabit.EXTRA_HOUR,0);
            goal_targeted=intent.getIntExtra(AddHabit.EXTRA_GOALVALUE,0);
            goal_unit=intent.getStringExtra(AddHabit.EXTRA_GOALUNIT);
            is_duration=intent.getIntExtra(AddHabit.EXTRA_ISDURATION,0);
            goal_reached=intent.getIntExtra(AddHabit.EXTRA_GOALREACHED,0);
            total_completed=intent.getIntExtra(AddHabit.EXTRA_TOTALCOMPLETED,0);
            is_timeCompleted=intent.getIntExtra(AddHabit.EXTRA_ISTIMECOMPLETED,0);
            time_created=intent.getStringExtra(AddHabit.EXTRA_TIMECREATED);


            txtView_habitName.setText(habit_name);
            txtView_goalReached.setText(Integer.toString(goal_reached));
            txtView_goalTarget.setText(Integer.toString(goal_targeted));
            txtView_habitUnit.setText(goal_unit);
            txtView_habitUnitInput.setText(goal_unit);
            txtView_hourUnit.setText(goal_unit);
            txtView_hourTarget.setText(Long.toString(habit_hour));

            if(is_duration==1){
                display_time.setVisibility(View.VISIBLE);
                input_time.setVisibility(View.VISIBLE);
                btn_playPause.setVisibility(View.VISIBLE);

                display_goal.setVisibility(View.GONE);
                input_goal.setVisibility(View.GONE);
                btn_submit.setVisibility(View.GONE);

            }else{
                display_goal.setVisibility(View.VISIBLE);
                input_goal.setVisibility(View.VISIBLE);
                btn_submit.setVisibility(View.VISIBLE);

                display_time.setVisibility(View.GONE);
                input_time.setVisibility(View.GONE);
                btn_playPause.setVisibility(View.GONE);

            }
        }

        //countdown timer
        if(goal_unit.equals("Hour")){
            timeLeftMillis=habit_hour*3600000;
        }
        else if(goal_unit.equals("Minute")){
            timeLeftMillis=habit_hour*60000;
        }
        Date currentTime= Calendar.getInstance().getTime();
        Date deadline;
        SimpleDateFormat sdf=new SimpleDateFormat("HH"+":"+"mm"+":"+"ss");
        try {
            deadline= sdf.parse("00:00:00");
            if(deadline==currentTime){
                is_timeCompleted=0;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        btn_playPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timerRunning){
                    pauseTimer();
                }else{
                    startTimer();
                    Intent timerIntent= new Intent(HabitClicked.this,MyService.class);
                    timerIntent.putExtra(HabitClicked.EXTRA_TIMERTIME,timeLeftMillis);
                    startService(timerIntent);
                }

            }
        });
        updateCountdownText();

        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               confirmationDialog();
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               deleteProgress();
               finish();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateProgress();
            }
        });



    }

    void confirmationDialog(){
        if(timerRunning==true){
            AlertDialog.Builder builder= new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to quit?").setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            returnToMain();
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();
        }else{
            returnToMain();
        }
    }

    @Override
    public void onBackPressed() {
        if(timerRunning==true){
            AlertDialog.Builder builder= new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to quit?").setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            HabitClicked.super.onBackPressed();
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();
        }
    }

    private void updateGUI(Intent intent){
        if(intent.getExtras()!=null){
            long timeleft=intent.getLongExtra(MyService.EXTRA_TIMESERVICE,0);
            Log.i(TAG,"Countdown remaining:"+timeleft);
        }
    }

    private BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
           updateCountdownText();
           updateGUI(intent);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver,new IntentFilter(MyService.EXTRA_TIMESERVICE));
    }

    @Override
    protected void onStop() {
        try {
            unregisterReceiver(receiver);
        }catch (Exception e){

        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this,MyService.class));
        super.onDestroy();
    }

    private void returnToMain(){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    private void startTimer(){
        countDownTimer=new CountDownTimer(timeLeftMillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftMillis=millisUntilFinished;
                is_timeCompleted=0;
                updateCountdownText();
            }

            @Override
            public void onFinish() {
                timerRunning=false;
                temp_inputGoal=1;
                is_timeCompleted=habit_hour;
                btn_playPause.setVisibility(View.GONE);
                btn_submit.setVisibility(View.VISIBLE);
            }
        }.start();
        timerRunning=true;
        temp_inputGoal=0;
        is_timeCompleted=0;
        btn_playPause.setImageResource(R.drawable.pausecircle);
    }

    private void pauseTimer(){
        countDownTimer.cancel();
        is_timeCompleted=0;
        timerRunning=false;
        btn_playPause.setImageResource(R.drawable.playcircle);

    }

    private void updateCountdownText(){
        int hours=(int)(timeLeftMillis/1000)/60/60;
        int minutes=(int) (timeLeftMillis/1000)/60%60;
        int seconds=(int) (timeLeftMillis/1000)%60;

        String timeleftFormat=String.format(Locale.getDefault(),"%02d:%02d:%02d",hours,minutes,seconds);
        txtView_timer.setText(timeleftFormat);
    }

    /*@Override
    protected void onStop() {
        super.onStop();
        SharedPreferences prefs=getSharedPreferences("prefs",MODE_PRIVATE);
        SharedPreferences.Editor editor=prefs.edit();

        editor.putLong("timeLeftMillis",timeLeftMillis);
        editor.putBoolean("timeRunning",timerRunning);
        editor.putLong("endTime",timeEnd);
        editor.apply();
        if(countDownTimer!=null){
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences prefs=getSharedPreferences("prefs",MODE_PRIVATE);
        timeLeftMillis=prefs.getLong("timeLeftMillis",timeLeftMillis);
        timerRunning=prefs.getBoolean("timerRunning",false);
        updateCountdownText();

        if(timerRunning){
            timeEnd=prefs.getLong("endTime",0);
            timeLeftMillis=timeEnd-System.currentTimeMillis();

            if(timeLeftMillis<0){
                timeLeftMillis=0;
                timerRunning=false;
                updateCountdownText();
            }else{
                startTimer();
            }
        }
    }*/

    private void deleteProgress(){
        HabitItem habitItem=new HabitItem(habit_name,selected_days,is_duration,habit_hour,goal_targeted,
                goal_unit,goal_reached,total_completed,is_timeCompleted,time_created);
        id=getIntent().getIntExtra(AddHabit.EXTRA_ID,-1);
        habitItem.setId(id);
        habitItemViewModel.delete(habitItem);
        Toast.makeText(this,"Habit deleted",Toast.LENGTH_SHORT).show();
    }

    private void UpdateProgress(){
        //int input_id=id;
        String input_name=txtView_habitName.getText().toString();
        int input_reachedValue=0;
        int input_completed=temp_inputGoal+total_completed;
        int input_isTimeCompleted=is_timeCompleted;
        if(input_isTimeCompleted==0){
            input_reachedValue=Integer.parseInt(inputted_goal.getText().toString())+goal_reached;
        }

        //String lastDate= habitItemViewModel.getDate(input_name);


        Intent data=new Intent();
        data.putExtra(AddHabit.EXTRA_HABITNAME,input_name);
        data.putExtra(AddHabit.EXTRA_SELECTEDDAYS,selected_days);
        data.putExtra(AddHabit.EXTRA_HOUR,habit_hour);
        data.putExtra(AddHabit.EXTRA_GOALVALUE,goal_targeted);
        data.putExtra(AddHabit.EXTRA_GOALUNIT,goal_unit);
        data.putExtra(AddHabit.EXTRA_ISDURATION,is_duration);
        data.putExtra(AddHabit.EXTRA_GOALREACHED,input_reachedValue);
        data.putExtra(AddHabit.EXTRA_TOTALCOMPLETED, input_completed);
        data.putExtra(AddHabit.EXTRA_ISTIMECOMPLETED, input_isTimeCompleted);
        data.putExtra(AddHabit.EXTRA_TIMECREATED,time_created);

        id=getIntent().getIntExtra(AddHabit.EXTRA_ID,-1);
        if(id!=-1){
            data.putExtra(AddHabit.EXTRA_ID,id);
        }
        setResult(RESULT_OK,data);
        finish();
    }

    private void resetProgress(){
        //reset goal, alarm manager
        //error pindah ke main activity
        String input_name=txtView_habitName.getText().toString();
        int input_reachedValue=goal_reached;
        int input_completed=total_completed;
        int input_isTimeCompleted=is_timeCompleted;

        Intent resetIntent=new Intent(HabitClicked.this, ResetService.class);
        resetIntent.putExtra(AddHabit.EXTRA_HABITNAME,input_name);
        resetIntent.putExtra(AddHabit.EXTRA_SELECTEDDAYS,selected_days);
        resetIntent.putExtra(AddHabit.EXTRA_HOUR,habit_hour);
        resetIntent.putExtra(AddHabit.EXTRA_GOALVALUE,goal_targeted);
        resetIntent.putExtra(AddHabit.EXTRA_GOALUNIT,goal_unit);
        resetIntent.putExtra(AddHabit.EXTRA_ISDURATION,is_duration);
        resetIntent.putExtra(AddHabit.EXTRA_GOALREACHED,input_reachedValue);
        resetIntent.putExtra(AddHabit.EXTRA_TOTALCOMPLETED, input_completed);
        resetIntent.putExtra(AddHabit.EXTRA_ISTIMECOMPLETED, input_isTimeCompleted);
        resetIntent.putExtra(AddHabit.EXTRA_TIMECREATED,time_created);
        id=getIntent().getIntExtra(AddHabit.EXTRA_ID,-1);
        if(id!=-1){
            resetIntent.putExtra(AddHabit.EXTRA_ID,id);
        }
        PendingIntent pi=PendingIntent.getService(this,0,resetIntent,0);
        alarmManager=(AlarmManager)this.getSystemService(ALARM_SERVICE);
        Calendar calendar=Calendar.getInstance();
        //calendar.set(Calendar.HOUR_OF_DAY,24);
        calendar.set(Calendar.MINUTE,1);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);

        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_FIFTEEN_MINUTES,pi);
        //final int time=86400;
        if(alarmManager!=null){
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_FIFTEEN_MINUTES,pi);
        }
    }


    void initView(){
        txtView_habitName=findViewById(R.id.txtView_habitName);
        txtView_goalOrDuration=findViewById(R.id.txtView_titleGoal);
        txtView_goalReached=findViewById(R.id.txtView_goalValue_achieved);
        txtView_goalTarget=findViewById(R.id.txtView_goalValue_target);
        txtView_habitUnit=findViewById(R.id.txtView_goalUnit);
        txtView_habitUnitInput=findViewById(R.id.txtView_goalUnitInput);
        inputted_goal=findViewById(R.id.editText_inputGoalValue);
        btn_submit=findViewById(R.id.btn_submit);
        display_goal=findViewById(R.id.displayGoal_layout);
        display_time=findViewById(R.id.displayTime_layout);
        input_goal=findViewById(R.id.inputGoal_layout);
        input_time=findViewById(R.id.timer_layout);
        btn_playPause=findViewById(R.id.btn_playPause);
        txtView_timer=findViewById(R.id.txtView_Timer);
        txtView_hourTarget=findViewById(R.id.txtView_time_target);
        txtView_hourUnit=findViewById(R.id.txtView_timeUnit);
        btn_delete=findViewById(R.id.btn_delete);
        btn_return=findViewById(R.id.btn_return);
    }
}