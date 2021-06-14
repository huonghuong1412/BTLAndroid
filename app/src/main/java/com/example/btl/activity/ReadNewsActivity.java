package com.example.btl.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.btl.R;

public class ReadNewsActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_news);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setupUI();
        Intent intent = getIntent();
        String link = intent.getStringExtra("link");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(link);
        webView.setWebViewClient(new MyWebViewClient());
    }

    private void setupUI() {
        webView = findViewById(R.id.webView);
    }

}

class MyWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view,  String link) {
        return false;
    }
}