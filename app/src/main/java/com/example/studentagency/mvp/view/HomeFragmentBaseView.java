package com.example.studentagency.mvp.view;

import com.example.studentagency.bean.NewsBean;
import com.example.studentagency.bean.IndentBean;

import java.util.List;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2019/12/31
 * desc:
 */
public interface HomeFragmentBaseView extends IView{
    void getBannerDataSuccess(List<NewsBean> newsBeanList);
    void getBannerDataFail();
    void getIndentsDataSuccess(List<IndentBean> indentBeanList);
    void getIndentsDataFail();
}
