package com.example.studentagency.ui.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.studentagency.R;
import com.example.studentagency.bean.Cat;
import com.example.studentagency.mvp.presenter.LoginBasePresenter;
import com.example.studentagency.mvp.view.LoginBaseView;

import net.frakbot.jumpingbeans.JumpingBeans;

import java.lang.ref.WeakReference;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements LoginBaseView {

    private static final String TAG = "LoginActivity";
    private static final int STUDENTID_LENGTH = 11;
    private static final int PASSWORD_LENGTH = 6;
    private static final int LISTEN_EDITTEXT = 1;
    private Button btn_login;
    private EditText et_studentId, et_password;
    private String studentId = "";   //用户输入的学号
    private String password = "";    //用户输入的密码
    private MyHandler mHandler = new MyHandler(this);
    private TextView tv_description,tv_studentIdLength,tv_passwordLength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //沉浸式状态栏
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            View decorView=getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //初始化控件
        initViews();

        //设置文字跳动
        setLettersJump();

        //开启监听EditText
        mHandler.sendEmptyMessage(LISTEN_EDITTEXT);

        //设置控件的监听
        setViewsOnClick();
    }

    private void setLettersJump() {
        JumpingBeans jumpingBeans = JumpingBeans
                .with(tv_description)
                .makeTextJump(3,7)
                .setIsWave(true)
                .setLoopDuration(1200)
                //.setAnimatedDutyCycle(1.0f)
                .build();
    }

    private void initViews() {
        btn_login = findViewById(R.id.btn_login);
        et_studentId = findViewById(R.id.et_studentId);
        et_password = findViewById(R.id.et_password);
        tv_description = findViewById(R.id.tv_description);
        tv_studentIdLength = findViewById(R.id.tv_studentIdLength);
        tv_passwordLength = findViewById(R.id.tv_passwordLength);
    }

    private void setViewsOnClick() {

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: btn_login");
                Log.d(TAG, "onClick: studentId>>>>" + studentId);
                Log.d(TAG, "onClick: password>>>>" + password);

                LoginBasePresenter presenter = new LoginBasePresenter(LoginActivity.this);
                presenter.login(studentId, password);

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeMessages(LISTEN_EDITTEXT);
    }

    @Override
    public void LoginSuccess(Cat cat) {
        Log.d(TAG, "LoginSuccess: \n" +
                "猫的ID：" + cat.getCatId() +
                "\n猫的名字：" + cat.getCatName() +
                "\n猫的类型：" + cat.getType() +
                "\n猫的等级：" + cat.getLevel() +
                "\n猫的经验值：" + cat.getValue());
    }

    @Override
    public void LoginFailed() {
        Log.d(TAG, "获取数据失败");
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
                        studentId = et_studentId.getText().toString();
                        password = et_password.getText().toString();
                        int studentIdLength = studentId.length();
                        int passwordLength = password.length();
                        //如果两者的长度符合条件了，登录按钮就处于可按状态，否则就不可按
                        if (studentIdLength == STUDENTID_LENGTH && passwordLength == PASSWORD_LENGTH) {
                            btn_login.setEnabled(true);
                        } else {
                            btn_login.setEnabled(false);
                        }

                        /**
                         * 监听输入框下方的文字长度显示
                         */
                        //监听学号下方的文字
                        if (studentIdLength > 0 && studentIdLength < STUDENTID_LENGTH){
                            tv_studentIdLength.setVisibility(View.VISIBLE);
                            tv_studentIdLength.setText(studentIdLength+"/"+STUDENTID_LENGTH);
                            tv_studentIdLength.setTextColor(getResources().getColor(R.color.red));
                        }
                        else if (studentIdLength == STUDENTID_LENGTH) {
                            tv_studentIdLength.setText(studentIdLength+"/"+STUDENTID_LENGTH);
                            tv_studentIdLength.setTextColor(getResources().getColor(R.color.green));
                        }
                        else{
                            tv_studentIdLength.setVisibility(View.GONE);
                        }

                        //监听密码下方的文字
                        if (passwordLength > 0 && passwordLength < PASSWORD_LENGTH){
                            tv_passwordLength.setVisibility(View.VISIBLE);
                            tv_passwordLength.setText(passwordLength+"/"+PASSWORD_LENGTH);
                            tv_passwordLength.setTextColor(getResources().getColor(R.color.red));
                        }else if (passwordLength == PASSWORD_LENGTH) {
                            tv_passwordLength.setText(passwordLength+"/"+PASSWORD_LENGTH);
                            tv_passwordLength.setTextColor(getResources().getColor(R.color.green));
                        }else {
                            tv_passwordLength.setVisibility(View.GONE);
                        }

                        mHandler.sendEmptyMessageDelayed(LISTEN_EDITTEXT, 100);
                        break;
                }
            }
        }
    }
}
