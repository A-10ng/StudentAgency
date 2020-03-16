package com.example.studentagency.mvp.view;

import com.example.studentagency.bean.ResponseBean;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/01/10
 * desc:
 */
public interface ClassifyActivityBaseView extends IView {
//    void getIndentByTypeSuccess(List<IndentBean> indentBeanList);
    void getIndentByTypeSuccess(ResponseBean responseBean);
    void getIndentByTypeFail();
}
