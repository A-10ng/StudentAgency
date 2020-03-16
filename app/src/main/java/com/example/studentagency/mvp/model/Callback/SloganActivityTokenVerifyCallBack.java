package com.example.studentagency.mvp.model.Callback;

import com.example.studentagency.bean.ResponseBean;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/03/05
 * desc:
 */
public interface SloganActivityTokenVerifyCallBack {
    void tokenVerifySuccess(ResponseBean responseBean);
    void tokenVerifyFail();
}
