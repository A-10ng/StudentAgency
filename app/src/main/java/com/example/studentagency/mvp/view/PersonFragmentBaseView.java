package com.example.studentagency.mvp.view;

import com.example.studentagency.bean.UserBean;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/01
 * desc:
 */
public interface PersonFragmentBaseView extends IView{
    void getPersonFragmentInfoSuccess(UserBean userBean);
    void getPersonFragmentInfoFail();

    void uploadAvatarSuccess(Integer result);
    void uploadAvatarFail();
}
