package com.example.studentagency.viewholder.CreditScoreRecordActivity;

import android.view.View;
import android.widget.TextView;

import com.example.studentagency.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/18
 * desc:
 */
public class CreditViewHolder extends RecyclerView.ViewHolder{

    public TextView tv_description;
    public TextView tv_date;
    public TextView tv_increasement;

    public CreditViewHolder(@NonNull View itemView) {
        super(itemView);

        tv_description = itemView.findViewById(R.id.tv_description);
        tv_date = itemView.findViewById(R.id.tv_date);
        tv_increasement = itemView.findViewById(R.id.tv_increasement);
    }
}
