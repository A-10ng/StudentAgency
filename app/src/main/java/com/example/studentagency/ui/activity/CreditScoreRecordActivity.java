package com.example.studentagency.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.studentagency.R;
import com.example.studentagency.bean.CreditBean;
import com.example.studentagency.mvp.presenter.CreditScoreRecordActivityBasePresenter;
import com.example.studentagency.mvp.view.CreditScoreRecordActivityBaseView;
import com.example.studentagency.ui.adapter.MyFragmentPagerAdapter;
import com.example.studentagency.ui.fragment.CreditScoreRecord.AllRecordFragment;
import com.example.studentagency.ui.fragment.CreditScoreRecord.InputRecordFragment;
import com.example.studentagency.ui.fragment.CreditScoreRecord.OutputRecordFragment;
import com.example.studentagency.ui.widget.RuleDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class CreditScoreRecordActivity extends BaseActivity implements CreditScoreRecordActivityBaseView, View.OnClickListener {

    private static final String TAG = "CreditScoreRecordActivi";
    private CreditScoreRecordActivityBasePresenter presenter = new CreditScoreRecordActivityBasePresenter(this);

    //规则
    private TextView tv_creditRule;

    //信誉积分
    private TextView tv_creditScore;

    /**
     * 信誉记录
     */
    private List<Fragment> fragments;
    private ViewPager viewPager;
    //顶部导航栏的总布局
    private LinearLayout llayout_allRecord, llayout_inputRecord, llayout_outputRecord;
    //顶部导航栏的指示条
    private View all_indicator, input_indicator, output_indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_score_record);

        initViews();

        //绑定fragment到activity上
        bindingFragment();

        //获取信誉积分
        presenter.getCreditScore();
    }

    private void initViews() {
        //规则
        tv_creditRule = findViewById(R.id.tv_creditRule);
        tv_creditRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RuleDialogFragment dialogFragment = new RuleDialogFragment(CreditScoreRecordActivity.this);
                dialogFragment.show(getSupportFragmentManager(),"RuleDialogFragment");
            }
        });

        //信誉积分
        tv_creditScore = findViewById(R.id.tv_creditScore);

        /**
         * 信誉记录
         */
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
        llayout_allRecord = findViewById(R.id.llayout_allRecord);
        llayout_inputRecord = findViewById(R.id.llayout_inputRecord);
        llayout_outputRecord = findViewById(R.id.llayout_outputRecord);
        llayout_allRecord.setOnClickListener(this);
        llayout_inputRecord.setOnClickListener(this);
        llayout_outputRecord.setOnClickListener(this);

        //顶部导航栏的指示条
        all_indicator = findViewById(R.id.all_indicator);
        input_indicator = findViewById(R.id.input_indicator);
        output_indicator = findViewById(R.id.output_indicator);
    }

    private void bindingFragment() {
        //准备fragment
        fragments = new ArrayList<>();
        fragments.add(new AllRecordFragment());
        fragments.add(new InputRecordFragment());
        fragments.add(new OutputRecordFragment());

        //将viewpager与fragment绑定
        MyFragmentPagerAdapter pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(pagerAdapter);
    }

    //改变文字和指示条的样式
    private void changeTab(int position) {
        switch (position) {
            //全部
            case R.id.llayout_allRecord:
            case 0:
                all_indicator.setVisibility(View.VISIBLE);
                input_indicator.setVisibility(View.GONE);
                output_indicator.setVisibility(View.GONE);

                viewPager.setCurrentItem(0);
                break;
            //收入
            case R.id.llayout_inputRecord:
            case 1:
                all_indicator.setVisibility(View.GONE);
                input_indicator.setVisibility(View.VISIBLE);
                output_indicator.setVisibility(View.GONE);

                viewPager.setCurrentItem(1);
                break;
            //支出
            case R.id.llayout_outputRecord:
            case 2:
                all_indicator.setVisibility(View.GONE);
                input_indicator.setVisibility(View.GONE);
                output_indicator.setVisibility(View.VISIBLE);

                viewPager.setCurrentItem(2);
                break;
        }
    }

    @Override
    public void getCreditScoreSuccess(Integer score) {
        Log.i(TAG, "getCreditScoreSuccess: score>>>>>" + score);

        tv_creditScore.setText("" + score);
    }

    @Override
    public void getCreditScoreFail() {
        Log.i(TAG, "getCreditScoreFail: ");
        tv_creditScore.setText("0");
    }

    @Override
    public void onClick(View v) {
        changeTab(v.getId());
    }
}
