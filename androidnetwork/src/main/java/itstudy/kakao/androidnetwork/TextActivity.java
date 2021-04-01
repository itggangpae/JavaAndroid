package itstudy.kakao.androidnetwork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TextActivity extends AppCompatActivity {
    EditText inputurl;
    TextView txtmsg;
    public static String defaultUrl = "https://m.naver.com";
    Handler handler = new Handler();

    private String request(String urlStr) {
        StringBuilder output = new StringBuilder();
        String result = "";
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if (conn != null) {
                conn.setConnectTimeout(10000);
                conn.setUseCaches(false);
                //conn.setRequestProperty("Accept-Charset", "EUC-KR");
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(conn.getInputStream()));
                    while (true) {
                        String line = br.readLine();
                        if (line == null) break;
                        output.append(line + '\n');
                    }
                    br.close();
                    result = output.toString();
                }
                conn.disconnect();
            }
        } catch (Exception ex) {
            Log.e("SampleHTTPClient", "Exception in processing response.", ex);
        }

        return result.toString();
    }

    protected class ConnectThread extends Thread {
        String urlStr;

        public ConnectThread(String inStr) {
            urlStr = inStr;
        }

        public void run() {

            try {
                final String output = request(urlStr);
                handler.post(new Runnable() {
                    public void run() {
                        txtmsg.setText(output);
                    }
                });

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        inputurl = (EditText) findViewById(R.id.inputurl);
        inputurl.setText(defaultUrl);
        txtmsg = (TextView) findViewById(R.id.txtmsg);
        // 버튼 이벤트 처리
        Button requestBtn = (Button) findViewById(R.id.requestbtn);
        requestBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String urlStr = inputurl.getText().toString();

                ConnectThread thread = new ConnectThread(urlStr);
                thread.start();

            }
        });

    }

    public boolean onTouchEvent(MotionEvent event){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(inputurl.getWindowToken(), 0);
        return super.onTouchEvent(event);
    }

}