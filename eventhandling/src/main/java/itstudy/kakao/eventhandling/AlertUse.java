package itstudy.kakao.eventhandling;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AlertUse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_use);

        Button btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(AlertUse.this);
                dlg.setTitle("대화상자 만들기");
                dlg.setMessage("대화상자를 열었습니다.");
                dlg.setIcon(android.R.drawable.ic_dialog_alert);
                dlg.setCancelable(false);
                dlg.setNegativeButton("닫기", null);
                dlg.show();
            }
        });

    }
}