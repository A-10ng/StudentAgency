package com.example.studentagency.utils;

import androidx.appcompat.app.AppCompatActivity;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.NotificationClickEvent;
import cn.jpush.im.android.api.model.UserInfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.studentagency.R;
import com.example.studentagency.ui.activity.ChatActivity;
import com.example.studentagency.ui.activity.MainActivity;

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
