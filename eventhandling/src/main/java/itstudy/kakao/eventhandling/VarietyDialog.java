package itstudy.kakao.eventhandling;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import static java.util.Calendar.*;

public class VarietyDialog extends AppCompatActivity implements View.OnClickListener {
    Button alertBtn;
    Button listBtn;
    Button progressBtn;
    Button dateBtn;
    Button timeBtn;
    Button customDialogBtn;
    AlertDialog customDialog;
    AlertDialog listDialog;
    AlertDialog alertDialog;

    private void showToast(String message){
        Toast toast= Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onClick(View v) {
        if(v==alertBtn){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            builder.setTitle("알림");
            builder.setMessage("정말 종료 하시겠습니까?");
            builder.setPositiveButton("OK", dialogListener);
            builder.setNegativeButton("NO", null);
            alertDialog=builder.create();
            alertDialog.show();
        }else if(v==listBtn){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("알람 벨소리");
            builder.setSingleChoiceItems(R.array.dialog_array, 0, dialogListener);
            builder.setPositiveButton("확인", null);
            builder.setNegativeButton("취소", null);
            listDialog=builder.create();
            listDialog.show();
        }
        else if(v==progressBtn){
            ProgressDialog progressDialog=new ProgressDialog(this);
            progressDialog.setIcon(android.R.drawable.ic_dialog_alert);
            progressDialog.setTitle("Wait..");
            progressDialog.setMessage("서버 연동중입니다. 잠시만 기다리세요.");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }else if(v==dateBtn) {
            Calendar c = getInstance();
            int year = c.get(YEAR);
            int month = c.get(MONTH);
            int day = c.get(DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            showToast(year + ":" + (month + 1) + ":" + dayOfMonth);
                        }
                    }, year, month, day);
            datePickerDialog.show();
        }
        else if(v==timeBtn){
            Calendar c= getInstance();
            final int hour=c.get(HOUR_OF_DAY);
            int minute=c.get(MINUTE);
            TimePickerDialog timePickerDialog=new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            showToast(hourOfDay+":"+minute);
                        }
                    }, hour, minute, false);
            timePickerDialog.show();
        }else if(v==customDialogBtn){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            LayoutInflater inflater=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
            View view=inflater.inflate(R.layout.dialog_layout, null);
            builder.setView(view);
            builder.setPositiveButton("확인", dialogListener);
            builder.setNegativeButton("취소", null);
            customDialog=builder.create();
            customDialog.show();
        }
    }

        DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if(dialog==customDialog && which==DialogInterface.BUTTON_POSITIVE){
                showToast("custom dialog 확인 click....");
            }else if(dialog==listDialog){
                String[] datas=getResources().getStringArray(R.array.dialog_array);
                showToast(datas[which]+"선택하셨습니다.");
            }else if(dialog==alertDialog && which== DialogInterface.BUTTON_POSITIVE){
                showToast("alert dialog ok click....");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_variety_dialog);

        alertBtn=(Button)findViewById(R.id.btn_alert);
        listBtn=(Button)findViewById(R.id.btn_list);
        progressBtn=(Button)findViewById(R.id.btn_progress);
        dateBtn=(Button)findViewById(R.id.btn_date);
        timeBtn=(Button)findViewById(R.id.btn_time);
        customDialogBtn=(Button)findViewById(R.id.btn_custom);
        alertBtn.setOnClickListener(this);
        listBtn.setOnClickListener(this);
        progressBtn.setOnClickListener(this);
        dateBtn.setOnClickListener(this);
        timeBtn.setOnClickListener(this);
        customDialogBtn.setOnClickListener(this);
    }
}