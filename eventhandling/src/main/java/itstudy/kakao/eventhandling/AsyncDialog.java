package itstudy.kakao.eventhandling;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AsyncDialog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_dialog);

//        Button btn = (Button)findViewById(R.id.call);
//        btn.setOnClickListener(new Button.OnClickListener() {
//            public void onClick(View v) {
//                try {
//                    Thread.sleep(1000);
//                } catch (Exception e) {}
//                new AlertDialog.Builder(AsyncDialog.this)
//                        .setTitle("에러 발생")
//                        .setMessage("에러가 발생해서 종료합니다.")
//                        .setPositiveButton("종료", null)
//                        .show();
//                finish();
//                Toast.makeText(AsyncDialog.this, "작업이 무사히 끝났습니다.", Toast.LENGTH_LONG).show();
//            }
//        });


        Button btn = (Button)findViewById(R.id.call);
        btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    ;
                }
                new AlertDialog.Builder(AsyncDialog.this).setTitle("에러 발생")
                        .setMessage("에러가 발생해서 종료합니다.")
                        .setPositiveButton("종료", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                finish();
                                Toast.makeText(AsyncDialog.this, "작업이 무사히 끝났습니다.",
                                        Toast.LENGTH_LONG).show();
                            }
                        })
                        .show();
            }
        });
    }
}