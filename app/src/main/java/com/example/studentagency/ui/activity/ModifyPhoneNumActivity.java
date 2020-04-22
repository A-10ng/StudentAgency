package com.example.studentagency.ui.activity;

import android.content.Context;
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
import com.example.studentagency.mvp.presenter.ModifyPhoneNumActivityBasePresenter;
import com.example.studentagency.mvp.view.ModifyPhoneNumActivityBaseView;

import java.lang.ref.WeakReference;

import cn.jpush.android.api.JPushInterface;

public class ModifyPhoneNumActivity extends BaseActivity implements View.OnClickListener, ModifyPhoneNumActivityBaseView {

    private static final String TAG = "ModifyPhoneNumActivity";
    private static int LISTEN_EDITTEXT = 100;
    private MyHandler mHandler = new MyHandler(this);
    private ModifyPhoneNumActivityBasePresenter presenter = new ModifyPhoneNumActivityBasePresenter(this);

    //新手机号
    private EditText et_phoneNum;
    private String newPhoneNum;
    private ImageView iv_phoneNumState;

    //验证码
    private EditText et_verifyCode;
    private String verifyCode;
    private TextView tv_getVerifyCode;
    private boolean hasGetCode = false;
    private TimeOut timeOut = new TimeOut(60000, 1000);

    //保存按钮
    private Button btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_phone_num);

        initViews();
    }

    private void initViews() {
        //新手机号
        et_phoneNum = findViewById(R.id.et_phoneNum);
        iv_phoneNumState = findViewById(R.id.iv_phoneNumState);

        //验证码
        et_verifyCode = findViewById(R.id.et_verifyCode);
        tv_getVerifyCode = findViewById(R.id.tv_getVerifyCode);
        tv_getVerifyCode.setOnClickListener(this);

        //保存按钮
        btn_save = findViewById(R.id.btn_save);
        btn_save.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != timeOut) {
            timeOut.cancel();
            timeOut = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startListening();
    }

    private void startListening() {
        mHandler.sendEmptyMessage(LISTEN_EDITTEXT);
    }

    private void stopListening() {
        mHandler.removeMessages(LISTEN_EDITTEXT);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_getVerifyCode:
                Log.i(TAG, "onClick: newPhoneNum>>>>>" + newPhoneNum);
                hasGetCode = true;

                LemonBubble.getRoundProgressBubbleInfo()
                        .setLocationStyle(LemonBubbleLocationStyle.BOTTOM)
                        .setLayoutStyle(LemonBubbleLayoutStyle.ICON_LEFT_TITLE_RIGHT)
                        .setBubbleSize(200, 50)
                        .setProportionOfDeviation(0.1f)
                        .setTitle("获取中...")
                        .show(ModifyPhoneNumActivity.this);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        presenter.getVerifyCode(newPhoneNum);
                    }
                }, 1500);
                break;
            case R.id.btn_save:
                Log.i(TAG, "onClick: newPhoneNum>>>>>" + newPhoneNum + "\n" +
                        "verifyCode>>>>>" + verifyCode);

                LemonBubble.getRoundProgressBubbleInfo()
                        .setLocationStyle(LemonBubbleLocationStyle.BOTTOM)
                        .setLayoutStyle(LemonBubbleLayoutStyle.ICON_LEFT_TITLE_RIGHT)
                        .setBubbleSize(200, 50)
                        .setProportionOfDeviation(0.1f)
                        .setTitle("保存中...")
                        .show(ModifyPhoneNumActivity.this);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        presenter.modifyPhoneNum(newPhoneNum, verifyCode);
                    }
                }, 1500);
                break;
        }
    }

    @Override
    public void modifyPhoneNumSuccess(ResponseBean responseBean) {
        Log.i(TAG, "modifyPhoneNumSuccess: result>>>>>" + responseBean.getCode());

        if (responseBean.getCode() == 200) {
            LemonBubble.showRight(this, "保存成功！", 1000);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
//                    JPushInterface.setAlias(RegisterTwoActivity.this,300,phoneNum);
                    JPushInterface.setAlias(ModifyPhoneNumActivity.this, 300, newPhoneNum);

                    finish();
                }
            }, 1100);
        }  else {
            LemonBubble.showError(this, "保存失败，请重试！", 1200);
        }
    }

    @Override
    public void modifyPhoneNumFail() {
        Log.i(TAG, "modifyPhoneNumFail: ");

        LemonBubble.showError(this, "网络开了小差，请重试！", 1200);
    }

    @Override
    public void getVerifyCodeSuccess(ResponseBean responseBean) {
        Log.i(TAG, "getVerifyCodeSuccess: result>>>>>" + responseBean.getCode());

        if (responseBean.getCode() == 200) {
            LemonBubble.showRight(this, "发送成功！", 1000);

            timeOut.start();
            tv_getVerifyCode.setClickable(false);
            tv_getVerifyCode.setEnabled(false);
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

    private static class MyHandler extends Handler {

        private WeakReference<Context> reference;

        public MyHandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            ModifyPhoneNumActivity activity = (ModifyPhoneNumActivity) reference.get();
            if (activity != null) {
                if (msg.what == 100) {
                    //监听输入框的输入是否合法

                    //获取输入框的内容
                    activity.newPhoneNum = activity.et_phoneNum.getText().toString().trim();
                    activity.verifyCode = activity.et_verifyCode.getText().toString();

                    int phoneNumLength = activity.newPhoneNum.length();
                    int verifyCodeLength = activity.verifyCode.length();

                    if (phoneNumLength == 0) {
                        activity.tv_getVerifyCode.setClickable(false);
                        activity.tv_getVerifyCode.setEnabled(false);
                        activity.iv_phoneNumState.setVisibility(View.GONE);
                    } else if (phoneNumLength < 11) {
                        activity.tv_getVerifyCode.setClickable(false);
                        activity.tv_getVerifyCode.setEnabled(false);
                        activity.iv_phoneNumState.setImageResource(R.drawable.icon_error);
                        activity.iv_phoneNumState.setVisibility(View.VISIBLE);
                    } else if (phoneNumLength == 11) {
                        if (activity.hasGetCode) {
                            activity.tv_getVerifyCode.setClickable(false);
                            activity.tv_getVerifyCode.setEnabled(false);
                        } else {
                            activity.tv_getVerifyCode.setClickable(true);
                            activity.tv_getVerifyCode.setEnabled(true);
                        }
                        activity.iv_phoneNumState.setImageResource(R.drawable.icon_success);
                        activity.iv_phoneNumState.setVisibility(View.VISIBLE);
                    }
                    activity.btn_save.setEnabled(false);
                    if (phoneNumLength == 11 && verifyCodeLength > 0) {
                        activity.btn_save.setEnabled(true);
                    }


                    activity.mHandler.sendEmptyMessageDelayed(100, 200);
                }
            }
        }
    }

    class TimeOut extends CountDownTimer {

        public TimeOut(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            Log.i(TAG, "onTick: millisUntilFinished>>>>>" + millisUntilFinished + "\n" +
                    "hasGetCode>>>>>" + hasGetCode);
            tv_getVerifyCode.setClickable(false);
            tv_getVerifyCode.setEnabled(false);
            tv_getVerifyCode.setText(millisUntilFinished / 1000 + "秒后可重发");
        }

        @Override
        public void onFinish() {
            Log.i(TAG, "TimeOut onFinish: hasGetCode>>>>>" + hasGetCode);
            tv_getVerifyCode.setText("重新获取验证码");
            tv_getVerifyCode.setClickable(true);
            tv_getVerifyCode.setEnabled(true);

            hasGetCode = false;
        }
    }
}
