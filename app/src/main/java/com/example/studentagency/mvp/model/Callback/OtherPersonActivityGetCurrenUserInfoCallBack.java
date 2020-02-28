package com.example.studentagency.mvp.model.Callback;

import com.example.studentagency.bean.OtherPersonBean;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/25
 * desc:
 */
public interface OtherPersonActivityGetCurrenUserInfoCallBack {
    void getCurrentUserInfoSuccess(OtherPersonBean otherPersonBean);
    void getCurrentUserInfoFail();
}
