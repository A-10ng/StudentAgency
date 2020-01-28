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
 * time：2020/01/08
 * desc:
 */
public class CommentViewHolder extends RecyclerView.ViewHolder {

    public ImageView iv_comment_avatar;
    public TextView tv_comment_username,tv_commentTime,tv_comment_content;

    public CommentViewHolder(@NonNull View itemView) {
        super(itemView);
        iv_comment_avatar = itemView.findViewById(R.id.iv_comment_avatar);
        tv_comment_username = itemView.findViewById(R.id.tv_comment_username);
        tv_commentTime = itemView.findViewById(R.id.tv_commentTime);
        tv_comment_content = itemView.findViewById(R.id.tv_comment_content);
    }

    public View getView(){
        return this.itemView;
    }
}
