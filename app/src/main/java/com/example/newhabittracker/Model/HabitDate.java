package com.example.newhabittracker.Model;

import androidx.room.ColumnInfo;

public class HabitDate {
    @ColumnInfo(name="date_column")
    public String habitDate;

    public String getHabitDate() {
        return habitDate;
    }

    public void setHabitDate(String habitDate) {
        this.habitDate = habitDate;
    }
}
