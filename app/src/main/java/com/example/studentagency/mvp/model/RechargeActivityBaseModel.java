package com.example.studentagency.mvp.model;

import android.util.Log;

import com.example.studentagency.bean.ResponseBean;
import com.example.studentagency.http.ApiService;
import com.example.studentagency.http.RetrofitHelper;
import com.example.studentagency.mvp.model.Callback.RechargeActivityRechargeCallBack;
import com.example.studentagency.ui.activity.MyApp;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/03/16
 * desc:
 */
public class RechargeActivityBaseModel implements IModel {
    private static final String TAG = "OutputRecordFragmentBas";
    private ApiService apiService = RetrofitHelper.getInstance().getServer();

    public void recharge(float recharge,RechargeActivityRechargeCallBack callBack){
        apiService.recharge(MyApp.userId,recharge)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBean responseBean) {
                        callBack.rechargeSuccess(responseBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: e>>>>>"+e.toString());
                        callBack.rechargeFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
