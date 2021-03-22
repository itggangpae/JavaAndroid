package itstudy.kakao.androiduserinterface;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        LinearLayout linear=new LinearLayout(this);

        Button bt1=new Button(this);
        bt1.setText("버튼1");
        linear.addView(bt1);

        Button bt2=new Button(this);
        bt2.setText("버튼2");
        linear.addView(bt2);

        setContentView(linear);

    }
}