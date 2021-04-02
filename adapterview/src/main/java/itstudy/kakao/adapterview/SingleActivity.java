package itstudy.kakao.adapterview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class SingleActivity extends AppCompatActivity {
    ArrayList<String> data;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);

        //Model의 역할을 하는 데이터 생성
        data = new ArrayList<String>();
        data.add("Oracle");
        data.add("MySQL");
        data.add("MongoDB");

        //Controller 역할을 하는 Adapter 객체 생성
        adapter = new ArrayAdapter(
                this, android.R.layout.simple_list_item_single_choice, data);

        //View의 역할을 하는 ListView 만들기
        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        //list에서 항목을 선택했을 때 호출되는 메소드 설정
        //list에서 항목을 선택했을 때 호출되는 메소드 설정
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = data.get(position);
                Snackbar.make(view, item, Snackbar.LENGTH_LONG).show();
            }
        });

        //ADD 버튼 클릭 이벤트 처리
        Button add = (Button)findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText newItem = (EditText)findViewById(R.id.newitem);
                String item = newItem.getText().toString();
                if (item != null && item.trim().length() > 0) {
                    data.add(item.trim());
                    //데이터가 갱신되었다는 사실을 리스트뷰에게 통보
                    //ListView를 다시 출력합니다.
                    adapter.notifyDataSetChanged();
                    newItem.setText("");
                }
            }
        });

        //DELETE 버튼 클릭 이벤트 처리
        Button delete = (Button)findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //체크된 항목 가져오기
                int pos = listView.getCheckedItemPosition();
                if (pos >= 0 && pos < data.size()) {
                    data.remove(pos);
                    //선택된 것 해제
                    listView.clearChoices();
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }
}