package com.example.studentagency.mvp.model.Callback;


import com.example.studentagency.bean.ResponseBean;

import retrofit2.Response;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2019/10/14
 * desc:
 */
public interface LoginActivityLoginByVerifyCodeCallBack {
    void loginByVerifyCodeSuccess(Response<ResponseBean> response);
    void loginByVerifyCodeFail();
}
