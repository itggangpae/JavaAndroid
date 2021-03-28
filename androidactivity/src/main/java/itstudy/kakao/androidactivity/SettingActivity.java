package itstudy.kakao.androidactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {

    Button keyboardBtn;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        keyboardBtn=(Button)findViewById(R.id.toggleBtn);

        editText=(EditText)findViewById(R.id.edit);

        keyboardBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                InputMethodManager manager=(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                manager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        });
    }

    private void showToast(String message){
        Toast toast= Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showToast("onResume.....");
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.N){
            if(isInMultiWindowMode()){
                showToast("onResume....isInMultiWindowMode.. yes ");
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        showToast("onPause......");
    }
    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
        super.onMultiWindowModeChanged(isInMultiWindowMode);
        showToast("onMultiWindowModeChanged....."+isInMultiWindowMode);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation==Configuration.ORIENTATION_PORTRAIT){
            showToast("portrait.....");
        }else {
            showToast("landscape.....");
        }
    }
}
