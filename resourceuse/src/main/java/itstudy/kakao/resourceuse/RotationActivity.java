package itstudy.kakao.resourceuse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class RotationActivity extends AppCompatActivity {
    TextView textView;
    String  KEY_DATA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotation);

        textView = (TextView)findViewById(R.id.textView);

        if (savedInstanceState != null) {
            String data = savedInstanceState.getString(KEY_DATA);
            textView.setText(data);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        String data  = textView.getText().toString();
        outState.putString(KEY_DATA, data);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        setContentView(R.layout.activity_rotation);
        super.onConfigurationChanged(newConfig);
    }
}