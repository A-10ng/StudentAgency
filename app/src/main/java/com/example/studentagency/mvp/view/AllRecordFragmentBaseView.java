package com.example.studentagency.mvp.view;

import com.example.studentagency.bean.CreditBean;

import java.util.List;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/18
 * desc:
 */
public interface AllRecordFragmentBaseView extends IView {
    void getCreditRecordSuccess(List<CreditBean> creditBeans);
    void getCreditRecordFail();
}
