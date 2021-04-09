package itstudy.kakao.supportlibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class CoordinatorActivity extends AppCompatActivity {
    CoordinatorLayout coordinatorLayout;

    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private List<String> list;

        public MyAdapter(List<String> list) {
            this.list = list;
        }
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(android.R.layout.simple_list_item_1, viewGroup, false);
            return new MyViewHolder(view);
        }
        @Override
        public void onBindViewHolder(MyViewHolder viewHolder, int position) {
            String text = list.get(position);
            viewHolder.title.setText(text);
        }
        @Override
        public int getItemCount() {
            return list.size();
        }
    }


    private class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator);

        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);

        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recycler);

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("Item=" + i);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(list));

        FloatingActionButton fab=(FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(coordinatorLayout, "I am SnackBar", Snackbar.LENGTH_LONG)
                        .setAction("MoreAtion", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //.......................
                            }
                        })
                        .show();
            }
        });
        coordinatorLayout=(CoordinatorLayout)findViewById(R.id.coordinator);

    }
}