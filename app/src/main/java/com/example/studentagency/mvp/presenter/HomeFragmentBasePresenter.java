package com.example.studentagency.mvp.presenter;

import com.example.studentagency.bean.ResponseBean;
import com.example.studentagency.mvp.model.Callback.HomeFragmentBannerCallBack;
import com.example.studentagency.mvp.model.Callback.HomeFragmentIndentCallBack;
import com.example.studentagency.mvp.model.HomeFragmentBaseModel;
import com.example.studentagency.mvp.view.HomeFragmentBaseView;

import java.lang.ref.WeakReference;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2019/12/31
 * desc:
 */
public class HomeFragmentBasePresenter extends IPresenter{
    public HomeFragmentBasePresenter(HomeFragmentBaseView view) {
        this.mIModel = new HomeFragmentBaseModel();
        this.mViewRef = new WeakReference<>(view);
    }

    public void getBannerData(){
        if (mIModel != null && null != mViewRef && null != mViewRef.get()){
            ((HomeFragmentBaseModel)mIModel).getBannerData(new HomeFragmentBannerCallBack() {
                @Override
                public void onGetBannerDataSuccess(ResponseBean responseBean) {
                    if (mViewRef.get() != null) {
                        ((HomeFragmentBaseView) mViewRef.get()).getBannerDataSuccess(responseBean);
                    }
                }

                @Override
                public void onGetBannerDataFail() {
                    if (mViewRef.get() != null) {
                        ((HomeFragmentBaseView) mViewRef.get()).getBannerDataFail();
                    }
                }
            });
        }
    }

    public void getIndentData(){
        if (mIModel != null && null != mViewRef && null != mViewRef.get()){
            ((HomeFragmentBaseModel)mIModel).getIndentData(new HomeFragmentIndentCallBack() {
                @Override
                public void onGetIndentDataSuccess(ResponseBean responseBean) {
                    if (mViewRef.get() != null) {
                        ((HomeFragmentBaseView) mViewRef.get()).getIndentsDataSuccess(responseBean);
                    }
                }

                @Override
                public void onGetIndentDataFail() {
                    if (mViewRef.get() != null) {
                        ((HomeFragmentBaseView) mViewRef.get()).getIndentsDataFail();
                    }
                }
            });
        }
    }
}
