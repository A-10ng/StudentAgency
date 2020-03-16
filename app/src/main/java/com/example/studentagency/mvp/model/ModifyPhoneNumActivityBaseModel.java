package com.example.studentagency.mvp.model;

import android.util.Log;

import com.example.studentagency.bean.ResponseBean;
import com.example.studentagency.http.ApiService;
import com.example.studentagency.http.RetrofitHelper;
import com.example.studentagency.mvp.model.Callback.ModifyPhoneNumActivityGetVerifyCodeCallBack;
import com.example.studentagency.mvp.model.Callback.ModifyPhoneNumActivityModifyPhoneNumCallBack;
import com.example.studentagency.ui.activity.MyApp;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/23
 * desc:
 */
public class ModifyPhoneNumActivityBaseModel implements IModel {

    private static final String TAG = "ModifyPhoneNumActivityB";
    private ApiService apiService = RetrofitHelper.getInstance().getServer();

    public void getVerifyCode(String newPhoneNum, ModifyPhoneNumActivityGetVerifyCodeCallBack callBack) {
        apiService.getVerifyCode(newPhoneNum)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBean result) {
                        Log.i(TAG, "getVerifyCode onNext: result>>>>>" + result);
                        callBack.getVerifyCodeSuccess(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "getVerifyCode onError: e>>>>>" + e.getMessage());
                        callBack.getVerifyCodeFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void modifyPhoneNum(String newPhoneNum, String verifyCode,ModifyPhoneNumActivityModifyPhoneNumCallBack callBack) {
        apiService.modifyPhoneNum(MyApp.userId,newPhoneNum,verifyCode)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBean result) {
                        Log.i(TAG, "modifyPhoneNum onNext: result>>>>>" + result);
                        callBack.modifyPhoneNumSuccess(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "modifyPhoneNum onError: e>>>>>" + e.getMessage());
                        callBack.modifyPhoneNumFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
