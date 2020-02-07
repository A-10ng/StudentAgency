package com.example.studentagency.mvp.presenter;

import com.example.studentagency.bean.UserBean;
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
                public void getPersonFragmentSuccess(UserBean userBean) {
                    if (null != mViewRef.get())
                    ((PersonFragmentBaseView)mViewRef.get()).getPersonFragmentInfoSuccess(userBean);
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
                public void uploadAvatarSuccess(Integer result) {
                    if (null != mViewRef.get())
                        ((PersonFragmentBaseView)mViewRef.get()).uploadAvatarSuccess(result);
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
