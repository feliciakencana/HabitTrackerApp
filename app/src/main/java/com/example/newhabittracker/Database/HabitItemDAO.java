package com.example.newhabittracker.Database;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.newhabittracker.Model.HabitList;
import com.example.newhabittracker.Model.HabitCompleted;
import com.example.newhabittracker.Model.HabitDate;
import com.example.newhabittracker.Model.HabitItem;

import java.util.List;
import java.util.concurrent.ExecutionException;

//interface
@Dao
public interface HabitItemDAO {


    //table habitItem
    @Insert
    void insert(HabitItem habitItem);
    @Update
    void update(HabitItem habitItem);
    @Delete
    void delete(HabitItem habitItem);

    @Insert
    void insert(HabitCompleted habitCompleted);
    @Update
    void update(HabitCompleted habitCompleted);
    @Delete
    void delete(HabitCompleted habitCompleted);

    @Insert
    void insert(HabitList habitList);
    @Update
    void update(HabitList habitList);
    @Delete
    void delete(HabitList habitList);



    @Query("Update habitItem_table Set goalReached_column=0,  isTimeComplete_column=0")
            void resetProgress();


    @Query("SELECT * FROM habitItem_table WHERE strftime('%w','now') = selectedDays_column")
            LiveData<List<HabitItem>> getTodayData();


    @Query("SELECT * FROM habitItem_table WHERE selectedDays_column = :num ORDER BY habitName_column ASC")
    LiveData<List<HabitItem>>getChosenDay(int num);

    @Query("SELECT * from habitCompleted_table")
    LiveData<List<HabitCompleted>>getHabitCompleted();

    @Query("SELECT * FROM habitList_table")
    LiveData<List<HabitList>>getHabitList();

    @Query("SELECT date_column FROM habitCompleted_table WHERE habitName_column = :habitName")
    List<HabitDate> getDate(String habitName) throws ExecutionException, InterruptedException;




}


