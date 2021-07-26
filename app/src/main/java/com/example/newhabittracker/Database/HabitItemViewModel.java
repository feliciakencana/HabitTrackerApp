package com.example.newhabittracker.Database;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.newhabittracker.Model.HabitList;
import com.example.newhabittracker.Model.HabitCompleted;
import com.example.newhabittracker.Model.HabitDate;
import com.example.newhabittracker.Model.HabitItem;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class HabitItemViewModel extends AndroidViewModel{
    private HabitItemRepository repository;
    private LiveData<List<HabitItem>> chosenDay,todayData;
    private LiveData<List<HabitCompleted>> habitCompleted;
    private  List<HabitDate> getDate;
    private LiveData<List<HabitList>> getHabitList;


    public HabitItemViewModel(@NonNull Application application) {
        super(application);
        repository=new HabitItemRepository(application);
        todayData=repository.getTodayData();
        habitCompleted =repository.getHabitCompleted();
        getHabitList=repository.getHabitList();

    }
    public void insert(HabitItem habitItem){
        repository.insert(habitItem);
    }


    public void update(HabitItem habitItem){
        repository.update(habitItem);
    }

    public void delete(HabitItem habitItem){
        repository.delete(habitItem);
    }

    //HabitCompleted
    public void insert(HabitCompleted habitCompleted){
        repository.insert(habitCompleted);
    }


    public void update(HabitCompleted habitCompleted){
        repository.update(habitCompleted);
    }

    public void delete(HabitCompleted habitCompleted){
        repository.delete(habitCompleted);
    }

    //HabitList
    public void insert(HabitList habitList){
        repository.insert(habitList);
    }

    public void update(HabitList habitList){
        repository.update(habitList);
    }

    public void delete(HabitList habitList){
        repository.delete(habitList);
    }

    public void resetProgress(){
        repository.resetProgress();
    }




    public LiveData<List<HabitItem>>getTodayData(){
        return todayData;
    }

    public LiveData<List<HabitList>>getHabitList(){
        return getHabitList;
    }

    public LiveData<List<HabitCompleted>>getHabitCompleted(){
        return habitCompleted;
    }

    public LiveData<List<HabitItem>>getChosenDay(int nums){
        chosenDay=repository.getChosenDay(nums);
        return chosenDay;
    }

    public List<HabitDate> getDate(String habitName) throws ExecutionException, InterruptedException {
        getDate=repository.getDate(habitName);
        return getDate;
    }


}
