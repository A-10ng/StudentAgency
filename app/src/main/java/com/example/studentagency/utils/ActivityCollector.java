package com.example.studentagency.utils;

import android.app.Activity;

import java.util.ArrayList;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2019/10/28
 * desc:该类主要用来实现“退出登录”功能
 */
public class ActivityCollector {
    private static ArrayList<Activity> activityList = new ArrayList<>();

    //禁止外部实例化
    private ActivityCollector(){}

    public static void addActivity(Activity activity){
        activityList.add(activity);
    }

    public static void removeActivity(Activity activity){
        activityList.remove(activity);
    }

    public static void finishAll(){
        for (Activity activity : activityList) {
            if (!activity.isFinishing()){
                activity.finish();
            }
        }

        activityList.clear();
    }
}
