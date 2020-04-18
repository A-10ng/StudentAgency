package com.example.studentagency.mvp.model;

import android.util.Log;

import com.example.studentagency.bean.ResponseBean;
import com.example.studentagency.http.ApiService;
import com.example.studentagency.http.RetrofitHelper;
import com.example.studentagency.mvp.model.Callback.IndentActivityAcceptIndentCallBack;
import com.example.studentagency.mvp.model.Callback.IndentActivityGetCommentInfoCallBack;
import com.example.studentagency.mvp.model.Callback.IndentActivityGetIndentInfoCallBack;
import com.example.studentagency.mvp.model.Callback.IndentActivityGetPublishInfoCallBack;
import com.example.studentagency.mvp.model.Callback.IndentActivityGetRatingStarsCallBack;
import com.example.studentagency.mvp.model.Callback.IndentActivityGiveACommentCallBack;
import com.example.studentagency.ui.activity.MyApp;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/01/05
 * desc:
 */
public class IndentActivityBaseModel implements IModel {

    private static final String TAG = "IndentActivityBaseModel";
    private ApiService apiService = RetrofitHelper.getInstance().getServer();

    public void getPublishInfo(int publishId, final IndentActivityGetPublishInfoCallBack callBack) {
        apiService.getPublishInfoInIndentActivity(publishId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBean userBean) {
                        callBack.onGetPublishInfoSuccess(userBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "getPublishInfo: onError e>>>>>" + e.getMessage());
                        callBack.onGetPublishInfoFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void getIndentInfo(int indentId, final IndentActivityGetIndentInfoCallBack callBack){
        apiService.getIndentInfoInIndentActivity(indentId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBean indentBean) {
                        callBack.onGetIndentInfoSuccess(indentBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "getIndentInfo: onError e>>>>>"+e.getMessage());
                        callBack.onGetIndentInfoFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getCommentInfo(int indentId, final IndentActivityGetCommentInfoCallBack callBack){
        apiService.getCommentInfoInIndentActivity(indentId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBean commentBeans) {
                        callBack.onGetCommentInfoSuccess(commentBeans);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: e>>>>>"+e.getMessage());
                        callBack.onGetCommentInfoFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void acceptIndent(int acceptId,int indentId, String acceptedTime, final IndentActivityAcceptIndentCallBack callBack){
        apiService.acceptIndent(acceptId,indentId,acceptedTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBean result) {
                        callBack.onAcceptIndentSuccess(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "acceptIndent onError: e>>>>>"+e.getMessage());
                        callBack.onAcceptIndentFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void giveAComment(int indentId, int userId, String content, String commentTime,
                             final IndentActivityGiveACommentCallBack callBack){
        apiService.giveAComment(indentId,userId,content,commentTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBean result) {
                        callBack.onGiveACommentSuccess(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "giveAComment onError: e>>>>>"+e.getMessage());
                        callBack.onGiveACommentFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getRatingStarsInfo(int indentId, IndentActivityGetRatingStarsCallBack callBack) {
        apiService.getRatingStarsInfo(MyApp.userId,indentId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBean creditBean) {
                        callBack.getRatingStarsInfoSuccess(creditBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: e>>>>>"+e.toString());
                        callBack.getRatingStarsInfoFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
