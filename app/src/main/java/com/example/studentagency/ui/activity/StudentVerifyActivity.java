package com.example.studentagency.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.studentagency.R;
import com.example.studentagency.Utils.DateUtils;
import com.example.studentagency.Utils.FileUtils;
import com.example.studentagency.Utils.ImageUtils;
import com.example.studentagency.mvp.presenter.StudentVerifyActivityBasePresenter;
import com.example.studentagency.mvp.view.StudentVerifyActivityBaseView;
import com.example.studentagency.ui.fragment.StudentVerifyFragment.ErrorFragment;
import com.example.studentagency.ui.fragment.StudentVerifyFragment.UnverifyFragment;
import com.example.studentagency.ui.fragment.StudentVerifyFragment.VerifiedFragment;
import com.example.studentagency.ui.fragment.StudentVerifyFragment.VerifyingFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.File;
import java.io.IOException;

public class StudentVerifyActivity extends BaseActivity implements StudentVerifyActivityBaseView {

    /**
     * 网络异常:-1,0
     * 未审核:1
     * 审核中:2
     * 已审核:3
     */
    private static final String TAG = "StudentVerifyActivity";
    private StudentVerifyActivityBasePresenter presenter = new StudentVerifyActivityBasePresenter(this);
    private int INT_STUDENT_VERVIFY = -1;

    //菊花加载图
    private ImageView iv_loading;

    //smartRefreshLayout
    private SmartRefreshLayout smartRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_verify);

        initAllViews();

        initSmartRefreshLayout();

        //获取personFragment传过来的INT_STUDENT_VERVIFY
        getVerifyState();
    }

    private void initAllViews() {
        //菊花加载图
        iv_loading = findViewById(R.id.iv_loading);
        Glide.with(this)
                .asGif()
                .load(R.drawable.loading)
                .into(iv_loading);

        smartRefreshLayout = findViewById(R.id.smartRefreshLayout);
    }

    private void initSmartRefreshLayout() {
        smartRefreshLayout.setEnableLoadMore(false);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //发送请求
                presenter.getVerifyState(MyApp.userId);
            }
        });
    }

    private void getVerifyState() {
        Intent intent = getIntent();
        if (null != intent) {
            INT_STUDENT_VERVIFY = intent.getIntExtra("INT_STUDENT_VERVIFY", -1);
        }

        if (-1 == INT_STUDENT_VERVIFY) {
            //发送请求
            presenter.getVerifyState(MyApp.userId);
        }
        else {
            setMatchingFragment(INT_STUDENT_VERVIFY);
        }
    }

    @Override
    public void getVerifyStateSuccess(Integer result) {
        Log.i(TAG, "getVerifyStateSuccess: result>>>>>" + result);

        INT_STUDENT_VERVIFY = result;

        setMatchingFragment(INT_STUDENT_VERVIFY);

        smartRefreshLayout.finishRefresh();
    }

    private void setMatchingFragment(int int_student_vervify) {
        //网络异常
        if (-1 == int_student_vervify || 0 == int_student_vervify) {
            replaceFragment(new ErrorFragment());
        }
        //未审核
        else if (1 == int_student_vervify) {
            replaceFragment(new UnverifyFragment());
        }
        //审核中
        else if (2 == int_student_vervify) {
            replaceFragment(new VerifyingFragment());
        }
        //已审核
        else {
            replaceFragment(new VerifiedFragment());
        }
    }

    private void replaceFragment(Fragment fragment) {
        iv_loading.setVisibility(View.GONE);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
    }

    @Override
    public void getVerifyStateFail() {
        Log.i(TAG, "getVerifyStateFail: ");

        INT_STUDENT_VERVIFY = -1;

        setMatchingFragment(INT_STUDENT_VERVIFY);

        smartRefreshLayout.finishRefresh();
    }
}
