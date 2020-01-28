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
public class IndentViewHolder extends RecyclerView.ViewHolder {

    public LinearLayout item_root;
    public ImageView iv_avatar;
    public TextView tv_username;
    public ImageView iv_verifyState;
    public ImageView iv_state;
    public TextView tv_description;
    public TextView tv_price;
    public TextView tv_address;
    private View itemView;


    public IndentViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;
        iv_avatar = itemView.findViewById(R.id.iv_avatar);
        tv_username = itemView.findViewById(R.id.tv_username);
        iv_verifyState = itemView.findViewById(R.id.iv_verifyState);
        iv_state = itemView.findViewById(R.id.iv_state);
        tv_description = itemView.findViewById(R.id.tv_description);
        tv_price = itemView.findViewById(R.id.tv_price);
        tv_address = itemView.findViewById(R.id.tv_address);
        item_root = itemView.findViewById(R.id.item_root);
    }

    public View getView(){
        return this.itemView;
    }

}
