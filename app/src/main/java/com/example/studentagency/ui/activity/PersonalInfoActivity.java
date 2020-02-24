package com.example.studentagency.ui.activity;

import androidx.annotation.Nullable;

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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.lemonbubble.LemonBubble;
import com.example.lemonbubble.enums.LemonBubbleLayoutStyle;
import com.example.lemonbubble.enums.LemonBubbleLocationStyle;
import com.example.studentagency.R;
import com.example.studentagency.bean.UserBean;
import com.example.studentagency.mvp.presenter.PersonalInfoActivityBasePresenter;
import com.example.studentagency.mvp.view.PersonalInfoActivityBaseView;

import java.lang.ref.WeakReference;

public class PersonalInfoActivity extends BaseActivity implements PersonalInfoActivityBaseView {

    private static final String TAG = "PersonalInfoActivity";
    private static final int REQUEST_PICK_SCHOOL = 100;
    private static final int REQUEST_PICK_ADDRESS = 101;
    private static final int RESULT_PICK_SCHOOL = 3;
    private static final int EDIT_TEXT_LISTEN = 4;
    private PersonalInfoActivityBasePresenter presenter = new PersonalInfoActivityBasePresenter(this);
    private MyHandler myHandler = new MyHandler(this);
    private boolean getDataSuccess = false;

    private UserBean originalUserbean;

    //userId
    private TextView tv_userId;

    //学校
    private TextView tv_school;
    private String originalSchool = "";
    private String changedSchool = "";

    //用户名
    private EditText et_username;
    private String changedUsername;
    private String et_username_content;

    //性别
    private RadioGroup rg_gender;
    private RadioButton rb_male, rb_female;
    private int originalGender = -1;
    private int changedGender;

    //收货地址
    private EditText et_address;
    private ImageView iv_address;
    private String changedAddress = "";
    private String et_address_content = "";

    //保存按钮
    private Button btn_save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        initAllViews();

        startListening();

        //发送请求
        presenter.getPersonalInfo(MyApp.userId);
    }

    private void initAllViews() {
        //userId
        tv_userId = findViewById(R.id.tv_userId);

        //学校
        tv_school = findViewById(R.id.tv_school);
        tv_school.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(PersonalInfoActivity.this,
                        PickSchoolActivity.class), REQUEST_PICK_SCHOOL);
            }
        });

        //用户名
        et_username = findViewById(R.id.et_username);

        //性别
        rg_gender = findViewById(R.id.rg_gender);
        rb_male = findViewById(R.id.rb_male);
        rb_female = findViewById(R.id.rb_female);

        //收货地址
        et_address = findViewById(R.id.et_address);
        iv_address = findViewById(R.id.iv_address);
        iv_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(PersonalInfoActivity.this, AddressActivity.class), REQUEST_PICK_ADDRESS);
            }
        });

        //保存按钮
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
                        .show(PersonalInfoActivity.this);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(TAG, "run: changedSchool>>>>>" + changedSchool + "\n" +
                                "changedUsername>>>>>" + changedUsername + "\n" +
                                "changedGender>>>>>" + changedGender + "\n" +
                                "changedAddress>>>>>" + changedAddress);

                        originalUserbean.setSchool(changedSchool);
                        originalUserbean.setUsername(changedUsername);
                        originalUserbean.setGender(changedGender);
                        originalUserbean.setAddress(changedAddress);
                        presenter.changePersonalInfo(originalUserbean);
                    }
                }, 1500);
            }
        });
    }

    private void startListening() {
        myHandler.sendEmptyMessage(EDIT_TEXT_LISTEN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_PICK_SCHOOL && resultCode == RESULT_PICK_SCHOOL) {
            String schoolName = data.getStringExtra("schoolName");
            tv_school.setText(schoolName);
            Log.i(TAG, "onActivityResult: originalSchool>>>>>" + originalSchool);
        }
        if (requestCode == REQUEST_PICK_ADDRESS){
            if (resultCode == RESULT_OK){
                String address = data.getStringExtra("pickedAddress");
                et_address.setText(address);
                Log.i(TAG, "onActivityResult: address>>>>>" + address);
            }
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

    private void stopListening() {
        myHandler.removeMessages(EDIT_TEXT_LISTEN);
    }

    @Override
    public void getPersonalInfoSuccess(UserBean userBean) {
        Log.i(TAG, "getPersonalInfoSuccess: userBean>>>>>" + userBean.toString());

        setPageInfo(userBean);
    }

    private void setPageInfo(UserBean userBean) {
        if (null == userBean) {
            originalSchool = "";

            originalGender = -1;

            //userId
            tv_userId.setText("");

            //用户名
            et_username.setHint("");

            //收货地址
            et_address.setHint("");

            getDataSuccess = false;

            iv_address.setClickable(false);
            tv_school.setClickable(false);

            LemonBubble.showError(this, "网络异常，请退出重试！", 1500);
        } else {
            originalUserbean = userBean;

            originalSchool = userBean.getSchool();
            originalGender = userBean.getGender();

            changedUsername = userBean.getUsername();
            changedAddress = userBean.getAddress();

            //userId
            tv_userId.setText(userBean.getUserId() + "");

            //学校
            tv_school.setText(userBean.getSchool());

            //性别
            if (userBean.getGender() == 0) {
                rb_female.setChecked(true);
            } else {
                rb_male.setChecked(true);
            }

            //用户名
            et_username.setHint(userBean.getUsername());

            //收货地址
            et_address.setHint(userBean.getAddress());

            getDataSuccess = true;

            iv_address.setClickable(true);
            tv_school.setClickable(true);
        }
    }

    @Override
    public void getPersonalInfoFail() {
        Log.i(TAG, "getPersonalInfoFail: ");

        setPageInfo(null);
    }

    @Override
    public void changeUserInfoSuccess(Integer result) {
        Log.i(TAG, "changeUserInfoSuccess: result>>>>>" + result);

        if (1 == result) {
            LemonBubble.showRight(this, "保存成功！", 1000);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 1100);
        } else {
            LemonBubble.showError(this, "保存失败！", 1000);
        }
    }

    @Override
    public void changeUserInfoFail() {
        Log.i(TAG, "changeUserInfoFail: ");

        LemonBubble.showError(this, "网络异常，请重试！", 1000);
    }

    private class MyHandler extends Handler {
        private WeakReference<Context> reference;

        public MyHandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            PersonalInfoActivity personalInfoActivity = (PersonalInfoActivity) reference.get();
            if (null != personalInfoActivity) {
                switch (msg.what) {
                    case EDIT_TEXT_LISTEN:
                        changedSchool = tv_school.getText().toString().trim();
                        et_username_content = et_username.getText().toString().trim();
                        changedGender = rg_gender.getCheckedRadioButtonId() == R.id.rb_male ? 1 : 0;
                        et_address_content = et_address.getText().toString().trim();

                        if (!getDataSuccess ||
                                (originalSchool.equals(changedSchool) && TextUtils.isEmpty(et_username_content) &&
                                        originalGender == changedGender && TextUtils.isEmpty(et_address_content))) {
                            btn_save.setEnabled(false);
                        } else {
                            if (!TextUtils.isEmpty(et_username_content)) changedUsername = et_username_content;
                            if (!TextUtils.isEmpty(et_address_content)) changedAddress = et_address_content;

                            btn_save.setEnabled(true);
                        }

                        myHandler.sendEmptyMessageDelayed(EDIT_TEXT_LISTEN, 300);
                        break;
                }
            }
        }
    }
}
