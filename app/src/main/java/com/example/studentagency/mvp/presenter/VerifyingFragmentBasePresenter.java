package com.example.studentagency.mvp.presenter;

import com.example.studentagency.mvp.model.Callback.VerifyingFragmentGetVerifyPicCallBack;
import com.example.studentagency.mvp.model.VerifyingFragmentBaseModel;
import com.example.studentagency.mvp.view.VerifyingFragmentBaseView;

import java.lang.ref.WeakReference;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/18
 * desc:
 */
public class VerifyingFragmentBasePresenter extends IPresenter {

    public VerifyingFragmentBasePresenter(VerifyingFragmentBaseView view) {
        this.mIModel = new VerifyingFragmentBaseModel();
        this.mViewRef = new WeakReference<>(view);
    }

    public void getVerifyPic(){
        if (null != mViewRef && null != mViewRef.get() && null != mIModel){
            ((VerifyingFragmentBaseModel)mIModel).getVerifyPic(new VerifyingFragmentGetVerifyPicCallBack() {
                @Override
                public void getVerifyPicSuccess(String picPath) {
                    if (null != mViewRef.get()){
                        ((VerifyingFragmentBaseView)mViewRef.get()).getVerifyPicSuccess(picPath);
                    }
                }

                @Override
                public void getVerifyPicFail() {
                    if (null != mViewRef.get()){
                        ((VerifyingFragmentBaseView)mViewRef.get()).getVerifyPicFail();
                    }
                }
            });
        }
    }
}
