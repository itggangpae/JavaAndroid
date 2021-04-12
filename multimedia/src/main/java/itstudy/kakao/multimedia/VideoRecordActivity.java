package itstudy.kakao.multimedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.android.material.snackbar.Snackbar;
import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

import java.io.File;

import static android.provider.MediaStore.*;

public class VideoRecordActivity extends AppCompatActivity implements AutoPermissionsListener {
    MediaPlayer player;
    MediaRecorder recorder;

    String filename;

    SurfaceHolder holder;

    public void startRecording() {
        if (recorder == null) {
            recorder = new MediaRecorder();
        }

        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        recorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);
        recorder.setOutputFile(filename);

        recorder.setPreviewDisplay(holder.getSurface());

        try {
            recorder.prepare();
            recorder.start();
        } catch (Exception e) {
            e.printStackTrace();

            recorder.release();
            recorder = null;
        }
    }

    public void stopRecording() {
        if (recorder == null) {
            return;
        }

        recorder.stop();
        recorder.reset();
        recorder.release();
        recorder = null;

        ContentValues values = new ContentValues(10);

        values.put(MediaColumns.TITLE, "RecordedVideo");
        values.put(Audio.Media.ALBUM, "Video Album");
        values.put(Audio.Media.ARTIST, "Mike");
        values.put(Audio.Media.DISPLAY_NAME, "Recorded Video");
        values.put(MediaColumns.DATE_ADDED, System.currentTimeMillis() / 1000);
        values.put(MediaColumns.MIME_TYPE, "video/mp4");
        values.put(Audio.Media.DATA, filename);

        Uri videoUri = getContentResolver().insert(Video.Media.EXTERNAL_CONTENT_URI, values);
        if (videoUri == null) {
            Log.d("SampleVideoRecorder", "Video insert failed.");
            return;
        }

        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, videoUri));

    }

    public void startPlay() {
        if (player == null) {
            player = new MediaPlayer();
        }
        try {
            player.setDataSource(filename);
            player.setDisplay(holder);

            player.prepare();
            player.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopPlay() {
        if (player == null) {
            return;
        }

        player.stop();
        player.release();
        player = null;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_record);

        SurfaceView surface = new SurfaceView(this);
        holder = surface.getHolder();
        //holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        FrameLayout frame = findViewById(R.id.container);
        frame.addView(surface);

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
        filename = fileDir + File.separator + "video.mp4";

        AutoPermissions.Companion.loadAllPermissions(this, 101);

    }
}