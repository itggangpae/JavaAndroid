package itstudy.kakao.androidnetwork;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class HaniActivity extends AppCompatActivity {
    String xml;
    StringBuffer sBuffer;
    TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hani);

       display = (TextView)findViewById(R.id.display);

    }

    public void click(View v) {
        ThreadEx th = new ThreadEx();
        th.start();
    }

    Handler mHandler = new Handler(Looper.getMainLooper()){
        public void handleMessage(Message msg){
            String article = (String)msg.obj;
            display.setText(article);
        }
    };

    protected class ThreadEx extends Thread {
        public void run() {
            sBuffer = new StringBuffer();
            try {
                String urlAddr = "http://www.hani.co.kr/rss/";
                URL url = new URL(urlAddr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                if (conn != null) {
                    conn.setConnectTimeout(20000);
                    conn.setUseCaches(false);
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        InputStreamReader isr = new InputStreamReader(
                                conn.getInputStream());
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
                }
                xml = sBuffer.toString();

            } catch (Exception e) {
                Log.e("다운로드 중 에러 발생", e.getMessage());
            }

            try {
                Log.e("xml", xml);
                if (xml != null) {
                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder documentBuilder = factory.newDocumentBuilder();
                    InputStream is = new ByteArrayInputStream(xml.getBytes());
                    Document doc = documentBuilder.parse(is);
                    Element element = doc.getDocumentElement();
                    NodeList titles = element.getElementsByTagName("title");
                    NodeList links = element.getElementsByTagName("link");

                    int n = titles.getLength();
                    sBuffer = new StringBuffer();
                    for (int i = 1; i < n; i++) {
                        Node title = titles.item(i);
                        Node text = title.getFirstChild();
                        String titleValue = text.getNodeValue();

                        Node link = links.item(i);
                        Node linktext = link.getFirstChild();
                        String linkValue = linktext.getNodeValue();

                        sBuffer.append(titleValue + ":" + linkValue + "\n\n");
                    }
                    Message message = new Message();
                    message.obj = sBuffer.toString();
                    mHandler.sendMessage(message);

                }
            }
            catch(Exception e){
                Log.e("파싱 중 에러 발생", e.getMessage());
            }
        }
    }

}