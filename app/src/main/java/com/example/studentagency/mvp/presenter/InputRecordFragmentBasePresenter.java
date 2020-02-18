package com.example.studentagency.mvp.presenter;

import com.example.studentagency.bean.CreditBean;
import com.example.studentagency.mvp.model.Callback.InputRecordFragmentGetCreditInputRecordCallBack;
import com.example.studentagency.mvp.model.IndentActivityBaseModel;
import com.example.studentagency.mvp.model.InputRecordFragmentBaseModel;
import com.example.studentagency.mvp.view.InputRecordFragmentBaseView;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/18
 * desc:
 */
public class InputRecordFragmentBasePresenter extends IPresenter {

    public InputRecordFragmentBasePresenter(InputRecordFragmentBaseView view) {
        this.mIModel = new InputRecordFragmentBaseModel();
        this.mViewRef = new WeakReference<>(view);
    }

    public void getCreditInputRecord(){
        if (null != mViewRef && null != mViewRef.get() && null != mIModel){
            ((InputRecordFragmentBaseModel)mIModel).getCreditInputRecord(new InputRecordFragmentGetCreditInputRecordCallBack() {
                @Override
                public void getCreditInputRecordSuccess(List<CreditBean> creditBeans) {
                    if (null != mViewRef.get()){
                        ((InputRecordFragmentBaseView)mViewRef.get()).getCreditInputRecordSuccess(creditBeans);
                    }
                }

                @Override
                public void getCreditInputRecordFail() {
                    if (null != mViewRef.get()){
                        ((InputRecordFragmentBaseView)mViewRef.get()).getCreditInputRecordFail();
                    }
                }
            });
        }
    }
}
