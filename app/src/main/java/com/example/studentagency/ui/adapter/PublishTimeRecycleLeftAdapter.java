package com.example.studentagency.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.studentagency.R;
import com.example.studentagency.viewholder.PublishActivity.TimeLeftViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/01/14
 * desc:
 */
public class PublishTimeRecycleLeftAdapter extends RecyclerView.Adapter {

    private List<String> mLeftDataList;
    private int selected_position = 0;
    private OnClickLeftItemListener onClickLeftItemListener;
    private Context context;

    public PublishTimeRecycleLeftAdapter(List<String> mDataList,Context context) {
        this.mLeftDataList = mDataList;
        this.context = context;
    }

    public void setOnClickLeftItemListener(OnClickLeftItemListener onClickLeftItemListener) {
        this.onClickLeftItemListener = onClickLeftItemListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_time_recycle_left_item,parent,false);
        return new TimeLeftViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (position == selected_position){
            ((TimeLeftViewHolder)holder).root_layout.setBackgroundColor(Color.WHITE);
        }else {
            ((TimeLeftViewHolder)holder).root_layout.setBackgroundColor(ContextCompat.getColor(context,R.color.lightgray));
        }

        ((TimeLeftViewHolder)holder).tv_time.setText(mLeftDataList.get(position));

        ((TimeLeftViewHolder)holder).root_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected_position = position;
                notifyDataSetChanged();

                if (null != onClickLeftItemListener){
                    onClickLeftItemListener.clickLeftItem(mLeftDataList.get(position),position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mLeftDataList == null ? 0 : mLeftDataList.size();
    }

    public interface OnClickLeftItemListener{
        void clickLeftItem(String leftTime,int position);
    }
}
