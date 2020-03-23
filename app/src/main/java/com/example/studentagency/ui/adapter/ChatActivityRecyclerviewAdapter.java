package com.example.studentagency.ui.adapter;

import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.studentagency.R;
import com.example.studentagency.bean.ChatBean;
import com.example.studentagency.utils.TimeFormat;

import java.io.File;
import java.text.NumberFormat;
import java.util.List;

import cn.jpush.im.android.api.callback.DownloadCompletionCallback;
import cn.jpush.im.android.api.content.ImageContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.content.VideoContent;
import cn.jpush.im.android.api.content.VoiceContent;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/26
 * desc:
 */
public class ChatActivityRecyclerviewAdapter extends BaseMultiItemQuickAdapter<ChatBean, BaseViewHolder> {

    public int playVoiceIndex = -1;

    public ChatActivityRecyclerviewAdapter(List<ChatBean> dataList) {
        super(dataList);

        addItemType(ChatBean.TEXT_SEND, R.layout.item_chat_text_send);
        addItemType(ChatBean.TEXT_RECEIVE, R.layout.item_chat_text_receive);

        addItemType(ChatBean.IMG_SEND, R.layout.item_chat_img_send);
        addItemType(ChatBean.IMG_RECEIVE, R.layout.item_chat_img_receive);

        addItemType(ChatBean.VOICE_SEND, R.layout.item_chat_voice_send);
        addItemType(ChatBean.VOICE_RECEIVE, R.layout.item_chat_voice_receive);

        addItemType(ChatBean.VIDEO_SEND, R.layout.item_chat_img_send);
        addItemType(ChatBean.VIDEO_RECEIVE,R.layout.item_chat_img_receive);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChatBean item) {
        if (item.itemType == ChatBean.RETRACT) {
            return;
        }

        if (item.message == null) {
            return;
        }

        if (helper.getAdapterPosition() == 0) {
            TimeFormat timeFormat = new TimeFormat(mContext, item.message.getCreateTime());
            helper.setText(R.id.tv_time, timeFormat.getDetailTime());
            helper.getView(R.id.tv_time).setVisibility(View.VISIBLE);
        } else {
            ChatBean oldBean = getData().get(helper.getAdapterPosition() - 1);
            ChatBean nowBean = item;

            if (oldBean != null && nowBean != null) {
                if (oldBean.message != null && nowBean.message != null) {


                    long oldTime = oldBean.message.getCreateTime();
                    long nowTime = nowBean.message.getCreateTime();

                    // 如果两条消息之间的间隔超过五分钟则显示时间
                    if (nowTime - oldTime > 300000) {
                        TimeFormat timeFormat = new TimeFormat(mContext, nowBean.message.getCreateTime());
                        helper.setText(R.id.tv_time, timeFormat.getDetailTime());
                        helper.getView(R.id.tv_time).setVisibility(View.VISIBLE);
                    } else {
                        helper.getView(R.id.tv_time).setVisibility(View.GONE);
                    }

                } else {
                    helper.getView(R.id.tv_time).setVisibility(View.GONE);
                }


            } else {
                helper.getView(R.id.tv_time).setVisibility(View.GONE);
            }


        }


        switch (helper.getItemViewType()) {
            case ChatBean.TEXT_SEND:
            case ChatBean.TEXT_RECEIVE:

                helper.setText(R.id.tv, ((TextContent) item.message.getContent()).getText());

                break;
/*                helper.setText(R.id.tv,((TextContent)item.message.getContent()).getText());
                if(item.upload){
                    helper.getView(R.id.pb).setVisibility(View.INVISIBLE);
                }else{
                    helper.getView(R.id.pb).setVisibility(View.VISIBLE);
                }
                break;*/
            case ChatBean.IMG_SEND:
            case ChatBean.IMG_RECEIVE:

                RequestOptions options = new RequestOptions();
                options.centerInside()
                        .placeholder(R.color.white)
                        .diskCacheStrategy(DiskCacheStrategy.ALL);

                ImageContent imageContent = ((ImageContent) item.message.getContent());
                Glide.with(mContext).load(imageContent.getLocalThumbnailPath()).apply(options)
                        .into((ImageView) helper.getView(R.id.iv));

                break;

            case ChatBean.VOICE_RECEIVE:
            case ChatBean.VOICE_SEND:

                helper.setText(R.id.tv, ((VoiceContent) item.message.getContent()).getDuration() + "\"");

                ImageView iv_voice = helper.getView(R.id.iv_voice);
                AnimationDrawable mVoiceAnimation = (AnimationDrawable) iv_voice.getDrawable();

                mVoiceAnimation.start();

                if (playVoiceIndex == helper.getAdapterPosition()) {
                    if (mVoiceAnimation.isRunning()) {

                    } else {
                        mVoiceAnimation.start();
                    }
                } else {
                    if (mVoiceAnimation.isRunning()) {
                        mVoiceAnimation.stop();
                    }
                }

                break;

            case ChatBean.VIDEO_SEND:
            case ChatBean.VIDEO_RECEIVE:

                VideoContent videoContent = (VideoContent)item.message.getContent();

                final RequestOptions options2= new RequestOptions();
                options2.centerInside()
                        .placeholder(R.color.white)
                        .diskCacheStrategy(DiskCacheStrategy.ALL);


                videoContent.downloadThumbImage(item.message,new DownloadCompletionCallback(){

                    @Override
                    public void onComplete(int i, String s, File file) {
                        Glide.with(mContext).load(file.getPath()).apply(options2)
                                .into((ImageView) helper.getView(R.id.iv));

                    }
                });

                break;
        }


        helper.addOnClickListener(R.id.iv_head);

        RequestOptions requestOptions = RequestOptions.circleCropTransform();
        Glide.with(helper.getView(R.id.iv_head).getContext())
                .load(item.message.getFromUser().getSignature())
                .error(R.drawable.avatar_male)
                .apply(requestOptions)
                .into((ImageView) helper.getView(R.id.iv_head));

        if (item.upload) {
            helper.getView(R.id.pb).setVisibility(View.INVISIBLE);
        } else {
            helper.getView(R.id.pb).setVisibility(View.VISIBLE);
        }
    }

    public static String getFileSize(Number fileSize) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        //保留小数点后两位
        numberFormat.setMaximumFractionDigits(2);
        double size = fileSize.doubleValue();
        String sizeDisplay;
        if (size > 1048576.0) {
            double result = size / 1048576.0;
            sizeDisplay = numberFormat.format(result) + "MB";
        } else if (size > 1024) {
            double result = size / 1024;
            sizeDisplay = numberFormat.format(result) + "KB";
        } else {
            sizeDisplay = numberFormat.format(size) + "B";
        }
        return sizeDisplay;
    }
}
