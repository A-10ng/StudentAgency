package com.example.studentagency.mvp.model;

import android.util.Log;

import com.example.studentagency.http.ApiService;
import com.example.studentagency.http.RetrofitHelper;
import com.example.studentagency.mvp.model.Callback.ModifyPwdActivityChangePwdCallBack;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/08
 * desc:
 */
public class ModifyPwdActivityBaseModel implements IModel {

    private static final String TAG = "ModifyPwdActivityBaseMo";
    private ApiService apiService = RetrofitHelper.getInstance().getServer();

    public void changePwd(int userId, String newPwd, ModifyPwdActivityChangePwdCallBack callBack){
        apiService.changePwd(userId,newPwd)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer result) {
                        Log.i(TAG, "changePwd onNext: result>>>>>"+result);
                        callBack.changePwdSuccess(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "changePwd onError: e>>>>>"+e.getMessage());
                        callBack.changePwdFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
