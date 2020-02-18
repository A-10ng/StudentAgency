package com.example.studentagency.mvp.view;

import com.example.studentagency.bean.CreditBean;

import java.util.List;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/18
 * desc:
 */
public interface InputRecordFragmentBaseView extends IView {
    void getCreditInputRecordSuccess(List<CreditBean> creditBeans);
    void getCreditInputRecordFail();
}
