package itstudy.kakao.multimedia;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import com.google.android.material.snackbar.Snackbar;

public class VideoPlayActivity extends AppCompatActivity {
    //비디오 URL
    static final String VIDEO_URL = "http://sites.google.com/site/ubiaccessmobile/sample_video.mp4";
    //비디오 재생 뷰
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);

        Button startBtn = (Button)findViewById(R.id.startBtn);
        Button volumeBtn = (Button)findViewById(R.id.volumeBtn);

        //비디오 재생
        startBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                videoView.seekTo(0);
                videoView.start();
            }
        });

        //볼륨을 최대로 설정
        volumeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AudioManager mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
                int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, AudioManager.FLAG_SHOW_UI);
            }
        });

        videoView = (VideoView) findViewById(R.id.videoView);

        MediaController mc = new MediaController(this);
        videoView.setMediaController(mc);
        videoView.setVideoURI(Uri.parse(VIDEO_URL));

        //raw 디렉토리 경로 설정
        //String path = "android.resource://" + getPackageName() + "/" + R.raw.trailer;
        //videoView.setVideoURI(Uri.parse(path));

        videoView.requestFocus();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer player) {
                Snackbar.make(getWindow().getDecorView().getRootView(), "동영상이 준비되었습니다.\n'재생' 버튼을 누르세요.", Snackbar.LENGTH_LONG).show();
            }
        });

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer player) {
                Snackbar.make(getWindow().getDecorView().getRootView(), "동영상 재생이 완료되었습니다.", Snackbar.LENGTH_LONG).show();
            }
        });

    }

    protected void onResume() {
        Snackbar.make(getWindow().getDecorView().getRootView(), "동영상 준비중입니다.\n잠시 기다려주세요.", Snackbar.LENGTH_LONG).show();
        super.onResume();
    }

}