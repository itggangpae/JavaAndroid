package itstudy.kakao.androidlayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ConstraintUseActivity extends AppCompatActivity {
    private ConstraintSet applyConstraintSet = new ConstraintSet();
    private ConstraintSet resetConstraintSet = new ConstraintSet();
    private ConstraintLayout container;
    Button clickBtn;

    private void onApplyClick() {
        applyConstraintSet.setMargin(R.id.clickBtn, ConstraintSet.END, 8);
        applyConstraintSet.applyTo(container);
    }

    private void onResetClick() {
        resetConstraintSet.applyTo(container);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraint_use);

        container = (ConstraintLayout)findViewById(R.id.container);
        clickBtn = (Button)findViewById(R.id.clickBtn);


        clickBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.clickBtn) {
                    if (v.getTag().toString().equals("reset")) {
                        v.setTag("apply");
                        onApplyClick();
                    } else {
                        v.setTag("reset");
                        onResetClick();
                    }
                }
            }
        });

        applyConstraintSet.clone(container);
        resetConstraintSet.clone(container);

    }
}