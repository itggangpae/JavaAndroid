package itstudy.kakao.androidnetwork;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HtmlActivity extends AppCompatActivity {
    String html;
    StringBuffer sBuffer;

    TextView list;

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            String data = (String) msg.obj;
            list.setText(data);
        }
    };

    protected class ThreadEx extends Thread {
        public void run() {
            sBuffer = new StringBuffer();
            try {
                String urlAddr = "https://finance.naver.com";
                URL url = new URL(urlAddr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(20000);
                conn.setUseCaches(false);
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStreamReader isr = new InputStreamReader(conn.getInputStream(), "EUC-KR");
                    BufferedReader br = new BufferedReader(isr);
                    while (true) {
                        String line = br.readLine();
                        if (line == null) {
                            break;
                        }
                        sBuffer.append(line);
                    }
                    br.close();
                    conn.disconnect();
                }
            } catch (Exception e) {
                Log.e("다운로드 중 에러 발생", e.getMessage());
            }
            html = sBuffer.toString();
            Log.e("html", html);

            try {
                Document doc = Jsoup.parse(html);
                Elements elements = doc.select("a");
                sBuffer = new StringBuffer();
                for (Element link : elements) {
                    sBuffer.append(link.text().trim() + "\n");
                }
                Message msg = new Message();
                msg.obj = sBuffer.toString();
                mHandler.sendMessage(msg);
            } catch (Exception e) {
                Log.e("파싱 중 에러 발생", e.getMessage());
            }
        }
    }

    public void click(View v) {
        Toast.makeText(this, "시작", Toast.LENGTH_LONG).show();
        ThreadEx th = new ThreadEx();
        th.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html);

        list = (TextView) findViewById(R.id.list);
    }
}