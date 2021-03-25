package itstudy.kakao.resourceuse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class AnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        Button scaleBtn1  = (Button)findViewById(R.id.scaleBtn1);
        scaleBtn1.setOnClickListener( new View.OnClickListener() {
            public void  onClick(View v) {
                Animation anim =
                        AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale1);
                v.startAnimation(anim);
            }
        });

        Button scaleBtn2  = (Button)findViewById(R.id.scaleBtn2);
        scaleBtn2.setOnClickListener(new View.OnClickListener(){
            public void  onClick(View v) {
                Animation anim =
                        AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale2);
                v.startAnimation(anim);
            }
        });

    }
}