package itstudy.kakao.multimedia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class CameraCouponActivity extends AppCompatActivity {

    FrameLayout previewFrame;
    CameraSurfaceView cameraView;

    RelativeLayout iconLayout;
    ImageView icon01Image;
    ImageView icon02Image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_coupon);

        previewFrame = (FrameLayout) findViewById(R.id.previewFrame);
        cameraView = (CameraSurfaceView) findViewById(R.id.cameraView);

        iconLayout = (RelativeLayout) findViewById(R.id.iconLayout);
        icon01Image = (ImageView) findViewById(R.id.icon01Image);
        icon02Image = (ImageView) findViewById(R.id.icon02Image);

        Button showButton = (Button) findViewById(R.id.showButton);
        showButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                iconLayout.setVisibility(View.VISIBLE);
            }
        });

        Button hideButton = (Button) findViewById(R.id.hideButton);
        hideButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                iconLayout.setVisibility(View.INVISIBLE);
            }
        });


    }
}

