package com.example.studentagency.mvp.presenter;

import com.example.studentagency.bean.OtherPersonBean;
import com.example.studentagency.mvp.model.Callback.OtherPersonActivityGetCurrenUserInfoCallBack;
import com.example.studentagency.mvp.model.OtherPersonActivityBaseModel;
import com.example.studentagency.mvp.view.OtherPersonActivityBaseView;

import java.lang.ref.WeakReference;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/25
 * desc:
 */
public class OtherPersonActivityBasePresenter extends IPresenter {

    public OtherPersonActivityBasePresenter(OtherPersonActivityBaseView view) {
        this.mIModel = new OtherPersonActivityBaseModel();
        this.mViewRef = new WeakReference<>(view);
    }

    public void getCurrentUserInfo(String phoneNum) {
        if (null != mViewRef && null != mViewRef.get() && null != mIModel){
            ((OtherPersonActivityBaseModel)mIModel).getCurrentUserInfo(phoneNum,new OtherPersonActivityGetCurrenUserInfoCallBack() {
                @Override
                public void getCurrentUserInfoSuccess(OtherPersonBean otherPersonBean) {
                    if (null != mViewRef.get()){
                        ((OtherPersonActivityBaseView)mViewRef.get()).getCurrentUserInfoSuccess(otherPersonBean);
                    }
                }

                @Override
                public void getCurrentUserInfoFail() {
                    if (null != mViewRef.get()){
                        ((OtherPersonActivityBaseView)mViewRef.get()).getCurrentUserInfoFail();
                    }
                }
            });
        }
    }
}
