package itstudy.kakao.androiduserinterface;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewAttributeActivity extends AppCompatActivity implements View.OnClickListener {
    Button trueBtn;
    Button falseBtn;
    TextView targetTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attribute);

        trueBtn = (Button) findViewById(R.id.btn_visible_true);
        targetTextView = (TextView) findViewById(R.id.text_visible_target);
        falseBtn = (Button) findViewById(R.id.btn_visible_false);
        trueBtn.setOnClickListener(this);
        falseBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == trueBtn) {
            targetTextView.setVisibility(View.VISIBLE);
        } else if (v == falseBtn) {
            targetTextView.setVisibility(View.INVISIBLE);
        }
    }
}

