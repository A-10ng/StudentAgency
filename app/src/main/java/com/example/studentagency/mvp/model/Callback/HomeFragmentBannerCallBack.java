package com.example.studentagency.mvp.model.Callback;

import com.example.studentagency.bean.ResponseBean;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/01/01
 * desc:
 */
public interface HomeFragmentBannerCallBack {
    void onGetBannerDataSuccess(ResponseBean responseBean);
    void onGetBannerDataFail();
}
