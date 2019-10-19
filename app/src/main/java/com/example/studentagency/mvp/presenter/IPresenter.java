package com.example.studentagency.mvp.presenter;

import com.example.studentagency.mvp.model.IModel;
import com.example.studentagency.mvp.view.IView;

import java.lang.ref.WeakReference;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2019/10/09
 * desc:
 */
public class IPresenter {
    protected WeakReference<? extends IView> mViewRef;
    protected IModel mIModel;
}
