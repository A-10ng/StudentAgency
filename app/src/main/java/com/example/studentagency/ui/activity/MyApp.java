package com.example.studentagency.ui.activity;

import android.app.Application;
import android.content.Context;

import com.example.studentagency.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.wuyr.coffeeheader.CoffeeHeader;

import androidx.annotation.NonNull;
import cn.jpush.android.api.JPushInterface;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2019/12/16
 * desc:
 */
public class MyApp extends Application {

    public static final String PLACEHOLDER_PIC = "http://www.longsh1z.top/resources/placeholder_pic.png";
    public static int userId = 20160001;
    public static boolean hadLogin = false;

    @Override
    public void onCreate() {
        super.onCreate();

        //极光推送初始化
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
