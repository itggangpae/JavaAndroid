package itstudy.kakao.supportlibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class RecyclerBasicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_basic);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        PersonAdapter adapter = new PersonAdapter();

        adapter.addItem(new Person("김좌진", "010-1000-1000"));
        adapter.addItem(new Person("홍범도", "010-2000-2000"));
        adapter.addItem(new Person("김원봉", "010-3000-3000"));
        adapter.addItem(new Person("김구", "010-4000-4000"));

        recyclerView.setAdapter(adapter);
    }
}