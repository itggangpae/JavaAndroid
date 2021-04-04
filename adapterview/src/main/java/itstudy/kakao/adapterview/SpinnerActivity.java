package itstudy.kakao.adapterview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class SpinnerActivity extends AppCompatActivity {
    boolean mInitSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);

        Spinner spin = (Spinner)findViewById(R.id.myspinner);
        spin.setPrompt("분야를 고르세요.");

        ArrayAdapter<CharSequence> adspin = ArrayAdapter.createFromResource(this, R.array.departments,
                android.R.layout.simple_spinner_item);

        adspin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adspin);
        spin.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                if (mInitSpinner == false) {
                    mInitSpinner = true;
                    return;
                }

                Snackbar.make(getWindow().getDecorView().getRootView(),adspin.getItem(position) + "를 선택", Snackbar.LENGTH_SHORT).show();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}