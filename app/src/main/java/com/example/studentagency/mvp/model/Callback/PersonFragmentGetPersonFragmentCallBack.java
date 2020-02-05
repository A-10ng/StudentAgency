package com.example.studentagency.mvp.model.Callback;

import com.example.studentagency.bean.UserBean;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/02
 * desc:
 */
public interface PersonFragmentGetPersonFragmentCallBack {
    void getPersonFragmentSuccess(UserBean userBean);
    void getPersonFragmentFail();
}
