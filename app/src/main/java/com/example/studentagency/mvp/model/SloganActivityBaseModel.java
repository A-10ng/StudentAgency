package com.example.studentagency.mvp.model;

import android.util.Log;

import com.example.studentagency.bean.ResponseBean;
import com.example.studentagency.http.ApiService;
import com.example.studentagency.http.RetrofitHelper;
import com.example.studentagency.mvp.model.Callback.SloganActivityTokenVerifyCallBack;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/03/05
 * desc:
 */
public class SloganActivityBaseModel implements IModel {

    private static final String TAG = "SloganActivityBaseModel";
    private ApiService apiService = RetrofitHelper.getInstance().getServer();

    public void tokenVerify(String token, SloganActivityTokenVerifyCallBack callBack) {
        apiService.tokenVerify(token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBean result) {
                        Log.i(TAG, "onNext: result>>>>>"+result);
                        callBack.tokenVerifySuccess(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: e>>>>>"+e.toString());
                        callBack.tokenVerifyFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
