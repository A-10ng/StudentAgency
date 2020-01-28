package com.example.studentagency.mvp.presenter;

import com.example.studentagency.bean.IndentBean;
import com.example.studentagency.mvp.model.Callback.ClassifyActivityGetIndentByTypeCallBack;
import com.example.studentagency.mvp.model.ClassifyActivityBaseModel;
import com.example.studentagency.mvp.view.ClassifyActivityBaseView;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/01/10
 * desc:
 */
public class ClassifyActivityBasePresenter extends IPresenter {

    public ClassifyActivityBasePresenter(ClassifyActivityBaseView view) {
        this.mIModel = new ClassifyActivityBaseModel();
        this.mViewRef = new WeakReference<>(view);
    }

    public void getIndentByType(int type){
        if (null != mIModel && null != mViewRef && null != mViewRef.get()){
            ((ClassifyActivityBaseModel)mIModel).getIndentByType(type, new ClassifyActivityGetIndentByTypeCallBack() {
                @Override
                public void getIndentByTypeSuccess(List<IndentBean> indentBeanList) {
                    if (null != mViewRef.get()){
                        ((ClassifyActivityBaseView)mViewRef.get()).getIndentByTypeSuccess(indentBeanList);
                    }
                }

                @Override
                public void getIndentByTypeFail() {
                    if (null != mViewRef.get()){
                        ((ClassifyActivityBaseView)mViewRef.get()).getIndentByTypeFail();
                    }
                }
            });
        }
    }
}
