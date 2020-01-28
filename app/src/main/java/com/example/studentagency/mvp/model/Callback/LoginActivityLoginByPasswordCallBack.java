package com.example.studentagency.mvp.model.Callback;


import com.example.studentagency.bean.UserBean;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2019/10/14
 * desc:
 */
public interface LoginActivityLoginByPasswordCallBack {
    void loginByPasswordSuccess(Integer result);
    void loginByPasswordFail();
}
