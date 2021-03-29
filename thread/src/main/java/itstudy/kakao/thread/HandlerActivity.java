package itstudy.kakao.thread;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HandlerActivity extends AppCompatActivity {
//    private void doUpload() {
//        for (int i = 0; i < 10; i++) {
//            try { Thread.sleep(1000); } catch (InterruptedException e) {;}
//        }
//        Toast.makeText(this, "업로드를 완료했습니다.", Toast.LENGTH_LONG).show();
//    }

    private void doUpload() {
        for (int i = 0; i < 20; i++) {
            try { Thread.sleep(100); } catch (InterruptedException e) {;}
        }
        Toast.makeText(this, "업로드를 완료했습니다.", Toast.LENGTH_SHORT).show();
    }


    Handler mHandler = new Handler(Looper.myLooper()) {
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                doUpload();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);

        Button upload = (Button)findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
//                new AlertDialog.Builder(HandlerActivity.this)
//                        .setTitle("질문")
//                        .setMessage("업로드 하시겠습니까?")
//                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int whichButton) {
//                                doUpload();
//                            }
//                        })
//                        .setNegativeButton("아니오", null)
//                        .show();

//                new AlertDialog.Builder(HandlerActivity.this)
//                        .setTitle("질문")
//                        .setMessage("업로드 하시겠습니까?")
//                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int whichButton) {
//                                mHandler.sendEmptyMessageDelayed(0,10);
//                            }
//                        })
//                        .setNegativeButton("아니오", null)
//                        .show();

                new AlertDialog.Builder(HandlerActivity.this)
                        .setTitle("질문")
                        .setMessage("업로드 하시겠습니까?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Button btnUpload = (Button)findViewById(R.id.upload);
                                btnUpload.postDelayed(new Runnable() {
                                    public void run() {
                                        doUpload();
                                    }
                                },10);
                            }

                        })
                        .setNegativeButton("아니오", null)
                        .show();

            }
        });
    }
}