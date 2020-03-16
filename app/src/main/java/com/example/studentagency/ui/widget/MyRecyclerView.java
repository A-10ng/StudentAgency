package com.example.studentagency.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2019/12/17
 * desc:
 */
public class MyRecyclerView extends RecyclerView {
    private static final String TAG = "MyRecyclerView";

    public MyRecyclerView(@NonNull Context context) {
        super(context);
    }

    public MyRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        LinearLayoutManager manager = (LinearLayoutManager) getLayoutManager();
        int childCount = manager.getChildCount();
        //对每个item进行初始化
        for (int i = 0; i < childCount; i++) {
            manager.getChildAt(i).setAlpha(1);
            manager.getChildAt(i).setScaleX(1);
            manager.getChildAt(i).setScaleY(1);
        }
        calculateAlphaAndScale(this,manager);
    }

    private void calculateAlphaAndScale(MyRecyclerView myRecyclerView, LinearLayoutManager manager) {
        int firstItemPosition = manager.findFirstVisibleItemPosition();
        int lastItemPosition = manager.findLastVisibleItemPosition();

        //根据索引找到当前视图的最下面的一个View
        View lastView = manager.getChildAt(lastItemPosition - firstItemPosition);
        if (lastView != null){
            /**
             * 当找到最后一个View时，根据可见部分占view总部分的占比，计算出透明度和缩放比例
             */
            //一个item的高度
            int itemHeight = lastView.getHeight();
            //最后一个view可见部分的高度
            int visibleHeight = myRecyclerView.getHeight() - lastView.getTop();//
            if (visibleHeight < 0){
                return;
            }
            float ratio = visibleHeight * 1.0f / itemHeight;
            if (ratio > 1.0){
                return;
            }
            lastView.setAlpha(ratio);
            float scale = 0.9f;//默认最小的缩放比例
            float scaleFactor = scale + (1 - scale) * ratio;
            lastView.setScaleX(scaleFactor);//按X轴缩放
            lastView.setScaleY(scaleFactor);//按Y轴缩放
        }
    }
}
