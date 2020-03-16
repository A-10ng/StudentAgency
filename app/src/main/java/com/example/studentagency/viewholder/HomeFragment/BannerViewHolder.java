package com.example.studentagency.viewholder.HomeFragment;

import android.view.View;

import com.example.studentagency.R;
import com.zhengsr.viewpagerlib.indicator.ZoomIndicator;
import com.zhengsr.viewpagerlib.view.BannerViewPager;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2019/12/18
 * desc:
 */
public class BannerViewHolder extends RecyclerView.ViewHolder {

     public BannerViewPager bannerViewPager;
     public ZoomIndicator zoomIndicator;

    public BannerViewHolder(@NonNull View itemView) {
        super(itemView);
        bannerViewPager = itemView.findViewById(R.id.bannerViewPager);
        zoomIndicator = itemView.findViewById(R.id.zoomIndicator);
    }
}
