package com.example.newhabittracker.Service;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.newhabittracker.HabitClicked;

import static android.content.ContentValues.TAG;

public class MyService extends Service {
    private CountDownTimer countDownTimer;
    private long timeLeftMillis;

    public static final String EXTRA_TIMESERVICE=
            "com.example.myapplication.EXTRA_TIMESERVICE";
    Intent broadcast=new Intent(EXTRA_TIMESERVICE);

    @Nullable
    @Override

    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Intent intent = new Intent();
        timeLeftMillis=intent.getLongExtra(HabitClicked.EXTRA_TIMERTIME,0);
        countDownTimer=new CountDownTimer(timeLeftMillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftMillis=millisUntilFinished;
                Log.i(TAG,"Countdown seconds remaining"+millisUntilFinished/1000);
                broadcast.putExtra(MyService.EXTRA_TIMESERVICE,timeLeftMillis);
                sendBroadcast(broadcast);
            }

            @Override
            public void onFinish() {
                Log.i(TAG,"Timer finished");
            }
        }.start();

    }

    @Override
    public void onDestroy() {
        countDownTimer.cancel();
        Log.i(TAG,"Timer cancelled");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public IBinder onbind(Intent arg0){
        return null;
    }
    /*private void pauseTimer(){
        countDownTimer.cancel();
        is_timeCompleted=0;
        timerRunning=false;
        btn_playPause.setImageResource(R.drawable.playcircle);

    }*/

}
