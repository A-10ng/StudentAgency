package com.example.studentagency.mvp.view;

import com.example.studentagency.bean.Cat;

import java.util.List;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2019/10/14
 * desc:
 */
public interface LoginBaseView extends IView{
    void LoginSuccess(Cat cats);
    void LoginFailed();
}
