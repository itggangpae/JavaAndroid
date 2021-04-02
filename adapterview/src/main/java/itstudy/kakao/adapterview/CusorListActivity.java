package itstudy.kakao.adapterview;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CusorListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cusor_list);

        ListView titleView = (ListView) findViewById(R.id.main_listview_simple);
        ListView cursorView = (ListView) findViewById(R.id.main_listview_cursor);

        List<Map<String, String>> jobData = new ArrayList<>();

        JobDBHelper helper = new JobDBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from job_data", null);
        while (cursor.moveToNext()) {
            HashMap<String, String> map = new HashMap<>();
            map.put("name", cursor.getString(1));
            map.put("content", cursor.getString(2));
            jobData.add(map);
        }

        SimpleAdapter titleAdapter = new SimpleAdapter(this, jobData,
                android.R.layout.simple_list_item_2, new String[]{"name", "content"},
                new int[]{android.R.id.text1, android.R.id.text2});
        titleView.setAdapter(titleAdapter);

        CursorAdapter cursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2,
                cursor, new String[]{"name", "content"}, new int[]{android.R.id.text1, android.R.id.text2},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        cursorView.setAdapter(cursorAdapter);

    }
}