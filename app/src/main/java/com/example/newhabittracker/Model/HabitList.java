package com.example.newhabittracker.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "habitList_table")
public class HabitList {
    @PrimaryKey
    @NonNull
    private String habitName_column;
    private int totalCompleted_column;

    public HabitList(@NonNull String habitName_column, int totalCompleted_column) {
        this.habitName_column = habitName_column;
        this.totalCompleted_column = totalCompleted_column;
    }

    @NonNull
    public String getHabitName_column() {
        return habitName_column;
    }

    public void setHabitName_column(@NonNull String habitName_column) {
        this.habitName_column = habitName_column;
    }

    public int getTotalCompleted_column() {
        return totalCompleted_column;
    }

    public void setTotalCompleted_column(int totalCompleted_column) {
        this.totalCompleted_column = totalCompleted_column;
    }
}
