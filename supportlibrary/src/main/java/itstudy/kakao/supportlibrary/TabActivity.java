package itstudy.kakao.supportlibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class TabActivity extends AppCompatActivity {
    ViewPager viewPager;
    FloatingActionButton fab;
    RelativeLayout relativeLayout;

    class MyPagerAdapter extends FragmentPagerAdapter {
        List<Fragment> fragments=new ArrayList<>();
        String title[]=new String[]{"Tab1", "Tab2", "Tab3"};
        public MyPagerAdapter(FragmentManager fm){
            super(fm);
            fragments.add(new TabOne());
            fragments.add(new TabTwo());
            fragments.add(new TabThree());
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        relativeLayout=(RelativeLayout)findViewById(R.id.container);
        viewPager=(ViewPager)findViewById(R.id.viewpager);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        TabLayout tabLayout=(TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        fab=(FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(relativeLayout, "I am SnackBar", Snackbar.LENGTH_LONG)
                        .setAction("MoreAction", new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {

                            }
                        }).show();
            }
        });

    }
}

