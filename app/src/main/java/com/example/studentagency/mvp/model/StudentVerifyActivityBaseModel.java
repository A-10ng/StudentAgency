package com.example.studentagency.mvp.model;

import android.util.Log;

import com.example.studentagency.http.ApiService;
import com.example.studentagency.http.RetrofitHelper;
import com.example.studentagency.mvp.model.Callback.StudentVerifyActivityGetVerifyStateCallBack;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/17
 * desc:
 */
public class StudentVerifyActivityBaseModel implements IModel {
    private static final String TAG = "StudentVerifyActivityBa";
    private ApiService apiService = RetrofitHelper.getInstance().getServer();

    public void getVerifyState(int userId, StudentVerifyActivityGetVerifyStateCallBack callBack){
        apiService.getVerifyState(userId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer result) {
                        Log.i(TAG, "onNext: result>>>>>"+result);
                        callBack.getVerifyStateSuccess(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: ");
                        callBack.getVerifyStateFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
