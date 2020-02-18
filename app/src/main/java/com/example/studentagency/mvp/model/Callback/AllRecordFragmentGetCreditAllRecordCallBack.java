package com.example.studentagency.mvp.model.Callback;

import com.example.studentagency.bean.CreditBean;

import java.util.List;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/18
 * desc:
 */
public interface AllRecordFragmentGetCreditAllRecordCallBack {
    void getCreditAllRecordSuccess(List<CreditBean> creditBeans);
    void getCreditAllRecordFail();
}
