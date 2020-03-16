package com.example.studentagency.mvp.view;

import com.example.studentagency.bean.ResponseBean;

import retrofit2.Response;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2019/10/14
 * desc:
 */
public interface LoginActivityBaseView extends IView{
    //通过密码登录
    void loginByPasswordSuccess(Response<ResponseBean> response);
    void loginByPasswordFail();

    //通过验证码登录
    void loginByVerifyCodeSuccess(Response<ResponseBean> response);
    void loginByVerifyCodeFail();

    //获取验证码
//    void getVerifyCodeSuccess(Integer result);
    void getVerifyCodeSuccess(ResponseBean responseBean);
    void getVerifyCodeFail();
}
