package itstudy.kakao.resourceuse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

public class AnimationSetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_set);

        Button btn = (Button) findViewById(R.id.btnstart);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                AnimationSet ani = new AnimationSet(true);
                ani.setInterpolator(new LinearInterpolator());
                TranslateAnimation trans = new TranslateAnimation(
                        Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 1.0f,
                        Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f
                );
                trans.setDuration(3000);
                ani.addAnimation(trans);
                AlphaAnimation alpha = new AlphaAnimation(1.0f, 0.0f);
                alpha.setDuration(300);
                alpha.setStartOffset(500);
                alpha.setRepeatCount(4);
                alpha.setRepeatMode(Animation.REVERSE);
                ani.addAnimation(alpha);
                ImageView animtarget = (ImageView) findViewById(R.id.animtarget);
                animtarget.startAnimation(ani);
            }

        });
    }
}