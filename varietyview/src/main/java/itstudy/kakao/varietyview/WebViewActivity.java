package itstudy.kakao.varietyview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class WebViewActivity extends AppCompatActivity {
    WebView webView;
    EditText address;
    Button btngo, btnback, btnforward, btnlocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        address = (EditText) findViewById(R.id.address);
        btngo = (Button) findViewById(R.id.btngo);
        btnback = (Button) findViewById(R.id.btnback);
        btnforward = (Button) findViewById(R.id.btnforward);
        btnlocal = (Button) findViewById(R.id.btnlocal);

        webView = (WebView) findViewById(R.id.web);


        webView.setWebViewClient(new WebViewClient());
        WebSettings set = webView.getSettings();
        set.setJavaScriptEnabled(true);
        set.setBuiltInZoomControls(true);
        webView.loadUrl("http://www.google.com");

        Button.OnClickListener handler = new Button.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btngo:
                        String url;
                        EditText addr = (EditText) findViewById(R.id.address);
                        url = addr.getText().toString();
                        webView.loadUrl(url);
                        break;
                    case R.id.btnback:
                        if (webView.canGoBack()) {
                            webView.goBack();
                        }
                        break;
                    case R.id.btnforward:
                        if (webView.canGoForward()) {
                            webView.goForward();
                        }
                        break;
                    case R.id.btnlocal:
                        webView.loadUrl("file:///android_asset/test.html");
                        break;
                }
            }
        };

        btngo.setOnClickListener(handler);
        btnback.setOnClickListener(handler);
        btnforward.setOnClickListener(handler);
        btnlocal.setOnClickListener(handler);


    }
}