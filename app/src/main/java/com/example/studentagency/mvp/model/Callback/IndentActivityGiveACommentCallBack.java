package com.example.studentagency.mvp.model.Callback;

import com.example.studentagency.bean.ResponseBean;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/01/09
 * desc:
 */
public interface IndentActivityGiveACommentCallBack {
    void onGiveACommentSuccess(ResponseBean responseBean);
    void onGiveACommentFail();
}
