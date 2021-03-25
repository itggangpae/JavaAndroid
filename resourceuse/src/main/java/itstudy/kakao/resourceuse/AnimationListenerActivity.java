package itstudy.kakao.resourceuse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

public class AnimationListenerActivity extends AppCompatActivity {
    boolean isPageOpen = false;
    Animation translateLeftAnim;
    Animation translateRightAnim;
    LinearLayout slidingPage01;
    Button openBtn01;

    //애니메이션 리스너
    class SlidingPageAnimationListener implements Animation.AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if (isPageOpen) {
                slidingPage01.setVisibility(View.INVISIBLE);
                openBtn01.setText("Open");
                isPageOpen = false;
            } else {
                openBtn01.setText("Close");
                isPageOpen = true;
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_listener);

        // 슬라이딩으로 보여질 레이아웃 객체 참조
        slidingPage01 = (LinearLayout) findViewById(R.id.slidingPage01);

        // 애니메이션 객체 로딩
        translateLeftAnim = AnimationUtils.loadAnimation(this, R.anim.translate_left);
        translateRightAnim = AnimationUtils.loadAnimation(this, R.anim.translate_right);

        // 애니메이션 객체에 리스너 설정
        SlidingPageAnimationListener animListener = new SlidingPageAnimationListener();
        translateLeftAnim.setAnimationListener(animListener);
        translateRightAnim.setAnimationListener(animListener);

        // 버튼 이벤트 처리
        openBtn01 = (Button)findViewById(R.id.openBtn01);
        openBtn01.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // 애니메이션 적용
                Log.e("isPageOpen", "$isPageOpen");
                if (isPageOpen) {

                    slidingPage01.startAnimation(translateRightAnim);
                } else {
                    slidingPage01.setVisibility(View.VISIBLE);
                    slidingPage01.startAnimation(translateLeftAnim);
                }
            }
        });
    }
}