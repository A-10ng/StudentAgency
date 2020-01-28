package com.example.studentagency.mvp.model.Callback;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/01/16
 * desc:
 */
public interface LoginActivityGetVerifyCodeCallBack {
    void getVerifyCodeSuccess(Integer result);
    void getVerifyCodeFail();
}
