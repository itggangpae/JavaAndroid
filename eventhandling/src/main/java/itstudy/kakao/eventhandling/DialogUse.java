package itstudy.kakao.eventhandling;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DialogUse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_use);

        Button btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Dialog dlg = new Dialog(DialogUse.this);
                TextView text = new TextView(DialogUse.this);
                text.setText("다이얼로그 만들기");
                dlg.setContentView(text);
                dlg.setTitle("대화상자");
                dlg.show();
            }
        });

    }
}