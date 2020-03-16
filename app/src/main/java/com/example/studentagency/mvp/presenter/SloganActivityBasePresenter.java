package com.example.studentagency.mvp.presenter;

import com.example.studentagency.bean.ResponseBean;
import com.example.studentagency.mvp.model.Callback.SloganActivityTokenVerifyCallBack;
import com.example.studentagency.mvp.model.SloganActivityBaseModel;
import com.example.studentagency.mvp.view.SloganActivityBaseView;

import java.lang.ref.WeakReference;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/03/05
 * desc:
 */
public class SloganActivityBasePresenter extends IPresenter {

    public SloganActivityBasePresenter(SloganActivityBaseView view) {
        this.mIModel = new SloganActivityBaseModel();
        this.mViewRef = new WeakReference<>(view);
    }

    public void tokenVerify(String token){
        if (null != mViewRef && null != mViewRef.get() && null != mIModel){
            ((SloganActivityBaseModel)mIModel).tokenVerify(token,new SloganActivityTokenVerifyCallBack() {
                @Override
                public void tokenVerifySuccess(ResponseBean responseBean) {
                    if (null != mViewRef.get()){
                        ((SloganActivityBaseView)mViewRef.get()).tokenVerifySuccess(responseBean);
                    }
                }

                @Override
                public void tokenVerifyFail() {
                    if (null != mViewRef.get()){
                        ((SloganActivityBaseView)mViewRef.get()).tokenVerifyFail();
                    }
                }
            });
        }
    }
}
