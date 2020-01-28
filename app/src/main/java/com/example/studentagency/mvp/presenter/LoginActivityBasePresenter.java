package com.example.studentagency.mvp.presenter;


import com.example.studentagency.bean.UserBean;
import com.example.studentagency.mvp.model.Callback.LoginActivityGetVerifyCodeCallBack;
import com.example.studentagency.mvp.model.Callback.LoginActivityLoginByPasswordCallBack;
import com.example.studentagency.mvp.model.Callback.LoginActivityLoginByVerifyCodeCallBack;
import com.example.studentagency.mvp.model.LoginActivityBaseModel;
import com.example.studentagency.mvp.view.LoginActivityBaseView;

import java.lang.ref.WeakReference;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2019/10/14
 * desc:
 */
public class LoginActivityBasePresenter extends IPresenter {

    public LoginActivityBasePresenter(LoginActivityBaseView view) {
        this.mIModel = new LoginActivityBaseModel();
        this.mViewRef = new WeakReference<>(view);
    }

    public void loginByPassword(String phoneNum,String password) {
        if (mIModel != null && mViewRef != null && mViewRef.get() != null) {
            ((LoginActivityBaseModel) mIModel).loginByPassword(phoneNum, password, new LoginActivityLoginByPasswordCallBack() {
                @Override
                public void loginByPasswordSuccess(Integer result) {
                    if (null != mViewRef.get()){
                        ((LoginActivityBaseView)mViewRef.get()).loginByPasswordSuccess(result);
                    }
                }

                @Override
                public void loginByPasswordFail() {
                    if (null != mViewRef.get()){
                        ((LoginActivityBaseView)mViewRef.get()).loginByPasswordFail();
                    }
                }
            });
        }
    }

    public void loginByVerifyCode(String phoneNum,String verifyCode) {
        if (mIModel != null && mViewRef != null && mViewRef.get() != null) {
            ((LoginActivityBaseModel) mIModel).loginByVerifyCode(phoneNum, verifyCode, new LoginActivityLoginByVerifyCodeCallBack() {
                @Override
                public void loginByVerifyCodeSuccess(Integer result) {
                    if (null != mViewRef.get()){
                        ((LoginActivityBaseView)mViewRef.get()).loginByVerifyCodeSuccess(result);
                    }
                }

                @Override
                public void loginByVerifyCodeFail() {
                    if (null != mViewRef.get()){
                        ((LoginActivityBaseView)mViewRef.get()).loginByVerifyCodeFail();
                    }
                }
            });
        }
    }

    public void getVerifyCode(String phoneNum){
        if (null != mIModel && null != mViewRef && null != mViewRef.get()){
            ((LoginActivityBaseModel)mIModel).getVerifyCode(phoneNum, new LoginActivityGetVerifyCodeCallBack() {
                @Override
                public void getVerifyCodeSuccess(Integer result) {
                    if (null != mViewRef.get()){
                        ((LoginActivityBaseView)mViewRef.get()).getVerifyCodeSuccess(result);
                    }
                }

                @Override
                public void getVerifyCodeFail() {
                    if (null != mViewRef.get()){
                        ((LoginActivityBaseView)mViewRef.get()).getVerifyCodeFail();
                    }
                }
            });
        }
    }
}
