package com.example.studentagency.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.lemonbubble.LemonBubble;
import com.example.lemonbubble.enums.LemonBubbleLayoutStyle;
import com.example.lemonbubble.enums.LemonBubbleLocationStyle;
import com.example.studentagency.R;
import com.example.studentagency.bean.ResponseBean;
import com.example.studentagency.mvp.presenter.ModifyPwdActivityBasePresenter;
import com.example.studentagency.mvp.view.ModifyPwdActivityBaseView;

import java.lang.ref.WeakReference;

public class ModifyPwdActivity extends BaseActivity implements ModifyPwdActivityBaseView {

    private static final String TAG = "ModifyPwdActivity";
    private static final int EDIT_LISTEN = 1;
    private EditText et_password, et_confirmPwd;
    private Button btn_save;
    private MyHandler myHandler = new MyHandler(this);
    private ModifyPwdActivityBasePresenter presenter = new ModifyPwdActivityBasePresenter(this);
    private String newPwd;//新密码
    private String confirmPwd;//确认密码

    private ImageView iv_passwordState;//新密码提示图
    private ImageView iv_confirmPwdState;//确认密码提示图

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_pwd);

        initAllViews();

        //开启监听密码以及确认密码输入框
        startListening();
    }

    private void initAllViews() {
        et_password = findViewById(R.id.et_password);
        et_confirmPwd = findViewById(R.id.et_confirmPwd);

        iv_passwordState = findViewById(R.id.iv_passwordState);
        iv_confirmPwdState = findViewById(R.id.iv_confirmPwdState);

        btn_save = findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LemonBubble.getRoundProgressBubbleInfo()
                        .setLocationStyle(LemonBubbleLocationStyle.BOTTOM)
                        .setLayoutStyle(LemonBubbleLayoutStyle.ICON_LEFT_TITLE_RIGHT)
                        .setBubbleSize(200, 50)
                        .setProportionOfDeviation(0.1f)
                        .setTitle("保存中...")
                        .show(ModifyPwdActivity.this);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(TAG, "run: newPwd>>>>>"+newPwd);
                        presenter.changePwd(MyApp.userId,newPwd);
                    }
                }, 1500);
            }
        });
    }

    private void startListening() {
        myHandler.sendEmptyMessage(EDIT_LISTEN);
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
        myHandler.removeMessages(EDIT_LISTEN);
    }

    @Override
    public void changePwdSuccess(ResponseBean responseBean) {
        Log.i(TAG, "changePwdSuccess: result>>>>>"+responseBean.getCode());

        if (200 == responseBean.getCode()){
            LemonBubble.showRight(this,"保存成功！",1000);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 1100);
        }else {
            LemonBubble.showError(this,"保存失败，请重试！",1000);
        }
    }

    @Override
    public void changePwdFail() {
        LemonBubble.showError(this,"网络异常，请重试！",1000);
    }

    private class MyHandler extends Handler {

        private WeakReference<Context> reference;

        public MyHandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            ModifyPwdActivity modifyPwdActivity = (ModifyPwdActivity) reference.get();
            if (null != modifyPwdActivity) {
                switch (msg.what) {
                    case EDIT_LISTEN:
                        newPwd = et_password.getText().toString().trim();
                        confirmPwd = et_confirmPwd.getText().toString().trim();

                        int newPwdLength = newPwd.length();
                        int confirmPwdLength = confirmPwd.length();

                        if (newPwdLength == 0) {
                            iv_passwordState.setVisibility(View.GONE);
                        } else {
                            iv_passwordState.setVisibility(View.VISIBLE);
                        }

                        if (confirmPwdLength == 0) {
                            iv_confirmPwdState.setVisibility(View.GONE);
                            btn_save.setEnabled(false);
                        } else {
                            if (newPwd.equals(confirmPwd)) {
                                iv_confirmPwdState.setImageResource(R.drawable.icon_success);
                                iv_confirmPwdState.setVisibility(View.VISIBLE);

                                btn_save.setEnabled(true);
                            } else {
                                iv_confirmPwdState.setImageResource(R.drawable.icon_error);
                                iv_confirmPwdState.setVisibility(View.VISIBLE);

                                btn_save.setEnabled(false);
                            }
                        }

                        myHandler.sendEmptyMessageDelayed(EDIT_LISTEN, 300);
                        break;
                }
            }
        }
    }
}
