package itstudy.kakao.androidnetwork;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class KakaoActivity extends AppCompatActivity {
    String json;
    TextView list;
    StringBuilder sb;

    protected class ThreadEx extends Thread {
        public void run() {
            try {
                String urlAddr = "https://dapi.kakao.com/v3/search/book?target=title&query=";
                String title = "삼국지";
                urlAddr += URLEncoder.encode(title, "utf-8");
                URL url = new URL(urlAddr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Authorization", "KakaoAK ae6b2875ee4452804ed4e01890078a7e");
                if (conn != null) {
                    conn.setConnectTimeout(20000);
                    conn.setUseCaches(false);
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        InputStreamReader isr = new InputStreamReader(conn.getInputStream());
                        BufferedReader br = new BufferedReader(isr);
                        while (true) {
                            String line = br.readLine();
                            if (line == null) {
                                break;
                            }
                            sb.append(line);
                        }
                        br.close();
                        conn.disconnect();
                    }
                }
                json = sb.toString();

            } catch (Exception e) {
                Log.e("다운로드 중 에러 발생", e.getMessage());
            }
            try {
                sb = new StringBuilder();
                JSONObject obj = new JSONObject(json);
                JSONArray documents = obj.getJSONArray("documents");
                for (int i = 0; i < documents.length(); i++) {
                    JSONObject book = documents.getJSONObject(i);
                    sb.append("제목:" + book.getString("title") + ",가격:" + book.getString("price") + "\n");
                }
                Message message = new Message();
                message.obj = sb.toString();
                mHandler.sendMessage(message);
            } catch (Exception e) {
                Log.e("파싱 중 에러", e.getMessage());
            }
        }
    }


    public void click(View v) {
        ThreadEx th = new ThreadEx();
        th.start();
    }

    Handler mHandler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message msg) {
            String result = (String) msg.obj;
            list.setText(result);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakao);

        list = (TextView) findViewById(R.id.list);
        sb = new StringBuilder();
    }
}