package itstudy.kakao.varietyview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HybridActivity extends AppCompatActivity {

    //웹 뷰에 대한 참조를 저장하기 위한 변수
    private WebView mWebView = null;

    public class AndroidJavaScriptInterface
    {
        private Context mContext = null;
        private Handler handler = new Handler();

        public AndroidJavaScriptInterface(Context aContext)
        {
            mContext = aContext;
        }

        @JavascriptInterface
        public void showToastMessage(final String aMessage)
        {
            handler.post(new Runnable()
            {
                public void run()
                {
                    Toast.makeText(mContext, aMessage, Toast.LENGTH_SHORT).show();
                    Log.e("전달 받은 메시지", aMessage);
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hybrid);

        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new AndroidJavaScriptInterface(this), "MYApp");
        mWebView.loadUrl("http://cyberadam.cafe24.com/");

        Button btn = (Button)findViewById(R.id.sendmsg);
        btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                TextView sendText = (TextView)findViewById(R.id.sendtxt);
                String sendmessage = sendText.getText().toString();
                Toast.makeText(HybridActivity.this, sendmessage, Toast.LENGTH_LONG).show();
                mWebView.loadUrl("javascript:showDisplayMessage('"+ sendmessage +"')");
            }
        });
    }
}