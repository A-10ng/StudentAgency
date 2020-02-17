package com.example.studentagency.mvp.presenter;

import com.example.studentagency.bean.UserBean;
import com.example.studentagency.mvp.model.Callback.PersonalInfoActivityChangePersonalInfoCallBack;
import com.example.studentagency.mvp.model.Callback.PersonalInfoActivityGetPersonalInfoCallBack;
import com.example.studentagency.mvp.model.PersonFragmentBaseModel;
import com.example.studentagency.mvp.model.PersonalInfoActivityBaseModel;
import com.example.studentagency.mvp.view.PersonFragmentBaseView;
import com.example.studentagency.mvp.view.PersonalInfoActivityBaseView;

import java.lang.ref.WeakReference;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/10
 * desc:
 */
public class PersonalInfoActivityBasePresenter extends IPresenter {

    public PersonalInfoActivityBasePresenter(PersonalInfoActivityBaseView view) {
        this.mViewRef = new WeakReference<>(view);
        this.mIModel = new PersonalInfoActivityBaseModel();
    }

    public void getPersonalInfo(int userId){
        if (null != mIModel && null != mViewRef && null != mViewRef.get()){
            ((PersonalInfoActivityBaseModel)mIModel).getPersonalInfo(userId, new PersonalInfoActivityGetPersonalInfoCallBack() {
                @Override
                public void getPersonalInfoSuccess(UserBean userBean) {
                    if (null != mViewRef.get()){
                        ((PersonalInfoActivityBaseView)mViewRef.get()).getPersonalInfoSuccess(userBean);
                    }
                }

                @Override
                public void getPersonalInfoFail() {
                    if (null != mViewRef.get()){
                        ((PersonalInfoActivityBaseView)mViewRef.get()).getPersonalInfoFail();
                    }
                }
            });
        }
    }

    public void changePersonalInfo(UserBean userBean){
        if (null != mIModel && null != mViewRef && null != mViewRef.get()){
            ((PersonalInfoActivityBaseModel)mIModel).changePersonalInfo(userBean, new PersonalInfoActivityChangePersonalInfoCallBack() {
                @Override
                public void changePersonalInfoSuccess(Integer result) {
                    if (null != mViewRef.get()){
                        ((PersonalInfoActivityBaseView)mViewRef.get()).changeUserInfoSuccess(result);
                    }
                }

                @Override
                public void changePersonalInfoFail() {
                    if (null != mViewRef.get()){
                        ((PersonalInfoActivityBaseView)mViewRef.get()).changeUserInfoFail();
                    }
                }
            });
        }
    }
}
