package com.example.studentagency.mvp.presenter;


import android.util.Log;

import com.example.studentagency.bean.ResponseBean;
import com.example.studentagency.mvp.model.Callback.LoginActivityGetVerifyCodeCallBack;
import com.example.studentagency.mvp.model.Callback.LoginActivityLoginByPasswordCallBack;
import com.example.studentagency.mvp.model.Callback.LoginActivityLoginByVerifyCodeCallBack;
import com.example.studentagency.mvp.model.LoginActivityBaseModel;
import com.example.studentagency.mvp.view.LoginActivityBaseView;

import java.lang.ref.WeakReference;

import retrofit2.Response;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2019/10/14
 * desc:
 */
public class LoginActivityBasePresenter extends IPresenter {

    private static final String TAG = "LoginActivityBasePresen";

    public LoginActivityBasePresenter(LoginActivityBaseView view) {
        this.mIModel = new LoginActivityBaseModel();
        this.mViewRef = new WeakReference<>(view);
    }

    public void loginByPassword(String phoneNum,String password) {
        if (mIModel != null && mViewRef != null && mViewRef.get() != null) {
            Log.i(TAG, "BasePresenter--------------------------loginByPassword----------------------------");
            ((LoginActivityBaseModel) mIModel).loginByPassword(phoneNum, password, new LoginActivityLoginByPasswordCallBack() {
                @Override
                public void loginByPasswordSuccess(Response<ResponseBean> response) {
                    if (null != mViewRef.get()){
                        ((LoginActivityBaseView)mViewRef.get()).loginByPasswordSuccess(response);
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
                public void loginByVerifyCodeSuccess(Response<ResponseBean> response) {
                    if (null != mViewRef.get()){
                        ((LoginActivityBaseView)mViewRef.get()).loginByVerifyCodeSuccess(response);
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
                public void getVerifyCodeSuccess(ResponseBean responseBean) {
                    if (null != mViewRef.get()){
                        ((LoginActivityBaseView)mViewRef.get()).getVerifyCodeSuccess(responseBean);
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
