package itstudy.kakao.localdata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit = (EditText)findViewById(R.id.edittext);
    }

    public void click(View v) {
        switch (v.getId()) {
            case R.id.save:
                try {
                    FileOutputStream fos = openFileOutput("test.txt",
                            Context.MODE_PRIVATE);
                    String str = "안드로이드 파일 입출력";
                    fos.write(str.getBytes());
                    fos.close();
                    edit.setText("저장 성공");
                } catch (Exception e) {}
                break;
            case R.id.load:
                try {
                    FileInputStream fis = openFileInput("test.txt");
                    byte[] data = new byte[fis.available()];
                    while (fis.read(data) != -1) {;}
                    fis.close();
                    edit.setText(new String(data));
                } catch (FileNotFoundException e) {
                    edit.setText("파일이 없습니다.");
                }
                catch (Exception e) {;}
                break;
            case R.id.loadres:
                try {
                    InputStream fres = getResources().openRawResource(R.raw.creed);
                    byte[] data = new byte[fres.available()];
                    while (fres.read(data) != -1) {;}
                    fres.close();
                    edit.setText(new String(data));
                } catch (Exception e) {;}
                break;
            case R.id.delete:
                if (deleteFile("test.txt")) {
                    edit.setText("삭제 성공");
                } else {
                    edit.setText("삭제 실패");
                }
                break;
        }
    }
}