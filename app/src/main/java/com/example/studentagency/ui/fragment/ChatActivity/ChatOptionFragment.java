package com.example.studentagency.ui.fragment.ChatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.studentagency.R;
import com.example.studentagency.bean.ChatBean;
import com.example.studentagency.bean.ChatOptionBean;
import com.example.studentagency.ui.activity.ChatActivity;
import com.example.studentagency.ui.adapter.ChatOptionAdapter;
import com.example.studentagency.utils.VariableName;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.ImageContent;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.api.BasicCallback;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/27
 * desc:
 */
public class ChatOptionFragment extends Fragment {

    public String username;
    public String nickname;
    private List<ChatOptionBean> chatOptionBeanList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ChatOptionAdapter adapter;
    private View rootView;
    private String messageType;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case VariableName.REQUEST_CODE_ONE:
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                if (selectList != null && selectList.size() == 1 && selectList.get(0) != null) {
                    //所有图片都在这里拿到

                    messageType = VariableName.IMG;
                    if (PictureMimeType.isPictureType(selectList.get(0).getPictureType()) == PictureConfig.TYPE_IMAGE) {
                        //这是图片
                        messageType = VariableName.IMG;

                        ImageContent.createImageContentAsync(new File(selectList.get(0).getPath()), new ImageContent.CreateImageContentCallback() {
                            @Override
                            public void gotResult(int responseCode, String s, ImageContent imageContent) {
                                if (responseCode == 0) {
                                    imageContent.setStringExtra(VariableName.TYPE, messageType);
                                    Message msg = ((ChatActivity) getActivity()).conversation.createSendMessage(imageContent);
                                    sendMessage(msg, ChatBean.IMG_SEND);
                                }
                            }
                        });
                    }else if(PictureMimeType.isPictureType(selectList.get(0).getPictureType()) == PictureConfig.TYPE_VIDEO ){
                        // 这是视频
                        messageType = VariableName.VIDEO;

                        // sendFile(ChatBean.VIDEO_SEND,selectList[0].path)

                        int index = selectList.get(0).getPath().lastIndexOf('/');
                        String fileName = "";
                        if (index > 0) {
                            fileName = selectList.get(0).getPath().substring(index + 1);
                        }

                        MediaMetadataRetriever media = new MediaMetadataRetriever();
                        media.setDataSource(selectList.get(0).getPath());
                        Bitmap bitmap = media.getFrameAtTime();

                        Message message = null;

                        try {
                            message = JMessageClient.createSingleVideoMessage(username, VariableName.JIGUANG_APP_KEY,
                                    bitmap, "jpeg",
                                    new File(selectList.get(0).getPath()), fileName, 1000);
                            sendMessage(message,ChatBean.VIDEO_SEND);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_chat_option, container, false);

        initData();

        initRecyclerview();

        return rootView;
    }

    private void initData() {
        chatOptionBeanList.clear();
        chatOptionBeanList.add(new ChatOptionBean(R.drawable.chat_option_photo));
    }

    private void initRecyclerview() {
        recyclerView = rootView.findViewById(R.id.recycle);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));

        adapter = new ChatOptionAdapter(R.layout.item_chat_option, chatOptionBeanList);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position) {
                    case 0:
                        //选择图片
                        PictureSelector.create(ChatOptionFragment.this)
                                .openGallery(PictureMimeType.ofAll())
                                .maxSelectNum(1)
                                .minSelectNum(1)
                                .selectionMode(PictureConfig.SINGLE)
                                .previewImage(true)
                                .compress(true)
                                .forResult(VariableName.REQUEST_CODE_ONE);
                }
            }
        });

        recyclerView.setAdapter(adapter);
    }

    private void sendMessage(Message msg, int type) {
        ChatBean bean = new ChatBean(msg, type);
        bean.upload = false;
        ((ChatActivity) getActivity()).addMessageRefresh(bean);

        int now = ((ChatActivity) getActivity()).chatBeanList.size();

        JMessageClient.sendMessage(msg);
        msg.setOnSendCompleteCallback(new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if (i != 0) {
                    return;
                }
                ((ChatActivity) getActivity()).chatBeanList.get(now - 1).upload = true;
                ((ChatActivity) getActivity()).adapter.notifyDataSetChanged();
            }
        });
    }
}
