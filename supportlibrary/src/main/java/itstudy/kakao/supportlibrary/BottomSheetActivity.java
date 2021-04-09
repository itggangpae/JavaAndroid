package itstudy.kakao.supportlibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class BottomSheetActivity extends AppCompatActivity {
    class Data {
        public String title;
        public Drawable image;
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;

        public ItemHolder(View root) {
            super(root);
            imageView = (ImageView) itemView.findViewById(R.id.sheet_row_imageView);
            textView = (TextView) itemView.findViewById(R.id.sheet_row_textView);
        }
    }

    class RecyclerViewAdapter extends RecyclerView.Adapter<ItemHolder> {
        private List<Data> list;

        public RecyclerViewAdapter(List<Data> list) {
            this.list = list;
        }

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.sheet_row, parent, false);
            return new ItemHolder(root);
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, int position) {
            Data vo = list.get(position);
            holder.textView.setText(vo.title);
            holder.imageView.setImageDrawable(vo.image);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    Button btn;
    CoordinatorLayout coordinatorLayout;
    BottomSheetBehavior<View> persistentBottomSheet;
    BottomSheetDialog modalBottomSheet;

    private void createDialog() {
        List<Data> list = new ArrayList<>();
        Data vo = new Data();
        vo.title = "Keep";
        vo.image = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_1, null);
        list.add(vo);

        vo = new Data();
        vo.title = "Inbox";
        vo.image = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_2, null);
        list.add(vo);

        vo = new Data();
        vo.title = "Messanger";
        vo.image = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_3, null);
        list.add(vo);

        vo = new Data();
        vo.title = "Google+";
        vo.image = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_4, null);
        list.add(vo);

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(list);
        View view = getLayoutInflater().inflate(R.layout.modal_sheet, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        modalBottomSheet = new BottomSheetDialog(this);
        modalBottomSheet.setContentView(view);
        modalBottomSheet.show();
    }

    private void initPeristentBottomSheet() {
        View bottomSheet = coordinatorLayout.findViewById(R.id.bottom_sheet);
        persistentBottomSheet = BottomSheetBehavior.from(bottomSheet);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);
        btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog();
            }
        });

        initPeristentBottomSheet();

    }
}