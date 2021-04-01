package itstudy.kakao.localdata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class PrefActivity extends AppCompatActivity {
    // nameField 의 데이터를 저장할 Key
    String nameFieldKey = "nameField";
    // pushCheckBox 의 데이터를 저장할 Key
    String pushCheckBoxKey = "pushCheckBox";
    // shared preference 객체, Activity 초기화 이후에 사용해야 하기 때문에 lazy 위임을 사용
    SharedPreferences preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pref);

        preference = getSharedPreferences("PrefExActivity", Context.MODE_PRIVATE);

        Button saveButton = (Button)findViewById(R.id.saveButton);
        Button loadButton = (Button)findViewById(R.id.loadButton);
        EditText nameField = (EditText)findViewById(R.id.nameField);
        CheckBox pushCheckBox = (CheckBox)findViewById(R.id.pushCheckBox);

        // 저장 버튼이 클릭된 경우
        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // SharedPreference 에서 nameFieldKey 키값으로 nameField 의 현재 텍스트를 저장한다.
                preference.edit().putString(nameFieldKey, nameField.getText().toString()).apply();
                // SharedPreference 에서 pushCheckBoxKey 키값으로 체크 박스의 현재 체크 상태를 저장한다.
                preference.edit().putBoolean(pushCheckBoxKey, pushCheckBox.isChecked()).apply();
            }
        });

        // 불러오기 버튼이 클릭된 경우

        loadButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // SharedPreference 에서 nameFieldKey 로 저장된 문자열을 불러와 nameField 의 텍스트로 설정
                nameField.setText(preference.getString(nameFieldKey, ""));
                // SharedPreference 에서 pushCheckBoxKey 키값으로 불린값을 불러와 pushCheckBox 의 체크상태를 설정
                pushCheckBox.setChecked(preference.getBoolean(pushCheckBoxKey, false));
            }
        });

    }
}