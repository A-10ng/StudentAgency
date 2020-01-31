package com.example.studentagency.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.studentagency.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/01/28
 * desc:
 */
public class PickSchoolActivityRecyclerViewAdapter extends RecyclerView.Adapter {

    private List<String> dataList = new ArrayList<>();

    private OnClickItemListener onClickItemListener;

    public PickSchoolActivityRecyclerViewAdapter(List<String> dataList) {
        this.dataList = dataList;
    }

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_pick_school_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder)holder).tv_schoolName.setText(dataList.get(position));
        ((MyViewHolder)holder).tv_schoolName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItemListener != null){
                    onClickItemListener.clickItem(dataList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }


    public void update(List<String> filterDataList) {
        dataList = filterDataList;
        notifyDataSetChanged();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_schoolName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_schoolName = itemView.findViewById(R.id.tv_schoolName);
        }
    }

    public interface OnClickItemListener{
        void clickItem(String schoolName);
    }
}
