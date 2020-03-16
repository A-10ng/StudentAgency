package com.example.studentagency.mvp.model;

import android.util.Log;

import com.example.studentagency.bean.ResponseBean;
import com.example.studentagency.http.ApiService;
import com.example.studentagency.http.RetrofitHelper;
import com.example.studentagency.mvp.model.Callback.RegisterTwoActivityGetVerifyCodeCallBack;
import com.example.studentagency.mvp.model.Callback.RegisterTwoActivityRegisterCallBack;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/01/30
 * desc:
 */
public class RegisterActivityBaseModel implements IModel {

    private static final String TAG = "RegisterActivityBaseMod";
    private ApiService apiService = RetrofitHelper.getInstance().getServer();

    public void getVerifyCode(String phoneNum, RegisterTwoActivityGetVerifyCodeCallBack callBack){
        apiService.getVerifyCode(phoneNum)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBean result) {
                        Log.i(TAG, "getVerifyCode onNext: result>>>>>"+result);
                        callBack.getVerifyCodeSuccess(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "getVerifyCode onError: e>>>>>"+e.getMessage());
                        callBack.getVerifyCodeFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void register(String username, int gender, String password,
                         String school, String phoneNum, RegisterTwoActivityRegisterCallBack callBack){
        apiService.register(username,gender,password, school, phoneNum)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBean result) {
                        Log.i(TAG, "register onNext: result>>>>>"+result);
                        callBack.registerSuccess(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "register onError: e>>>>>"+e.getMessage());
                        callBack.registerFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
