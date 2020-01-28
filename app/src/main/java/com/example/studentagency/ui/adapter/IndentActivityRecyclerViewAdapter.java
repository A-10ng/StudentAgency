package com.example.studentagency.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.studentagency.R;
import com.example.studentagency.bean.CommentBean;
import com.example.studentagency.bean.PublishAndIndentBean;
import com.example.studentagency.viewholder.IndentActivity.BlankViewHolder;
import com.example.studentagency.viewholder.IndentActivity.ClickUnfoldBiewHolder;
import com.example.studentagency.viewholder.IndentActivity.CommentViewHolder;
import com.example.studentagency.viewholder.IndentActivity.NoCommentViewHolder;
import com.example.studentagency.viewholder.IndentActivity.PublishAIndentViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/01/06
 * desc:
 */
public class IndentActivityRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_PUBLISH_AND_INDENT = 0;
    private static final int ITEM_COMMENT_CONTENT = 1;
    private static final int ITEM_COMMENT_NONE = 2;
    private static final int ITEM_CLICKUNFOLD = 3;
    private static final int ITEM_BLANK = 4;
    private static final String TAG = "IndentActivityRecyclerV";

    private List<Object> mDataList;
    private List<CommentBean> allCommentDataList = new ArrayList<>();
    private int commentDataList_position;//记录当前读到的留言位置

    public IndentActivityRecyclerViewAdapter(List<Object> mDataList) {
        this.mDataList = mDataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == ITEM_PUBLISH_AND_INDENT) {
            view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.layout_indent_info_publish_and_indent, parent, false);
            viewHolder = new PublishAIndentViewHolder(view);
        }
        else if (viewType == ITEM_COMMENT_NONE) {
            view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.layout_indent_info_no_comment, parent, false);
            viewHolder = new NoCommentViewHolder(view);
        }
        else if (viewType == ITEM_COMMENT_CONTENT) {
            view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.layout_indent_info_comment, parent, false);
            viewHolder = new CommentViewHolder(view);
        }else if (viewType == ITEM_CLICKUNFOLD){
            view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.layout_indent_info_clickunfold, parent, false);
            viewHolder = new ClickUnfoldBiewHolder(view);
        }
        else if (viewType == ITEM_BLANK){
            view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.layout_indent_info_blank, parent, false);
            viewHolder = new BlankViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PublishAIndentViewHolder) {
            bindPublishAndIndentViewHolder(holder, position);
        } else if (holder instanceof CommentViewHolder) {
            bindCommentViewHolder(holder, position);
        }else if (holder instanceof ClickUnfoldBiewHolder){
            bindClickUnfoldViewHolder(holder, position);
        }
        /**
         * 无人留言的时候不用处理
         */
    }

    private void bindClickUnfoldViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ClickUnfoldBiewHolder) holder).layout_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commentDataList_position + 5 >= allCommentDataList.size()){
                    mDataList.subList(mDataList.size() - 2,mDataList.size()).clear();
                    mDataList.addAll(allCommentDataList.subList(commentDataList_position,allCommentDataList.size()));
                    mDataList.add("空白处");
                    commentDataList_position = allCommentDataList.size();
                }else {
                    mDataList.subList(mDataList.size() - 2,mDataList.size()).clear();
                    mDataList.addAll(allCommentDataList.subList(commentDataList_position,commentDataList_position+5));
                    mDataList.add("点击展开更多留言");
                    mDataList.add("空白处");
                    commentDataList_position += 5;
                }
                notifyDataSetChanged();
            }
        });
    }

    private void bindPublishAndIndentViewHolder(RecyclerView.ViewHolder holder, int position) {
        PublishAndIndentBean bean = (PublishAndIndentBean) mDataList.get(position);

        //发布方头像
        Glide.with(((PublishAIndentViewHolder) holder).getView())
                .load(bean.getAvatar())
                .placeholder(R.drawable.placeholder_pic)
                .into(((PublishAIndentViewHolder) holder).iv_avatar);

        //用户名
        ((PublishAIndentViewHolder) holder).tv_username.setText(bean.getUsername());

        //用户性别
        if (bean.getGender() == 0) {
            ((PublishAIndentViewHolder) holder).iv_gender.setImageResource(R.drawable.gender_female);
        } else {
            ((PublishAIndentViewHolder) holder).iv_gender.setImageResource(R.drawable.gender_male);
        }

        //审核状态
        if (bean.getVerifyState() == 3) {
            ((PublishAIndentViewHolder) holder).iv_verifyState.setImageResource(R.drawable.verified);
        } else {
            ((PublishAIndentViewHolder) holder).iv_verifyState.setImageResource(R.drawable.unverified);
        }

        //信誉积分
        ((PublishAIndentViewHolder) holder).tv_creditScore.setText("信誉积分: " + bean.getCreditScore());

        //订单发布时间
        ((PublishAIndentViewHolder) holder).tv_publishTime.setText("发布于: " + bean.getPublishTime());

        //订单号
        ((PublishAIndentViewHolder) holder).tv_indentId.setText(""+bean.getIndentId());

        //支付费用
        ((PublishAIndentViewHolder) holder).tv_price.setText(bean.getPrice());

        //代办类型
        if (bean.getType() == 0) {
            ((PublishAIndentViewHolder) holder).tv_type.setText("代购");
        } else if (bean.getType() == 1) {
            ((PublishAIndentViewHolder) holder).tv_type.setText("代拿快递");
        } else {
            ((PublishAIndentViewHolder) holder).tv_type.setText("其他代办");
        }

        //收货地址
        ((PublishAIndentViewHolder) holder).tv_address.setText(bean.getAddress());

        //送达时间
        ((PublishAIndentViewHolder) holder).tv_planTime.setText(bean.getPlanTime());

        //代办描述
        ((PublishAIndentViewHolder) holder).tv_description.setText(bean.getDescription());
    }

    private void bindCommentViewHolder(RecyclerView.ViewHolder holder, int position) {
        CommentBean bean = (CommentBean) mDataList.get(position);

        Glide.with(((CommentViewHolder)holder).getView())
                .load(bean.getAvatar())
                .placeholder(R.drawable.placeholder_pic)
                .into(((CommentViewHolder)holder).iv_comment_avatar);

        ((CommentViewHolder)holder).tv_comment_username.setText(bean.getUsername());

        ((CommentViewHolder)holder).tv_commentTime.setText(bean.getCommentTime());

        ((CommentViewHolder)holder).tv_comment_content.setText(bean.getContent());
    }

    @Override
    public int getItemViewType(int position) {
        if (mDataList.get(position) instanceof PublishAndIndentBean) {
            return ITEM_PUBLISH_AND_INDENT;
        } else if (mDataList.get(position) == "暂无留言") {
            return ITEM_COMMENT_NONE;
        } else if (mDataList.get(position) instanceof CommentBean) {
            return ITEM_COMMENT_CONTENT;
        } else if (mDataList.get(position) == "点击展开更多留言") {
            return ITEM_CLICKUNFOLD;
        }else if (mDataList.get(position) == "空白处"){
            return ITEM_BLANK;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    public void setPublishOrIndentData(PublishAndIndentBean bean) {
        mDataList.set(0,bean);
        notifyDataSetChanged();
    }

    public void setCommentData(List<CommentBean> commentDataList) {
        mDataList.subList(1,mDataList.size()).clear();
        commentDataList_position = 0;
        allCommentDataList.clear();
        allCommentDataList.addAll(commentDataList);

        Log.i(TAG, "setCommentData: commentDataList.size()>>>>>"+commentDataList.size());
        if (commentDataList.isEmpty()){
            mDataList.add("暂无留言");
            mDataList.add("空白处");
        }else {
            if (commentDataList.size() > 5){
                commentDataList_position = 5;
                mDataList.addAll(allCommentDataList.subList(0,commentDataList_position));
                mDataList.add("点击展开更多留言");
                mDataList.add("空白处");
            }
            else {
                mDataList.addAll(commentDataList);
                mDataList.add("空白处");
            }
        }

        notifyDataSetChanged();
    }

    public void addComment(CommentBean commentBean) {
        if (mDataList.get(mDataList.size() - 2) == "点击展开更多留言"){
            allCommentDataList.add(commentBean);
        }
        else {
            mDataList.subList(mDataList.size() - 1,mDataList.size()).clear();
            mDataList.add(commentBean);
            mDataList.add("空白处");
            commentDataList_position = mDataList.size();
        }
        notifyDataSetChanged();
    }
}
