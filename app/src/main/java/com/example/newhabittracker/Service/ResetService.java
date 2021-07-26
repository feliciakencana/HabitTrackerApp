package com.example.newhabittracker.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.newhabittracker.Database.HabitItemViewModel;

public class ResetService extends Service {
    private HabitItemViewModel habitItemViewModel;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        habitItemViewModel= new HabitItemViewModel(getApplication());
        habitItemViewModel.resetProgress();
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}
