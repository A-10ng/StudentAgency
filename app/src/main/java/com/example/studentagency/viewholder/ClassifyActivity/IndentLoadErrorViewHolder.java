package com.example.studentagency.viewholder.ClassifyActivity;

import android.view.View;
import android.widget.ImageView;

import com.example.studentagency.R;

import androidx.recyclerview.widget.RecyclerView;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/01/01
 * desc:
 */
public class IndentLoadErrorViewHolder extends RecyclerView.ViewHolder {

    public ImageView iv_loadError;

    public IndentLoadErrorViewHolder(View view) {
        super(view);
        iv_loadError = view.findViewById(R.id.iv_loadError);
    }
}
