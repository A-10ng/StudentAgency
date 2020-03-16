package com.example.studentagency.viewholder.AddressActivity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.studentagency.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/19
 * desc:
 */
public class ItemViewHolder extends RecyclerView.ViewHolder {

    public TextView tv_tag;
    public TextView tv_username;
    public TextView tv_defaultTag;
    public TextView tv_address;
    public TextView tv_editAddress;
    public LinearLayout item_root;

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_tag = itemView.findViewById(R.id.tv_tag);
        tv_username = itemView.findViewById(R.id.tv_username);
        tv_defaultTag = itemView.findViewById(R.id.tv_defaultTag);
        tv_address = itemView.findViewById(R.id.tv_address);
        tv_editAddress = itemView.findViewById(R.id.tv_editAddress);
        item_root = itemView.findViewById(R.id.item_root);
    }
}
