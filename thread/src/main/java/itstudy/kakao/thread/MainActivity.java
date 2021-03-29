package itstudy.kakao.thread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int value = 0;
    TextView txt;

//    Handler handler = new Handler() {
//        public void handleMessage(Message msg) {
//            if (msg.what == 0) {
//                txt.setText("Value : " + value);
//            }
//        }
//    };
//
//    class BackThread extends Thread {
//        public void run() {
//            while (value <20) {
//                value++;
//                try {
//                    Thread.sleep(1000);
//                    handler.sendEmptyMessage(0);
//                }
//                catch (InterruptedException e) {return;}
//            }
//        }
//    }

    Handler handler = new Handler();

    class BackThread extends Thread {
        public void run() {
            while (true) {
                value++;
                handler.post(new Runnable() {
                    public void run() {
                        txt.setText("value : " + value);
                    }
                });
                try { Thread.sleep(1000); } catch (InterruptedException e) {;}
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt = (TextView)findViewById(R.id.txt);
        Button increse = (Button)findViewById(R.id.increase);
        increse.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Thread th = new BackThread();
                th.setDaemon(true);
                th.start();
            }
        });
    }
}