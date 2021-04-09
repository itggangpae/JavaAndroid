package itstudy.kakao.supportlibrary;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

class MyPagerAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> fragments;
    public MyPagerAdapter(FragmentManager manager){
        super(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        fragments=new ArrayList<>();
        fragments.add(new OneFragment());
        fragments.add(new ThreeFragment());
    }
    @Override
    public int getCount() {
        return 2;
    }
    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }
}