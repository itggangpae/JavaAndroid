package itstudy.kakao.eventhandling;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LongclickActivity extends AppCompatActivity {
    int count = 0;
    TextView txt;

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.decrease:
                count--;
                txt.setText("" + count);
                break;
            case R.id.increase:
                count++;
                txt.setText("" + count);
                break;
        }
    }

    View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
        public boolean onLongClick(View v) {
            switch (v.getId()) {
                case R.id.decrease:
                    count = 0;
                    txt.setText("" + count);
                    return true;
                case R.id.increase:
                    count = 100;
                    txt.setText("" + count);
                    break;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_longclick);

        txt=(TextView)findViewById(R.id.count);
        findViewById(R.id.decrease).setOnLongClickListener(longClickListener);
        findViewById(R.id.increase).setOnLongClickListener(longClickListener);

    }
}