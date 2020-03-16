package com.example.studentagency.mvp.view;

import com.example.studentagency.bean.ResponseBean;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/18
 * desc:
 */
public interface CreditScoreRecordActivityBaseView extends IView {
//    void getCreditScoreSuccess(Integer score);
    void getCreditScoreSuccess(ResponseBean responseBean);
    void getCreditScoreFail();
}
