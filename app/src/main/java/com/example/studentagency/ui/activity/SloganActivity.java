package com.example.studentagency.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.lemonbubble.LemonBubble;
import com.example.studentagency.bean.ResponseBean;
import com.example.studentagency.mvp.presenter.SloganActivityBasePresenter;
import com.example.studentagency.mvp.view.SloganActivityBaseView;
import com.example.studentagency.utils.SharedPreferencesUtils;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.util.List;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

public class SloganActivity extends BaseActivity implements SloganActivityBaseView {

    private static final String TAG = "SloganActivity";
    private SloganActivityBasePresenter presenter = new SloganActivityBasePresenter(this);
    private SharedPreferencesUtils preferencesUtils;
    private int type = -1;
    private static final int PERSON_INDENT_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_slogan);

        type = getIntent().getIntExtra("type",-1);
        Log.i(TAG, "onCreate: type>>>>>"+type);

        preferencesUtils = new SharedPreferencesUtils(this);

        boolean isFirstInstall = preferencesUtils.getBoolean("isFirstInstall", true);
        Log.i(TAG, "isFirstInstall>>>>>" + isFirstInstall);
        if (isFirstInstall) {
            preferencesUtils.putBoolean("isFirstInstall", false);

            // 申请多个权限。
            AndPermission.with(SloganActivity.this)
                    .runtime()
                    .permission(Permission.WRITE_EXTERNAL_STORAGE,
                            Permission.RECORD_AUDIO,
                            Permission.CAMERA)
                    .onGranted(new Action<List<String>>() {
                        @Override
                        public void onAction(List<String> data) {
                            Log.i(TAG, "onAction: 授权成功！");
                            tokenVerify();
                        }
                    })
                    .onDenied(new Action<List<String>>() {
                        @Override
                        public void onAction(List<String> data) {
                            Log.i(TAG, "onAction: 授权失败！");
                            tokenVerify();
                        }
                    })
                    .start();

        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    tokenVerify();
                }
            }, 1000);
        }
    }

    private void tokenVerify() {
        String token = preferencesUtils.getString("token", null);
        Log.i(TAG, "onAnimationEnd: token>>>>>" + token);

        if (null != token) {
            presenter.tokenVerify(token);
        } else {
            startActivity(new Intent(SloganActivity.this, LoginActivity.class));
            finish();
        }
    }

    @Override
    public void tokenVerifySuccess(ResponseBean responseBean) {
        Log.i(TAG, "cookieVerifySuccess: result>>>>>" + responseBean.getCode());

        if (responseBean.getCode() == 200) { //验证成功
            int userId = preferencesUtils.getInt("userId", -1);
            String userPhoneNum = preferencesUtils.getString("phoneNum", null);
            Log.i(TAG, "tokenVerifySuccess: userId>>>>>" + userId + "  userPhoneNum>>>>>" + userPhoneNum);

            //极光推送服务登录
            JMessageClient.login(userPhoneNum, userPhoneNum, new BasicCallback() {
                @Override
                public void gotResult(int resonpseCode, String s) {
                    Log.i(TAG, "JMessageClient.login gotResult: resonpseCode>>>>>" + resonpseCode + " s>>>>>" + s);
                    if (resonpseCode == 0) {
                        MyApp.userId = userId;
                        MyApp.userPhoneNum = userPhoneNum;

                        JPushInterface.setAlias(SloganActivity.this, 300, userPhoneNum);

                        if (type == PERSON_INDENT_ACTIVITY){
                            startActivity(new Intent(SloganActivity.this, PersonIndentActivity.class));
                        }else {
                            startActivity(new Intent(SloganActivity.this, MainActivity.class));
                        }

                        finish();
                    } else {
                        LemonBubble.showError(SloganActivity.this, "极光IM服务出现差错，或者您还未注册！", 1000);

                        startActivity(new Intent(SloganActivity.this, LoginActivity.class));
                        finish();
                    }
                }
            });
        } else { //cookie错误
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    @Override
    public void tokenVerifyFail() {
        Log.i(TAG, "cookieVerifyFail: ");

        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
