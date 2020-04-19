package com.example.studentagency.mvp.model;

import android.util.Log;

import com.example.studentagency.bean.ResponseBean;
import com.example.studentagency.http.ApiService;
import com.example.studentagency.http.RetrofitHelper;
import com.example.studentagency.mvp.model.Callback.HomeFragmentBannerCallBack;
import com.example.studentagency.mvp.model.Callback.HomeFragmentIndentCallBack;
import com.example.studentagency.ui.activity.MyApp;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2019/12/31
 * desc:
 */
public class HomeFragmentBaseModel implements IModel {

    private static final String TAG = "HomeFragmentBaseModel";
    private ApiService apiService = RetrofitHelper.getInstance().getServer();

    public void getBannerData(final HomeFragmentBannerCallBack callBack) {
        apiService.getBannerData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBean newsBeanList) {
                        callBack.onGetBannerDataSuccess(newsBeanList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "getBannerData: onError e: "+e.getMessage() + "---localMessage---"+ e.getLocalizedMessage());
                        callBack.onGetBannerDataFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getIndentData(final HomeFragmentIndentCallBack callBack){
        apiService.getIndentData(MyApp.userPhoneNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBean responseBean) {
                        callBack.onGetIndentDataSuccess(responseBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "getIndentData: onError e: "+e.getMessage() + "---localMessage---"+ e.getLocalizedMessage());
                        callBack.onGetIndentDataFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
