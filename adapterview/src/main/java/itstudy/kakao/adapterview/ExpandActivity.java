package itstudy.kakao.adapterview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpandActivity extends AppCompatActivity {
    ExpandableListView list;

    String[] main = new String[] {
            "BackEnd", "FrontEnd", "Database"
    };

    String[][] sub = new String[][] {
            {"Java Spring", "JavaScript Node.js", "PHP Lalavel", "Python Django", "Ruby on Rails" },
            {"HTML", "CSS", "JavaScript", "jQuery", "react", "vue", "angular", "kotlin", "swift"},
            {"Oracle", "MySQL", "MS-SQL", "MongoDB"},
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand);

        list = (ExpandableListView)findViewById(R.id.list);

        //출력할 데이터 생성
        List<Map<String, String>> mainData = new ArrayList<Map<String, String>>();
        List<List<Map<String, String>>> subData =
                new ArrayList<List<Map<String, String>>>();
        for (int i = 0; i < main.length; i++) {
            Map<String, String> mainMap = new HashMap<String, String>();
            mainMap.put("main", main[i]);
            mainData.add(mainMap);

            List<Map<String, String>> children = new ArrayList<Map<String, String>>();
            for (int j = 0; j < sub[i].length; j++) {
                Map<String, String> subMap = new HashMap<String, String>();
                subMap.put("sub", sub[i][j]);
                children.add(subMap);
            }
            subData.add(children);
        }

        //출력 설정
        ExpandableListAdapter adapter = new SimpleExpandableListAdapter(
                this,
                mainData,
                android.R.layout.simple_expandable_list_item_1,
                new String[] { "main" },
                new int[] { android.R.id.text1 },
                subData,
                android.R.layout.simple_expandable_list_item_1,
                new String[] { "sub" },
                new int[] { android.R.id.text1 }
        );
        list.setAdapter(adapter);

    }
}