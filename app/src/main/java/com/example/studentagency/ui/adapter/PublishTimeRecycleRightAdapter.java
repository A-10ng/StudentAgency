package com.example.studentagency.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.studentagency.R;
import com.example.studentagency.viewholder.PublishActivity.TimeRightViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/01/14
 * desc:
 */
public class PublishTimeRecycleRightAdapter extends RecyclerView.Adapter {

    private List<String> mRightDataList;
    private OnClickRightItemListener onClickRightItemListener;
    private int selected_position = -1;

    public PublishTimeRecycleRightAdapter(List<String> mRightDataList) {
        this.mRightDataList = mRightDataList;
    }

    public void setOnClickRightItemListener(OnClickRightItemListener onClickRightItemListener) {
        this.onClickRightItemListener = onClickRightItemListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_time_recycle_right_item,parent,false);
        TimeRightViewHolder viewHolder = new TimeRightViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((TimeRightViewHolder)holder).tv_time.setText(mRightDataList.get(position));

        if (position == selected_position){
            ((TimeRightViewHolder)holder).iv_picked.setVisibility(View.VISIBLE);
        }else {
            ((TimeRightViewHolder)holder).iv_picked.setVisibility(View.GONE);
        }

        ((TimeRightViewHolder)holder).root_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected_position = position;
                notifyDataSetChanged();
                if (null != onClickRightItemListener){
                    onClickRightItemListener.clickRightItem(mRightDataList.get(position),position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRightDataList == null ? 0 : mRightDataList.size();
    }

    public interface OnClickRightItemListener{
        void clickRightItem(String rightTime,int position);
    }
}
