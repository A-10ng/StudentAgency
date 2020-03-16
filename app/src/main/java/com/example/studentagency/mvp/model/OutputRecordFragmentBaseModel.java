package com.example.studentagency.mvp.model;

import android.util.Log;

import com.example.studentagency.bean.ResponseBean;
import com.example.studentagency.http.ApiService;
import com.example.studentagency.http.RetrofitHelper;
import com.example.studentagency.mvp.model.Callback.OutputRecordFragmentGetCreditOutputRecordCallBack;
import com.example.studentagency.ui.activity.MyApp;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/18
 * desc:
 */
public class OutputRecordFragmentBaseModel implements IModel {

    private static final String TAG = "OutputRecordFragmentBas";
    private ApiService apiService = RetrofitHelper.getInstance().getServer();

    public void getCreditOutputRecord(OutputRecordFragmentGetCreditOutputRecordCallBack callBack){
        apiService.getCreditOutputRecord(MyApp.userId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBean creditBeans) {
                        callBack.getCreditOutputRecordSuccess(creditBeans);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: e>>>>>"+e.toString());
                        callBack.getCreditOutputRecordFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
