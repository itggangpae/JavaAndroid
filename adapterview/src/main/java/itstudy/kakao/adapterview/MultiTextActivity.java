package itstudy.kakao.adapterview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiTextActivity extends AppCompatActivity {
    List<Map<String, String>> list;
    ArrayAdapter<String> adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_text);

        list = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("subject", "Java");
        map.put("description", "Object Oriented Programming");
        list.add(map);

        map = new HashMap<>();
        map.put("subject", "Oracle");
        map.put("description", "RDBMS");
        list.add(map);

        map = new HashMap<>();
        map.put("subject", "Mongo DB");
        map.put("description", "No SQL");
        list.add(map);

        map = new HashMap<>();
        map.put("subject", "JSP&Servlet");
        map.put("description", "Java Web Programming");
        list.add(map);

        map = new HashMap<>();
        map.put("subject", "Spring");
        map.put("description", "Java Framework");
        list.add(map);

        map = new HashMap<>();
        map.put("subject", "Android");
        map.put("description", "SmartPhone SDK");
        list.add(map);

        map = new HashMap<>();
        map.put("subject", "iOS");
        map.put("description", "SmartPhone SDK");
        list.add(map);

        SimpleAdapter adapter = new SimpleAdapter(this, list,android.R.layout.simple_list_item_2,
                new String[]{"subject", "description"}, new int[]{android.R.id.text1, android.R.id.text2});

        // 어댑터 연결
        listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }
}