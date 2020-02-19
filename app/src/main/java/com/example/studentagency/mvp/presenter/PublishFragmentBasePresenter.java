package com.example.studentagency.mvp.presenter;

import com.example.studentagency.bean.IndentBean;
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
import java.util.List;

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
                public void getPublishIndentsSuccess(List<IndentBean> indentBeanList) {
                    if (null != mViewRef.get()){
                        ((PublishFragmentBaseView)mViewRef.get()).getPublishIndentsSuccess(indentBeanList);
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
                public void cancelIndentNotTakenSuccess(Integer result) {
                    if (null != mViewRef.get()){
                        ((PublishFragmentBaseView)mViewRef.get()).cancelIndentNotTakenSuccess(result);
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
                public void cancelIndentHadTakenSuccess(Integer result) {
                    if (null != mViewRef.get()){
                        ((PublishFragmentBaseView)mViewRef.get()).cancelIndentHadTakenSuccess(result);
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
                public void deleteIndentNotCommentSuccess(Integer result) {
                    if (null != mViewRef.get()){
                        ((PublishFragmentBaseView)mViewRef.get()).deleteIndentNotCommentSuccess(result);
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
                public void deleteIndentHadCommentSuccess(Integer result) {
                    if (null != mViewRef.get()){
                        ((PublishFragmentBaseView)mViewRef.get()).deleteIndentHadCommentSuccess(result);
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

    public void ensureAcceptGoods(int indentId, String price) {
        if (null != mViewRef && null != mViewRef.get() && null != mIModel){
            ((PublishFragmentBaseModel)mIModel).ensureAcceptGoods(indentId, price,new PublishFragmentEnsureAcceptGoodsCallBack() {
                @Override
                public void ensureAcceptGoodsSuccess(Integer result) {
                    if (null != mViewRef.get()){
                        ((PublishFragmentBaseView)mViewRef.get()).ensureAcceptGoodsSuccess(result);
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

    public void giveRating(int increasement,String happenTime) {
        if (null != mViewRef && null != mViewRef.get() && null != mIModel){
            ((PublishFragmentBaseModel)mIModel).giveRating(increasement, happenTime,new PublishFragmentGiveRatingCallBack() {
                @Override
                public void giveRatingSuccess(Integer result) {
                    if (null != mViewRef.get()){
                        ((PublishFragmentBaseView)mViewRef.get()).giveRatingSuccess(result);
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
