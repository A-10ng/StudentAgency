package com.example.studentagency.mvp.presenter;

import com.example.studentagency.bean.ResponseBean;
import com.example.studentagency.mvp.model.Callback.IndentActivityAcceptIndentCallBack;
import com.example.studentagency.mvp.model.Callback.IndentActivityGetCommentInfoCallBack;
import com.example.studentagency.mvp.model.Callback.IndentActivityGetIndentInfoCallBack;
import com.example.studentagency.mvp.model.Callback.IndentActivityGetPublishInfoCallBack;
import com.example.studentagency.mvp.model.Callback.IndentActivityGetRatingStarsCallBack;
import com.example.studentagency.mvp.model.Callback.IndentActivityGiveACommentCallBack;
import com.example.studentagency.mvp.model.IndentActivityBaseModel;
import com.example.studentagency.mvp.view.IndentActivityBaseView;

import java.lang.ref.WeakReference;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/01/05
 * desc:
 */
public class IndentActivityBasePresenter extends IPresenter {

    public IndentActivityBasePresenter(IndentActivityBaseView view) {
        this.mIModel = new IndentActivityBaseModel();
        this.mViewRef = new WeakReference<>(view);
    }

    public void getPublishInfo(int publishId){
        if (null != mIModel && null != mViewRef && null != mViewRef.get()){
            ((IndentActivityBaseModel)mIModel).getPublishInfo(publishId,new IndentActivityGetPublishInfoCallBack() {
                @Override
                public void onGetPublishInfoSuccess(ResponseBean responseBean) {
                    if (mViewRef.get() != null){
                        ((IndentActivityBaseView)mViewRef.get()).getPublishInfoSuccess(responseBean);
                    }
                }

                @Override
                public void onGetPublishInfoFail() {
                    if (mViewRef.get() != null){
                        ((IndentActivityBaseView)mViewRef.get()).getPusblishInfoFail();
                    }
                }
            });
        }
    }

    public void getIndentInfo(int indentId){
        if (null != mIModel && null != mViewRef && null != mViewRef.get()){
            ((IndentActivityBaseModel)mIModel).getIndentInfo(indentId,new IndentActivityGetIndentInfoCallBack() {
                @Override
                public void onGetIndentInfoSuccess(ResponseBean responseBean) {
                    if (null != mViewRef.get()){
                        ((IndentActivityBaseView)mViewRef.get()).getIndentInfoSuccess(responseBean);
                    }
                }

                @Override
                public void onGetIndentInfoFail() {
                    if (null != mViewRef.get()){
                        ((IndentActivityBaseView)mViewRef.get()).getIndentInfoFail();
                    }
                }
            });
        }
    }

    public void getCommentInfo(int indentId){
        if (null != mIModel && null != mViewRef && null != mViewRef.get()){
            ((IndentActivityBaseModel)mIModel).getCommentInfo(indentId,new IndentActivityGetCommentInfoCallBack() {
                @Override
                public void onGetCommentInfoSuccess(ResponseBean responseBean) {
                    if (null != mViewRef.get()){
                        ((IndentActivityBaseView)mViewRef.get()).getCommentInfoSuccess(responseBean);
                    }
                }

                @Override
                public void onGetCommentInfoFail() {
                    if (null != mViewRef.get()){
                        ((IndentActivityBaseView)mViewRef.get()).getCommentInfoFail();
                    }
                }
            });
        }
    }

    public void acceptIndent(int indentId,String acceptedTime){
        if (null != mIModel && null != mViewRef && null != mViewRef.get()){
            ((IndentActivityBaseModel)mIModel).acceptIndent(indentId, acceptedTime, new IndentActivityAcceptIndentCallBack() {
                @Override
                public void onAcceptIndentSuccess(ResponseBean responseBean) {
                    if (null != mViewRef.get()){
                        ((IndentActivityBaseView)mViewRef.get()).acceptIndentSuccess(responseBean);
                    }
                }

                @Override
                public void onAcceptIndentFail() {
                    if (null != mViewRef.get()){
                        ((IndentActivityBaseView)mViewRef.get()).acceptIndentFail();
                    }
                }
            });
        }
    }

    public void giveAComment(int indentId, int userId, String content, String commentTime) {
        if (null != mIModel && null != mViewRef && null != mViewRef.get()){
            ((IndentActivityBaseModel)mIModel).giveAComment(indentId, userId, content, commentTime,
                    new IndentActivityGiveACommentCallBack() {
                @Override
                public void onGiveACommentSuccess(ResponseBean responseBean) {
                    if (null != mViewRef.get()){
                        ((IndentActivityBaseView)mViewRef.get()).giveACommentSuccess(responseBean);
                    }
                }

                @Override
                public void onGiveACommentFail() {
                    if (null != mViewRef.get()){
                        ((IndentActivityBaseView)mViewRef.get()).giveACommentFail();
                    }
                }
            });
        }
    }

    public void getRatingStarsInfo(int indentId) {
        if (null != mIModel && null != mViewRef && null != mViewRef.get()){
            ((IndentActivityBaseModel)mIModel).getRatingStarsInfo(indentId,
                    new IndentActivityGetRatingStarsCallBack() {
                        @Override
                        public void getRatingStarsInfoSuccess(ResponseBean responseBean) {
                            if (null != mViewRef.get()){
                                ((IndentActivityBaseView)mViewRef.get()).getRatingStarsInfoSuccess(responseBean);
                            }
                        }

                        @Override
                        public void getRatingStarsInfoFail() {
                            if (null != mViewRef.get()){
                                ((IndentActivityBaseView)mViewRef.get()).getRatingStarsInfoFail();
                            }
                        }
                    });
        }
    }
}
