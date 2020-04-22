package com.example.studentagency.mvp.model;

import android.util.Log;

import com.example.studentagency.bean.ResponseBean;
import com.example.studentagency.bean.UserBean;
import com.example.studentagency.http.ApiService;
import com.example.studentagency.http.RetrofitHelper;
import com.example.studentagency.mvp.model.Callback.PersonalInfoActivityChangePersonalInfoCallBack;
import com.example.studentagency.mvp.model.Callback.PersonalInfoActivityGetPersonalInfoCallBack;
import com.google.gson.Gson;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import cn.jmessage.support.qiniu.android.utils.Json;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

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
                .subscribe(new Observer<ResponseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBean userBean) {
                        callBack.getPersonalInfoSuccess(userBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "getPersonalInfo onError: e>>>>>"+e.getMessage() + "---localMessage---"+ e.getLocalizedMessage());
                        callBack.getPersonalInfoFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void changePersonalInfo(UserBean userBean, PersonalInfoActivityChangePersonalInfoCallBack callBack){
//        String jsonStr = new Gson().toJson(userBean);
//        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),jsonStr);
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"),jsonStr);
        apiService.changePersonalInfo(
                userBean.getUserId(),
                userBean.getUsername(),
                userBean.getGender(),
                userBean.getSchool())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBean result) {
                        Log.i(TAG, "changePersonalInfo onNext: result>>>>>"+result);
                        callBack.changePersonalInfoSuccess(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "changePersonalInfo onError: e>>>>>"+e.getMessage() + "---localMessage---"+ e.getLocalizedMessage());
                        callBack.changePersonalInfoFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
