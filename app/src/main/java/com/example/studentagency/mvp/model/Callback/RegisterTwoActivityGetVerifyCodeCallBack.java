package com.example.studentagency.mvp.model.Callback;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/01/30
 * desc:
 */
public interface RegisterTwoActivityGetVerifyCodeCallBack {
    void getVerifyCodeSuccess(Integer result);
    void getVerifyCodeFail();
}
