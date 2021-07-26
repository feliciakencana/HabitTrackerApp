package com.example.newhabittracker.Service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class Receiver extends BroadcastReceiver {

    private static final String TAG_BOOT_BROADCAST_RECEIVER = "BOOT_BROADCAST_RECEIVER";

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();

        String message = "BootDeviceReceiver onReceive, action is " + action;

        Toast.makeText(context, message, Toast.LENGTH_LONG).show();

        Log.d(TAG_BOOT_BROADCAST_RECEIVER, action);

        if(Intent.ACTION_BOOT_COMPLETED.equals(action))
        {
            //startServiceDirectly(context);

            startServiceByAlarm(context);
        }
    }


    /* Create an repeat Alarm that will invoke the background service for each execution time.
     * The interval time can be specified by your self.  */
    private void startServiceByAlarm(Context context)
    {
        // Get alarm manager.
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        // Create intent to invoke the background service.
        Intent intent = new Intent(context, ResetService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        long startTime = System.currentTimeMillis();
        long intervalTime = 60*1000;

        String message = "Start service use repeat alarm. ";

        Toast.makeText(context, message, Toast.LENGTH_LONG).show();

        Log.d(TAG_BOOT_BROADCAST_RECEIVER, message);

        // Create repeat alarm.
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, startTime, intervalTime, pendingIntent);
    }
}