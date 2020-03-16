package com.example.studentagency.mvp.model.Callback;

import com.example.studentagency.bean.ResponseBean;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/03/16
 * desc:
 */
public interface RechargeActivityRechargeCallBack {
    void rechargeSuccess(ResponseBean responseBean);
    void rechargeFail();
}
