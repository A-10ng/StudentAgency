package com.example.studentagency.utils;

import android.content.Context;
import android.content.Intent;

import com.example.studentagency.ui.activity.ChatActivity;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.NotificationClickEvent;
import cn.jpush.im.android.api.model.UserInfo;

public class NotificationClickEventReceiver{

    private Context context;

    public NotificationClickEventReceiver(Context context) {
        this.context = context;

        JMessageClient.registerEventReceiver(this);
    }


    public void onEvent(NotificationClickEvent event){
        UserInfo userInfo = (UserInfo) event.getMessage().getTargetInfo();
        String username = userInfo.getUserName();
        String nickname = userInfo.getNickname();
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra("username",username);
        intent.putExtra("nickname",nickname);
        context.startActivity(intent);
    }
}
