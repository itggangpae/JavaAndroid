package itstudy.kakao.eventhandling;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TimerActivity extends AppCompatActivity {
    int value=0;
    TextView txt;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        txt=(TextView)findViewById(R.id.text);
        timer = new CountDownTimer(10 * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                value++;
                txt.setText("Value = " + value);
            }

            public void onFinish() {
                txt.setText("finshed");
            }
        };

        Button btnStart = (Button)findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                timer.start();
            }
        });

        Button btnEnd = (Button)findViewById(R.id.btnStop);
        btnEnd.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                timer.cancel();
            }
        });

    }
}