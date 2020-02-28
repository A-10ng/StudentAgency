package com.example.studentagency.mvp.model;

import android.util.Log;

import com.example.studentagency.bean.OtherPersonBean;
import com.example.studentagency.http.ApiService;
import com.example.studentagency.http.RetrofitHelper;
import com.example.studentagency.mvp.model.Callback.OtherPersonActivityGetCurrenUserInfoCallBack;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/25
 * desc:
 */
public class OtherPersonActivityBaseModel implements IModel {

    private static final String TAG = "OtherPersonActivityBase";
    private ApiService apiService = RetrofitHelper.getInstance().getServer();

    public void getCurrentUserInfo(String phoneNum, OtherPersonActivityGetCurrenUserInfoCallBack callBack) {
        apiService.getCurrentUserInfo(phoneNum)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<OtherPersonBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(OtherPersonBean otherPersonBean) {
                        Log.i(TAG, "onNext: otherPersonBean>>>>>"+otherPersonBean.toString());
                        callBack.getCurrentUserInfoSuccess(otherPersonBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: e>>>>>"+e.toString());
                        callBack.getCurrentUserInfoFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
