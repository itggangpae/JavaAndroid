package itstudy.kakao.supportlibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager manager=getSupportFragmentManager();
        OneFragment oneFragment=new OneFragment();
        TwoFragment twoFragment=new TwoFragment();
        ThreeFragment threeFragment=new ThreeFragment();

        FragmentTransaction tf=manager.beginTransaction();
        tf.addToBackStack(null);
        tf.add(R.id.main_container, oneFragment);
        tf.commit();

        Button btn1=findViewById(R.id.main_btn1);
        Button btn2=findViewById(R.id.main_btn2);
        Button btn3=findViewById(R.id.main_btn3);

        View.OnClickListener eventHandler = new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(v==btn1){
                    if(!oneFragment.isVisible()){
                        FragmentTransaction tf=manager.beginTransaction();
                        tf.addToBackStack(null);
                        tf.replace(R.id.main_container, oneFragment);
                        tf.commit();
                    }
                }else if(v==btn2){
                    if(!twoFragment.isVisible()){
                        twoFragment.show(manager, null);
                    }
                }
                else if(v==btn3){
                    if(!threeFragment.isVisible()){
                        FragmentTransaction tf=manager.beginTransaction();
                        tf.addToBackStack(null);
                        tf.replace(R.id.main_container, threeFragment);
                        tf.commit();
                    }
                }
            }
        };

        btn1.setOnClickListener(eventHandler);
        btn2.setOnClickListener(eventHandler);
        btn3.setOnClickListener(eventHandler);

        ViewPager pager=findViewById(R.id.pager);
        MyPagerAdapter adapter=new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
    }
}