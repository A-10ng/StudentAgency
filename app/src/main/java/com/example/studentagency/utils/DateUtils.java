package com.example.studentagency.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2019/10/14
 * desc:
 */
public class DateUtils {
    private static final String TAG = "DateUtils";

    public static String getCurrentDateByFormat(String format) {
        Date currentTime = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.CHINA);
        return dateFormat.format(currentTime);
    }

    public static String addDaysToMonAndDate(int num) {
        Date currentTime = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM月dd日", Locale.CHINA);

        String currentMonAndDate = dateFormat.format(currentTime);
        Log.i(TAG, "现在的日期是：" + currentMonAndDate);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, num);// num为增加的天数，可以改变的
        currentTime = calendar.getTime();
        String enddate = dateFormat.format(currentTime);
        Log.i(TAG, "增加天数以后的日期：" + enddate);
        return enddate;
    }
}
