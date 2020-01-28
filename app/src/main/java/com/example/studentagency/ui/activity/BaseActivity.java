package com.example.studentagency.ui.activity;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.studentagency.R;
import com.example.studentagency.Utils.ActivityCollector;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        //改变状态栏颜色
        changeStatusBarColor();

        ActivityCollector.addActivity(this);
        Log.i(TAG, "BaseActivity add Activity : " + getClass().getSimpleName());
    }

    private void changeStatusBarColor() {
        Window window = getWindow();
        //取消状态栏透明
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //添加Flag把状态栏设为可绘制模式
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.themeColor));
        //设置系统状态栏处于可见状态
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
        Log.i(TAG, "BaseActivity remove Activity : " + getClass().getSimpleName());
    }
}
