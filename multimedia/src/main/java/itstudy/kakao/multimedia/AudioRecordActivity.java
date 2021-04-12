package itstudy.kakao.multimedia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import com.google.android.material.snackbar.Snackbar;
import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

import java.io.File;
import java.io.IOException;

public class AudioRecordActivity extends AppCompatActivity implements AutoPermissionsListener {
    MediaRecorder recorder;
    MediaPlayer player;

    String filename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_record);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRecording();
            }
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRecording();
            }
        });

        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlay();
            }
        });

        Button button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlay();
            }
        });

        String fileDir = getFilesDir().getAbsolutePath();
        filename = fileDir + File.separator + "recorded.mp4";

        AutoPermissions.Companion.loadAllPermissions(this, 101);
    }

    public void startRecording() {
        if (recorder == null) {
            recorder = new MediaRecorder();
        }
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        recorder.setOutputFile(filename);

        try {
            recorder.prepare();
            recorder.start();
        } catch (Exception e) {
            Log.e("SampleAudioRecorder", e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    public void stopRecording() {
        try {
            if (recorder == null) {
                return;
            }

            recorder.stop();
            recorder.release();
            recorder = null;

            ContentValues values = new ContentValues(10);

            values.put(MediaStore.MediaColumns.TITLE, "Recorded");
            values.put(MediaStore.Audio.Media.ALBUM, "Audio Album");
            values.put(MediaStore.Audio.Media.ARTIST, "Mike");
            values.put(MediaStore.Audio.Media.DISPLAY_NAME, "Recorded Audio");
            values.put(MediaStore.Audio.Media.IS_RINGTONE, 1);
            values.put(MediaStore.Audio.Media.IS_MUSIC, 1);
            values.put(MediaStore.MediaColumns.DATE_ADDED,
                    System.currentTimeMillis() / 1000);
            values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp4");
            values.put(MediaStore.Audio.Media.DATA, filename);

            Uri audioUri = getContentResolver().insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, values);
            if (audioUri == null) {
                Log.e("SampleAudioRecorder", "Audio insert failed.");
                return;
            }
        }catch (Exception e){
            Log.e("SampleAudioRecorder", e.getLocalizedMessage());
        }
    }

    public void startPlay() {
        killMediaPlayer();

        try {
            player = new MediaPlayer();
            player.setDataSource("file://" + filename);
            player.prepare();
            player.start();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void stopPlay() {
        if (player != null) {
            player.stop();
        }
    }

    private void killMediaPlayer() {
        if (player != null) {
            try {
                player.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //권한을 요청하는 메소드
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AutoPermissions.Companion.parsePermissions(this, requestCode, permissions, this);
    }

    //거부한 권한에 대한 정보를 알려주는 메소드
    @Override
    public void onDenied(int requestCode, String[] permissions) {
        Snackbar.make(getWindow().getDecorView().getRootView(), "permissions denied : " + permissions.length,
                Snackbar.LENGTH_LONG).show();
    }

    @Override
    //허용한 권한에 대한 정보를 알려주는 메소드
    public void onGranted(int requestCode, String[] permissions) {
        Snackbar.make(getWindow().getDecorView().getRootView(), "permissions granted : " + permissions.length, Snackbar.LENGTH_LONG).show();
    }
}