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
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.lemonbubble.LemonBubble;
import com.example.lemonbubble.enums.LemonBubbleLayoutStyle;
import com.example.lemonbubble.enums.LemonBubbleLocationStyle;
import com.example.lemonhello.LemonHello;
import com.example.lemonhello.LemonHelloAction;
import com.example.lemonhello.LemonHelloInfo;
import com.example.lemonhello.LemonHelloView;
import com.example.lemonhello.interfaces.LemonHelloActionDelegate;
import com.example.studentagency.R;
import com.example.studentagency.utils.DateUtils;
import com.example.studentagency.mvp.presenter.PublishActivityBasePresenter;
import com.example.studentagency.mvp.view.PublishActivityBaseView;
import com.example.studentagency.ui.widget.TimeDialogFragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

import static com.example.studentagency.ui.activity.MyApp.userId;

public class PublishActivity extends BaseActivity implements PublishActivityBaseView,TimeDialogFragment.OnGetPickedTimeListener {
    private static final String TAG = "PublishActivity";
    private static final int LISTEN_EDIT = 1;
    private static final int REQUEST_PICK_ADDRESS = 2;
    private PublishActivityBasePresenter presenter = new PublishActivityBasePresenter(this);

    //选择代办类型
    private int agencyType = 0;
    private RadioGroup rg_agencyType;

    //选择送达时间
    private TextView tv_pickTime;
    private String finalTime;
    private List<String> leftRecycleDataList = new ArrayList<>();//左边recyclerview的数据源
    private List<List<String>> rightRecycleDataList = new ArrayList<>();//右边recyclerview的数据源

    //代办需求
    private EditText et_description;
    private String str_description;

    //收货地点
    private EditText et_address;
    private String str_address;
    private ImageView iv_pickAddress;

    //支付费用
    private EditText et_price;
    private TextView tv_tips;
    private MyHandler mHandler = new MyHandler(this);
    private String str_price;

    //发布按钮
    private Button btn_publish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);

        findAllViews();

        initAgencyType();

        initTimeDialogFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //开启监听3个EditText
        startListening();
    }

    private void startListening() {
        mHandler.sendEmptyMessage(LISTEN_EDIT);
    }

    private void findAllViews() {
        //代办类型
        rg_agencyType = findViewById(R.id.rg_agencyType);

        //代办需求
        et_description = findViewById(R.id.et_description);

        //收货地点
        et_address = findViewById(R.id.et_address);
        iv_pickAddress = findViewById(R.id.iv_pickAddress);
        iv_pickAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PublishActivity.this,AddressActivity.class);
                startActivityForResult(intent,REQUEST_PICK_ADDRESS);
            }
        });

        //支付费用
        et_price = findViewById(R.id.et_price);
        tv_tips = findViewById(R.id.tv_tips);

        //发布按钮
        btn_publish = findViewById(R.id.btn_publish);
        btn_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: " +
                        "agencyType>>>>>" + agencyType + "\n" +
                        "str_description>>>>>" + str_description + "\n" +
                        "str_address>>>>>" + str_address + "\n" +
                        "str_price>>>>>" + str_price + "\n" +
                        "finalTime>>>>>" + finalTime + "\n");
                showEnsurePublishDialog();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_PICK_ADDRESS){
            if (resultCode == RESULT_OK){
                String pickedAddress = data.getStringExtra("pickedAddress");
                Log.i(TAG, "onActivityResult: pickedAddress>>>>>"+pickedAddress);

                et_address.setText(pickedAddress);
                str_address = pickedAddress;
            }
        }
    }

    private void showEnsurePublishDialog() {
        LemonHello.getInformationHello("提示", "您确定要发布该订单吗？")
                .addAction(new LemonHelloAction("取消", new LemonHelloActionDelegate() {
                    @Override
                    public void onClick(LemonHelloView lemonHelloView, LemonHelloInfo lemonHelloInfo, LemonHelloAction lemonHelloAction) {
                        Log.i(TAG, "onClick: 取消发布");
                        lemonHelloView.hide();
                    }
                }))
                .addAction(new LemonHelloAction("确定", new LemonHelloActionDelegate() {
                    @Override
                    public void onClick(LemonHelloView lemonHelloView, LemonHelloInfo lemonHelloInfo, LemonHelloAction lemonHelloAction) {
                        Log.i(TAG, "onClick: 确定发布");
                        lemonHelloView.hide();

                        LemonBubble.getRoundProgressBubbleInfo()
                                .setLocationStyle(LemonBubbleLocationStyle.BOTTOM)
                                .setLayoutStyle(LemonBubbleLayoutStyle.ICON_LEFT_TITLE_RIGHT)
                                .setBubbleSize(200, 50)
                                .setProportionOfDeviation(0.1f)
                                .setTitle("发布中...")
                                .show(PublishActivity.this);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                String publishTime = DateUtils.getCurrentDateByFormat("yyyy-MM-dd HH:mm");
                                float price = Float.parseFloat(str_price);
                                Log.i(TAG, "onClick: " +
                                        "agencyType>>>>>" + agencyType + "\n" +
                                        "publishId>>>>>" + userId + "\n" +
                                        "publishTime>>>>>" + publishTime + "\n" +
                                        "description>>>>>" + str_description + "\n" +
                                        "address>>>>>" + str_address + "\n" +
                                        "price>>>>>" + price + "\n" +
                                        "planTime>>>>>" + finalTime + "\n");
                                presenter.publishIndent(userId,agencyType,price,str_description,str_address,
                                        publishTime,finalTime);

                            }
                        }, 1500);
                    }
                }))
                .show(PublishActivity.this);
    }

    private void initAgencyType() {
        rg_agencyType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_shopping:
                        agencyType = 0;
                        break;
                    case R.id.rb_delivery:
                        agencyType = 1;
                        break;
                    case R.id.rb_others:
                        agencyType = 2;
                        break;
                }
            }
        });
    }

    private void initTimeDialogFragment() {
        tv_pickTime = findViewById(R.id.tv_pickTime);
        finalTime = tv_pickTime.getText().toString();
        tv_pickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLeftRecycleData();
                setRightRecycleData();

                TimeDialogFragment timeDialogFragment = new TimeDialogFragment(
                        leftRecycleDataList, rightRecycleDataList, PublishActivity.this);
                timeDialogFragment.setGetPickedTimeListener(PublishActivity.this);
                timeDialogFragment.show(getSupportFragmentManager(), "TimeDialogFragment");
            }
        });
    }

    private void setLeftRecycleData() {
        int currentMon = Integer.parseInt(DateUtils.getCurrentDateByFormat("MM"));
        int currentDate = Integer.parseInt(DateUtils.getCurrentDateByFormat("dd"));
        Log.i(TAG, "setLeftRecycleData: before currentMon>>>>>" + currentMon + "\n" +
                "currentDate>>>>>" + currentDate);

        leftRecycleDataList.clear();
        leftRecycleDataList.add("今天");
        leftRecycleDataList.add("明天");
        for (int i = 2; i < 5; i++) {
            leftRecycleDataList.add(DateUtils.addDaysToMonAndDate(i));
        }
    }

    private void setRightRecycleData() {
        int currentMin = Integer.parseInt(DateUtils.getCurrentDateByFormat("mm"));
        int currentHours = Integer.parseInt(DateUtils.getCurrentDateByFormat("HH"));
        Log.i(TAG, "setRightRecycleData: before currentHours>>>>>" + currentHours + "\n" +
                "currentMin>>>>>" + currentMin);

        //将当前的分钟数向上取整
        if (currentMin >= 0 && currentMin <= 10) {
            currentMin = 10;
        } else if (currentMin > 10 && currentMin <= 20) {
            currentMin = 20;
        } else if (currentMin > 20 && currentMin <= 30) {
            currentMin = 30;
        } else if (currentMin > 30 && currentMin <= 40) {
            currentMin = 40;
        } else if (currentMin > 40 && currentMin <= 50) {
            currentMin = 50;
        } else {
            currentMin = 60;
        }
        Log.i(TAG, "setRightRecycleData: after currentHours>>>>>" + currentHours + "\n" +
                "currentMin>>>>>" + currentMin);

        rightRecycleDataList.clear();
        List<String> today = new ArrayList<>();
        List<String> otherDay = new ArrayList<>();

        addTodayData(currentHours, currentMin, today);
        addOtherDayData(otherDay);
    }

    private void addTodayData(int currentHours, int currentMin, List<String> today) {
        //如果当前的分钟数等于60，则今天的时间选择里只设置尽快到达
        if (currentHours == 23 && currentMin == 60) {
            today.add("尽快到达");
            rightRecycleDataList.add(today);
        }
        //如果当前的分钟数不等于60，还要进行其他处理
        else {
            if (currentMin <= 20) {
                today.add("尽快送达");
                //最早的送达时间必须是半小时之后
                currentMin += 30;
                today.add(currentHours + ":" + currentMin);

                currentMin = (currentMin + 20) % 60;
                for (int i = currentHours + 1; i < 24; i++) {
                    for (int j = currentMin; j < 60; j += 20) {
                        if (j == 0) {
                            today.add(i + ":00");
                            continue;
                        }
                        today.add(i + ":" + j);
                    }
                }
            } else if (currentMin >= 30 && currentMin <= 40) {
                today.add("尽快送达");
                currentMin = (currentMin + 30) % 60;
                for (int i = currentHours + 1; i < 24; i++) {
                    for (int j = currentMin; j < 60; j += 20) {
                        if (j == 0) {
                            today.add(i + ":00");
                            continue;
                        }
                        today.add(i + ":" + j);
                    }
                }
            } else {
                today.add("尽快送达");
                currentMin = (currentMin + 30) % 60;

                for (int j = currentMin; j < 60; j += 20) {
                    if (j == 0) {
                        today.add((currentHours + 1) + ":00");
                        continue;
                    }
                    today.add((currentHours + 1) + ":" + j);
                }

                for (int i = currentHours + 2; i < 24; i++) {
                    for (int j = currentMin - 20; j < 60; j += 20) {
                        if (j == 0) {
                            today.add(i + ":00");
                            continue;
                        }
                        today.add(i + ":" + j);
                    }
                }
            }
            rightRecycleDataList.add(today);

        }
    }

    private void addOtherDayData(List<String> otherDay) {
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 60; j += 30) {
                if (j == 0) {
                    otherDay.add(i + ":00");
                } else {
                    otherDay.add(i + ":" + j);
                }
            }
        }
        rightRecycleDataList.add(otherDay);
    }

    @Override
    public void getPickedTime(String finalTime) {
        Log.i(TAG, "getPickedTime: finalTime>>>>>" + finalTime);
        this.finalTime = finalTime;
        tv_pickTime.setText(finalTime);
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

    private boolean checkPriceValidity(String str_price) {
        if (TextUtils.isEmpty(str_price)) {
            tv_tips.setVisibility(View.GONE);
            return false;
        }

        if (str_price.equals("0")) {
            tv_tips.setVisibility(View.VISIBLE);
            return false;
        }

        if (str_price.length() >= 2) {
            if ((str_price.charAt(0) == '0' && str_price.charAt(1) == '0') ||
                    (str_price.charAt(0) == '0' && str_price.charAt(1) > '0' && str_price.charAt(1) <= '9')) {
                str_price = str_price.substring(1, str_price.length());
                et_price.setText(str_price);
                tv_tips.setVisibility(View.VISIBLE);
                return false;
            }
        }

        //支持小数点前3位以及小数点后1位
        if (str_price.matches("^\\d{1,3}(\\.\\d{1})?$")) {
            tv_tips.setVisibility(View.GONE);
            return true;
        } else {
            tv_tips.setVisibility(View.VISIBLE);
            return false;
        }
    }

    @Override
    public void publishIndentSuccess(Integer result) {
        Log.i(TAG, "publishIndentSuccess: result>>>>>"+result);

        //发布成功
        if (result == 1) {
            LemonBubble.showRight(this, "发布成功！", 1500);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(PublishActivity.this, MainActivity.class));
                }
            }, 1600);
        }
        //发布失败
        else {
            LemonBubble.showError(this, "发布失败！", 1500);
        }
    }

    @Override
    public void publishIndentFail() {
        Log.i(TAG, "publishIndentFail");

        LemonBubble.showError(this, "网络异常，请重试！", 1500);
    }

    private class MyHandler extends Handler {

        private WeakReference<Context> reference;

        public MyHandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            PublishActivity publishActivity = (PublishActivity) reference.get();
            if (publishActivity != null) {
                switch (msg.what) {
                    //监听输入框的输入是否合法
                    case LISTEN_EDIT:
                        //获取输入框的内容
                        str_description = et_description.getText().toString().trim();
                        str_address = et_address.getText().toString().trim();
                        str_price = et_price.getText().toString();

                        //如果全部都符合条件了，发布按钮就处于可按状态，否则就不可按
                        if (!TextUtils.isEmpty(str_description) &&
                                !TextUtils.isEmpty(str_address) &&
                                checkPriceValidity(str_price)) {
                            btn_publish.setEnabled(true);
                        } else {
                            btn_publish.setEnabled(false);
                        }
                        mHandler.sendEmptyMessageDelayed(LISTEN_EDIT, 500);
                        break;
                }
            }
        }
    }
}
