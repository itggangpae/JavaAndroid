package itstudy.kakao.androiduserinterface;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputFilter;
import android.widget.EditText;

public class EditFilterActivity extends AppCompatActivity {
    EditText limitEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_filter);

        limitEdit = (EditText)findViewById(R.id.limit);
        limitEdit.setFilters(new InputFilter[] {
                new InputFilter.LengthFilter(3)
        });
    }
}
