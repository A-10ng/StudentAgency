package com.example.studentagency.mvp.presenter;

import com.example.studentagency.mvp.model.Callback.StudentVerifyActivityGetVerifyStateCallBack;
import com.example.studentagency.mvp.model.StudentVerifyActivityBaseModel;
import com.example.studentagency.mvp.view.StudentVerifyActivityBaseView;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/17
 * desc:
 */
public class StudentVerifyActivityBasePresenter extends IPresenter {

    public StudentVerifyActivityBasePresenter(StudentVerifyActivityBaseView view) {
        this.mIModel = new StudentVerifyActivityBaseModel();
        this.mViewRef = new WeakReference<>(view);
    }

    public void getVerifyState(int userId) {
        if (null != mViewRef && null != mIModel && null != mViewRef.get()){
            ((StudentVerifyActivityBaseModel)mIModel).getVerifyState(userId, new StudentVerifyActivityGetVerifyStateCallBack() {
                @Override
                public void getVerifyStateSuccess(Integer result) {
                    if (null != mViewRef.get()){
                        ((StudentVerifyActivityBaseView)mViewRef.get()).getVerifyStateSuccess(result);
                    }
                }

                @Override
                public void getVerifyStateFail() {
                    if (null != mViewRef.get()){
                        ((StudentVerifyActivityBaseView)mViewRef.get()).getVerifyStateFail();
                    }
                }
            });
        }
    }
}
