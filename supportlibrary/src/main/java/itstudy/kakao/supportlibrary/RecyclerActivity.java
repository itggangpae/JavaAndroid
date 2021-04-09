package itstudy.kakao.supportlibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class RecyclerActivity extends AppCompatActivity {
    //RecyclerView 의 항목 뷰를 위한 클래스
    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public MyViewHolder(View itemView){
            super(itemView);
            title=itemView.findViewById(android.R.id.text1);
        }
    }

    //RecyclerView에 데이터를 연결해 줄 클래스
    class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{
        List<String> list;
        public MyAdapter(List<String> list){
            this.list=list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view= LayoutInflater.from(viewGroup.getContext())
                    .inflate(android.R.layout.simple_list_item_1, viewGroup, false);
            return new MyViewHolder(view);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
            String text=list.get(i);
            myViewHolder.title.setText(text);
        }
    }

    //RecyclerView 에 데코레이션을 위한 클래스
    class MyItemDecoration extends RecyclerView.ItemDecoration{
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            int index=parent.getChildAdapterPosition(view)+1;
            if(index % 3 == 0){
                outRect.set(20, 20, 20, 60);
            }else {
                outRect.set(20, 20, 20, 20);
            }
            view.setBackgroundColor(0xFFECE9E9);
            ViewCompat.setElevation(view, 20.0f);
        }

        @Override
        public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.onDrawOver(c, parent, state);
            int width=parent.getWidth();
            int height=parent.getHeight();
            Drawable dr= ResourcesCompat.getDrawable(getResources(), R.drawable.img, null);
            int drWidth=dr.getIntrinsicWidth();
            int drHeight=dr.getIntrinsicHeight();
            int left=width/2 - drWidth/2;
            int top=height/2 - drHeight/2;
            c.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.img), left, top, null);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        RecyclerView recyclerView=findViewById(R.id.recycler);

        List<String> list=new ArrayList<>();
        list.add("태연");
        list.add("제시카");
        list.add("유리");
        list.add("티파니");
        list.add("수영");
        list.add("써니");
        list.add("효연");
        list.add("윤아");
        list.add("서현");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(list));
        recyclerView.addItemDecoration(new MyItemDecoration());

    }
}