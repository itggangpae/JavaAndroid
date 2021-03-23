package itstudy.kakao.eventhandling;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ClickActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click);
    }

    public void onClick(View v) {
        TextView text=(TextView)findViewById(R.id.mobile);
        switch (v.getId()) {
            case R.id.apple:
                text.setText("Apple");
                break;
            case R.id.google:
                text.setText("Google");
                break;
        }
    }

}