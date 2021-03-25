package itstudy.kakao.varietyview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class ProgressActivity extends AppCompatActivity {
    ProgressBar lect, circle;

    SeekBar seekBar;
    TextView volume;

    RatingBar rating;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        View.OnClickListener eventHandler = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.start:
                        lect.setProgress(lect.getProgress() + 10);
                        circle.setVisibility(View.VISIBLE);
                        break;
                    case R.id.stop:
                        lect.setProgress(lect.getProgress() - 10);
                        circle.setVisibility(View.GONE);
                        break;
                }
            }
        };

        Button start = (Button)findViewById(R.id.start);
        Button stop = (Button)findViewById(R.id.stop);
        start.setOnClickListener(eventHandler);
        stop.setOnClickListener(eventHandler);
        lect = (ProgressBar)findViewById(R.id.progressbar);
        circle = (ProgressBar)findViewById(R.id.progressind) ;

        seekBar = (SeekBar)findViewById(R.id.seekbar);
        volume = (TextView)findViewById(R.id.volume);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                volume.setText("볼륨 : " + progress);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(ProgressActivity.this, "볼륨 조절시작", Toast.LENGTH_SHORT).show();
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(ProgressActivity.this, "볼륨 조절 종료", Toast.LENGTH_SHORT).show();
            }
        });

        rating = (RatingBar)findViewById(R.id.rating);
        txt = (TextView)findViewById(R.id.txt);

        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() 		{
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                txt.setText("Now Rate : " + rating);
            }
        });


    }
}