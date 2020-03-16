package com.example.studentagency.mvp.presenter;

import com.example.studentagency.bean.ResponseBean;
import com.example.studentagency.mvp.model.Callback.OutputRecordFragmentGetCreditOutputRecordCallBack;
import com.example.studentagency.mvp.model.OutputRecordFragmentBaseModel;
import com.example.studentagency.mvp.view.OutputRecordFragmentBaseView;

import java.lang.ref.WeakReference;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/18
 * desc:
 */
public class OutputRecordFragmentBasePresenter extends IPresenter {

    public OutputRecordFragmentBasePresenter(OutputRecordFragmentBaseView view) {
        this.mIModel = new OutputRecordFragmentBaseModel();
        this.mViewRef = new WeakReference<>(view);
    }

    public void getCreditOutputRecord(){
        if (null != mViewRef && null != mViewRef.get() && null != mIModel){
            ((OutputRecordFragmentBaseModel)mIModel).getCreditOutputRecord(new OutputRecordFragmentGetCreditOutputRecordCallBack() {
                @Override
                public void getCreditOutputRecordSuccess(ResponseBean responseBean) {
                    if (null != mViewRef.get()){
                        ((OutputRecordFragmentBaseView)mViewRef.get()).getCreditOutputRecordSuccess(responseBean);
                    }
                }

                @Override
                public void getCreditOutputRecordFail() {
                    if (null != mViewRef.get()){
                        ((OutputRecordFragmentBaseView)mViewRef.get()).getCreditOutputRecordFail();
                    }
                }
            });
        }
    }
}
