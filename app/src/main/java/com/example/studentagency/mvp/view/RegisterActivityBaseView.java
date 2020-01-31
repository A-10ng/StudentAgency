package com.example.studentagency.mvp.view;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/01/30
 * desc:
 */
public interface RegisterActivityBaseView extends IView {
    //获取验证码
    void getVerifyCodeSuccess(Integer result);
    void getVerifyCodeFail();

    //注册
    void registerSuccess(Integer result);
    void registerFail();
}
