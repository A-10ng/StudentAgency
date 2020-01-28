package com.example.lemonhello.interfaces;

import com.example.lemonhello.LemonHelloAction;
import com.example.lemonhello.LemonHelloInfo;
import com.example.lemonhello.LemonHelloView;

/**
 * LemonHello - 事件回调代理
 * Created by LiuRi on 2017/1/11.
 */

public interface LemonHelloActionDelegate {

    void onClick(
            LemonHelloView helloView,
            LemonHelloInfo helloInfo,
            LemonHelloAction helloAction
    );

}
