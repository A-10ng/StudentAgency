package com.example.studentagency.mvp.presenter;

import com.example.studentagency.bean.ResponseBean;
import com.example.studentagency.mvp.model.AllRecordFragmentBaseModel;
import com.example.studentagency.mvp.model.Callback.AllRecordFragmentGetCreditAllRecordCallBack;
import com.example.studentagency.mvp.view.AllRecordFragmentBaseView;

import java.lang.ref.WeakReference;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/18
 * desc:
 */
public class AllRecordFragmentBasePresenter extends IPresenter {

    public AllRecordFragmentBasePresenter(AllRecordFragmentBaseView view) {
        this.mIModel = new AllRecordFragmentBaseModel();
        this.mViewRef = new WeakReference<>(view);
    }

    public void getCreditAllRecord(){
        if (null != mViewRef && null != mViewRef.get() && null != mIModel){
            ((AllRecordFragmentBaseModel)mIModel).getCreditAllRecord(new AllRecordFragmentGetCreditAllRecordCallBack() {
                @Override
                public void getCreditAllRecordSuccess(ResponseBean responseBean) {
                    if (null != mViewRef.get()){
                        ((AllRecordFragmentBaseView)mViewRef.get()).getCreditRecordSuccess(responseBean);
                    }
                }

                @Override
                public void getCreditAllRecordFail() {
                    if (null != mViewRef.get()){
                        ((AllRecordFragmentBaseView)mViewRef.get()).getCreditRecordFail();
                    }
                }
            });
        }
    }
}
