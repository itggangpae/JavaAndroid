package itstudy.kakao.adapterview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class MovieAdapter extends BaseAdapter {
    //뷰를 출력할 때 필요한 Context(문맥-어떤 작업을 하기 위해 필요한 정보를 저장한 객체) 변수
    Context context;
    //ListView에 출력할 데이터
    List<Movie> data;
    //항목 뷰에 해당하는 레이아웃의 아이디를 저장할 변수
    int layout;

    //xml로 만들어진 레이아웃을 뷰로 변환하기 위한 클래스의 변수
    LayoutInflater inflater;

    //생성자
    public MovieAdapter(Context context, List<Movie> data, int layout) {
        this.context = context;
        this.data = data;
        this.layout = layout;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //이미지 뷰에 다운로드 받은 이미지를 출력하는 핸들러
    Handler imageHandler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message msg) {
            Map<String, Object> map = (Map<String, Object>) msg.obj;
            ImageView imageview = (ImageView) map.get("imageview");
            Bitmap bit = (Bitmap) map.get("bit");
            imageview.setImageBitmap(bit);
        }
    };

    //이미지를 다운로드 받는 스레드
    class ImageThread extends Thread {
        String imagename;
        ImageView imageview;

        public void run() {
            try {
                InputStream inuptStream = new URL("http://cyberadam.cafe24.com/movieimage/" + imagename).openStream();
                Bitmap bit = BitmapFactory.decodeStream(inuptStream);
                inuptStream.close();
                Message message = new Message();
                Map<String, Object> map = new HashMap<>();
                map.put("bit", bit);
                map.put("imageview", imageview);
                message.obj = map;
                imageHandler.sendMessage(message);
            } catch (Exception e) {
                Log.e("이미지 다운로드 실패", e.getLocalizedMessage());
            }
        }
    }

    //출력할 데이터의 개수를 설정하는 메소드
    @Override
    public int getCount() {
        return data.size();
    }

    //항목 뷰에 보여질 문자열을 설정하는 메소드
    //position은 반복문이 수행될 때의 인덱스
    @Override
    public Object getItem(int position) {
        return data.get(position).getTitle();
    }

    //각 항목뷰의 아이디를 설정하는 메소드
    @Override
    public long getItemId(int position) {
        return (long) position;
    }

    //ListView에 출력될 실제 뷰의 모양을 설정하는 메소드
    //convertView는 화면에 보여질 뷰인데 처음에는 null이 넘어오고 두번째 부터는
    //이전에 출력된 뷰가 넘어옵니다.
    //인덱스마다 다른 뷰를 출력하고자 하면 convertView를 새로 만들지만
    //모든 항목뷰의 모양이 같다면 처음 한번만 만들면 됩니다.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View returnView = convertView;
        //convertView 생성
        if (returnView == null) {
            //layout에 정의된 뷰를 parent에 넣을 수 있도록 View로 생성
            returnView = inflater.inflate(layout, parent, false);
        }

        //이미지 출력
        ImageView imgView = (ImageView) returnView.findViewById(R.id.movieimage);
        ImageThread imagethread = new ImageThread();
        imagethread.imagename = data.get(position).getThumbnail();
        imagethread.imageview = imgView;
        imagethread.start();

        //텍스트 출력
        TextView titleview = (TextView) returnView.findViewById(R.id.movietitle);
        titleview.setText(data.get(position).getTitle());

        TextView subtitleview = (TextView) returnView.findViewById(R.id.moviesubtitle);
        subtitleview.setText(data.get(position).getSubtitle());

        RatingBar ratingview = (RatingBar) returnView.findViewById(R.id.movierating);
        ratingview.setRating((float) data.get(position).getRating() / 5);
        Log.e("rating", ratingview.getRating() + "");
        return returnView;
    }
}
