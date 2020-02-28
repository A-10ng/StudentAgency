package com.example.studentagency.viewholder.MessageFragment;

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
 * time：2020/02/26
 * desc:
 */
public class ItemViewHolder extends RecyclerView.ViewHolder {

    public ImageView iv_avatar;
    public TextView tv_nickname;
    public TextView tv_content;
    public ImageView iv_red_dot;
    public RelativeLayout layout_item;

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
        iv_avatar = itemView.findViewById(R.id.iv_avatar);
        tv_nickname = itemView.findViewById(R.id.tv_nickname);
        tv_content = itemView.findViewById(R.id.tv_content);
        iv_red_dot = itemView.findViewById(R.id.iv_red_dot);
        layout_item = itemView.findViewById(R.id.layout_item);
    }
}
