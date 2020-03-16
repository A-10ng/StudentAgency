package com.example.studentagency.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lemonbubble.LemonBubble;
import com.example.lemonbubble.enums.LemonBubbleLayoutStyle;
import com.example.lemonbubble.enums.LemonBubbleLocationStyle;
import com.example.studentagency.R;
import com.example.studentagency.bean.ResponseBean;
import com.example.studentagency.mvp.presenter.RechargeActivityBasePresenter;
import com.example.studentagency.mvp.view.RechargeActivityBaseView;

import java.lang.ref.WeakReference;

public class RechargeActivity extends BaseActivity implements RechargeActivityBaseView {

    private static final String TAG = "RechargeActivity";
    private MyHandler mHandler = new MyHandler(this);
    private static final int LISTEN_EDIT = 1;
    private RechargeActivityBasePresenter presenter = new RechargeActivityBasePresenter(this);

    private EditText et_recharge;
    private TextView tv_tips;
    private Button btn_recharge;

    private String str_recharge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);

        findAllViews();
    }

    private void findAllViews() {
        et_recharge = findViewById(R.id.et_recharge);
        tv_tips = findViewById(R.id.tv_tips);
        btn_recharge = findViewById(R.id.btn_recharge);

        btn_recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: str_recharge>>>>>"+str_recharge);

                LemonBubble.getRoundProgressBubbleInfo()
                        .setLocationStyle(LemonBubbleLocationStyle.BOTTOM)
                        .setLayoutStyle(LemonBubbleLayoutStyle.ICON_LEFT_TITLE_RIGHT)
                        .setBubbleSize(200, 50)
                        .setProportionOfDeviation(0.1f)
                        .setTitle("充值中...")
                        .show(RechargeActivity.this);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        presenter.recharge(str_recharge);
                    }
                }, 1500);
            }
        });
    }

    private static boolean checkPriceValidity(String str_recharge, RechargeActivity rechargeActivity) {
        if (TextUtils.isEmpty(str_recharge)) {
            rechargeActivity.tv_tips.setVisibility(View.GONE);
            return false;
        }

        if (str_recharge.equals("0")) {
            rechargeActivity.tv_tips.setVisibility(View.VISIBLE);
            return false;
        }

        if (str_recharge.length() >= 2) {
            if ((str_recharge.charAt(0) == '0' && str_recharge.charAt(1) == '0') ||
                    (str_recharge.charAt(0) == '0' && str_recharge.charAt(1) > '0' && str_recharge.charAt(1) <= '9')) {
                str_recharge = str_recharge.substring(1, str_recharge.length());
                rechargeActivity.et_recharge.setText(str_recharge);
                rechargeActivity.tv_tips.setVisibility(View.VISIBLE);
                return false;
            }
        }

        //支持小数点前3位以及小数点后1位
        if (str_recharge.matches("^\\d{1,3}(\\.\\d{1})?$")) {
            rechargeActivity.tv_tips.setVisibility(View.GONE);
            return true;
        } else {
            rechargeActivity.tv_tips.setVisibility(View.VISIBLE);
            return false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        //开启监听EditText
        startListening();
    }

    private void startListening() {
        mHandler.sendEmptyMessage(LISTEN_EDIT);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //停止监听
        stopListening();
    }

    private void stopListening() {
        mHandler.removeMessages(LISTEN_EDIT);
    }

    @Override
    public void rechargeSuccess(ResponseBean responseBean) {
        Log.i(TAG, "rechargeSuccess: result>>>>>"+responseBean.getCode());

        if (200 == responseBean.getCode()){
            LemonBubble.showRight(this,"充值成功！",1000);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent();
                    intent.putExtra("recharge",str_recharge);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }, 1100);
        }else {
            LemonBubble.showError(this,"充值失败，请重试！",1000);
        }
    }

    @Override
    public void rechargeFail() {
        LemonBubble.showError(this,"充值失败，请重试！",1000);
    }

    private static class MyHandler extends Handler {

        private WeakReference<Context> reference;

        public MyHandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            RechargeActivity rechargeActivity = (RechargeActivity) reference.get();
            if (rechargeActivity != null) {
                switch (msg.what) {
                    //监听输入框的输入是否合法
                    case LISTEN_EDIT:
                        //获取输入框的内容
                        rechargeActivity.str_recharge = rechargeActivity.et_recharge.getText().toString();

                        //如果全部都符合条件了，发布按钮就处于可按状态，否则就不可按
                        if (checkPriceValidity(rechargeActivity.str_recharge,rechargeActivity)) {
                            rechargeActivity.btn_recharge.setEnabled(true);
                        } else {
                            rechargeActivity.btn_recharge.setEnabled(false);
                        }
                        rechargeActivity.mHandler.sendEmptyMessageDelayed(LISTEN_EDIT, 500);
                        break;
                }
            }
        }
    }
}
