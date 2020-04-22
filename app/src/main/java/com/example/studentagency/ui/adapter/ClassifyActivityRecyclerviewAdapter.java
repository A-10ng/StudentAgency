package com.example.studentagency.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.studentagency.R;
import com.example.studentagency.bean.IndentBean;
import com.example.studentagency.viewholder.ClassifyActivity.IndentLoadErrorViewHolder;
import com.example.studentagency.viewholder.ClassifyActivity.IndentViewHolder;
import com.example.studentagency.viewholder.ClassifyActivity.NoIndentViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/01/10
 * desc:
 */
public class ClassifyActivityRecyclerviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_INDENT = 0;
    private static final int ITEM_NONE = 1;
    private static final int ITEM_ERROR = 2;
    private List<Object> mDataList;
    private IndentItemClickListenr indentItemClickListener;

    public ClassifyActivityRecyclerviewAdapter(List<Object> mDataList) {
        this.mDataList = mDataList;
    }

    public void setIndentItemClickListener(IndentItemClickListenr indentItemClickListener) {
        this.indentItemClickListener = indentItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder;
        if (viewType == ITEM_INDENT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_classify_indent_item, parent, false);
            viewHolder = new IndentViewHolder(view);
        } else if (viewType == ITEM_ERROR) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_classify_indent_loaderror, parent, false);
            viewHolder = new IndentLoadErrorViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_no_indent_item, parent, false);
            viewHolder = new NoIndentViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //如果是订单
        if (holder instanceof IndentViewHolder) {
            bindIndentViewHolder(holder, position);
        }
    }

    private void bindIndentViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final IndentBean bean = (IndentBean) mDataList.get(position);

        RequestOptions options = RequestOptions.circleCropTransform();
        Glide.with(((IndentViewHolder) holder).getView())
                .load(bean.getAvatar())
                .placeholder(R.drawable.placeholder_pic)
                .apply(options)
                .into(((IndentViewHolder) holder).iv_avatar);

        ((IndentViewHolder) holder).tv_username.setText(bean.getUsername());

        if (3 == bean.getVerifyState()) {
            ((IndentViewHolder) holder).iv_verifyState.setImageResource(R.drawable.verified);
        } else {
            ((IndentViewHolder) holder).iv_verifyState.setImageResource(R.drawable.unverified);
        }

        ((IndentViewHolder) holder).tv_description.setText(bean.getDescription());
        ((IndentViewHolder) holder).tv_price.setText("￥ "+bean.getPrice());
        ((IndentViewHolder) holder).tv_plantime.setText(bean.getPlanTime());
        ((IndentViewHolder) holder).tv_address.setText(bean.getAddress());

        ((IndentViewHolder) holder).item_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (indentItemClickListener != null) {
                    indentItemClickListener.onIndentItemClick(bean.getPublishId(),bean.getIndentId(), position);
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        if (mDataList.get(position) instanceof IndentBean) {
            return ITEM_INDENT;
        } else if (mDataList.get(position) == "暂无更多订单") {
            return ITEM_NONE;
        } else if (mDataList.get(position) == "加载失败") {
            return ITEM_ERROR;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @SuppressWarnings("unchecked")
    public void setIndentData(Object data) {
        mDataList.clear();
        if (data instanceof String){
            mDataList.add(data);
        }else {
            mDataList.addAll((List<IndentBean>)data);
        }
        notifyDataSetChanged();
    }

    public void loadMoreData(List<IndentBean> tempList) {
        mDataList.addAll(tempList);
        notifyDataSetChanged();
    }

    public interface IndentItemClickListenr {
        void onIndentItemClick(int publishId,int indentId, int position);
    }
}
