package com.example.studentagency.mvp.view;

import com.example.studentagency.bean.ResponseBean;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/01/15
 * desc:
 */
public interface PublishActivityBaseView extends IView {
//    void publishIndentSuccess(Integer result);
    void publishIndentSuccess(ResponseBean responseBean);
    void publishIndentFail();
}
