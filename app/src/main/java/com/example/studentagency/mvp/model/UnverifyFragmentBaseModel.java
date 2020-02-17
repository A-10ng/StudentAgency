package com.example.studentagency.mvp.model;

import android.util.Log;

import com.example.studentagency.http.ApiService;
import com.example.studentagency.http.RetrofitHelper;
import com.example.studentagency.mvp.model.Callback.UnverifyFragmentUploadVerifyPicCallBack;
import com.example.studentagency.ui.activity.MyApp;

import java.io.File;

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
 * time：2020/02/17
 * desc:
 */
public class UnverifyFragmentBaseModel implements IModel {

    private static final String TAG = "UnverifyFragmentBaseMod";
    private ApiService apiService = RetrofitHelper.getInstance().getServer();

    public void uploadVerifyPic(File verifyPicFile, UnverifyFragmentUploadVerifyPicCallBack callBack) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),verifyPicFile);
        MultipartBody.Part verifyPic = MultipartBody.Part.createFormData("VerifyPic",verifyPicFile.getName(),requestBody);
        apiService.uploadVerifyPic(verifyPic, MyApp.userId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer result) {
                        Log.i(TAG, "onNext: result>>>>>"+result);
                        callBack.uploadVerifyPicSuccess(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: ");
                        callBack.uploadVerifyPicFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
