package com.example.studentagency.mvp.model;

import android.util.Log;

import com.example.studentagency.bean.ResponseBean;
import com.example.studentagency.http.ApiService;
import com.example.studentagency.http.RetrofitHelper;
import com.example.studentagency.mvp.model.Callback.ClassifyActivityGetIndentByTypeCallBack;
import com.example.studentagency.ui.activity.MyApp;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/01/10
 * desc:
 */
public class ClassifyActivityBaseModel implements IModel {

    private static final String TAG = "ClassifyActivityBaseMod";
    private ApiService apiService = RetrofitHelper.getInstance().getServer();

    public void getIndentByType(int type, final ClassifyActivityGetIndentByTypeCallBack callBack){
        apiService.getIndentByType(type, MyApp.userId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBean indentBeanList) {
                        callBack.getIndentByTypeSuccess(indentBeanList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "getIndentByType onError: e>>>>>"+e.getMessage() + "---localMessage---"+ e.getLocalizedMessage());
                        callBack.getIndentByTypeFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
