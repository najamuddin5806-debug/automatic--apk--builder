package com.example.adinjector;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // WebView create kar rahe hain taaki link app ke andar chale
        webView = new WebView(this);
        setContentView(webView);

        // JavaScript aur storage enable karna zaroori hai ads load hone ke liye
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        webView.setWebViewClient(new WebViewClient());

        // Aapka Adsterra Direct Link
        webView.loadUrl("https://www.profitableratecpmnetwork.com/hdk2jkb1tq?key=1811747717fba27cc8d799ee6913935e");
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
