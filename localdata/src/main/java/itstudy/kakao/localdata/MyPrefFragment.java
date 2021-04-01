package itstudy.kakao.localdata;

import android.os.Bundle;
import android.util.Log;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

// PreferenceFragment: XML 로 작성한 Preference 를 UI 로 보여주는 클래스
public class MyPrefFragment extends PreferenceFragmentCompat {
    @Override
    public void  onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // Preference 정보가 있는 XML 파일 지정
        addPreferencesFromResource(R.xml.setting_pref);
        //환경 설정 값이 변경될 때 작업을 수행
        ListPreference singerlistPref = (ListPreference)findPreference("singerlist");
        singerlistPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Log.e("singerlist",  newValue.toString());
                return true;
            }
        });
    }
}