package com.example.studentagency.mvp.view;

import com.example.studentagency.bean.UserBean;

import java.util.List;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2019/10/14
 * desc:
 */
public interface LoginActivityBaseView extends IView{
    //通过密码登录
    void loginByPasswordSuccess(Integer result);
    void loginByPasswordFail();

    //通过验证码登录
    void loginByVerifyCodeSuccess(Integer result);
    void loginByVerifyCodeFail();

    //获取验证码
    void getVerifyCodeSuccess(Integer result);
    void getVerifyCodeFail();
}
