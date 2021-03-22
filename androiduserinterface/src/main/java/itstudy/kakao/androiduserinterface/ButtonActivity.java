package itstudy.kakao.androiduserinterface;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.ToggleButton;

public class ButtonActivity extends AppCompatActivity {
    private ToggleButton togBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button);

        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                EditText edit = (EditText) findViewById(R.id.edit);
                edit.setText("버튼 누름");
            }
        });

        togBtn = (ToggleButton) findViewById(R.id.MyToggle);

        RadioGroup.OnCheckedChangeListener radioCheck =
                new RadioGroup.OnCheckedChangeListener() {
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (group.getId() == R.id.ColorGroup) {
                            switch (checkedId) {
                                case R.id.Red:
                                    togBtn.setTextColor(Color.RED);
                                    break;
                                case R.id.Green:
                                    togBtn.setTextColor(Color.GREEN);
                                    break;
                                case R.id.Blue:
                                    togBtn.setTextColor(Color.BLUE);
                                    break;
                            }
                        }
                    }
                };
        CompoundButton.OnCheckedChangeListener checkChange =
                new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (buttonView.getId() == R.id.BigFont) {
                            if (isChecked) {
                                togBtn.setTextSize(40);
                            } else {
                                togBtn.setTextSize(20);
                            }
                        }
                    }
                };

        RadioGroup colGroup = (RadioGroup) findViewById(R.id.ColorGroup);
        colGroup.setOnCheckedChangeListener(radioCheck);

        CheckBox chkBig = (CheckBox) findViewById(R.id.BigFont);
        chkBig.setOnCheckedChangeListener(checkChange);
    }
}