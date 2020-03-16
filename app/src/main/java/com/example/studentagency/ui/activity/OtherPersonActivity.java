package com.example.studentagency.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.studentagency.R;
import com.example.studentagency.asyncTask.GetBitmapTask;
import com.example.studentagency.bean.OtherPersonBean;
import com.example.studentagency.bean.ResponseBean;
import com.example.studentagency.mvp.presenter.OtherPersonActivityBasePresenter;
import com.example.studentagency.mvp.view.OtherPersonActivityBaseView;
import com.example.studentagency.utils.BlurUtils;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

public class OtherPersonActivity extends BaseActivity implements OtherPersonActivityBaseView {

    private static final String TAG = "OtherPersonActivity";
    private OtherPersonActivityBasePresenter presenter = new OtherPersonActivityBasePresenter(this);
    private String phoneNum;

    //下拉刷新
    private SmartRefreshLayout smartRefreshLayout;

    //头像区
    private ImageView iv_avatar, iv_avatar_bg;

    //用户名
    private TextView tv_username;

    //性别
    private ImageView iv_gender;

    //学校
    private TextView tv_school;

    //信誉
    private TextView tv_creditScore;
    private ImageView iv_verifyState;

    //发布数
    private TextView tv_publishNum;

    //接单数
    private TextView tv_acceptNum;

    //聊天
    private LinearLayout layout_chat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_person);

        getPassedInfo();

        initViews();

        initSmartRefreshLayout();

        presenter.getCurrentUserInfo(phoneNum);
    }

    private void getPassedInfo() {
        Intent intent = getIntent();
        if (null != intent) {
            phoneNum = intent.getStringExtra("phoneNum");
            Log.i(TAG, "getPassedInfo: phoneNum>>>>>" + phoneNum);
        }
    }

    private void initViews() {
        //头像区
        iv_avatar = findViewById(R.id.iv_avatar);
        iv_avatar_bg = findViewById(R.id.iv_avatar_bg);

        //用户名
        tv_username = findViewById(R.id.tv_username);

        //性别
        iv_gender = findViewById(R.id.iv_gender);

        //学校
        tv_school = findViewById(R.id.tv_school);

        //信誉
        tv_creditScore = findViewById(R.id.tv_creditScore);
        iv_verifyState = findViewById(R.id.iv_verifyState);

        //发布数
        tv_publishNum = findViewById(R.id.tv_publishNum);

        //接单数
        tv_acceptNum = findViewById(R.id.tv_acceptNum);

        //聊天
        layout_chat = findViewById(R.id.layout_chat);
        layout_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转至聊天界面

            }
        });
    }

    private void initSmartRefreshLayout() {
        smartRefreshLayout = findViewById(R.id.smartRefreshLayout);
        smartRefreshLayout.setEnableLoadMore(false);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshData();
            }
        });
    }

    private void refreshData() {
        presenter.getCurrentUserInfo(phoneNum);
    }

    @Override
    public void getCurrentUserInfoSuccess(ResponseBean responseBean) {
        Log.i(TAG, "getCurrentUserInfoSuccess: ");

        Gson gson = new Gson();
        OtherPersonBean otherPersonBean = gson.fromJson(gson.toJson(responseBean.getData()),OtherPersonBean.class);

        setCurrentUserInfo(otherPersonBean);

        smartRefreshLayout.finishRefresh();
    }

    private void setCurrentUserInfo(OtherPersonBean otherPersonBean) {
        if (null != otherPersonBean) {
            initAvatarAndBackground(otherPersonBean);

            //用户名
            tv_username.setText(otherPersonBean.getUsername());

            //性别
            if (otherPersonBean.getGender() == 0) {
                Glide.with(this)
                        .load(R.drawable.gender_female)
                        .into(iv_gender);
            } else {
                Glide.with(this)
                        .load(R.drawable.gender_male)
                        .into(iv_gender);
            }

            //学校
            tv_school.setText(otherPersonBean.getSchool());

            //信誉
            tv_creditScore.setText("信誉积分：" + otherPersonBean.getCreditScore());
            if (otherPersonBean.getVerifyState() == 3) {
                Glide.with(this)
                        .load(R.drawable.verified)
                        .into(iv_verifyState);
            } else {
                Glide.with(this)
                        .load(R.drawable.unverified)
                        .into(iv_verifyState);
            }

            //发布数
            tv_publishNum.setText(otherPersonBean.getPublishNum() + "");

            //接单数
            tv_acceptNum.setText(otherPersonBean.getAcceptNum() + "");

        } else {
            initAvatarAndBackground(R.drawable.avatar_male);

            //用户名
            tv_username.setText("");

            //性别
            Glide.with(this)
                    .load(R.drawable.gender_male)
                    .into(iv_gender);

            //学校
            tv_school.setText("");

            //信誉
            tv_creditScore.setText("信誉积分：");
            Glide.with(this)
                    .load(R.drawable.unverified)
                    .into(iv_verifyState);

            //发布数
            tv_publishNum.setText("0");

            //接单数
            tv_acceptNum.setText("0");
        }

    }

    private void initAvatarAndBackground(OtherPersonBean otherPersonBean) {
        //头像区
        RequestOptions requestOptions = RequestOptions.circleCropTransform();
        Glide.with(this)
                .load(otherPersonBean.getAvatar())
                .apply(requestOptions)
                .error(R.drawable.placeholder_pic)
                .into(iv_avatar);

        //获取bitmap
        GetBitmapTask getBitmapTask = new GetBitmapTask();
        getBitmapTask.setGetBitmapListener(new GetBitmapTask.GetBitmapListener() {
            @Override
            public void getBitmap(Bitmap bitmap) {
                iv_avatar_bg.setBackgroundColor(Color.TRANSPARENT);
                iv_avatar_bg.setImageBitmap(BlurUtils.fastBlur(bitmap, 15));
            }
        });
        List<Object> params = new ArrayList<>();
        params.add(this);
        params.add(otherPersonBean.getAvatar());
        getBitmapTask.execute(params);
    }

    private void initAvatarAndBackground(int resId) {
        RequestOptions requestOptions = RequestOptions.circleCropTransform();
        Glide.with(this)
                .load(resId)
                .placeholder(R.drawable.placeholder_pic)
                .apply(requestOptions)
                .apply(requestOptions)
                .into(iv_avatar);

        iv_avatar_bg.setBackground(new ColorDrawable(0x1296db));
        iv_avatar_bg.setBackgroundColor(ContextCompat.getColor(this, R.color.themeColor));
        iv_avatar_bg.setImageBitmap(null);
    }

    @Override
    public void getCurrentUserInfoFail() {
        Log.i(TAG, "getCurrentUserInfoFail: ");

        setCurrentUserInfo(null);

        smartRefreshLayout.finishRefresh();
    }
}
