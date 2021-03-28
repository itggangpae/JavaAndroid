package itstudy.kakao.androidactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FirstActivity extends AppCompatActivity {
    TextView text;
    final static int ACT_EDIT = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        text = (TextView) findViewById(R.id.text);
        Button btn = (Button) findViewById(R.id.btnedit);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                intent.putExtra("TextIn", text.getText().toString());
                startActivityForResult(intent, ACT_EDIT);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ACT_EDIT:
                if (resultCode == RESULT_OK) {
                    text.setText(data.getStringExtra("TextOut"));
                }
                break;
        }

    }
}