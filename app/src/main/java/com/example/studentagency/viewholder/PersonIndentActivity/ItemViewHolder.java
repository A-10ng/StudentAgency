package com.example.studentagency.viewholder.PersonIndentActivity;

import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
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

    public TextView tv_price;
    public TextView tv_type;
    public TextView tv_state;
    public TextView tv_description;
    public TextView tv_address;
    public TextView tv_planTime;
    public TextView tv_publishTime;
    public TextView tv_acceptTime_hint,tv_acceptTime;
    public Button btn_num1,btn_num2;
    public RelativeLayout layout_root;

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_price = itemView.findViewById(R.id.tv_price);
        tv_publishTime = itemView.findViewById(R.id.tv_publishTime);
        tv_type = itemView.findViewById(R.id.tv_type);
        tv_state = itemView.findViewById(R.id.tv_state);
        tv_description = itemView.findViewById(R.id.tv_description);
        tv_address = itemView.findViewById(R.id.tv_address);
        tv_planTime = itemView.findViewById(R.id.tv_planTime);
        tv_acceptTime_hint = itemView.findViewById(R.id.tv_acceptTime_hint);
        tv_acceptTime = itemView.findViewById(R.id.tv_acceptTime);
        btn_num1 = itemView.findViewById(R.id.btn_num1);
        btn_num2 = itemView.findViewById(R.id.btn_num2);
        layout_root = itemView.findViewById(R.id.layout_root);
    }
}
