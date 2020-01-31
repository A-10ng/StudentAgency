package com.example.studentagency.ui.activity;

import android.content.Context;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lemonbubble.LemonBubble;
import com.example.lemonbubble.enums.LemonBubbleLayoutStyle;
import com.example.lemonbubble.enums.LemonBubbleLocationStyle;
import com.example.studentagency.R;

import com.example.studentagency.mvp.presenter.LoginActivityBasePresenter;
import com.example.studentagency.mvp.view.LoginActivityBaseView;

import java.lang.ref.WeakReference;

public class LoginActivity extends BaseActivity implements LoginActivityBaseView, View.OnClickListener {

    private static final String TAG = "LoginActivity";
    private static final int LISTEN_EDITTEXT = 1;
    private LoginActivityBasePresenter presenter = new LoginActivityBasePresenter(this);
    private MyHandler mHandler = new MyHandler(this);

    //切换登录模式
    private boolean isPasswordMode = true;
    private TextView tv_changeMode;
    private LinearLayout root_password, root_verifyCode;

    //验证码
    private String verifyCode;
    private EditText et_verifyCode;
    private TextView tv_getVerifyCode;
    private TimeOut timeOut = new TimeOut(60000, 1000);
    private boolean hasGetCode = false;

    //登录按钮
    private Button btn_login;

    //注册按钮
    private Button btn_goToRegis;

    //手机号
    private EditText et_phoneNum;
    private ImageView iv_phoneNumState;
    private String phoneNum;

    //密码
    private EditText et_password;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //初始化控件
        initViews();

        //设置控件的监听
        setViewsOnClick();
    }

    private void initViews() {
        //切换登录模式
        root_password = findViewById(R.id.root_password);
        root_verifyCode = findViewById(R.id.root_verifyCode);
        tv_changeMode = findViewById(R.id.tv_changeMode);

        //验证码
        et_verifyCode = findViewById(R.id.et_verifyCode);
        tv_getVerifyCode = findViewById(R.id.tv_getVerifyCode);

        //登录按钮
        btn_login = findViewById(R.id.btn_login);

        //注册按钮
        btn_goToRegis = findViewById(R.id.btn_goToRegis);

        //手机号
        et_phoneNum = findViewById(R.id.et_phoneNum);
        iv_phoneNumState = findViewById(R.id.iv_phoneNumState);

        //密码
        et_password = findViewById(R.id.et_password);
    }

    private void startListening() {
        mHandler.sendEmptyMessage(LISTEN_EDITTEXT);
    }

    private void setViewsOnClick() {
        //切换登录模式
        tv_changeMode.setOnClickListener(this);

        //验证码
        tv_getVerifyCode.setOnClickListener(this);

        //登录按钮
        btn_login.setOnClickListener(this);

        //注册按钮
        btn_goToRegis.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startListening();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopListening();
    }

    private void stopListening() {
        mHandler.removeMessages(LISTEN_EDITTEXT);
    }

    @Override
    public void loginByPasswordSuccess(Integer result) {
        Log.i(TAG, "loginByPasswordSuccess: result>>>>>" + result);

        if (result == 1) {
            LemonBubble.showRight(this, "登录成功！", 1000);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            }, 1100);
        } else {
            LemonBubble.showError(this, "登录失败，请重试！", 1200);
        }
    }

    @Override
    public void loginByPasswordFail() {
        Log.i(TAG, "loginByPasswordFail");

        LemonBubble.showError(this, "网络开了小差，请重试！", 1200);
    }

    @Override
    public void loginByVerifyCodeSuccess(Integer result) {
        Log.i(TAG, "loginByVerifyCodeSuccess: result>>>>>" + result);
        if (result == 1) {
            LemonBubble.showRight(this, "登录成功！", 1000);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            }, 1100);
        } else {
            LemonBubble.showError(this, "登录失败，请重试！", 1200);
        }
    }

    @Override
    public void loginByVerifyCodeFail() {
        Log.i(TAG, "loginByVerifyCodeFail");

        LemonBubble.showError(this, "网络开了小差，请重试！", 1200);
    }

    @Override
    public void getVerifyCodeSuccess(Integer result) {
        Log.i(TAG, "getVerifyCodeSuccess: result>>>>>" + result);

        if (result == 1) {
            LemonBubble.showRight(this, "发送成功！", 1000);

            timeOut.start();
            tv_getVerifyCode.setClickable(false);
        } else {
            LemonBubble.showError(this, "发送失败，请重试！", 1200);

            hasGetCode = false;
        }
    }

    @Override
    public void getVerifyCodeFail() {
        Log.i(TAG, "getVerifyCodeFail");

        LemonBubble.showError(this, "网络开了小差，请重试！", 1200);

        hasGetCode = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //切换登录模式的tv
            case R.id.tv_changeMode:
                if (isPasswordMode) {
                    password = "";
                    et_password.setText("");
                    root_password.setVisibility(View.GONE);
                    root_verifyCode.setVisibility(View.VISIBLE);
                    tv_changeMode.setText("切换为密码登录");
                    isPasswordMode = false;
                } else {
                    verifyCode = "";
                    et_verifyCode.setText("");
                    root_verifyCode.setVisibility(View.GONE);
                    root_password.setVisibility(View.VISIBLE);
                    tv_changeMode.setText("切换为验证码登录");
                    isPasswordMode = true;
                }
                break;
            //获取验证码
            case R.id.tv_getVerifyCode:
                Log.i(TAG, "onClick: phoneNum>>>>>" + phoneNum);
                hasGetCode = true;

                LemonBubble.getRoundProgressBubbleInfo()
                        .setLocationStyle(LemonBubbleLocationStyle.BOTTOM)
                        .setLayoutStyle(LemonBubbleLayoutStyle.ICON_LEFT_TITLE_RIGHT)
                        .setBubbleSize(200, 50)
                        .setProportionOfDeviation(0.1f)
                        .setTitle("获取中...")
                        .show(LoginActivity.this);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        presenter.getVerifyCode(phoneNum);
                    }
                }, 1500);
                break;
            //点击登录按钮
            case R.id.btn_login:
                Log.i(TAG, "onClick: phoneNum>>>>>" + phoneNum + "\n" +
                        "password>>>>>" + password + "\n" +
                        "verifyCode>>>>>" + verifyCode);

                LemonBubble.getRoundProgressBubbleInfo()
                        .setLocationStyle(LemonBubbleLocationStyle.BOTTOM)
                        .setLayoutStyle(LemonBubbleLayoutStyle.ICON_LEFT_TITLE_RIGHT)
                        .setBubbleSize(200, 50)
                        .setProportionOfDeviation(0.1f)
                        .setTitle("登录中...")
                        .show(LoginActivity.this);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (isPasswordMode) {
                            presenter.loginByPassword(phoneNum, password);
                        } else {
                            presenter.loginByVerifyCode(phoneNum, verifyCode);
                        }
                    }
                }, 1500);
                break;
            //点击注册按钮
            case R.id.btn_goToRegis:
                startActivity(new Intent(LoginActivity.this, RegisterOneActivity.class));
        }
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    private class MyHandler extends Handler {

        private WeakReference<Context> reference;

        public MyHandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            LoginActivity loginActivity = (LoginActivity) reference.get();
            if (loginActivity != null) {
                switch (msg.what) {
                    //监听输入框的输入是否合法
                    case LISTEN_EDITTEXT:
                        //获取输入框的内容
                        phoneNum = et_phoneNum.getText().toString().trim();
                        password = et_password.getText().toString();
                        verifyCode = et_verifyCode.getText().toString();

                        int phoneNumLength = phoneNum.length();
                        int passwordLength = password.length();
                        int verifyCodeLength = verifyCode.length();

                        //如果目前是密码登录
                        if (isPasswordMode) {
                            if (phoneNumLength == 0) {
                                iv_phoneNumState.setVisibility(View.GONE);
                            } else if (phoneNumLength < 11) {
                                iv_phoneNumState.setImageResource(R.drawable.icon_error);
                                iv_phoneNumState.setVisibility(View.VISIBLE);
                            } else if (phoneNumLength == 11) {
                                iv_phoneNumState.setImageResource(R.drawable.icon_success);
                                iv_phoneNumState.setVisibility(View.VISIBLE);
                            }
                            btn_login.setEnabled(false);
                            if (phoneNumLength == 11 && passwordLength > 0) {
                                btn_login.setEnabled(true);
                            }
                        }
                        //如果目前是验证码登录
                        else {
                            if (phoneNumLength == 0) {
                                tv_getVerifyCode.setClickable(false);
                                iv_phoneNumState.setVisibility(View.GONE);
                            } else if (phoneNumLength < 11) {
                                tv_getVerifyCode.setClickable(false);
                                iv_phoneNumState.setImageResource(R.drawable.icon_error);
                                iv_phoneNumState.setVisibility(View.VISIBLE);
                            } else if (phoneNumLength == 11) {
                                if (hasGetCode){
                                    tv_getVerifyCode.setClickable(false);
                                }else {
                                    tv_getVerifyCode.setClickable(true);
                                }
                                iv_phoneNumState.setImageResource(R.drawable.icon_success);
                                iv_phoneNumState.setVisibility(View.VISIBLE);
                            }
                            btn_login.setEnabled(false);
                            if (phoneNumLength == 11 && verifyCodeLength > 0) {
                                btn_login.setEnabled(true);
                            }
                        }

                        mHandler.sendEmptyMessageDelayed(LISTEN_EDITTEXT, 200);
                        break;
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != timeOut){
            timeOut.cancel();
            timeOut = null;
        }
    }

    class TimeOut extends CountDownTimer {

        public TimeOut(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            Log.i(TAG, "onTick: millisUntilFinished>>>>>"+millisUntilFinished+"\n"+
                    "hasGetCode>>>>>"+hasGetCode);
            tv_getVerifyCode.setClickable(false);
            tv_getVerifyCode.setText(millisUntilFinished / 1000 + "秒后可重发");
        }

        @Override
        public void onFinish() {
            Log.i(TAG, "TimeOut onFinish: hasGetCode>>>>>"+hasGetCode);
            tv_getVerifyCode.setText("重新获取验证码");
            tv_getVerifyCode.setClickable(true);

            hasGetCode = false;
        }
    }
}
