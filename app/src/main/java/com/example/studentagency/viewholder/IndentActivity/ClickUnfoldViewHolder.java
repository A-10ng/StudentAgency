package com.example.studentagency.viewholder.IndentActivity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.studentagency.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/01/08
 * desc:
 */
public class ClickUnfoldViewHolder extends RecyclerView.ViewHolder {

    public LinearLayout layout_root;

    public ClickUnfoldViewHolder(@NonNull View itemView) {
        super(itemView);
        layout_root = itemView.findViewById(R.id.layout_root);
    }
}
