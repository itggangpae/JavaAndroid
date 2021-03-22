package itstudy.kakao.androiduserinterface;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class TextChangeActivity extends AppCompatActivity {
    EditText edit;
    TextView text;

    TextWatcher watcher = new TextWatcher() {
        public void afterTextChanged(Editable s) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count,	int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            text.setText("echo:" + s);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_change);

        edit = (EditText)findViewById(R.id.edit);
        text = (TextView)findViewById(R.id.text);
        edit.addTextChangedListener(watcher);

    }
}