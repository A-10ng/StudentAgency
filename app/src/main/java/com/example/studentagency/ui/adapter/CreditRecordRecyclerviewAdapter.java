package com.example.studentagency.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.studentagency.R;
import com.example.studentagency.bean.CreditBean;
import com.example.studentagency.viewholder.CreditScoreRecordActivity.CreditViewHolder;
import com.example.studentagency.viewholder.CreditScoreRecordActivity.ErrorViewHolder;
import com.example.studentagency.viewholder.CreditScoreRecordActivity.NodataViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/18
 * desc:
 */
public class CreditRecordRecyclerviewAdapter extends RecyclerView.Adapter {

    private static final String TAG = "CreditRecordRecyclervie";
    private List<Object> dataList;
    private static final int CREDIT_RECORD = 0;
    private static final int NO_RECORD = 1;
    private static final int ERROR = 2;

    public CreditRecordRecyclerviewAdapter(List<Object> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view = null;
        if (CREDIT_RECORD == viewType){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_credit_record_item,parent,false);
            viewHolder = new CreditViewHolder(view);
        }
        else if (NO_RECORD == viewType){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_credit_record_nodata,parent,false);
            viewHolder = new NodataViewHolder(view);
        }
        else if (ERROR == viewType){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_credit_record_error,parent,false);
            viewHolder = new ErrorViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CreditViewHolder){
            CreditBean creditBean = (CreditBean) dataList.get(position);
            ((CreditViewHolder)holder).tv_description.setText(creditBean.getDescription());
            ((CreditViewHolder)holder).tv_date.setText(creditBean.getDate());

            if (creditBean.getIncreasement() > 0){
                ((CreditViewHolder)holder).tv_increasement.setText("+"+creditBean.getIncreasement());
            }
            else if (creditBean.getIncreasement() == 0){
                ((CreditViewHolder)holder).tv_increasement.setText("0");
            }
            else {
                ((CreditViewHolder)holder).tv_increasement.setText(""+creditBean.getIncreasement());
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (dataList.get(position) instanceof CreditBean){
            return CREDIT_RECORD;
        }
        else if (dataList.get(position).equals("暂无数据") || dataList.get(position) == "暂无数据"){
            return NO_RECORD;
        }
        else if (dataList.get(position).equals("获取失败") || dataList.get(position) == "获取失败"){
            return ERROR;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 :dataList.size();
    }

    public void update(List<Object> newDataList) {
        Log.i(TAG, "update: newDataList.size()>>>>>"+newDataList.size());

        Log.i(TAG, "update: before dataList.size()>>>>>"+dataList.size());

        dataList.clear();
        dataList.addAll(newDataList);

        Log.i(TAG, "update: after dataList.size()>>>>>"+dataList.size());
        notifyDataSetChanged();
    }
}
