package com.example.studentagency.mvp.view;

import com.example.studentagency.bean.ResponseBean;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2019/12/31
 * desc:
 */
public interface HomeFragmentBaseView extends IView{
//    void getBannerDataSuccess(List<NewsBean> newsBeanList);
    void getBannerDataSuccess(ResponseBean responseBean);
    void getBannerDataFail();

    void getIndentsDataSuccess(ResponseBean responseBean);
    void getIndentsDataFail();
}
