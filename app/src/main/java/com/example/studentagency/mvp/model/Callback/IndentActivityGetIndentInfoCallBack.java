package com.example.studentagency.mvp.model.Callback;

import com.example.studentagency.bean.IndentBean;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/01/06
 * desc:
 */
public interface IndentActivityGetIndentInfoCallBack {
    void onGetIndentInfoSuccess(IndentBean indentBean);
    void onGetIndentInfoFail();
}
