package com.example.studentagency.mvp.presenter;

import com.example.studentagency.mvp.model.Callback.UnverifyFragmentUploadVerifyPicCallBack;
import com.example.studentagency.mvp.model.UnverifyFragmentBaseModel;
import com.example.studentagency.mvp.view.UnverifyFragmentBaseView;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/17
 * desc:
 */
public class UnverifyFragmentBasePresenter extends IPresenter {

    public UnverifyFragmentBasePresenter(UnverifyFragmentBaseView view) {
        this.mIModel = new UnverifyFragmentBaseModel();
        this.mViewRef = new WeakReference<>(view);
    }

    public void uploadVerifyPic(File verifyPicFile) {
        if (null != mViewRef && null != mIModel && null != mViewRef.get()){
            ((UnverifyFragmentBaseModel)mIModel).uploadVerifyPic(verifyPicFile, new UnverifyFragmentUploadVerifyPicCallBack() {
                @Override
                public void uploadVerifyPicSuccess(Integer result) {
                    if (null != mViewRef.get()){
                        ((UnverifyFragmentBaseView)mViewRef.get()).uploadVerifyPicSuccess(result);
                    }
                }

                @Override
                public void uploadVerifyPicFail() {
                    if (null != mViewRef.get()){
                        ((UnverifyFragmentBaseView)mViewRef.get()).uploadVerifyPicFail();
                    }
                }
            });
        }
    }
}
