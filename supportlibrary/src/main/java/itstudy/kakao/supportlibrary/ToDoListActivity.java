package itstudy.kakao.supportlibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class ToDoListActivity extends AppCompatActivity {
    //공통된 속성을 저장할 상위 클래스
    static class ItemVO {
        public  int type;
        static int  TYPE_HEADER = 0;
        static int  TYPE_DATA = 1;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    //헤더에 사용할 데이터 클래스
    class HeaderItem extends ItemVO{
        private String date;


        public HeaderItem(String date){
            this.date = date;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        @Override
        public String toString() {
            return "HeaderItem{" +
                    "type=" + type +
                    ", date='" + date + '\'' +
                    '}';
        }
    }

    //항목에 사용할 데이터 클래스
    class DataItem extends ItemVO{
        private int id;
        private String title;
        private String conternt;
        private boolean completed;

        public DataItem(int id, String title, String conternt, boolean completed) {
            this.id = id;
            this.title = title;
            this.conternt = conternt;
            this.completed = completed;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getConternt() {
            return conternt;
        }

        public void setConternt(String conternt) {
            this.conternt = conternt;
        }

        public boolean isCompleted() {
            return completed;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }

        @Override
        public String toString() {
            return "DataItem{" +
                    "id=" + id +
                    ", type=" + type +
                    ", title='" + title + '\'' +
                    ", conternt='" + conternt + '\'' +
                    ", completed=" + completed +
                    '}';
        }
    }


    //헤더 뷰 설정
    class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView headerView;
        public HeaderViewHolder(View view){
            super(view);
            headerView = (TextView)view.findViewById(R.id.itemHeaderView);
        }
    }

    //항목 뷰 설정
    class DataViewHolder extends RecyclerView.ViewHolder{
        ImageView completedIconView;
        TextView itemTitleView;
        TextView itemContentView;

        public DataViewHolder(View view){
            super(view);

            completedIconView = (ImageView)view.findViewById(R.id.completedIconView);
            itemTitleView = (TextView)view.findViewById(R.id.itemTitleView);
            itemContentView = (TextView)view.findViewById(R.id.itemContentView);
        }
    }

    //데이터 목록을 저장할 List
    List<ItemVO> list  = new ArrayList<>();

    //항목을 출력할 어댑터 설정
    class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        List<ItemVO> list;

        public MyAdapter(List<ItemVO> list) {
            this.list = list;
        }

        //아이템 항목의 종류를 설정하는 함수
        public int getItemViewType(int position) {
            return list.get(position).type;
        }

        //헤더와 항목 뷰를 설정하는 함수
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            if (viewType == ItemVO.TYPE_HEADER) {
                return new HeaderViewHolder(layoutInflater.inflate(R.layout.item_header, parent, false));
            } else {
                return new DataViewHolder(layoutInflater.inflate(R.layout.item_main, parent, false));
            }
        }

        //뷰에 데이터를 바인딩 하는 함수
        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ItemVO itemVO = list.get(position);
            if (itemVO.type == ItemVO.TYPE_HEADER) {
                HeaderViewHolder viewHolder = (HeaderViewHolder)holder;
                HeaderItem headerItem = (HeaderItem)itemVO;
                viewHolder.headerView.setText(headerItem.getDate());
            } else {
                DataViewHolder viewHolder = (DataViewHolder)holder;
                DataItem dataItem = (DataItem)itemVO;
                viewHolder.itemTitleView.setText(dataItem.getTitle());
                viewHolder.itemContentView.setText(dataItem.getConternt());

                if (dataItem.completed) {
                    viewHolder.completedIconView.setImageResource(R.drawable.icon_completed);
                } else {
                    viewHolder.completedIconView.setImageResource(R.drawable.icon);
                }
                viewHolder.completedIconView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DBHelper helper = new DBHelper(ToDoListActivity.this);
                        SQLiteDatabase db = helper.getWritableDatabase();
                        Log.e("id", dataItem.getId() + "");
                        Log.e("completed", dataItem.completed + "");

                        if (dataItem.completed) {
                            db.execSQL("update tb_todo set completed=? where _id=?", new Object[]{0, dataItem.getId()});
                            viewHolder.completedIconView.setImageResource(R.drawable.icon);
                        } else {
                            db.execSQL("update tb_todo set completed=? where _id=?", new Object[]{1, dataItem.getId()});
                            viewHolder.completedIconView.setImageResource(R.drawable.icon_completed);
                        }
                        dataItem.completed = !dataItem.completed;
                        db.close();
                    }
                });
            }
        }

        //출력할 항목의 개수 설정
        @Override
        public int getItemCount() {
            return list.size();
        }
    }


    //항목 뷰를 설정하는 클래스
     class MyDecoration extends RecyclerView.ItemDecoration{
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state){
            super.getItemOffsets(outRect, view, parent, state);
            int index = parent.getChildAdapterPosition(view);
            ItemVO itemVO = list.get(index);
            if (itemVO.type == ItemVO.TYPE_DATA) {
                view.setBackgroundColor(0xFFFFFFFF);
                ViewCompat.setElevation(view, 10.0f);
            }
            outRect.set(20, 10, 20, 10);
        }
    }

    //데이터베이스를 읽이서 list에 저장하고 RecyclerView에 데이터를 설정하는 함수
    private void selectDB(){
        list = new ArrayList<>();
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from tb_todo order by date desc", null);
        Calendar preDate = null;
        try {
            while (cursor.moveToNext()) {
                String dbdate = cursor.getString(3);
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dbdate);
                GregorianCalendar currentDate = new GregorianCalendar();
                currentDate.setTime(date);

                if (!currentDate.equals(preDate)) {
                    HeaderItem headerItem = new HeaderItem(dbdate);
                    headerItem.setType(0);
                    list.add(headerItem);
                    preDate = currentDate;
                    boolean completed = cursor.getInt(4) != 0;
                    DataItem dataItem = new DataItem(
                            cursor.getInt(0), cursor.getString(1), cursor.getString(2), completed);
                    dataItem.setType(1);
                    list.add(dataItem);


                }
            }
        }catch(Exception e){
            Log.e("데이터 읽기 예외", e.getLocalizedMessage());
        }
        Log.e("데이터", list.toString());

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(list));
        recyclerView.addItemDecoration(new MyDecoration());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        //데이터 가져오는 함수를 호출
        selectDB();

        //플로팅 액션 버튼의 클릭 이벤트 처리
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ToDoListActivity.this, AddToDoActivity.class);
                startActivityForResult(intent, 10);
            }
        });
    }

    //하위 Activity를 호출한 후 호출되는 함수 재정의
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==10 && resultCode== Activity.RESULT_OK){
            selectDB();
        }
    }

}