package com.example.studentagency.mvp.presenter;

import com.example.studentagency.bean.ResponseBean;
import com.example.studentagency.mvp.model.Callback.PublishActivityPublishIndentCallBack;
import com.example.studentagency.mvp.model.PublishActivityBaseModel;
import com.example.studentagency.mvp.view.PublishActivityBaseView;

import java.lang.ref.WeakReference;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/01/15
 * desc:
 */
public class PublishActivityBasePresenter extends IPresenter {

    public PublishActivityBasePresenter(PublishActivityBaseView view) {
        this.mIModel = new PublishActivityBaseModel();
        this.mViewRef = new WeakReference<>(view);
    }

    public void publishIndent(int publishId, int type, float price,
                              String description, int address,
                              String publishTime, String planTime){
        if (null != mIModel && null != mViewRef && null != mViewRef.get()){
            ((PublishActivityBaseModel)mIModel).publishIndent(
                    publishId, type, price, description, address, publishTime, planTime,
                    new PublishActivityPublishIndentCallBack() {
                        @Override
                        public void publishIndentSuccess(ResponseBean responseBean) {
                            if (mViewRef.get() != null){
                                ((PublishActivityBaseView)mViewRef.get()).publishIndentSuccess(responseBean);
                            }
                        }

                        @Override
                        public void publishIndentFail() {
                            if (mViewRef.get() != null){
                                ((PublishActivityBaseView)mViewRef.get()).publishIndentFail();
                            }
                        }
                    });
        }
    }
}
