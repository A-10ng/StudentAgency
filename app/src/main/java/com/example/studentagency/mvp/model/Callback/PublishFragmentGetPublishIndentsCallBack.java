package com.example.studentagency.mvp.model.Callback;

import com.example.studentagency.bean.IndentBean;

import java.util.List;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/19
 * desc:
 */
public interface PublishFragmentGetPublishIndentsCallBack {
    void getPublishIndentsSuccess(List<IndentBean> indentBeanList);
    void getPublishIndentsFail();
}
