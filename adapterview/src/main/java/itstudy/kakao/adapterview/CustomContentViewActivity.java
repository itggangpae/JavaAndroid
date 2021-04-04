package itstudy.kakao.adapterview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class CustomContentViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_content_view);

        //ListView에 출력할 데이터 생성
        List<VO> data = new ArrayList<VO>();
        VO vo = new VO();
        vo.setIcon(R.mipmap.ic_launcher);
        //Stack:LIFO(Last Input First Out)-마지막에 삽입된 데이터가 먼저 출력
        //함수의 데이터 저장에 사용
        vo.setName("Stack");
        data.add(vo);
        vo = new VO();
        vo.setIcon(R.mipmap.ic_launcher);
        vo.setName("Queue");
        data.add(vo);
        vo = new VO();
        vo.setIcon(R.mipmap.ic_launcher);
        vo.setName("Deque");
        data.add(vo);

        //데이터를 ListView에 출력할 수 있도록 Adapter에 주입
        MyAdapter adapter = new MyAdapter(
                this, data, R.layout.icontext);

        //리스트에 adapter 연결
        ListView list = (ListView)findViewById(R.id.listView);
        list.setAdapter(adapter);


        AnimationSet set = new AnimationSet(true);
        Animation rtl = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 1.0f,Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f
        );
        rtl.setDuration(1000);
        set.addAnimation(rtl);

        Animation alpha = new AlphaAnimation(0.0f, 1.0f);
        alpha.setDuration(1000);
        set.addAnimation(alpha);

        LayoutAnimationController controller =
                new LayoutAnimationController(set, 0.5f);
        list.setLayoutAnimation(controller);

    }
}