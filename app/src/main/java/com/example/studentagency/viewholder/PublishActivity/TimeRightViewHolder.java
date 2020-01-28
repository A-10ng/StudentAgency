package com.example.studentagency.viewholder.PublishActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.studentagency.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/01/14
 * desc:
 */
public class TimeRightViewHolder extends RecyclerView.ViewHolder {

    public TextView tv_time;
    public ImageView iv_picked;
    public RelativeLayout root_relative;

    public TimeRightViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_time = itemView.findViewById(R.id.tv_time);
        iv_picked = itemView.findViewById(R.id.iv_picked);
        root_relative = itemView.findViewById(R.id.root_relative);
    }
}
