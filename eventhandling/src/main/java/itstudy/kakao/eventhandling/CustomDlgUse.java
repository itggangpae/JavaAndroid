package itstudy.kakao.eventhandling;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomDlgUse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_dlg_use);

        Button btn = (Button) findViewById(R.id.call);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //xml로 만든 레이아웃을 뷰로 변환하기
                final LinearLayout linear =
                        (LinearLayout) View.inflate(CustomDlgUse.this, R.layout.login, null);
                new AlertDialog.Builder(CustomDlgUse.this)
                        .setTitle("Login")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setView(linear)
                        .setPositiveButton("로그인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText id = (EditText) linear.findViewById(R.id.id);
                                EditText password = (EditText) linear.findViewById(R.id.password);
                                TextView txt = (TextView) findViewById(R.id.text);
                                txt.setText("id:" + id.getText() +
                                        " password:" + password.getText());
                            }
                        })
                        .setNegativeButton("취소", null)
                        .show();
            }
        });


    }
}