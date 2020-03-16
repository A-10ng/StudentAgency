package com.example.studentagency.mvp.presenter;

import com.example.studentagency.bean.ResponseBean;
import com.example.studentagency.mvp.model.Callback.PersonFragmentGetPersonFragmentCallBack;
import com.example.studentagency.mvp.model.Callback.PersonFragmentUploadAvatarCallBack;
import com.example.studentagency.mvp.model.PersonFragmentBaseModel;
import com.example.studentagency.mvp.view.PersonFragmentBaseView;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/02
 * desc:
 */
public class PersonFragmentBasePresenter extends IPresenter {

    public PersonFragmentBasePresenter(PersonFragmentBaseView view) {
        this.mIModel = new PersonFragmentBaseModel();
        this.mViewRef = new WeakReference<>(view);
    }

    public void getPersonFragmentInfo(int userId){
        if (null != mViewRef && null != mViewRef.get() && null != mIModel){
            ((PersonFragmentBaseModel)mIModel).getPersonFragmentInfo(userId,new PersonFragmentGetPersonFragmentCallBack() {
                @Override
                public void getPersonFragmentSuccess(ResponseBean responseBean) {
                    if (null != mViewRef.get())
                    ((PersonFragmentBaseView)mViewRef.get()).getPersonFragmentInfoSuccess(responseBean);
                }

                @Override
                public void getPersonFragmentFail() {
                    if (null != mViewRef.get())
                        ((PersonFragmentBaseView)mViewRef.get()).getPersonFragmentInfoFail();
                }
            });
        }
    }

    public void uploadAvatar(int userId, File avatarFile){
        if (null != mViewRef && null != mViewRef.get() && null != mIModel){
            ((PersonFragmentBaseModel)mIModel).uploadAvatar(userId,avatarFile,new PersonFragmentUploadAvatarCallBack() {
                @Override
                public void uploadAvatarSuccess(ResponseBean responseBean) {
                    if (null != mViewRef.get())
                        ((PersonFragmentBaseView)mViewRef.get()).uploadAvatarSuccess(responseBean);
                }

                @Override
                public void uploadAvatarFail() {
                    if (null != mViewRef.get())
                        ((PersonFragmentBaseView)mViewRef.get()).uploadAvatarFail();
                }
            });
        }
    }
}
