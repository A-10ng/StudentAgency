package com.example.studentagency.mvp.view;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/23
 * desc:
 */
public interface ModifyPhoneNumActivityBaseView extends IView {
    void modifyPhoneNumSuccess(Integer result);
    void modifyPhoneNumFail();

    void getVerifyCodeSuccess(Integer result);
    void getVerifyCodeFail();
}
