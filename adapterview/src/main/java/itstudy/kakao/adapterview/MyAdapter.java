package itstudy.kakao.adapterview;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MyAdapter extends BaseAdapter {
    //뷰를 출력할 때 필요한 Context(문맥-어떤 작업을 하기 위해 필요한 정보를 저장한 객체) 변수
    Context context;
    //ListView에 출력할 데이터
    List<VO> data;
    //항목 뷰에 해당하는 레이아웃의 아이디를 저장할 변수
    int layout;
    //xml로 만들어진 레이아웃을 뷰로 변환하기 위한 클래스의 변수
    LayoutInflater inflater;

    public MyAdapter(Context context, List<VO> data, int layout) {
        super();
        this.context = context;
        this.data = data;
        this.layout = layout;
        inflater = (LayoutInflater)context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    //출력할 데이터의 개수를 설정하는 메소드
    public int getCount() {
        return data.size();
    }

    @Override
    //항목 뷰에 보여질 문자열을 설정하는 메소드
    //position은 반복문이 수행될 때의 인덱스
    public Object getItem(int position) {
        return data.get(position).getName();
    }

    @Override
    //각 항목뷰의 아이디를 설정하는 메소드
    public long getItemId(int position) {
        return position;
    }


    @Override
    //ListView에 출력될 실제 뷰의 모양을 설정하는 메소드
    //convertView는 화면에 보여질 뷰인데 처음에는 null이 넘어오고 두번째 부터는
    //이전에 출력된 뷰가 넘어옵니다.
    //인덱스마다 다른 뷰를 출력하고자 하면 convertView를 새로 만들지만
    //모든 항목뷰의 모양이 같다면 처음 한번만 만들면 됩니다.
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        //convertView 생성
        if(convertView == null){
            //layout에 정의된 뷰를 parent에 넣을 수 있도록 View로 생성
            convertView = inflater.inflate(layout, parent, false);
        }
        if(position % 2 == 0)
            convertView.setBackgroundColor(Color.RED);
        else
            convertView.setBackgroundColor(Color.BLUE);

        //이미지 출력
        ImageView imgView = (ImageView)convertView.findViewById(R.id.img);
        imgView.setImageResource(data.get(pos).getIcon());
        //텍스트 출력
        TextView txt = (TextView)convertView.findViewById(R.id.text);
        txt.setText(data.get(pos).getName());

        //버튼의 이벤트 처리
        Button btn = (Button)convertView.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mes = "Select Item:" + data.get(pos).getName();
                Toast.makeText(context, mes,Toast.LENGTH_LONG).show();
            }
        });
        return convertView;
    }
}
