package itstudy.kakao.adapterview;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String [] ar =  {"C&C++", "Java", "Python", "JavaScript"};


        /*
        List<String> ar = new ArrayList<String>();
        ar.add("C&C++");
        ar.add("Java");
        ar.add("Python");
        ar.add("JavaScript");
        */

        // 어댑터 준비
        //ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ar);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.pl, android.R.layout.simple_list_item_1);

        // 어댑터 연결
        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setDivider(new ColorDrawable(Color.RED));
        listView.setDividerHeight(2);

    }
}