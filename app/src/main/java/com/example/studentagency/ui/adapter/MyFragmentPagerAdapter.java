package com.example.studentagency.ui.adapter;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2019/11/03
 * desc:
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;

    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
