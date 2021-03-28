package itstudy.kakao.androidactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button)findViewById(R.id.call);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SubActivity를 화면에 출력
                //명시적 Intent - Intent에 클래스 이름을 명시
//                Intent intent = new Intent(MainActivity.this, SubActivity.class);
//                startActivity(intent);

                //암시적 인텐트 호출
                Intent intent = new Intent();
                intent.setAction("com.example.ACTION_VIEW");
                startActivity(intent);
            }
        });
    }
}