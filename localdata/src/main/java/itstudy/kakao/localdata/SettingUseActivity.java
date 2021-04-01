package itstudy.kakao.localdata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;


public class SettingUseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_use);

        //환경 설정 레이아웃으로 뷰를 변경
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new MyPrefFragment()).commit();
        //환경 설정 값을 읽어오기
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Log.e("내가 좋아하는 걸그룹", prefs.getString("singerlist", "기본값").toString());

    }
}