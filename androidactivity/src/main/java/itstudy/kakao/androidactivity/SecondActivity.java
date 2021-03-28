package itstudy.kakao.androidactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SecondActivity extends AppCompatActivity {
    EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        edit = (EditText) findViewById(R.id.stredit);
        Intent intent = getIntent();
        String text = intent.getStringExtra("TextIn");
        if (text != null) {
            edit.setText(text);
        }
        Button.OnClickListener handler = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnok:
                        Intent intent = new Intent();
                        intent.putExtra("TextOut", edit.getText().toString());
                        setResult(RESULT_OK, intent);
                        finish();
                        break;
                    case R.id.btncancel:
                        setResult(RESULT_CANCELED);
                        finish();
                        break;
                }
            }
        };
        Button ok = (Button) findViewById(R.id.btnok);
        Button cancel = (Button) findViewById(R.id.btncancel);
        ok.setOnClickListener(handler);
        cancel.setOnClickListener(handler);
    }
}