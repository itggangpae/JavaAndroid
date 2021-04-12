package itstudy.kakao.multimedia;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class PlayAudioActivity extends AppCompatActivity {
    //노래 목록을 저장할 List
    ArrayList<String> list;
    //현재 재생 중인 노래의 인덱스
    int idx;
    //음악 재생기
    MediaPlayer player;

    //버튼
    Button playBtn, stopBtn, prevBtn, nextBtn;
    //노래 제목을 출력할 텍스트 뷰
    TextView fileName;
    //진행 상황을 나타낼 뷰
    SeekBar progress;

    //노래 재생 여부를 저장할 변수
    boolean wasPlaying;

    //노래 제목 과 진행률을 표시해주는 핸들러
    Handler messageHander = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message msg) {
            boolean result = (Boolean) msg.obj;
            String resultMsg = null;
            if (result == true) {
                resultMsg = "재생 준비 완료";
                fileName.setText("파일 : " + list.get(msg.what));
                progress.setMax(player.getDuration());
            } else {
                resultMsg = "재생 준비 실패";
            }
            Snackbar.make(getWindow().getDecorView().getRootView(), resultMsg, Snackbar.LENGTH_LONG).show();
            if (result == false) {
                finish();
            }
        }
    };

    //노래 준비를 수행해주는 메소드
    private boolean prepare() {
        try {
            player.prepare();
        } catch (IllegalStateException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    //노래의 인덱스를 받아서 준비 상태로 만들고 그 결과를 핸들러에게 전송하는 메소드
    private void loadMedia(int idx) {
        Message message = new Message();
        message.what = idx;
        try {
            player.setDataSource(this, Uri.parse(list.get(idx)));
        } catch (Exception e) {
            message.obj = false;
        }
        if (prepare() == false) {
            message.obj = false;
        } else {
            message.obj = true;
        }
        messageHander.sendMessage(message);
    }

    // 0.2초에 한번꼴로 재생 위치fmf 갱신 해주는 핸들러
    Handler progressHandler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message msg) {
            if (player == null)
                return;
            if (player.isPlaying()) {
                progress.setProgress(player.getCurrentPosition());
            }
            progressHandler.sendEmptyMessageDelayed(0, 200);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_audio);

        //파일이름을 출력할 뷰 찾아오기
        fileName = (TextView) findViewById(R.id.filename);
        list = new ArrayList<String>();

        //노래 목록을 가져오는 스레드
        new Thread() {
            public void run() {
                try {
                    String addr = "http://cyberadam.cafe24.com/song/";
                    java.net.URL url = new URL(addr + "song.txt");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    while (true) {
                        String line = br.readLine();
                        if (line == null) {
                            break;
                        }
                        sb.append(line + "\n");
                    }
                    String str = sb.toString();
                    String[] songList = str.split(",");
                    for (String song : songList) {
                        list.add(addr + song + ".mp3");
                    }
                    Log.e("목록 준비 완료", list.toString());
                    player = new MediaPlayer();
                    idx = 0;
                    loadMedia(idx);
                    // 완료 리스너, 시크바 변경 리스너 등록
                    progress = (SeekBar) findViewById(R.id.progress);
                    // 재생 완료되면 다음곡으로
                    player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            idx = (idx == list.size() - 1 ? 0 : idx + 1);
                            player.reset();
                            loadMedia(idx);
                            player.start();
                        }
                    });

                    // 에러 발생시 메시지 출력
                    player.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                        @Override
                        public boolean onError(MediaPlayer mp, int what, int extra) {
                            String err = "OnError occured. what = " + what + " ,extra = "
                                    + extra;
                            Snackbar.make(getWindow().getDecorView().getRootView(), err, Snackbar.LENGTH_LONG).show();
                            return false;
                        }
                    });

                    // 위치 이동 완료 처리
                    player.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                        @Override
                        public void onSeekComplete(MediaPlayer mp) {
                            if (wasPlaying) {
                                player.start();
                            }
                        }
                    });

                    // 재생 위치 이동
                    progress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        public void onProgressChanged(SeekBar seekBar, int progress,
                                                      boolean fromUser) {
                            if (fromUser) {
                                player.seekTo(progress);
                            }
                        }

                        public void onStartTrackingTouch(SeekBar seekBar) {
                            wasPlaying = player.isPlaying();
                            if (wasPlaying) {
                                player.pause();
                            }
                        }

                        public void onStopTrackingTouch(SeekBar seekBar) {
                        }
                    });
                    progressHandler.sendEmptyMessageDelayed(0, 200);
                } catch (Exception e) {
                    Log.e("목록 다운로드 예외", e.getMessage());
                    e.printStackTrace();
                }
            }
        }.start();


        playBtn = (Button) findViewById(R.id.play);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player.isPlaying() == false) {
                    player.start();
                    playBtn.setText("Pause");
                } else {
                    player.pause();
                    playBtn.setText("Play");
                }
            }
        });

        stopBtn = (Button) findViewById(R.id.stop);
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.stop();
                playBtn.setText("Play");
                progress.setProgress(0);
                prepare();
            }
        });

        prevBtn = (Button) findViewById(R.id.prev);
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean wasPlaying = player.isPlaying();
                idx = (idx == 0 ? list.size() - 1 : idx - 1);
                player.reset();
                loadMedia(idx);

                // 이전에 재생중이었으면 다음 곡 바로 재생
                if (wasPlaying) {
                    player.start();
                    playBtn.setText("Pause");
                }
            }
        });

        nextBtn = (Button) findViewById(R.id.next);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean wasPlaying = player.isPlaying();
                idx = (idx == list.size() - 1 ? 0 : idx + 1);
                player.reset();
                loadMedia(idx);

                // 이전에 재생중이었으면 다음 곡 바로 재생
                if (wasPlaying) {
                    player.start();
                    playBtn.setText("Pause");
                }
            }
        });
    }

    // 액티비티 종료시 재생 강제 종료
    public void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
            player = null;
        }
    }
}