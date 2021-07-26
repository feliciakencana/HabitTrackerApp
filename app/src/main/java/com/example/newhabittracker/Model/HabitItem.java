package com.example.newhabittracker.Model;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "habitItem_table")
public class HabitItem {
    @PrimaryKey(autoGenerate = true)
    private int id; //primary key
    private String habitName_column;
    private Integer selectedDays_column;
    private int isDuration_column;
    private int targetedHour_column;
    private int targetedValue_column;
    private String valueUnit_column;
    private int goalReached_column;
    private int totalCompleted_column;
    private int isTimeComplete_column;
    private String timeCreated_column;

    public HabitItem(String habitName_column, Integer selectedDays_column, int isDuration_column, int targetedHour_column,
                     int targetedValue_column, String valueUnit_column,
                     int goalReached_column, int totalCompleted_column, int isTimeComplete_column,String timeCreated_column){
        this.habitName_column=habitName_column;
        this.selectedDays_column=selectedDays_column;
        this.isDuration_column=isDuration_column;
        this.targetedHour_column=targetedHour_column;
        this.targetedValue_column=targetedValue_column;
        this.valueUnit_column=valueUnit_column;
        this.goalReached_column=goalReached_column;
        this.totalCompleted_column=totalCompleted_column;
        this.isTimeComplete_column = isTimeComplete_column;
        this.timeCreated_column=timeCreated_column;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getHabitName_column(){
        return habitName_column;
    }

    public Integer getSelectedDays_column(){
        return selectedDays_column;
    }

    public int getIsDuration_column(){
        return isDuration_column;
    }

    public int getTargetedHour_column(){
        return targetedHour_column;
    }

    public int getTargetedValue_column(){
        return targetedValue_column;
    }

    public String getValueUnit_column(){
        return valueUnit_column;
    }

    public int getGoalReached_column(){
        return goalReached_column;
    }

    public int getTotalCompleted_column(){
        return totalCompleted_column;
    }

    public int getIsTimeComplete_column(){
        return isTimeComplete_column;
    }

    public String getTimeCreated_column(){
        return timeCreated_column;
    }

}
