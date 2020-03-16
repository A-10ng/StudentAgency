package com.example.studentagency.mvp.presenter;

import com.example.studentagency.bean.ResponseBean;
import com.example.studentagency.mvp.model.Callback.RechargeActivityRechargeCallBack;
import com.example.studentagency.mvp.model.RechargeActivityBaseModel;
import com.example.studentagency.mvp.view.RechargeActivityBaseView;

import java.lang.ref.WeakReference;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/03/16
 * desc:
 */
public class RechargeActivityBasePresenter extends IPresenter {

    public RechargeActivityBasePresenter(RechargeActivityBaseView view) {
        this.mIModel = new RechargeActivityBaseModel();
        this.mViewRef = new WeakReference<>(view);
    }

    public void recharge(String recharge){
        if (null != mIModel && null != mViewRef && null != mViewRef.get()){
            ((RechargeActivityBaseModel)mIModel).recharge(recharge,new RechargeActivityRechargeCallBack() {
                @Override
                public void rechargeSuccess(ResponseBean responseBean) {
                    if (mViewRef.get() != null){
                        ((RechargeActivityBaseView)mViewRef.get()).rechargeSuccess(responseBean);
                    }
                }

                @Override
                public void rechargeFail() {
                    if (mViewRef.get() != null){
                        ((RechargeActivityBaseView)mViewRef.get()).rechargeFail();
                    }
                }
            });
        }
    }
}
