package com.example.studentagency.mvp.presenter;

import com.example.studentagency.mvp.model.Callback.ModifyPwdActivityChangePwdCallBack;
import com.example.studentagency.mvp.model.ModifyPwdActivityBaseModel;
import com.example.studentagency.mvp.view.ModifyPwdActivityBaseView;

import java.lang.ref.WeakReference;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/08
 * desc:
 */
public class ModifyPwdActivityBasePresenter extends IPresenter {

    public ModifyPwdActivityBasePresenter(ModifyPwdActivityBaseView view) {
        this.mIModel = new ModifyPwdActivityBaseModel();
        this.mViewRef = new WeakReference<>(view);
    }

    public void changePwd(int userId,String newPwd){
        if (null != mViewRef && null != mViewRef.get() && null != mIModel){
            ((ModifyPwdActivityBaseModel)mIModel).changePwd(userId, newPwd, new ModifyPwdActivityChangePwdCallBack() {
                @Override
                public void changePwdSuccess(Integer result) {
                    if (null != mViewRef.get()){
                        ((ModifyPwdActivityBaseView)mViewRef.get()).changePwdSuccess(result);
                    }
                }

                @Override
                public void changePwdFail() {
                    if (null != mViewRef.get()){
                        ((ModifyPwdActivityBaseView)mViewRef.get()).changePwdFail();
                    }
                }
            });
        }
    }
}
