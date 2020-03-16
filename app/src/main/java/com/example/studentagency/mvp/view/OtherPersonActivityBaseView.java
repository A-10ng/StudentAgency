package com.example.studentagency.mvp.view;

import com.example.studentagency.bean.ResponseBean;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/25
 * desc:
 */
public interface OtherPersonActivityBaseView extends IView {
//    void getCurrentUserInfoSuccess(OtherPersonBean otherPersonBean);
    void getCurrentUserInfoSuccess(ResponseBean responseBean);
    void getCurrentUserInfoFail();
}
