package com.example.newhabittracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class AddHabit extends AppCompatActivity {

    private ImageButton btn_return, btn_save;
    private TextView time_start, time_section, goal_section, hour_unit;
    private EditText habit_name, hour_duration,goal_value,goal_unit;
    private ChipGroup selected_days;
    private int hour_start, minute_start, temp_isDuration, temp_isReminder;
    private LinearLayout layout_time, layout_goal;
    private RadioGroup radioGroup;
    private  Switch reminder;
    private String temp_selectedDays="";
    private Spinner time_spinner;
    private ArrayList<Integer> list = new ArrayList<>(10);

    public static final String EXTRA_ID=
            "com.example.myapplication.EXTRA_ID";
    public static final String EXTRA_HABITNAME=
            "com.example.myapplication.EXTRA_HABITNAME";
    public static final String EXTRA_SELECTEDDAYS=
            "com.example.myapplication.EXTRA_SELECTEDDAYS";
    public static final String EXTRA_ISDURATION=
            "com.example.myapplication.EXTRA_ISDURATION";
    public static final String EXTRA_HOUR=
            "com.example.myapplication.EXTRA_HOUR";
    public static final String EXTRA_GOALVALUE=
            "com.example.myapplication.EXTRA_GOALVALUE";
    public static final String EXTRA_GOALUNIT=
            "com.example.myapplication.EXTRA_GOALUNIT";
    public static final String EXTRA_GOALREACHED=
            "com.example.myapplication.EXTRA_GOALREACHED";
    public static final String EXTRA_TOTALCOMPLETED=
            "com.example.myapplication.EXTRA_TOTALCOMPLETED";
    public static final String EXTRA_ISTIMECOMPLETED=
            "com.example.myapplication.EXTRA_ISTIMECOMPLETED";
    public static final String EXTRA_TIMECREATED=
            "com.example.myapplication.EXTRA_TIMECREATED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);
        initView();
        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnMain();
            }
        });

        //radioGroup to choose goal type
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radioButton_time:
                        time_section.setVisibility(View.VISIBLE);
                        layout_time.setVisibility(View.VISIBLE);
                        goal_section.setVisibility(View.GONE);
                        layout_goal.setVisibility(View.GONE);
                        temp_isDuration=1;
                        break;
                    case R.id.radioButton_goal:
                        goal_section.setVisibility(View.VISIBLE);
                        layout_goal.setVisibility(View.VISIBLE);
                        time_section.setVisibility(View.GONE);
                        layout_time.setVisibility(View.GONE);
                        temp_isDuration=0;
                        break;
                }

            }
        });

        //chip to select days(multiple choice)
        final int[] days = {0,1,2,3,4,5,6};
        for (int i = 0; i < selected_days.getChildCount(); i++) {
            final Chip chip = (Chip) selected_days.getChildAt(i);
            final int finalI = i;
            chip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        list.add(days[finalI]);
                    } else {
                        list.remove(days[finalI]);
                    }
                }
            });
        }

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveHabit();
            }
        });

    }


    void returnMain(){
        Intent return_main=new Intent(this,MainActivity.class);
        startActivity(return_main);
    }

    private void saveHabit() {
        int input_isDuration=temp_isDuration;
        int input_hour, input_goalValue;
        String input_unit;

        String input_name = habit_name.getText().toString();
        if (input_name.trim().isEmpty()) {
            Toast.makeText(this, "Please insert habit name", Toast.LENGTH_SHORT).show();
            //Toast.LENGTH_SHORT buat brp lama durasi message muncul
            return;
        }
        if(input_isDuration==1){
            input_hour = Integer.parseInt(hour_duration.getText().toString());
            input_goalValue= 0;
            input_unit=time_spinner.getSelectedItem().toString();
        }else{
            input_goalValue = Integer.parseInt(goal_value.getText().toString());
            input_hour=0;
            input_unit=goal_unit.getText().toString();
        }

        int input_goalReached=0, input_totalCompleted=0, input_isTimeCompleted=0;

        SimpleDateFormat today_sdf=new SimpleDateFormat("dd"+"MMMM"+"yyyy");
        String input_timeCreated= today_sdf.format(new Date());

        Intent data= new Intent();
        data.putExtra(EXTRA_HABITNAME,input_name);
        data.putIntegerArrayListExtra(EXTRA_SELECTEDDAYS,list);
        data.putExtra(EXTRA_ISDURATION,input_isDuration);
        data.putExtra(EXTRA_HOUR,input_hour);
        data.putExtra(EXTRA_GOALVALUE,input_goalValue);
        data.putExtra(EXTRA_GOALUNIT,input_unit);
        data.putExtra(EXTRA_GOALREACHED,input_goalReached);
        data.putExtra(EXTRA_TOTALCOMPLETED,input_totalCompleted);
        data.putExtra(EXTRA_ISTIMECOMPLETED,input_isTimeCompleted);
        data.putExtra(EXTRA_TIMECREATED,input_timeCreated);

        int id=getIntent().getIntExtra(EXTRA_ID,-1);
        if(id!=-1){
            data.putExtra(EXTRA_ID,id);
        }

        setResult(RESULT_OK,data);
        finish();

    }

    void initView(){
        //gimmick layout
        btn_return=findViewById(R.id.btn_return);
        btn_save=findViewById(R.id.btn_save);
        time_section=findViewById(R.id.textView_time);
        goal_section=findViewById(R.id.textView_goal);
        layout_time=findViewById(R.id.linearLayout_time);
        layout_goal=findViewById(R.id.linearLayout_goal);

        //item
        habit_name=findViewById(R.id.editText_name);
        selected_days=findViewById(R.id.chipGroup_days);
        radioGroup=findViewById(R.id.radioGroup);
        hour_duration=findViewById(R.id.editText_durationVal);
        time_spinner=findViewById(R.id.addhabit_spinner);
        goal_value=findViewById(R.id.editText_goalValue);
        goal_unit=findViewById(R.id.editText_goalUnit);


    }
}