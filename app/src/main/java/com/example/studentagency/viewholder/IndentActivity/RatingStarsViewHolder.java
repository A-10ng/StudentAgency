package com.example.studentagency.viewholder.IndentActivity;

import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.studentagency.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/23
 * desc:
 */
public class RatingStarsViewHolder extends RecyclerView.ViewHolder {

    public RatingBar ratingBar;
    public TextView tv_happenTime;
    public TextView tv_increasement;

    public RatingStarsViewHolder(@NonNull View itemView) {
        super(itemView);

        ratingBar = itemView.findViewById(R.id.ratingBar);
        tv_happenTime = itemView.findViewById(R.id.tv_happenTime);
        tv_increasement = itemView.findViewById(R.id.tv_increasement);
    }
}
