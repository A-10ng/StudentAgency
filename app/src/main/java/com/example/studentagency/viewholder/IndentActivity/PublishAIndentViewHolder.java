package com.example.studentagency.viewholder.IndentActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.studentagency.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/01/07
 * desc:
 */
public class PublishAIndentViewHolder extends RecyclerView.ViewHolder {

    //发布方
    public ImageView iv_avatar,iv_gender,iv_verifyState;
    public TextView tv_username,tv_creditScore,tv_publishTime;

    //订单信息
    public TextView tv_indentId,tv_price,tv_type,tv_address,tv_planTime;

    //代办描述
    public TextView tv_description;

    public PublishAIndentViewHolder(@NonNull View itemView) {
        super(itemView);
        //发布方信息
        iv_avatar = itemView.findViewById(R.id.iv_avatar);
        iv_gender = itemView.findViewById(R.id.iv_gender);
        iv_verifyState = itemView.findViewById(R.id.iv_verifyState);
        tv_username = itemView.findViewById(R.id.tv_username);
        tv_creditScore = itemView.findViewById(R.id.tv_creditScore);
        tv_publishTime = itemView.findViewById(R.id.tv_publishTime);

        //订单信息
        tv_indentId = itemView.findViewById(R.id.tv_indentId);
        tv_price = itemView.findViewById(R.id.tv_price);
        tv_type = itemView.findViewById(R.id.tv_type);
        tv_address = itemView.findViewById(R.id.tv_address);
        tv_planTime = itemView.findViewById(R.id.tv_planTime);

        //代办描述
        tv_description = itemView.findViewById(R.id.tv_description);
    }

    public View getView(){
        return this.itemView;
    }
}
