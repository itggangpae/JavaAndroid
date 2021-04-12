package itstudy.kakao.multimedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;

public class XylophoneActivity extends AppCompatActivity {

    //텍스트 뷰와 사운드 파일이름을 쌍으로 저장하기 만들기
    private ArrayList<Pair> sounds;

    //사운드 풀
    private SoundPool soundPool;

    //노래를 가져오고 텍스트 뷰의 클릭 이벤트 처리를 위한 함수
    private void tune(Pair<Integer, Integer> pitch ) {
        int soundId = soundPool.load(this, pitch.second, 1);
        findViewById(pitch.first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.play(soundId, 1.0f, 1.0f, 0, 0, 1.0f);
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //기기 가로 방향 고정
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xylophone);

        //운영체제 버전에 따라 사운드 풀 생성
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            soundPool = new SoundPool.Builder().setMaxStreams(8).build();
        } else {
            soundPool = new SoundPool(8, AudioManager.STREAM_MUSIC, 0);
        }

        //텍스트 뷰의 아이디 와 사운드를 쌍으로 저장
        Pair []  pairs = {
            new Pair(R.id.do1, R.raw.do1),
                    new Pair(R.id.re, R.raw.re),
                    new Pair(R.id.mi, R.raw.mi),
                    new Pair(R.id.fa, R.raw.fa),
                    new Pair(R.id.sol, R.raw.sol),
                    new Pair(R.id.la, R.raw.la),
                    new Pair(R.id.si, R.raw.si),
                    new Pair(R.id.do2, R.raw.do2)};
        sounds = new ArrayList(Arrays.asList(pairs));

        //텍스트 뷰에 이벤트 연결
        for(Pair<Integer, Integer> pair : sounds){
            tune(pair);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release();
    }

}