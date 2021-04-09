package itstudy.kakao.supportlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class AddToDoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do);

        TextView addDateView = (TextView)findViewById(R.id.addDateView);

        addDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int  day = c.get(Calendar.DAY_OF_MONTH);

                new  DatePickerDialog(AddToDoActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        addDateView.setText(year + "-" + (month+1) + "-" + dayOfMonth);
                    }
                }, year, month, day).show();
            }
        });
    }

    public boolean  onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        EditText addTitleEditView = (EditText)findViewById(R.id.addTitleEditView);
        EditText addContentEditView = (EditText)findViewById(R.id.addContentEditView);
        TextView addDateView = (TextView)findViewById(R.id.addDateView);
        if(item.getItemId()==R.id.menu_add){
            if(addTitleEditView.getText().toString() != null && addContentEditView.getText().toString() != null) {
                DBHelper helper = new DBHelper(this);
                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("title", addTitleEditView.getText().toString());
                contentValues.put("content", addContentEditView.getText().toString());
                contentValues.put("date", addDateView.getText().toString());
                contentValues.put("completed", 0);
                db.insert("tb_todo", null, contentValues);
                db.close();
                setResult(Activity.RESULT_OK);
                finish();
            } else {
                Snackbar.make(getWindow().getDecorView().getRootView(), "모든 데이터가 입력되지 않았습니다.", Snackbar.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

}