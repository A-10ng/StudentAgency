package com.example.studentagency.mvp.model;

import android.util.Log;

import com.example.studentagency.bean.CreditBean;
import com.example.studentagency.http.ApiService;
import com.example.studentagency.http.RetrofitHelper;
import com.example.studentagency.mvp.model.Callback.AllRecordFragmentGetCreditAllRecordCallBack;
import com.example.studentagency.mvp.model.Callback.InputRecordFragmentGetCreditInputRecordCallBack;
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
public class InputRecordFragmentBaseModel implements IModel {

    private static final String TAG = "InputRecordFragmentBase";
    private ApiService apiService = RetrofitHelper.getInstance().getServer();

    public void getCreditInputRecord(InputRecordFragmentGetCreditInputRecordCallBack callBack){
        apiService.getCreditInputRecord(MyApp.userId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<CreditBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<CreditBean> creditBeans) {
                        Log.i(TAG, "onNext: creditBeans>>>>>"+creditBeans.toString());
                        callBack.getCreditInputRecordSuccess(creditBeans);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: e>>>>>"+e.toString());
                        callBack.getCreditInputRecordFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
