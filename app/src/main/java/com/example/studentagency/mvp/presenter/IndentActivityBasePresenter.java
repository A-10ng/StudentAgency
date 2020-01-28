package com.example.studentagency.mvp.presenter;

import com.example.studentagency.bean.CommentBean;
import com.example.studentagency.bean.IndentBean;
import com.example.studentagency.bean.UserBean;
import com.example.studentagency.mvp.model.Callback.IndentActivityAcceptIndentCallBack;
import com.example.studentagency.mvp.model.Callback.IndentActivityGetCommentInfoCallBack;
import com.example.studentagency.mvp.model.Callback.IndentActivityGetIndentInfoCallBack;
import com.example.studentagency.mvp.model.Callback.IndentActivityGetPublishInfoCallBack;
import com.example.studentagency.mvp.model.Callback.IndentActivityGiveACommentCallBack;
import com.example.studentagency.mvp.model.IndentActivityBaseModel;
import com.example.studentagency.mvp.view.IndentActivityBaseView;

import java.lang.ref.WeakReference;
import java.util.List;

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

    public void getPublishInfo(){
        if (null != mIModel && null != mViewRef && null != mViewRef.get()){
            ((IndentActivityBaseModel)mIModel).getPublishInfo(new IndentActivityGetPublishInfoCallBack() {
                @Override
                public void onGetPublishInfoSuccess(UserBean userBean) {
                    if (mViewRef.get() != null){
                        ((IndentActivityBaseView)mViewRef.get()).getPublishInfoSuccess(userBean);
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
                public void onGetIndentInfoSuccess(IndentBean indentBean) {
                    if (null != mViewRef.get()){
                        ((IndentActivityBaseView)mViewRef.get()).getIndentInfoSuccess(indentBean);
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
                public void onGetCommentInfoSuccess(List<CommentBean> commentBeans) {
                    if (null != mViewRef.get()){
                        ((IndentActivityBaseView)mViewRef.get()).getCommentInfoSuccess(commentBeans);
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
                public void onAcceptIndentSuccess(Integer result) {
                    if (null != mViewRef.get()){
                        ((IndentActivityBaseView)mViewRef.get()).acceptIndentSuccess(result);
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
                public void onGiveACommentSuccess(Integer result) {
                    if (null != mViewRef.get()){
                        ((IndentActivityBaseView)mViewRef.get()).giveACommentSuccess(result);
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
}
