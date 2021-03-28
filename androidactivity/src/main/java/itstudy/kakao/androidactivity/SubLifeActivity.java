package itstudy.kakao.androidactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SubLifeActivity extends AppCompatActivity {
    int count;
    TextView countVeiw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_life);

        setContentView(R.layout.activity_sub_life);
        Button btn=(Button)findViewById(R.id.detail_btn);
        countVeiw=(TextView)findViewById(R.id.detail_count);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                count++;
                countVeiw.setText(String.valueOf(count));
            }
        });

    }
}