package com.example.studentagency.mvp.presenter;

import com.example.studentagency.bean.ResponseBean;
import com.example.studentagency.mvp.model.Callback.PublishFragmentCancelIndentHadTakenCallBack;
import com.example.studentagency.mvp.model.Callback.PublishFragmentCancelIndentNotTakenCallBack;
import com.example.studentagency.mvp.model.Callback.PublishFragmentDeleteIndentHadCommentCallBack;
import com.example.studentagency.mvp.model.Callback.PublishFragmentDeleteIndentNotCommentCallBack;
import com.example.studentagency.mvp.model.Callback.PublishFragmentEnsureAcceptGoodsCallBack;
import com.example.studentagency.mvp.model.Callback.PublishFragmentGetPublishIndentsCallBack;
import com.example.studentagency.mvp.model.Callback.PublishFragmentGiveRatingCallBack;
import com.example.studentagency.mvp.model.PublishFragmentBaseModel;
import com.example.studentagency.mvp.view.PublishFragmentBaseView;

import java.lang.ref.WeakReference;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/19
 * desc:
 */
public class PublishFragmentBasePresenter extends IPresenter {

    public PublishFragmentBasePresenter(PublishFragmentBaseView view) {
        this.mIModel = new PublishFragmentBaseModel();
        this.mViewRef = new WeakReference<>(view);
    }

    public void getPublishIndents(){
        if (null != mViewRef && null != mViewRef.get() && null != mIModel){
            ((PublishFragmentBaseModel)mIModel).getPublishIndents(new PublishFragmentGetPublishIndentsCallBack() {
                @Override
                public void getPublishIndentsSuccess(ResponseBean responseBean) {
                    if (null != mViewRef.get()){
                        ((PublishFragmentBaseView)mViewRef.get()).getPublishIndentsSuccess(responseBean);
                    }
                }

                @Override
                public void getPublishIndentsFail() {
                    if (null != mViewRef.get()){
                        ((PublishFragmentBaseView)mViewRef.get()).getPublishIndentsFail();
                    }
                }
            });
        }
    }

    public void cancelIndentNotTaken(int indentId,String price){
        if (null != mViewRef && null != mViewRef.get() && null != mIModel){
            ((PublishFragmentBaseModel)mIModel).cancelIndentNotTaken(indentId, price,new PublishFragmentCancelIndentNotTakenCallBack() {
                @Override
                public void cancelIndentNotTakenSuccess(ResponseBean responseBean) {
                    if (null != mViewRef.get()){
                        ((PublishFragmentBaseView)mViewRef.get()).cancelIndentNotTakenSuccess(responseBean);
                    }
                }

                @Override
                public void cancelIndentNotTakenFail() {
                    if (null != mViewRef.get()){
                        ((PublishFragmentBaseView)mViewRef.get()).cancelIndentNotTakenFail();
                    }
                }
            });
        }
    }

    public void cancelIndentHadTaken(int indentId, String price) {
        if (null != mViewRef && null != mViewRef.get() && null != mIModel){
            ((PublishFragmentBaseModel)mIModel).cancelIndentHadTaken(indentId, price,new PublishFragmentCancelIndentHadTakenCallBack() {
                @Override
                public void cancelIndentHadTakenSuccess(ResponseBean responseBean) {
                    if (null != mViewRef.get()){
                        ((PublishFragmentBaseView)mViewRef.get()).cancelIndentHadTakenSuccess(responseBean);
                    }
                }

                @Override
                public void cancelIndentHadTakenFail() {
                    if (null != mViewRef.get()){
                        ((PublishFragmentBaseView)mViewRef.get()).cancelIndentHadTakenFail();
                    }
                }
            });
        }
    }

    public void deleteIndentNotComment(int indentId, String price) {
        if (null != mViewRef && null != mViewRef.get() && null != mIModel){
            ((PublishFragmentBaseModel)mIModel).deleteIndentNotComment(indentId, price,new PublishFragmentDeleteIndentNotCommentCallBack() {
                @Override
                public void deleteIndentNotCommentSuccess(ResponseBean responseBean) {
                    if (null != mViewRef.get()){
                        ((PublishFragmentBaseView)mViewRef.get()).deleteIndentNotCommentSuccess(responseBean);
                    }
                }

                @Override
                public void deleteIndentNotCommentFail() {
                    if (null != mViewRef.get()){
                        ((PublishFragmentBaseView)mViewRef.get()).deleteIndentNotCommentFail();
                    }
                }
            });
        }
    }

    public void deleteIndentHadComment(int indentId, String price) {
        if (null != mViewRef && null != mViewRef.get() && null != mIModel){
            ((PublishFragmentBaseModel)mIModel).deleteIndentHadComment(indentId, price,new PublishFragmentDeleteIndentHadCommentCallBack() {
                @Override
                public void deleteIndentHadCommentSuccess(ResponseBean responseBean) {
                    if (null != mViewRef.get()){
                        ((PublishFragmentBaseView)mViewRef.get()).deleteIndentHadCommentSuccess(responseBean);
                    }
                }

                @Override
                public void deleteIndentHadCommentFail() {
                    if (null != mViewRef.get()){
                        ((PublishFragmentBaseView)mViewRef.get()).deleteIndentHadCommentFail();
                    }
                }
            });
        }
    }

    public void ensureAcceptGoods(String finishTime,int indentId, String price) {
        if (null != mViewRef && null != mViewRef.get() && null != mIModel){
            ((PublishFragmentBaseModel)mIModel).ensureAcceptGoods(finishTime,indentId, price,new PublishFragmentEnsureAcceptGoodsCallBack() {
                @Override
                public void ensureAcceptGoodsSuccess(ResponseBean responseBean) {
                    if (null != mViewRef.get()){
                        ((PublishFragmentBaseView)mViewRef.get()).ensureAcceptGoodsSuccess(responseBean);
                    }
                }

                @Override
                public void ensureAcceptGoodsFail() {
                    if (null != mViewRef.get()){
                        ((PublishFragmentBaseView)mViewRef.get()).ensureAcceptGoodsFail();
                    }
                }
            });
        }
    }

    public void giveRating(int acceptId,int indentId,int increasement,String happenTime) {
        if (null != mViewRef && null != mViewRef.get() && null != mIModel){
            ((PublishFragmentBaseModel)mIModel).giveRating(acceptId,indentId,increasement, happenTime,new PublishFragmentGiveRatingCallBack() {
                @Override
                public void giveRatingSuccess(ResponseBean responseBean) {
                    if (null != mViewRef.get()){
                        ((PublishFragmentBaseView)mViewRef.get()).giveRatingSuccess(responseBean);
                    }
                }

                @Override
                public void giveRatingFail() {
                    if (null != mViewRef.get()){
                        ((PublishFragmentBaseView)mViewRef.get()).giveRatingFail();
                    }
                }
            });
        }
    }
}
