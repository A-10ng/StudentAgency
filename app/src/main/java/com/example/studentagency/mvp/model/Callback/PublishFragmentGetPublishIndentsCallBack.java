package com.example.studentagency.mvp.model.Callback;

import com.example.studentagency.bean.ResponseBean;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/19
 * desc:
 */
public interface PublishFragmentGetPublishIndentsCallBack {
    void getPublishIndentsSuccess(ResponseBean responseBean);
    void getPublishIndentsFail();
}
