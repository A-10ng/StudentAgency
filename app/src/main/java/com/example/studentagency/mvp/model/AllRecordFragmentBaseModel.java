package com.example.studentagency.mvp.model;

import android.util.Log;

import com.example.studentagency.bean.CreditBean;
import com.example.studentagency.http.ApiService;
import com.example.studentagency.http.RetrofitHelper;
import com.example.studentagency.mvp.model.Callback.AllRecordFragmentGetCreditAllRecordCallBack;
import com.example.studentagency.ui.activity.MyApp;

import java.util.List;

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
public class AllRecordFragmentBaseModel implements IModel {

    private static final String TAG = "AllRecordFragmentBaseMo";
    private ApiService apiService = RetrofitHelper.getInstance().getServer();

    public void getCreditAllRecord(AllRecordFragmentGetCreditAllRecordCallBack callBack){
        apiService.getCreditAllRecord(MyApp.userId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<CreditBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<CreditBean> creditBeans) {
                        Log.i(TAG, "onNext: creditBeans>>>>>"+creditBeans.toString());
                        callBack.getCreditAllRecordSuccess(creditBeans);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: e>>>>>"+e.toString());
                        callBack.getCreditAllRecordFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
