package com.example.newhabittracker.Database;
import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.newhabittracker.Model.HabitList;
import com.example.newhabittracker.Model.HabitCompleted;
import com.example.newhabittracker.Model.HabitItem;

@Database(entities = {HabitItem.class, HabitCompleted.class, HabitList.class},
            version = 19)//{Data1.class,Data2.class} kalo mau lbh dri 1 database
//@TypeConverters({Converters.class})

public abstract class HabitItemDatabase extends RoomDatabase{
    private static HabitItemDatabase instance;//static singleton agar cm bs pke instance ini doang
    public abstract HabitItemDAO habitItemDAO();
    //synchronized hnya 1 thread bs access instance di bwh ini per time
    public static synchronized HabitItemDatabase getInstance(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(),
                    HabitItemDatabase.class ,"habitItem_database").fallbackToDestructiveMigration()
                    .addCallback(roomCallback).build();
        }
        return instance;
    }

    //bkin data di dlm database biar bisa keliatan di  recycleview
    private static RoomDatabase.Callback roomCallback=new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBAsyncTask(instance).execute();
        }
    };

    private static class PopulateDBAsyncTask extends AsyncTask<Void,Void,Void>{
        private HabitItemDAO habitItemDAO;
        private PopulateDBAsyncTask(HabitItemDatabase db){
            habitItemDAO=db.habitItemDAO();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            habitItemDAO.insert(new HabitItem("Study",6,1,2,
                    0,null,0,0,0,"11:00"));
            habitItemDAO.insert(new HabitCompleted("Study",0,"0000-00-00",0));
            return null;
        }
    }

}


