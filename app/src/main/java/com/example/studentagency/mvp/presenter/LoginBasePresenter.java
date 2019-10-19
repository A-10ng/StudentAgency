package com.example.studentagency.mvp.presenter;

import com.example.studentagency.bean.Cat;
import com.example.studentagency.mvp.model.Callback.LoginCallBack;
import com.example.studentagency.mvp.model.LoginBaseModel;
import com.example.studentagency.mvp.view.LoginBaseView;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2019/10/14
 * desc:
 */
public class LoginBasePresenter extends IPresenter {

    public LoginBasePresenter(LoginBaseView view) {
        this.mIModel = new LoginBaseModel();
        this.mViewRef = new WeakReference<>(view);
    }

    public void login(String studentId,String password) {
        if (mIModel != null && mViewRef != null && mViewRef.get() != null) {
            ((LoginBaseModel) mIModel).login(studentId,password,new LoginCallBack() {
                @Override
                public void OnSuccess(Cat cat) {
                    if (mViewRef.get() != null) {
                        ((LoginBaseView) mViewRef.get()).LoginSuccess(cat);
                    }
                }

                @Override
                public void OnFailed() {
                    if (mViewRef.get() != null) {
                        ((LoginBaseView) mViewRef.get()).LoginFailed();
                    }
                }
            });
        }
    }
}
