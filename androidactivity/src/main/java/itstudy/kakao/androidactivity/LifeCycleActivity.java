package itstudy.kakao.androidactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class LifeCycleActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> datas;
    ArrayAdapter<String> adapter;
    Button detailBtn;
    Button dialogBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_cycle);

        listView=(ListView)findViewById(R.id.main_list);
        detailBtn=(Button)findViewById(R.id.main_btn_detail);
        dialogBtn=(Button)findViewById(R.id.main_btn_dialog);


        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==detailBtn){
                    Intent intent=new Intent(LifeCycleActivity.this, SubLifeActivity.class);
                    startActivity(intent);
                }else if(v==dialogBtn){
                    Intent intent=new Intent(LifeCycleActivity.this, DialogActivity.class);
                    startActivity(intent);
                }
            }
        };
        detailBtn.setOnClickListener(clickListener);
        dialogBtn.setOnClickListener(clickListener);
        datas=new ArrayList<>();
        datas.add("onCreate....");
        adapter=new ArrayAdapter<String>(this, R.layout.item_main_list, datas);
        listView.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        datas.add("onResume....");
        adapter.notifyDataSetChanged();
    }
    @Override
    protected void onPause() {
        super.onPause();
        datas.add("onPause....");
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        datas.add("onStart....");
        adapter.notifyDataSetChanged();
    }
    @Override
    protected void onStop() {
        super.onStop();
        datas.add("onStop....");
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        datas.add("onRestart....");
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        datas.add("onDestory....");
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        datas.add("onSaveInstanceStatel....");
        adapter.notifyDataSetChanged();
        outState.putString("data1", "hello");
        outState.putInt("data2", 100);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        datas.add("onRestoreInstanceState....");
        adapter.notifyDataSetChanged();

        String data1=savedInstanceState.getString("data1");
        int data2=savedInstanceState.getInt("data2");
        Toast.makeText(this, data1+":"+data2, Toast.LENGTH_SHORT).show();
    }
}