package com.example.adinjector;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

public class StealthService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Har 5 minute baad dobara chalne ke liye alarm set karein
        scheduleNextRun();

        return START_STICKY;
    }

    private void scheduleNextRun() {
        Intent alarmIntent = new Intent(this, StealthService.class);
        PendingIntent pendingIntent = PendingIntent.getService(
                this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            // 5 minutes = 300,000 milliseconds
            long triggerTime = SystemClock.elapsedRealtime() + (5 * 60 * 1000);
            
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, pendingIntent);
            } else {
                alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, pendingIntent);
            }
        }
    }
}
