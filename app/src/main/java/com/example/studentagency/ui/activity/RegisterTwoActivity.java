package com.example.studentagency.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lemonbubble.LemonBubble;
import com.example.lemonbubble.enums.LemonBubbleLayoutStyle;
import com.example.lemonbubble.enums.LemonBubbleLocationStyle;
import com.example.studentagency.R;
import com.example.studentagency.bean.ResponseBean;
import com.example.studentagency.mvp.presenter.RegisterActivityBasePresenter;
import com.example.studentagency.mvp.view.RegisterActivityBaseView;
import com.example.studentagency.utils.SharedPreferencesUtils;

import java.lang.ref.WeakReference;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.options.RegisterOptionalUserInfo;
import cn.jpush.im.api.BasicCallback;

public class RegisterTwoActivity extends BaseActivity implements View.OnClickListener, RegisterActivityBaseView {

    private static final String TAG = "RegisterTwoActivity";
    private static final int LISTEN_EDIT = 1;
    private MyHandler myHandler = new MyHandler(this);
    private RegisterActivityBasePresenter presenter = new RegisterActivityBasePresenter(this);
    private SharedPreferencesUtils preferencesUtils = new SharedPreferencesUtils(this);

    //上一步填写的信息
    private String username;
    private int genderType;
    private String password;
    private String school;

    //手机号
    private EditText et_phoneNum;
    private String phoneNum;
    private ImageView iv_phoneNumState;

    //验证码
    private EditText et_verifyCode;
    private String verifyCode;
    private TextView tv_getVerifyCode;
    private boolean hasGetCode = false;
    private CountTime countTime = new CountTime(60000, 1000);

    //按钮
    private Button btn_lastStep;
    private Button btn_finishRegis;
    private Button btn_goToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_two);

        //获取上一步填写的信息
        getLastStepInfo();

        findAllViews();

        setViewsOnClick();
    }

    private void getLastStepInfo() {
        Intent intent = getIntent();
        if (null != intent) {
            username = intent.getStringExtra("username");
            genderType = intent.getIntExtra("genderType", -1);
            password = intent.getStringExtra("password");
            school = intent.getStringExtra("school");
        }
        Log.i(TAG, "getLastStepInfo: username>>>>>" + username + "\n" +
                "genderType>>>>>" + genderType + "\n" +
                "password>>>>>" + password + "\n" +
                "school>>>>>" + school);
    }

    private void findAllViews() {
        //手机号
        et_phoneNum = findViewById(R.id.et_phoneNum);
        iv_phoneNumState = findViewById(R.id.iv_phoneNumState);

        //验证码
        et_verifyCode = findViewById(R.id.et_verifyCode);
        tv_getVerifyCode = findViewById(R.id.tv_getVerifyCode);

        //按钮
        btn_lastStep = findViewById(R.id.btn_lastStep);
        btn_finishRegis = findViewById(R.id.btn_finishRegis);
        btn_goToLogin = findViewById(R.id.btn_goToLogin);
    }

    private void setViewsOnClick() {
        tv_getVerifyCode.setOnClickListener(this);

        btn_lastStep.setOnClickListener(this);
        btn_finishRegis.setOnClickListener(this);
        btn_goToLogin.setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        //取消监听
        cancelListening();

        if (null != countTime) {
            countTime.cancel();
            countTime = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        //开启监听
        startListening();
    }

    private void startListening() {
        myHandler.sendEmptyMessage(LISTEN_EDIT);
    }

    private void cancelListening() {
        myHandler.removeMessages(LISTEN_EDIT);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_lastStep:
                startActivity(new Intent(this, RegisterOneActivity.class));
                finish();
                break;
            case R.id.btn_finishRegis:
                Log.i(TAG, "onClick btn_finishRegis: username >>>>> " + username + "\n" +
                        "genderType >>>>> " + genderType + "\n" +
                        "password >>>>> " + password + "\n" +
                        "school >>>>> " + school + "\n" +
                        "phoneNum >>>>> " + phoneNum + "\n" +
                        "verifyCode >>>>> " + verifyCode);

                LemonBubble.getRoundProgressBubbleInfo()
                        .setLocationStyle(LemonBubbleLocationStyle.BOTTOM)
                        .setLayoutStyle(LemonBubbleLayoutStyle.ICON_LEFT_TITLE_RIGHT)
                        .setBubbleSize(200, 50)
                        .setProportionOfDeviation(0.1f)
                        .setTitle("注册中...")
                        .show(RegisterTwoActivity.this);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        RegisterOptionalUserInfo userInfo = new RegisterOptionalUserInfo();
                        userInfo.setNickname(username);
                        userInfo.setSignature("http://www.longsh1z.top/resources/avatar.jpg");
                        JMessageClient.register(phoneNum, phoneNum, userInfo, new BasicCallback() {
                            @Override
                            public void gotResult(int responseCode, String s) {
                                Log.i(TAG, "JMessageClient.register gotResult: responseCode>>>>>" + responseCode + " s>>>>>" + s);
                                if (responseCode == 0) {
                                    presenter.register(username, genderType, password, school, phoneNum);
                                } else if (responseCode == 898001){
                                    LemonBubble.showError(RegisterTwoActivity.this,"该手机号已注册过极光IM，请更换手机！",1200);
                                }
                                else {
                                    LemonBubble.showError(RegisterTwoActivity.this,"极光服务出现差错，请重试！",1200);
                                }
                            }
                        });
                    }
                }, 1500);
                break;
            case R.id.btn_goToLogin:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.tv_getVerifyCode:
                hasGetCode = true;

                LemonBubble.getRoundProgressBubbleInfo()
                        .setLocationStyle(LemonBubbleLocationStyle.BOTTOM)
                        .setLayoutStyle(LemonBubbleLayoutStyle.ICON_LEFT_TITLE_RIGHT)
                        .setBubbleSize(200, 50)
                        .setProportionOfDeviation(0.1f)
                        .setTitle("获取中...")
                        .show(RegisterTwoActivity.this);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        presenter.getVerifyCode(phoneNum);
                    }
                }, 1500);
                break;
        }
    }

    @Override
    public void getVerifyCodeSuccess(ResponseBean responseBean) {
        Log.i(TAG, "getVerifyCodeSuccess: result>>>>>" + responseBean.getCode());

        if (responseBean.getCode() == 200) {
            LemonBubble.showRight(this, "发送成功！", 1000);

            countTime.start();
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
    public void registerSuccess(ResponseBean responseBean) {
        Log.i(TAG, "registerSuccess: result>>>>>" + responseBean.getCode());

        if (responseBean.getCode() == 200) {
            LemonBubble.showRight(this, "注册成功！", 1000);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(RegisterTwoActivity.this, LoginActivity.class));
                }
            }, 1100);
        } else {
            LemonBubble.showError(this, "注册失败，请重试！", 1200);
        }
    }

    @Override
    public void registerFail() {
        Log.i(TAG, "registerFail");

        LemonBubble.showError(this, "网络开了小差，请重试！", 1200);
    }

    private class CountTime extends CountDownTimer {

        public CountTime(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tv_getVerifyCode.setClickable(false);
            tv_getVerifyCode.setText(millisUntilFinished / 1000 + "秒后可重发");
        }

        @Override
        public void onFinish() {
            tv_getVerifyCode.setText("重新获取验证码");
            tv_getVerifyCode.setClickable(true);

            hasGetCode = false;
        }
    }

    private class MyHandler extends Handler {
        private WeakReference<Context> reference;

        public MyHandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            RegisterTwoActivity registerTwoActivity = (RegisterTwoActivity) reference.get();
            if (null != registerTwoActivity) {
                switch (msg.what) {
                    case LISTEN_EDIT:
                        phoneNum = et_phoneNum.getText().toString().trim();
                        verifyCode = et_verifyCode.getText().toString().trim();
                        int phoneNumLength = phoneNum.length();
                        int verifyCodeLength = verifyCode.length();

                        if (phoneNumLength == 0) {
                            iv_phoneNumState.setVisibility(View.GONE);
                            tv_getVerifyCode.setClickable(false);
                        } else if (phoneNumLength < 11) {
                            iv_phoneNumState.setImageResource(R.drawable.icon_error);
                            iv_phoneNumState.setVisibility(View.VISIBLE);
                            tv_getVerifyCode.setClickable(false);
                        } else {
                            if (hasGetCode) {
                                tv_getVerifyCode.setClickable(false);
                            } else {
                                tv_getVerifyCode.setClickable(true);
                            }
                            iv_phoneNumState.setImageResource(R.drawable.icon_success);
                            iv_phoneNumState.setVisibility(View.VISIBLE);
                        }

                        if (phoneNumLength == 11 && verifyCodeLength > 0) {
                            btn_finishRegis.setEnabled(true);
                        } else {
                            btn_finishRegis.setEnabled(false);
                        }

                        myHandler.sendEmptyMessageDelayed(LISTEN_EDIT, 500);
                        break;
                }
            }
        }
    }
}
