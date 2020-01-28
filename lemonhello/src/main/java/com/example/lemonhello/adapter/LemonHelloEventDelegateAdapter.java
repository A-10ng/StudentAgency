package com.example.lemonhello.adapter;

import com.example.lemonhello.LemonHelloAction;
import com.example.lemonhello.LemonHelloInfo;
import com.example.lemonhello.LemonHelloView;
import com.example.lemonhello.interfaces.LemonHelloEventDelegate;

/**
 * LemonHello 事件代理适配器
 * Created by LiuRi on 2017/1/11.
 */

public abstract class LemonHelloEventDelegateAdapter implements LemonHelloEventDelegate {

    @Override
    public void onActionDispatch(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {

    }

    @Override
    public void onMaskTouch(LemonHelloView helloView, LemonHelloInfo helloInfo) {

    }
    
}
