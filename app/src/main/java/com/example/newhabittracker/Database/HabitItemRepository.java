package com.example.newhabittracker.Database;
import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.newhabittracker.Model.HabitList;
import com.example.newhabittracker.Model.HabitCompleted;
import com.example.newhabittracker.Model.HabitDate;
import com.example.newhabittracker.Model.HabitItem;

import java.util.List;
import java.util.concurrent.ExecutionException;


public class HabitItemRepository implements HabitItemDAO {
    //private UpdateChooseDays;
    private HabitItemDAO habitItemDAO;
    private LiveData<List<HabitCompleted>> goalData;
    private LiveData<List<HabitItem>> chosenDay,todayData;
    private LiveData<List<HabitList>>getHabitList;
    private  List<HabitDate> getDate;
    private HabitItemDatabase habitItemDatabase;



    public HabitItemRepository(Application application) {
        habitItemDatabase = HabitItemDatabase.getInstance(application);
        habitItemDAO = habitItemDatabase.habitItemDAO();
        todayData= habitItemDAO.getTodayData();
        goalData=habitItemDAO.getHabitCompleted();
        getHabitList=habitItemDAO.getHabitList();
    }
    //HabitItem
    public void insert(HabitItem habitItem) {
        new InsertDataAsyncTask(habitItemDAO).execute(habitItem);
    }

    public void update(HabitItem habitItem) {
        new UpdateDataAsyncTask(habitItemDAO).execute(habitItem);
    }

    public void delete(HabitItem habitItem) {
        new DeleteDataAsyncTask(habitItemDAO).execute(habitItem);
    }

    //HabitCompleted
    public void insert(HabitCompleted habitCompleted) {
        new InsertDataAsyncTask2(habitItemDAO).execute(habitCompleted);
    }

    public void update(HabitCompleted habitCompleted) {
        new UpdateDataAsyncTask2(habitItemDAO).execute(habitCompleted);
    }

    public void delete(HabitCompleted habitCompleted) {
        new DeleteDataAsyncTask2(habitItemDAO).execute(habitCompleted);
    }

    @Override
    public void insert(HabitList habitList) {
        new InsertDataAsyncTask3(habitItemDAO).execute(habitList);
    }

    @Override
    public void update(HabitList habitList) {
        new UpdateDataAsyncTask3(habitItemDAO).execute(habitList);
    }

    @Override
    public void delete(HabitList habitList) {
        new UpdateDataAsyncTask3(habitItemDAO).execute(habitList);
    }

    @Override
    public void resetProgress() {
        new ResetProgressAsyncTask(habitItemDAO).execute();
    }


    public LiveData<List<HabitItem>> getTodayData() {
        return todayData;
    }


    @Override
    public LiveData<List<HabitItem>>getChosenDay(int nums){
        chosenDay=habitItemDAO.getChosenDay(nums);
        return chosenDay;
    }

    public LiveData<List<HabitCompleted>>getHabitCompleted(){
        return goalData;
    }

    @Override
    public LiveData<List<HabitList>> getHabitList() {
        return getHabitList;
    }


    public List<HabitDate> getDate(String habitName) throws ExecutionException, InterruptedException {
        return new GetDateAsyncTask(habitItemDatabase,habitName).execute().get();
    }


    private static class InsertDataAsyncTask extends AsyncTask<HabitItem, Void, Void> {
        private HabitItemDAO habitItemDAO;

        private InsertDataAsyncTask(HabitItemDAO habitItemDAO) {
            this.habitItemDAO = habitItemDAO;
        }

        @Override
        protected Void doInBackground(HabitItem... habitItems) {
            habitItemDAO.insert(habitItems[0]);
            return null;
        }
    }

    private static class UpdateDataAsyncTask extends AsyncTask<HabitItem, Void, Void> {
        private HabitItemDAO habitItemDAO;

        private UpdateDataAsyncTask(HabitItemDAO habitItemDAO) {
            this.habitItemDAO = habitItemDAO;
        }

        @Override
        protected Void doInBackground(HabitItem... habitItems) {
            habitItemDAO.update(habitItems[0]);
            return null;
        }
    }

    private static class DeleteDataAsyncTask extends AsyncTask<HabitItem, Void, Void> {
        private HabitItemDAO habitItemDAO;

        private DeleteDataAsyncTask(HabitItemDAO habitItemDAO) {
            this.habitItemDAO = habitItemDAO;
        }

        @Override
        protected Void doInBackground(HabitItem... habitItems) {
            habitItemDAO.delete(habitItems[0]);
            return null;
        }
    }

    private static class InsertDataAsyncTask2 extends AsyncTask<HabitCompleted, Void, Void> {
        private HabitItemDAO habitItemDAO;

        private InsertDataAsyncTask2(HabitItemDAO habitItemDAO) {
            this.habitItemDAO = habitItemDAO;
        }

        @Override
        protected Void doInBackground(HabitCompleted... habitCompleteds) {
            habitItemDAO.insert(habitCompleteds[0]);
            return null;
        }
    }

    private static class UpdateDataAsyncTask2 extends AsyncTask<HabitCompleted, Void, Void> {
        private HabitItemDAO habitItemDAO;

        private UpdateDataAsyncTask2(HabitItemDAO habitItemDAO) {
            this.habitItemDAO = habitItemDAO;
        }

        @Override
        protected Void doInBackground(HabitCompleted... habitCompleteds) {
            habitItemDAO.update(habitCompleteds[0]);
            return null;
        }
    }

    private static class DeleteDataAsyncTask2 extends AsyncTask<HabitCompleted, Void, Void> {
        private HabitItemDAO habitItemDAO;

        private DeleteDataAsyncTask2(HabitItemDAO habitItemDAO) {
            this.habitItemDAO = habitItemDAO;
        }

        @Override
        protected Void doInBackground(HabitCompleted... habitCompleteds) {
            habitItemDAO.delete(habitCompleteds[0]);
            return null;
        }
    }

    private static class InsertDataAsyncTask3 extends AsyncTask<HabitList, Void, Void> {
        private HabitItemDAO habitItemDAO;

        private InsertDataAsyncTask3(HabitItemDAO habitItemDAO) {
            this.habitItemDAO = habitItemDAO;
        }

        @Override
        protected Void doInBackground(HabitList... habitLists) {
            habitItemDAO.insert(habitLists[0]);
            return null;
        }
    }

    private static class UpdateDataAsyncTask3 extends AsyncTask<HabitList, Void, Void> {
        private HabitItemDAO habitItemDAO;

        private UpdateDataAsyncTask3(HabitItemDAO habitItemDAO) {
            this.habitItemDAO = habitItemDAO;
        }

        @Override
        protected Void doInBackground(HabitList... habitLists) {
            habitItemDAO.update(habitLists[0]);
            return null;
        }
    }

    private static class DeleteDataAsyncTask3 extends AsyncTask<HabitList, Void, Void> {
        private HabitItemDAO habitItemDAO;

        private DeleteDataAsyncTask3(HabitItemDAO habitItemDAO) {
            this.habitItemDAO = habitItemDAO;
        }

        @Override
        protected Void doInBackground(HabitList... habitLists) {
            habitItemDAO.delete(habitLists[0]);
            return null;
        }
    }


    private static class ResetProgressAsyncTask extends AsyncTask<HabitItem,Void,Void>{
        private HabitItemDAO habitItemDAO;

        private ResetProgressAsyncTask(HabitItemDAO habitItemDAO){
            this.habitItemDAO=habitItemDAO;
        }

        @Override
        protected Void doInBackground(HabitItem... habitItems) {
            habitItemDAO.resetProgress();
            return null;
        }
    }

    private static class GetDateAsyncTask extends AsyncTask<Void,Void,List<HabitDate>>{
        private HabitItemDatabase habitItemDatabase;
        private String habitName;


        private GetDateAsyncTask(HabitItemDatabase habitItemDatabase, String habitName){
            this.habitItemDatabase=habitItemDatabase;
            this.habitName=habitName;
        }

        @Override
        protected List<HabitDate> doInBackground(Void... voids) {
            List<HabitDate> date = null;
            try {
                date = habitItemDatabase.habitItemDAO().getDate(habitName);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return date;
        }
    }





}
