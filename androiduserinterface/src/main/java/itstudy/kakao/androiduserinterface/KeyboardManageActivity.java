package itstudy.kakao.androiduserinterface;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class KeyboardManageActivity extends AppCompatActivity {
    EditText edit;
    Button btnShow;
    Button btnHide;

    //키보드 관리 객체의 주소를 저장할 변수
    InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard_manage);

        edit = (EditText)findViewById(R.id.edit);
        btnShow = (Button)findViewById(R.id.show);
        btnHide = (Button)findViewById(R.id.hide);
        //키보드 관리 객체 가져오기
        imm = (InputMethodManager)getSystemService(
                INPUT_METHOD_SERVICE);
        //btnShow를 눌렀을 때 키보드를 화면에 출력하고 키보드를 누를 때 edit에
        //입력되도록 설정
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.showSoftInput(edit, 0);
            }
        });
        //btnHide를 눌렀을 때 edit의 키보드를 숨기기
        btnHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //edit의 키보드 숨기기
                imm.hideSoftInputFromWindow(edit.getWindowToken(),0);
            }
        });

    }

}