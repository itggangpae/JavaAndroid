package itstudy.kakao.androidactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class BasicAppActivity extends AppCompatActivity {

    Button contactsBtn;
    Button cameraDataBtn;
    Button speechBtn;
    Button mapBtn;
    Button browserBtn;
    Button callBtn;

    TextView resultView;
    ImageView resultImageView;

    int reqWidth;
    int reqHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_app);

        resultView = (TextView) findViewById(R.id.resultView);
        contactsBtn = (Button) findViewById(R.id.btn_contacts);
        cameraDataBtn = (Button) findViewById(R.id.btn_camera_data);
        speechBtn = (Button) findViewById(R.id.btn_speech);
        mapBtn = (Button) findViewById(R.id.btn_map);
        browserBtn = (Button) findViewById(R.id.btn_browser);
        callBtn = (Button) findViewById(R.id.btn_call);
        resultImageView = (ImageView) findViewById(R.id.resultImageView);

        View.OnClickListener clickHandler = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == contactsBtn) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                    startActivityForResult(intent, 10);
                } else if (v == cameraDataBtn) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 30);
                } else if (v == speechBtn) {
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "음성인식 테스트");
                    startActivityForResult(intent, 50);
                } else if (v == mapBtn) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.5662952,126.9779451"));
                    startActivity(intent);
                } else if (v == browserBtn) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.seoul.go.kr"));
                    startActivity(intent);
                } else if (v == callBtn) {
                    if (ContextCompat.checkSelfPermission(BasicAppActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-3790-1997"));
                        startActivity(intent);
                    } else {
                        ActivityCompat.requestPermissions(BasicAppActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 100);
                    }
                }
            }
        };

        contactsBtn.setOnClickListener(clickHandler);
        cameraDataBtn.setOnClickListener(clickHandler);
        speechBtn.setOnClickListener(clickHandler);
        mapBtn.setOnClickListener(clickHandler);
        browserBtn.setOnClickListener(clickHandler);
        callBtn.setOnClickListener(clickHandler);
        resultImageView.setOnClickListener(clickHandler);
        reqWidth = getResources().getDimensionPixelSize(R.dimen.request_image_width);
        reqHeight = getResources().getDimensionPixelSize(R.dimen.request_image_height);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK) {
            String result = data.getDataString();
            resultView.setText(result);
        } else if (requestCode == 30 && resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            resultImageView.setImageBitmap(bitmap);
        } else if (requestCode == 50 && resultCode == RESULT_OK) {
            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String result = results.get(0);
            resultView.setText(result);
        }
    }

}