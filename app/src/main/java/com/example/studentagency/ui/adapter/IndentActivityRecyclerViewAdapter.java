package com.example.studentagency.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.studentagency.R;
import com.example.studentagency.bean.CommentBean;
import com.example.studentagency.bean.CreditBean;
import com.example.studentagency.bean.PublishAndIndentBean;
import com.example.studentagency.ui.activity.OtherPersonActivity;
import com.example.studentagency.viewholder.IndentActivity.BlankViewHolder;
import com.example.studentagency.viewholder.IndentActivity.ClickUnfoldViewHolder;
import com.example.studentagency.viewholder.IndentActivity.CommentViewHolder;
import com.example.studentagency.viewholder.IndentActivity.NoCommentViewHolder;
import com.example.studentagency.viewholder.IndentActivity.PublishAIndentViewHolder;
import com.example.studentagency.viewholder.IndentActivity.RatingStarsViewHolder;

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
    private static final int ITEM_RATING_STARS = 5;
    private static final String TAG = "IndentActivityRecyclerV";
    private boolean hadRatingStars = false;
    private Context context;

    private List<Object> mDataList;
    private List<CommentBean> allCommentDataList = new ArrayList<>();
    private int commentDataList_position;//记录当前读到的留言位置

    public IndentActivityRecyclerViewAdapter(List<Object> mDataList, Context context) {
        this.mDataList = mDataList;
        this.context = context;
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
        } else if (viewType == ITEM_COMMENT_NONE) {
            view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.layout_indent_info_no_comment, parent, false);
            viewHolder = new NoCommentViewHolder(view);
        } else if (viewType == ITEM_COMMENT_CONTENT) {
            view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.layout_indent_info_comment, parent, false);
            viewHolder = new CommentViewHolder(view);
        } else if (viewType == ITEM_CLICKUNFOLD) {
            view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.layout_indent_info_clickunfold, parent, false);
            viewHolder = new ClickUnfoldViewHolder(view);
        } else if (viewType == ITEM_BLANK) {
            view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.layout_indent_info_blank, parent, false);
            viewHolder = new BlankViewHolder(view);
        } else if (viewType == ITEM_RATING_STARS) {
            view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.layout_indent_info_rating_stars, parent, false);
            viewHolder = new RatingStarsViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PublishAIndentViewHolder) {
            bindPublishAndIndentViewHolder(holder, position);
        } else if (holder instanceof CommentViewHolder) {
            bindCommentViewHolder(holder, position);
        } else if (holder instanceof ClickUnfoldViewHolder) {
            bindClickUnfoldViewHolder(holder, position);
        } else if (holder instanceof RatingStarsViewHolder) {
            bindRatingStarsViewHolder(holder, position);
        }
        /**
         * 无人留言的时候不用处理
         */
    }

    private void bindRatingStarsViewHolder(RecyclerView.ViewHolder holder, int position) {
        CreditBean bean = (CreditBean) mDataList.get(position);
        RatingStarsViewHolder ratingStarsViewHolder = (RatingStarsViewHolder) holder;

        float rating  = (bean.getIncreasement() + 3)/2f+1;
        Log.i(TAG, "bindRatingStarsViewHolder: rating>>>>>"+rating);
        ratingStarsViewHolder.ratingBar.setRating(rating);

        ratingStarsViewHolder.tv_happenTime.setText(bean.getDate());

        if (bean.getIncreasement() > 0){
            ratingStarsViewHolder.tv_increasement.setText("+"+bean.getIncreasement()+"分");
        }else {
            ratingStarsViewHolder.tv_increasement.setText(bean.getIncreasement()+"分");
        }

    }

    private void bindPublishAndIndentViewHolder(RecyclerView.ViewHolder holder, int position) {
        PublishAndIndentBean bean = (PublishAndIndentBean) mDataList.get(position);
        PublishAIndentViewHolder publishAIndentViewHolder = (PublishAIndentViewHolder) holder;

        //发布方头像
        RequestOptions requestOptions = RequestOptions.circleCropTransform();
        Glide.with(publishAIndentViewHolder.getView())
                .load(bean.getAvatar())
                .placeholder(R.drawable.placeholder_pic)
                .apply(requestOptions)
                .into(publishAIndentViewHolder.iv_avatar);
        publishAIndentViewHolder.iv_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OtherPersonActivity.class);
                intent.putExtra("currentUserId",bean.getUserId());
                context.startActivity(intent);
            }
        });

        //用户名
        publishAIndentViewHolder.tv_username.setText(bean.getUsername());

        //用户性别
        if (bean.getGender() == 0) {
            publishAIndentViewHolder.iv_gender.setImageResource(R.drawable.gender_female);
        } else {
            publishAIndentViewHolder.iv_gender.setImageResource(R.drawable.gender_male);
        }

        //审核状态
        if (bean.getVerifyState() == 3) {
            publishAIndentViewHolder.iv_verifyState.setImageResource(R.drawable.verified);
        } else {
            publishAIndentViewHolder.iv_verifyState.setImageResource(R.drawable.unverified);
        }

        //信誉积分
        publishAIndentViewHolder.tv_creditScore.setText("信誉积分: " + bean.getCreditScore());

        //订单发布时间
        publishAIndentViewHolder.tv_publishTime.setText("发布于: " + bean.getPublishTime());

        //订单号
        publishAIndentViewHolder.tv_indentId.setText("" + bean.getIndentId());

        //支付费用
        publishAIndentViewHolder.tv_price.setText(bean.getPrice());

        //代办类型
        if (bean.getType() == 0) {
            publishAIndentViewHolder.tv_type.setText("代购");
        } else if (bean.getType() == 1) {
            publishAIndentViewHolder.tv_type.setText("代拿快递");
        } else {
            publishAIndentViewHolder.tv_type.setText("其他代办");
        }

        //收货地址
        publishAIndentViewHolder.tv_address.setText(bean.getAddress());

        //送达时间
        publishAIndentViewHolder.tv_planTime.setText(bean.getPlanTime());

        //代办描述
        publishAIndentViewHolder.tv_description.setText(bean.getDescription());
    }

    private void bindCommentViewHolder(RecyclerView.ViewHolder holder, int position) {
        CommentBean bean = (CommentBean) mDataList.get(position);
        CommentViewHolder commentViewHolder = (CommentViewHolder) holder;

        RequestOptions requestOptions = RequestOptions.circleCropTransform();
        Glide.with(commentViewHolder.getView())
                .load(bean.getAvatar())
                .placeholder(R.drawable.placeholder_pic)
                .apply(requestOptions)
                .into(commentViewHolder.iv_comment_avatar);

        commentViewHolder.tv_comment_username.setText(bean.getUsername());

        commentViewHolder.tv_commentTime.setText(bean.getCommentTime());

        commentViewHolder.tv_comment_content.setText(bean.getContent());
    }

    private void bindClickUnfoldViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ClickUnfoldViewHolder) holder).layout_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hadRatingStars){
                    if (commentDataList_position + 5 >= allCommentDataList.size()) {
                        mDataList.subList(mDataList.size() - 3, mDataList.size() - 2).clear();
                        mDataList.addAll(mDataList.size() - 2,allCommentDataList.subList(commentDataList_position, allCommentDataList.size()));
                        commentDataList_position = allCommentDataList.size();
                    } else {
                        mDataList.subList(mDataList.size() - 3, mDataList.size() - 2).clear();
                        mDataList.addAll(mDataList.size() - 2,allCommentDataList.subList(commentDataList_position, commentDataList_position + 5));
                        mDataList.add(mDataList.size() - 2,"点击展开更多留言");
                        commentDataList_position += 5;
                    }
                }else {
                    if (commentDataList_position + 5 >= allCommentDataList.size()) {
                        mDataList.subList(mDataList.size() - 2, mDataList.size()).clear();
                        mDataList.addAll(allCommentDataList.subList(commentDataList_position, allCommentDataList.size()));
                        mDataList.add("空白处");
                        commentDataList_position = allCommentDataList.size();
                    } else {
                        mDataList.subList(mDataList.size() - 2, mDataList.size()).clear();
                        mDataList.addAll(allCommentDataList.subList(commentDataList_position, commentDataList_position + 5));
                        mDataList.add("点击展开更多留言");
                        mDataList.add("空白处");
                        commentDataList_position += 5;
                    }
                }

                notifyDataSetChanged();
            }
        });
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
        } else if (mDataList.get(position) == "空白处") {
            return ITEM_BLANK;
        } else if (mDataList.get(position) instanceof CreditBean) {
            return ITEM_RATING_STARS;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    public void setPublishOrIndentData(PublishAndIndentBean bean) {
        mDataList.set(0, bean);
        notifyDataSetChanged();
    }

    public void setCommentData(List<CommentBean> commentDataList,boolean hadRatingStars) {
        Log.i(TAG, "setCommentData: commentDataList.size()>>>>>" + commentDataList.size());
        this.hadRatingStars = hadRatingStars;

        if (hadRatingStars){
            mDataList.subList(1, mDataList.size() - 2).clear();
            commentDataList_position = 0;
            allCommentDataList.clear();
            allCommentDataList.addAll(commentDataList);

            if (commentDataList.isEmpty()) {
                mDataList.add("暂无留言");
            } else {
                if (commentDataList.size() > 5) {
                    commentDataList_position = 5;
                    mDataList.addAll(1,allCommentDataList.subList(0, commentDataList_position));
                    mDataList.add(6,"点击展开更多留言");
                } else {
                    mDataList.addAll(1,commentDataList);
                }
            }
        }else {
            mDataList.subList(1, mDataList.size()).clear();
            commentDataList_position = 0;
            allCommentDataList.clear();
            allCommentDataList.addAll(commentDataList);

            if (commentDataList.isEmpty()) {
                mDataList.add("暂无留言");
                mDataList.add("空白处");
            } else {
                if (commentDataList.size() > 5) {
                    commentDataList_position = 5;
                    mDataList.addAll(allCommentDataList.subList(0, commentDataList_position));
                    mDataList.add("点击展开更多留言");
                    mDataList.add("空白处");
                } else {
                    mDataList.addAll(commentDataList);
                    mDataList.add("空白处");
                }
            }
        }

        notifyDataSetChanged();
    }

    public void addComment(CommentBean commentBean) {
        if (mDataList.get(mDataList.size() - 2) == "点击展开更多留言") {
            allCommentDataList.add(commentBean);
        } else {
            mDataList.subList(mDataList.size() - 1, mDataList.size()).clear();
            mDataList.add(commentBean);
            mDataList.add("空白处");
            commentDataList_position = mDataList.size();
        }
        notifyDataSetChanged();
    }

    public void setRatingStarsData(CreditBean creditBean) {
        mDataList.set(mDataList.size() - 2,creditBean);
        notifyDataSetChanged();
    }

    public void refreshDataWithRatingStars(List<Object> originalDataList) {
        mDataList.clear();
        mDataList.addAll(originalDataList);
        notifyDataSetChanged();
    }
}
