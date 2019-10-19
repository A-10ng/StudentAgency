package com.example.studentagency.mvp.model;

import com.example.studentagency.bean.Cat;
import com.example.studentagency.http.ApiService;
import com.example.studentagency.http.RetrofitHelper;
import com.example.studentagency.mvp.model.Callback.LoginCallBack;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2019/10/14
 * desc:
 */
public class LoginBaseModel implements IModel {

    public void login(String studentId,String password,final LoginCallBack callBack) {
        Cat cat = new Cat();
        cat.setCatId("001");
        cat.setCatName("LongSh1z");
        cat.setType("橘猫");
        cat.setLevel(6);
        cat.setValue(460);
//        callBack.OnFailed();
        callBack.OnSuccess(cat);
//        ApiService apiService = RetrofitHelper.getInstance().getServer();
//        apiService.login(studentId,password)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Cat>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(Cat cat) {
//                        callBack.OnSuccess(cat);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        callBack.OnFailed();
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
    }
}
