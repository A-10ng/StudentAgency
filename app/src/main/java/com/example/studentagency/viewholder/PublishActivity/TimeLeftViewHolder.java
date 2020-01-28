package com.example.studentagency.viewholder.PublishActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
public class TimeLeftViewHolder extends RecyclerView.ViewHolder {

    public RelativeLayout root_layout;
    public TextView tv_time;

    public TimeLeftViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_time = itemView.findViewById(R.id.tv_time);
        root_layout = itemView.findViewById(R.id.root_layout);
    }
}
