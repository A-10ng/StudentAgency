package com.example.studentagency.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.studentagency.R;

import java.lang.ref.WeakReference;

import androidx.annotation.Nullable;

public class RegisterOneActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "RegisterOneActivity";
    private static final int LISTEN_EDIT = 1;
    private static final int PICK_SCHOOL = 2;
    private static final int PICK_SCHOOL_Activity = 3;
    private MyHandler myHandler = new MyHandler(this);

    //学校
    private TextView tv_school;
    private String school = "广东外语外贸大学";

    //性别
    private RadioGroup rg_gender;
    private int genderType;

    //用户名
    private EditText et_username;
    private ImageView iv_usernameState;
    private String username;

    //密码
    private EditText et_password;
    private ImageView iv_passwordState;
    private String password;

    //确认密码
    private EditText et_confirmPwd;
    private ImageView iv_confirmPwdState;
    private String confirmPwd;

    //按钮
    private Button btn_nextStep;
    private Button btn_goToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_one);

        findAllViews();
    }

    private void findAllViews() {
        //学校
        tv_school = findViewById(R.id.tv_school);

        //用户名
        et_username = findViewById(R.id.et_username);
        iv_usernameState = findViewById(R.id.iv_usernameState);

        //性别
        rg_gender = findViewById(R.id.rg_gender);
        rg_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                genderType = checkedId == R.id.rb_female ? 0 : 1;
                Log.i(TAG, "onCheckedChanged: genderType>>>>>"+genderType);
            }
        });

        //密码
        et_password = findViewById(R.id.et_password);
        iv_passwordState = findViewById(R.id.iv_passwordState);


        //确认密码
        et_confirmPwd = findViewById(R.id.et_confirmPwd);
        iv_confirmPwdState = findViewById(R.id.iv_confirmPwdState);

        //按钮
        btn_nextStep = findViewById(R.id.btn_nextStep);
        btn_goToLogin = findViewById(R.id.btn_goToLogin);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //开启监听所有的editText
        startListening();

        setViewsOnClick();
    }

    private void setViewsOnClick() {
        tv_school.setOnClickListener(this);

        btn_nextStep.setOnClickListener(this);
        btn_goToLogin.setOnClickListener(this);
    }

    private void startListening() {
        myHandler.sendEmptyMessage(LISTEN_EDIT);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_school:
                startActivityForResult(new Intent(this,PickSchoolActivity.class),PICK_SCHOOL);
                break;
            case R.id.btn_nextStep:
                Log.i(TAG, "btn_nextStep onClick: username>>>>>"+username+"\n"+
                        "genderType>>>>>"+genderType+"\n"+
                        "password>>>>>"+password+"\n"+
                        "school>>>>>"+school);
                Intent intent = new Intent(this,RegisterTwoActivity.class);
                intent.putExtra("username",username);
                intent.putExtra("genderType",genderType);
                intent.putExtra("password",password);
                intent.putExtra("school",school);
                startActivity(intent);
                break;
            case R.id.btn_goToLogin:
                startActivity(new Intent(this,LoginActivity.class));
                finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        //取消监听
        cancelListening();
    }

    private void cancelListening() {
        myHandler.removeMessages(LISTEN_EDIT);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_SCHOOL && resultCode == PICK_SCHOOL_Activity){
            String schoolName = data.getStringExtra("schoolName");
            tv_school.setText(schoolName);
            school = schoolName;
            Log.i(TAG, "onActivityResult: school>>>>>"+school);
        }
    }

    private class MyHandler extends Handler{
        private WeakReference<Context> reference;

        public MyHandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            RegisterOneActivity registerOneActivity = (RegisterOneActivity) reference.get();
            if (null != registerOneActivity){
                switch (msg.what){
                    case LISTEN_EDIT:
                        //获取输入框的内容
                        username = et_username.getText().toString().trim();
                        password = et_password.getText().toString();
                        confirmPwd = et_confirmPwd.getText().toString();

                        int usernameLength = username.length();
                        int passwordLength = password.length();
                        int confirmPwdLength = confirmPwd.length();

                        //判断用户名的合法性
                        if (usernameLength == 0){
                            iv_usernameState.setVisibility(View.GONE);
                        }else {
                            iv_usernameState.setVisibility(View.VISIBLE);
                        }

                        //判断密码的合法性
                        if (passwordLength == 0){
                            iv_passwordState.setVisibility(View.GONE);
                        }else {
                            iv_passwordState.setVisibility(View.VISIBLE);
                        }

                        //判断确认密码的合法性
                        if (confirmPwdLength == 0){
                            iv_confirmPwdState.setVisibility(View.GONE);
                        }else if (!password.equals(confirmPwd)){
                            iv_confirmPwdState.setImageResource(R.drawable.icon_error);
                            iv_confirmPwdState.setVisibility(View.VISIBLE);
                        }else{
                            iv_confirmPwdState.setImageResource(R.drawable.icon_success);
                            iv_confirmPwdState.setVisibility(View.VISIBLE);
                        }

                        if (usernameLength > 0 && passwordLength > 0 && password.equals(confirmPwd)){
                            btn_nextStep.setEnabled(true);
                        }else {
                            btn_nextStep.setEnabled(false);
                        }

                        myHandler.sendEmptyMessageDelayed(LISTEN_EDIT,500);
                        break;
                }
            }
        }
    }
}
