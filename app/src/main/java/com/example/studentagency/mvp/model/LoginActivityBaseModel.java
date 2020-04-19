package com.example.studentagency.mvp.model;

import android.util.Log;

import com.example.studentagency.bean.ResponseBean;
import com.example.studentagency.http.ApiService;
import com.example.studentagency.http.RetrofitHelper;
import com.example.studentagency.mvp.model.Callback.LoginActivityGetVerifyCodeCallBack;
import com.example.studentagency.mvp.model.Callback.LoginActivityLoginByPasswordCallBack;
import com.example.studentagency.mvp.model.Callback.LoginActivityLoginByVerifyCodeCallBack;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2019/10/14
 * desc:
 */
public class LoginActivityBaseModel implements IModel {

    private static final String TAG = "LoginActivityBaseModel";
    ApiService apiService = RetrofitHelper.getInstance().getServer();

    public void loginByPassword(String phoneNum, String password, final LoginActivityLoginByPasswordCallBack callBack) {
        Log.i(TAG, "BaseModel--------------------------loginByPassword----------------------------");
        apiService.loginByPassword(phoneNum, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<ResponseBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<ResponseBean> response) {
                        Log.i(TAG, "BaseModel--------------------------loginByPassword-----------------onNext-----------");
                        callBack.loginByPasswordSuccess(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "loginByPassword onError: e>>>>>"+e.getMessage() + "---localMessage---"+ e.getLocalizedMessage());
                        callBack.loginByPasswordFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void loginByVerifyCode(String phoneNum, String verifyCode, final LoginActivityLoginByVerifyCodeCallBack callBack) {
        apiService.loginByVerifyCode(phoneNum, verifyCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<ResponseBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<ResponseBean> response) {
                        callBack.loginByVerifyCodeSuccess(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "loginByVerifyCode onError: e>>>>>"+e.getMessage() + "---localMessage---"+ e.getLocalizedMessage());
                        callBack.loginByVerifyCodeFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getVerifyCode(String phoneNum, LoginActivityGetVerifyCodeCallBack callBack){
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
                        Log.i(TAG, "getVerifyCode onError: e>>>>>"+e.getMessage() + "---localMessage---"+ e.getLocalizedMessage());
                        callBack.getVerifyCodeFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
