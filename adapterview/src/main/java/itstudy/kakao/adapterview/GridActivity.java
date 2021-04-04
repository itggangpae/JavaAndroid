package itstudy.kakao.adapterview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class GridActivity extends AppCompatActivity {

    class ImageAdapter extends BaseAdapter {
        private Context mContext;
        int[] picture = { R.drawable.icon01, R.drawable.icon02, R.drawable.icon03, R.drawable.icon04,R.drawable.icon05, R.drawable.icon06};
        public ImageAdapter(Context c) {
            mContext = c;
        }
        public int getCount() {
            return 100;
        }
        public Object getItem(int position) {
            return picture[position % picture.length];
        }
        public long getItemId(int position) {
            return position;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
                imageView.setAdjustViewBounds(false);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }

            imageView.setImageResource(picture[position % picture.length]);

            return imageView;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        GridView grid = (GridView)findViewById(R.id.grid);
        ImageAdapter adapter = new ImageAdapter(this);
        grid.setAdapter(adapter);

        grid.setOnItemClickListener(new GridView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Snackbar.make(getWindow().getDecorView().getRootView(), position + "번째 그림 선택",
                        Snackbar.LENGTH_SHORT).show();
            }
        });

    }
}