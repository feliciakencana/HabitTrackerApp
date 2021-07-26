package com.example.newhabittracker.Model;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "habitCompleted_table")
public class HabitCompleted {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String habitName_column;
    private int  totalCompleted_column;
    private String date_column;
    private int streak_column;


    public HabitCompleted(String habitName_column,int totalCompleted_column, String date_column, int streak_column){
        this.habitName_column=habitName_column;
        this.totalCompleted_column=totalCompleted_column;
        this.date_column=date_column;
        this.streak_column=streak_column;

    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getDate_column() {
        return date_column;
    }

    public int getStreak_column() {
        return streak_column;
    }

    public String getHabitName_column(){
        return habitName_column;
    }


    public int getTotalCompleted_column(){
        return totalCompleted_column;
    }

}


