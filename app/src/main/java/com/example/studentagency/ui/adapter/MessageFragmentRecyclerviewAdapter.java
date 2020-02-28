package com.example.studentagency.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.studentagency.R;
import com.example.studentagency.viewholder.MessageFragment.ErrorViewHolder;
import com.example.studentagency.viewholder.MessageFragment.ItemViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cn.jpush.im.android.api.content.CustomContent;
import cn.jpush.im.android.api.content.PromptContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/26
 * desc:
 */
public class MessageFragmentRecyclerviewAdapter extends RecyclerView.Adapter {

    private static final String TAG = "MessageFragmentRecycler";
    private static final int ITEM_ERROR = 1;
    private static final int ITEM_MESSAGE = 2;
    private List<Object> dataList;
    private OnClickItemListener onClickItemListener;

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    public MessageFragmentRecyclerviewAdapter(List<Object> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        View view;
        if (ITEM_ERROR == viewType) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_message_fragment_no_message, parent, false);
            holder = new ErrorViewHolder(view);
        } else if (ITEM_MESSAGE == viewType) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_message_fragment_item, parent, false);
            holder = new ItemViewHolder(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            Conversation conversation = (Conversation) dataList.get(position);
            UserInfo userInfo = (UserInfo) conversation.getTargetInfo();
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

            Log.i(TAG, "onBindViewHolder: position>>>>>"+position+"  userInfo.getAvatar()>>>>>"+userInfo.getAvatar());
            RequestOptions requestOptions = RequestOptions.circleCropTransform();
            Glide.with(itemViewHolder.iv_avatar.getContext())
                    .load(userInfo.getSignature())
                    .error(R.drawable.avatar_male)
                    .apply(requestOptions)
                    .into(itemViewHolder.iv_avatar);

            itemViewHolder.tv_nickname.setText(userInfo.getNickname());

            Message lastMessage = conversation.getLatestMessage();
            if (null != lastMessage) {
                String contentStr;

                switch (lastMessage.getContentType()) {
                    case image:
                        contentStr = "[图片]";
                        break;
                    case voice:
                        contentStr = "[语音]";
                        break;
                    case location:
                        contentStr = "[位置]";
                        break;
                    case file:
                        contentStr = "[文件]";
                        break;
                    case video:
                        contentStr = "[视频]";
                        break;
                    case prompt:
                        contentStr = ((PromptContent) lastMessage.getContent()).getPromptText();
                        break;
                    default:
                        contentStr = ((TextContent) lastMessage.getContent()).getText();
                        break;
                }

                itemViewHolder.tv_content.setText(contentStr);
            }

            if (conversation.getExtra().equals("NEW_MESSAGE")){
                itemViewHolder.iv_red_dot.setVisibility(View.VISIBLE);
            }else {
                itemViewHolder.iv_red_dot.setVisibility(View.GONE);
            }

            itemViewHolder.layout_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != onClickItemListener){
                        onClickItemListener.clickItem(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (dataList.get(position) instanceof String) {
            return ITEM_ERROR;
        } else if (dataList.get(position) instanceof Conversation) {
            return ITEM_MESSAGE;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        if (dataList == null || dataList.isEmpty()) {
            return 0;
        } else {
            return dataList.size();
        }
    }

    public interface OnClickItemListener{
        void clickItem(int position);
    }
}
