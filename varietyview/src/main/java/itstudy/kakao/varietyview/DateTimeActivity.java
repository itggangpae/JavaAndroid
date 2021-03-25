package itstudy.kakao.varietyview;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateTimeActivity extends AppCompatActivity {
    //선택한 년월일 시분을 저장하기 위한 변수
    int mYear, month, day, hour, minute;
    //텍스트 뷰와 버튼을 참조하기 위한 변수
    TextView txtDate, txtTime;
    Button btnchangedate, btnchangetime;

    //텍스트 뷰에 날짜와 시간을 출력하기 위한 메소드
    void updateNow() {
        txtDate.setText(String.format("%4d/%02d/%02d", mYear,
                month + 1, day));
        txtTime.setText(String.format("%02d:%02d", hour, minute));
    }

    //날짜 선택 대화상자에서 날짜를 선택하고 ok를 눌렀을 때 호출되는 핸들러
    DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    month = monthOfYear;
                    day = dayOfMonth;
                    updateNow();
                }
            };

    //시간 선택 대화상자에서 시간 선택하고 ok를 눌렀을 때 호출되는 핸들러
    TimePickerDialog.OnTimeSetListener mTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    hour = hourOfDay;
                    minute = minute;
                    updateNow();
                }
            };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_time);

        txtDate = (TextView) findViewById(R.id.txtdate);
        txtTime = (TextView) findViewById(R.id.txttime);

        //버튼을 만들고 버튼을 눌렀을 때 수행할 코드
        btnchangedate = (Button) findViewById(R.id.btnchangedate);
        btnchangedate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(DateTimeActivity.this, mDateSetListener,
                        mYear, month, day).show();
            }
        });

        btnchangetime = (Button) findViewById(R.id.btnchangetime);
        btnchangetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(DateTimeActivity.this, mTimeSetListener,
                        hour, minute, false).show();
            }
        });

        //현재 시간을 가져오기
        Calendar cal = new GregorianCalendar();
        mYear = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        hour = cal.get(Calendar.HOUR_OF_DAY);
        minute = cal.get(Calendar.MINUTE);

        updateNow();

    }
}