package com.example.studentagency.viewholder.HomeFragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.studentagency.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2019/12/18
 * desc:
 */
public class ClassifyViewHolder extends RecyclerView.ViewHolder {

    public LinearLayout root_shopping;
    public LinearLayout root_delivery;
    public LinearLayout root_others;

    public ImageView iv_shopping;
    public TextView tv_shopping;
    public ImageView iv_delivery;
    public TextView tv_delivery;
    public ImageView iv_others;
    public TextView tv_others;

    public ClassifyViewHolder(@NonNull View itemView) {
        super(itemView);
        root_shopping = itemView.findViewById(R.id.root_shopping);
        root_delivery = itemView.findViewById(R.id.root_delivery);
        root_others = itemView.findViewById(R.id.root_others);

        iv_shopping = itemView.findViewById(R.id.iv_shopping);
        tv_shopping = itemView.findViewById(R.id.tv_shopping);
        iv_delivery = itemView.findViewById(R.id.iv_delivery);
        tv_delivery = itemView.findViewById(R.id.tv_delivery);
        iv_others = itemView.findViewById(R.id.iv_others);
        tv_others = itemView.findViewById(R.id.tv_others);
    }
}
