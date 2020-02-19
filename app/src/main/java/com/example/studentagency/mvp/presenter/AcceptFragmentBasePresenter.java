package com.example.studentagency.mvp.presenter;

import com.example.studentagency.bean.IndentBean;
import com.example.studentagency.mvp.model.AcceptFragmentBaseModel;
import com.example.studentagency.mvp.model.AllRecordFragmentBaseModel;
import com.example.studentagency.mvp.model.Callback.AcceptFragmentCancelIndentHadTakenCallBack;
import com.example.studentagency.mvp.model.Callback.AcceptFragmentDeleteIndentHadCommentCallBack;
import com.example.studentagency.mvp.model.Callback.AcceptFragmentDeleteIndentNotCommentCallBack;
import com.example.studentagency.mvp.model.Callback.AcceptFragmentEnsureAcceptGoodsCallBack;
import com.example.studentagency.mvp.model.Callback.AcceptFragmentGetAcceptIndentsCallBack;
import com.example.studentagency.mvp.view.AcceptFragmentBaseView;
import com.example.studentagency.ui.fragment.PersonIndentActivity.AcceptFragment;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/19
 * desc:
 */
public class AcceptFragmentBasePresenter extends IPresenter {

    public AcceptFragmentBasePresenter(AcceptFragmentBaseView view) {
        this.mIModel = new AcceptFragmentBaseModel();
        this.mViewRef = new WeakReference<>(view);
    }

    public void getAcceptIndents(){
        if (null != mViewRef && null != mViewRef.get() && null != mIModel){
            ((AcceptFragmentBaseModel)mIModel).getAcceptIndents(new AcceptFragmentGetAcceptIndentsCallBack() {
                @Override
                public void getAcceptIndentsSuccess(List<IndentBean> indentBeanList) {
                    if (null != mViewRef.get()){
                        ((AcceptFragmentBaseView)mViewRef.get()).getAcceptIndentsSuccess(indentBeanList);
                    }
                }

                @Override
                public void getAcceptIndentsFail() {
                    if (null != mViewRef.get()){
                        ((AcceptFragmentBaseView)mViewRef.get()).getAcceptIndentsFail();
                    }
                }
            });
        }
    }

    public void cancelIndentHadTaken(int indentId, String price) {
        if (null != mViewRef && null != mViewRef.get() && null != mIModel){
            ((AcceptFragmentBaseModel)mIModel).cancelIndentHadTaken(indentId, price,new AcceptFragmentCancelIndentHadTakenCallBack() {
                @Override
                public void cancelIndentHadTakenSuccess(Integer result) {
                    if (null != mViewRef.get()){
                        ((AcceptFragmentBaseView)mViewRef.get()).cancelIndentHadTakenSuccess(result);
                    }
                }

                @Override
                public void cancelIndentHadTakenFail() {
                    if (null != mViewRef.get()){
                        ((AcceptFragmentBaseView)mViewRef.get()).cancelIndentHadTakenFail();
                    }
                }
            });
        }
    }

    public void deleteIndentNotComment(int indentId, String price) {
        if (null != mViewRef && null != mViewRef.get() && null != mIModel){
            ((AcceptFragmentBaseModel)mIModel).deleteIndentNotComment(indentId, price,new AcceptFragmentDeleteIndentNotCommentCallBack() {
                @Override
                public void deleteIndentNotCommentSuccess(Integer result) {
                    if (null != mViewRef.get()){
                        ((AcceptFragmentBaseView)mViewRef.get()).deleteIndentNotCommentSuccess(result);
                    }
                }

                @Override
                public void deleteIndentNotCommentFail() {
                    if (null != mViewRef.get()){
                        ((AcceptFragmentBaseView)mViewRef.get()).deleteIndentNotCommentFail();
                    }
                }
            });
        }
    }

    public void deleteIndentHadComment(int indentId, String price) {
        if (null != mViewRef && null != mViewRef.get() && null != mIModel){
            ((AcceptFragmentBaseModel)mIModel).deleteIndentHadComment(indentId, price,new AcceptFragmentDeleteIndentHadCommentCallBack() {
                @Override
                public void deleteIndentHadCommentSuccess(Integer result) {
                    if (null != mViewRef.get()){
                        ((AcceptFragmentBaseView)mViewRef.get()).deleteIndentHadCommentSuccess(result);
                    }
                }

                @Override
                public void deleteIndentHadCommentFail() {
                    if (null != mViewRef.get()){
                        ((AcceptFragmentBaseView)mViewRef.get()).deleteIndentHadCommentFail();
                    }
                }
            });
        }
    }

    public void ensureAcceptGoods(int indentId, String price) {
        if (null != mViewRef && null != mViewRef.get() && null != mIModel){
            ((AcceptFragmentBaseModel)mIModel).ensureAcceptGoods(indentId, price,new AcceptFragmentEnsureAcceptGoodsCallBack() {
                @Override
                public void ensureAcceptGoodsSuccess(Integer result) {
                    if (null != mViewRef.get()){
                        ((AcceptFragmentBaseView)mViewRef.get()).ensureAcceptGoodsSuccess(result);
                    }
                }

                @Override
                public void ensureAcceptGoodsFail() {
                    if (null != mViewRef.get()){
                        ((AcceptFragmentBaseView)mViewRef.get()).ensureAcceptGoodsFail();
                    }
                }
            });
        }
    }
}
