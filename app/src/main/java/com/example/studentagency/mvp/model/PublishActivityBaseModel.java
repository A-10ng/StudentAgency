package com.example.studentagency.mvp.model;

import android.util.Log;

import com.example.studentagency.bean.ResponseBean;
import com.example.studentagency.http.ApiService;
import com.example.studentagency.http.RetrofitHelper;
import com.example.studentagency.mvp.model.Callback.PublishActivityPublishIndentCallBack;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/01/15
 * desc:
 */
public class PublishActivityBaseModel implements IModel {
    private static final String TAG = "PublishActivityBaseMode";
    private ApiService apiService = RetrofitHelper.getInstance().getServer();

    public void publishIndent(int publishId, int type, float price,
                              String description, String address,
                              String publishTime, String planTime,
                              PublishActivityPublishIndentCallBack callBack) {
        apiService.publishIndent(publishId,type,price,description,address,publishTime,planTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBean result) {
                        Log.i(TAG, "publishIndent onNext: result>>>>>"+result);
                        callBack.publishIndentSuccess(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "publishIndent onError: e>>>>>"+e.getMessage());
                        callBack.publishIndentFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
