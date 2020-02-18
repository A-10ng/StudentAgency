package com.example.studentagency.mvp.model.Callback;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/18
 * desc:
 */
public interface CreditSRActivityGetCreditScoreCallBack {
    void getCreditScoreSuccess(Integer score);
    void getCreditScoreFail();
}
