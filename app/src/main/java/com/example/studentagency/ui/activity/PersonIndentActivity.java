package com.example.studentagency.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.studentagency.R;
import com.example.studentagency.ui.adapter.MyFragmentPagerAdapter;
import com.example.studentagency.ui.fragment.PersonIndentActivity.AcceptFragment;
import com.example.studentagency.ui.fragment.PersonIndentActivity.PublishFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class PersonIndentActivity extends BaseActivity implements View.OnClickListener {

    private List<Fragment> fragments;
    private ViewPager viewPager;
    //顶部导航栏的总布局
    private LinearLayout llayout_publsih, llayout_accept;
    //顶部导航栏的文字
    private TextView tv_publsih, tv_accept, tv_current;
    //顶部导航栏的指示条
    private View publsih_indicator, accept_indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_indent);

        initViews();

        //绑定fragment到activity上
        bindingFragment();
    }

    private void initViews() {
        viewPager = findViewById(R.id.viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //顶部导航栏的总布局
        llayout_publsih = findViewById(R.id.llayout_publsih);
        llayout_accept = findViewById(R.id.llayout_accept);
        llayout_publsih.setOnClickListener(this);
        llayout_accept.setOnClickListener(this);

        //顶部导航栏的文字
        tv_publsih = findViewById(R.id.tv_publsih);
        tv_accept = findViewById(R.id.tv_accept);

        //顶部导航栏的指示条
        publsih_indicator = findViewById(R.id.publsih_indicator);
        accept_indicator = findViewById(R.id.accept_indicator);

        //默认选中已发布的textview
        tv_current = tv_publsih;
        tv_publsih.setSelected(true);
    }

    private void bindingFragment() {
        //准备fragment
        fragments = new ArrayList<>();
        fragments.add(new PublishFragment());
        fragments.add(new AcceptFragment());

        //将viewpager与fragment绑定
        MyFragmentPagerAdapter pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(pagerAdapter);
    }

    //改变文字和指示条的样式
    private void changeTab(int position) {
        //取消选中当前的图标和文字
        tv_current.setSelected(false);
        switch (position) {
            //已发布
            case R.id.llayout_publsih:
            case 0:
                publsih_indicator.setVisibility(View.VISIBLE);
                accept_indicator.setVisibility(View.GONE);

                tv_publsih.setSelected(true);
                tv_current = tv_publsih;

                viewPager.setCurrentItem(0);
                break;
            //已接取
            case R.id.llayout_accept:
            case 1:
                publsih_indicator.setVisibility(View.GONE);
                accept_indicator.setVisibility(View.VISIBLE);

                tv_accept.setSelected(true);
                tv_current = tv_accept;

                viewPager.setCurrentItem(1);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        changeTab(v.getId());
    }

    @Override
    public void finish() {
        super.finish();

        startActivity(new Intent(this,MainActivity.class));
    }
}
