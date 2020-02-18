package com.example.studentagency.mvp.presenter;

import com.example.studentagency.mvp.model.Callback.CreditSRActivityGetCreditScoreCallBack;
import com.example.studentagency.mvp.model.CreditScoreRecordActivityBaseModel;
import com.example.studentagency.mvp.view.CreditScoreRecordActivityBaseView;

import java.lang.ref.WeakReference;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/18
 * desc:
 */
public class CreditScoreRecordActivityBasePresenter extends IPresenter {

    public CreditScoreRecordActivityBasePresenter(CreditScoreRecordActivityBaseView view) {
        this.mIModel = new CreditScoreRecordActivityBaseModel();
        this.mViewRef = new WeakReference<>(view);
    }

    public void getCreditScore(){
        if (null != mViewRef && null != mViewRef.get() && null != mIModel){
            ((CreditScoreRecordActivityBaseModel)mIModel).getCreditScore(new CreditSRActivityGetCreditScoreCallBack() {
                @Override
                public void getCreditScoreSuccess(Integer score) {
                    if (null != mViewRef.get()){
                        ((CreditScoreRecordActivityBaseView)mViewRef.get()).getCreditScoreSuccess(score);
                    }
                }

                @Override
                public void getCreditScoreFail() {
                    if (null != mViewRef.get()){
                        ((CreditScoreRecordActivityBaseView)mViewRef.get()).getCreditScoreFail();
                    }
                }
            });
        }
    }
}
