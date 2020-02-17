package com.example.studentagency.mvp.model;

import android.util.Log;

import com.example.studentagency.bean.UserBean;
import com.example.studentagency.http.ApiService;
import com.example.studentagency.http.RetrofitHelper;
import com.example.studentagency.mvp.model.Callback.PersonalInfoActivityChangePersonalInfoCallBack;
import com.example.studentagency.mvp.model.Callback.PersonalInfoActivityGetPersonalInfoCallBack;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/10
 * desc:
 */
public class PersonalInfoActivityBaseModel implements IModel {

    private static final String TAG = "PersonalInfoActivityBas";
    private ApiService apiService = RetrofitHelper.getInstance().getServer();

    public void getPersonalInfo(int userId, PersonalInfoActivityGetPersonalInfoCallBack callBack){
        apiService.getPersonalInfo(userId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<UserBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UserBean userBean) {
                        Log.i(TAG, "getPersonalInfo onNext: userBean>>>>>"+userBean.toString());
                        callBack.getPersonalInfoSuccess(userBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "getPersonalInfo onError: e>>>>>"+e.getMessage());
                        callBack.getPersonalInfoFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void changePersonalInfo(UserBean userBean, PersonalInfoActivityChangePersonalInfoCallBack callBack){
        apiService.changePersonalInfo(userBean)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer result) {
                        Log.i(TAG, "changePersonalInfo onNext: result>>>>>"+result);
                        callBack.changePersonalInfoSuccess(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "changePersonalInfo onError: e>>>>>"+e.getMessage());
                        callBack.changePersonalInfoFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
