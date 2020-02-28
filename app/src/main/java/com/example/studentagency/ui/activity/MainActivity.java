package com.example.studentagency.ui.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentagency.R;
import com.example.studentagency.utils.ActivityCollector;
import com.example.studentagency.ui.adapter.MyFragmentPagerAdapter;
import com.example.studentagency.ui.fragment.MainActivity.HomeFragment;
import com.example.studentagency.ui.fragment.MainActivity.MarketFragment;
import com.example.studentagency.ui.fragment.MainActivity.MessageFragment;
import com.example.studentagency.ui.fragment.MainActivity.PersonFragment;
import com.example.studentagency.ui.widget.TitleBar;
import com.example.studentagency.utils.VariableName;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.OfflineMessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;

import static com.example.studentagency.ui.activity.MyApp.hadLogin;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private static final int WRITE_EXTERNAL_STORAGE = 200;
    private View v_red_dot;
    private TitleBar titleBar;
    private List<Fragment> fragments;
    private ViewPager viewPager;
    //底部导航栏的总布局
    private LinearLayout llayout_home, llayout_market, llayout_message, llayout_person;
    //底部导航栏的图标
    private ImageView iv_home, iv_market, iv_message, iv_person, iv_current;
    //底部导航栏的文字
    private TextView tv_home, tv_market, tv_message, tv_person, tv_current;
    //记录当前点击返回键的时间
    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //订阅接收消息,子类只要重写onEvent就能收到消息
        JMessageClient.registerEventReceiver(this);

        //初始化控件及监听事件
        initViews();

        //绑定fragment到activity上
        bindingFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (hadLogin){
            setRedDotState();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        if (hadLogin){
            setRedDotState();
        }
    }

    private void initViews() {
        v_red_dot = findViewById(R.id.v_red_dot);

        titleBar = findViewById(R.id.titleBar);

        //底部导航栏linearlayout初始化
        llayout_home = findViewById(R.id.llayout_home);
        llayout_market = findViewById(R.id.llayout_market);
        llayout_message = findViewById(R.id.llayout_message);
        llayout_person = findViewById(R.id.llayout_person);

        //底部导航栏imageview初始化
        iv_home = findViewById(R.id.iv_home);
        iv_market = findViewById(R.id.iv_market);
        iv_message = findViewById(R.id.iv_message);
        iv_person = findViewById(R.id.iv_person);

        //底部导航栏textview初始化
        tv_home = findViewById(R.id.tv_home);
        tv_market = findViewById(R.id.tv_market);
        tv_message = findViewById(R.id.tv_message);
        tv_person = findViewById(R.id.tv_person);

        //默认选中首页的imageview和textview
        iv_current = iv_home;
        tv_current = tv_home;
        iv_home.setSelected(true);
        tv_home.setSelected(true);

        llayout_home.setOnClickListener(this);
        llayout_market.setOnClickListener(this);
        llayout_message.setOnClickListener(this);
        llayout_person.setOnClickListener(this);

        viewPager = findViewById(R.id.viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                changeTab(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void bindingFragment() {
        //准备fragment
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new MarketFragment());
        fragments.add(new MessageFragment());
        fragments.add(new PersonFragment());

        //将viewpager与fragment绑定
        MyFragmentPagerAdapter pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(pagerAdapter);
    }

    public void setRedDotState() {
        List<Conversation> conversations = JMessageClient.getConversationList();
        for (Conversation conversation : conversations) {
            if (conversation.getExtra().equals(VariableName.NEW_MESSAGE)) {
                setNew(true);
            }
        }
    }

    //改变底部栏图标和文字的样式
    private void changeTab(int position) {
        //取消选中当前的图标和文字
        iv_current.setSelected(false);
        tv_current.setSelected(false);
        switch (position) {
            //首页
            case R.id.llayout_home:
            case 0:
                titleBar.setTitle_name("首页");

                iv_home.setSelected(true);
                tv_home.setSelected(true);
                iv_current = iv_home;
                tv_current = tv_home;
                viewPager.setCurrentItem(0);
                break;
            //市场
            case R.id.llayout_market:
            case 1:
                titleBar.setTitle_name("市场");

                iv_market.setSelected(true);
                tv_market.setSelected(true);
                iv_current = iv_market;
                tv_current = tv_market;
                viewPager.setCurrentItem(1);
                break;
            //消息
            case R.id.llayout_message:
            case 2:
                titleBar.setTitle_name("消息");

                iv_message.setSelected(true);
                tv_message.setSelected(true);
                iv_current = iv_message;
                tv_current = tv_message;
                viewPager.setCurrentItem(2);
                break;
            //个人
            case R.id.llayout_person:
            case 3:
                titleBar.setTitle_name("个人");

                iv_person.setSelected(true);
                tv_person.setSelected(true);
                iv_current = iv_person;
                tv_current = tv_person;
                viewPager.setCurrentItem(3);
                break;
        }
    }

    public void setNew(boolean isNew) {
        if (isNew) {
            v_red_dot.setVisibility(View.VISIBLE);
        } else {
            v_red_dot.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        changeTab(v.getId());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断用户是否点击了“返回键”
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //与上次点击返回键时刻作差
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                //大于2000ms则认为是误操作，使用Toast进行提示
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                //并记录下本次点击“返回键”的时刻，以便下次进行判断
                mExitTime = System.currentTimeMillis();
            } else {
                ActivityCollector.finishAll();
                //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //接受了离线消息
    public void onEventMainThread(OfflineMessageEvent event) {
        if (hadLogin){
            if (event.getConversation().getExtra().equals(VariableName.NEW_MESSAGE)) {
                setNew(true);
            }
        }
    }
}
