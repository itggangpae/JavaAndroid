package itstudy.kakao.thread;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProgressActivity extends AppCompatActivity {
    int value;
    TextView txt;

    //ProgressDialog mProgress;
    boolean isQuit;

    ProgressBar progressBar;

    public void update() {
        for (int i=0;i<100;i++) {
            value++;
            txt.setText(Integer.toString(value));
            try {
                Thread.sleep(50);
            }
            catch (InterruptedException e) {;}
        }
    }

    Handler handler = new Handler(Looper.myLooper()) {
        public void handleMessage(Message msg) {
            value++;
            txt.setText(Integer.toString(value));
            try { Thread.sleep(50); } catch (InterruptedException e) {;}
            if (value < 100 && isQuit == false) {
                progressBar.setProgress(value);
                handler.sendEmptyMessage(0);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        txt=(TextView)findViewById(R.id.text);
        Button update = (Button)findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                value = 0;
                progressBar = (ProgressBar)findViewById(R.id.progressbar);
                isQuit = false;
                handler.sendEmptyMessage(0);
            }
        });
    }
}