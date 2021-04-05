package itstudy.kakao.adapterview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class LinkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link);

        WebView webview = (WebView)findViewById(R.id.webview);
                webview.setWebViewClient(new WebViewClient());
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);

        Intent intent = getIntent();
        String link = intent.getStringExtra("link");
        webview.loadUrl(link);
    }
}