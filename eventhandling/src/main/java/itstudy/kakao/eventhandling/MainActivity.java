package itstudy.kakao.eventhandling;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    Toast mToast = null;
    String str;

    //화면을 처음 터치할 때의 좌표를 저장하기 위한 변수
    float initX;
    //백버튼을 누른 시간을 저장하기 위한 변수
    long initTime;

    //토스트를 출력하기 위한 메소드
    private void showToast(String message){
        Toast toast= Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }
    //화면을 터치했을 때 호출되는 메소드
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            initX=event.getRawX();
        }else if(event.getAction()== MotionEvent.ACTION_UP){
            float diffX=initX-event.getRawX();
            if(diffX>30){
                showToast("왼쪽으로 화면을 밀었습니다.");
            }else if(diffX < -30){
                showToast("오른쪽으로 화면을 밀었습니다.");
            }
        }
        return true;
    }

    //키보드를 눌렀을 때 호출되는 메소드
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK){
            if(System.currentTimeMillis() - initTime > 3000){
                showToast("종료하려면 한번 더 누르세요.");
                initTime=System.currentTimeMillis();
            }else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    Button.OnClickListener mClickListener = new Button.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.shortmsg:
                    Toast.makeText(getApplicationContext(), "잠시 나타나는 메시지",
                            Toast.LENGTH_SHORT).show();
                    break;
                case R.id.longmsg:
                    /*
                    Toast.makeText(getApplicationContext(), "조금 길게 나타나는 메시지",
                            Toast.LENGTH_LONG).show();

                     */

                    Snackbar.make(v, "I’m Snackbar!", Snackbar.LENGTH_LONG)
                            .setAction("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                }
                            }).show();
                    String TAG = "MyActivity";
                    Log.e(TAG, "스낵바를 누르셨습니다.");

                    break;
            }
        }
    };


    /*
    protected class MyView extends View {
        public MyView(Context context) {
            super(context);
        }

        public boolean onTouchEvent(MotionEvent event) {
            super.onTouchEvent(event);
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                Toast.makeText(MainActivity.this,"Touch Event Received",
                        Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        }
    }
    */

//    protected class MyView extends View {
//        public MyView(Context context) {
//            super(context);
//        }
//    }
//    class TouchListenerClass implements View.OnTouchListener {
//        public boolean onTouch(View v, MotionEvent event) {
//            if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                Toast.makeText(MainActivity.this,"Touch Event Received.",
//                        Toast.LENGTH_SHORT).show();
//                return true;
//            }
//            return false;
//        }
//    }


    /*
    protected class MyView extends View implements View.OnTouchListener {
        public MyView(Context context) {
            super(context);
        }

        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                Toast.makeText(MainActivity.this,"Touch Event Received.",
                        Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        }
    }
*/
    protected class MyView extends View {
        public MyView(Context context) {
            super(context);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        findViewById(R.id.shortmsg).setOnClickListener(mClickListener);
//        findViewById(R.id.longmsg).setOnClickListener(mClickListener);

//        MyView vw = new MyView(this);
//        setContentView(vw);

//        MyView vw = new MyView(this);
//        vw.setOnTouchListener(new TouchListenerClass());
//        setContentView(vw);

//        MyView vw = new MyView(this);
//        vw.setOnTouchListener(vw);
//        setContentView(vw);

        /*
        MyView vw = new MyView(this);
        vw.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Toast.makeText(MainActivity.this,"Touch Event Received",
                            Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
        setContentView(vw);
         */

        MyView vw = new MyView(this);
        vw.setOnTouchListener((View v, MotionEvent event) -> {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Toast.makeText(MainActivity.this,"람다 식을 이용한 방법",
                            Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            });
        setContentView(vw);
    }
}