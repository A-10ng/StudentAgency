package com.example.studentagency.mvp.view;

import com.example.studentagency.bean.ResponseBean;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/17
 * desc:
 */
public interface UnverifyFragmentBaseView extends IView {
//    void uploadVerifyPicSuccess(Integer result);
    void uploadVerifyPicSuccess(ResponseBean responseBean);
    void uploadVerifyPicFail();
}
