package com.example.studentagency.mvp.view;

import com.example.studentagency.bean.ResponseBean;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/10
 * desc:
 */
public interface PersonalInfoActivityBaseView extends IView {
//    void getPersonalInfoSuccess(UserBean userBean);
    void getPersonalInfoSuccess(ResponseBean responseBean);
    void getPersonalInfoFail();

//    void changeUserInfoSuccess(Integer result);
    void changeUserInfoSuccess(ResponseBean responseBean);
    void changeUserInfoFail();
}
