package com.example.studentagency.mvp.model;

import android.util.Log;

import com.example.studentagency.bean.ResponseBean;
import com.example.studentagency.http.ApiService;
import com.example.studentagency.http.RetrofitHelper;
import com.example.studentagency.mvp.model.Callback.PublishFragmentCancelIndentHadTakenCallBack;
import com.example.studentagency.mvp.model.Callback.PublishFragmentCancelIndentNotTakenCallBack;
import com.example.studentagency.mvp.model.Callback.PublishFragmentDeleteIndentHadCommentCallBack;
import com.example.studentagency.mvp.model.Callback.PublishFragmentDeleteIndentNotCommentCallBack;
import com.example.studentagency.mvp.model.Callback.PublishFragmentEnsureAcceptGoodsCallBack;
import com.example.studentagency.mvp.model.Callback.PublishFragmentGetPublishIndentsCallBack;
import com.example.studentagency.mvp.model.Callback.PublishFragmentGiveRatingCallBack;
import com.example.studentagency.ui.activity.MyApp;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/19
 * desc:
 */
public class PublishFragmentBaseModel implements IModel {

    private static final String TAG = "PublishFragmentBaseMode";
    private ApiService apiService = RetrofitHelper.getInstance().getServer();

    public void getPublishIndents(PublishFragmentGetPublishIndentsCallBack callBack){
        apiService.getPublishIndents(MyApp.userId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBean indentBeanList) {
                        callBack.getPublishIndentsSuccess(indentBeanList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: e>>>>>"+e.toString());
                        callBack.getPublishIndentsFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void cancelIndentNotTaken(int indentId, String price, PublishFragmentCancelIndentNotTakenCallBack callBack) {
        apiService.cancelIndentNotTaken(indentId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBean result) {
                        Log.i(TAG, "onNext: result>>>>>"+result);
                        callBack.cancelIndentNotTakenSuccess(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: e>>>>>"+e.toString());
                        callBack.cancelIndentNotTakenFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void cancelIndentHadTaken(int indentId, String price, PublishFragmentCancelIndentHadTakenCallBack callBack) {
        apiService.cancelIndentHadTaken(indentId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBean result) {
                        Log.i(TAG, "onNext: result>>>>>"+result);
                        callBack.cancelIndentHadTakenSuccess(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: e>>>>>"+e.toString());
                        callBack.cancelIndentHadTakenFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void deleteIndentNotComment(int indentId, String price, PublishFragmentDeleteIndentNotCommentCallBack callBack) {
        apiService.deleteIndentNotComment(indentId,1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBean result) {
                        Log.i(TAG, "onNext: result>>>>>"+result);
                        callBack.deleteIndentNotCommentSuccess(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: e>>>>>"+e.toString());
                        callBack.deleteIndentNotCommentFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void deleteIndentHadComment(int indentId, String price, PublishFragmentDeleteIndentHadCommentCallBack callBack) {
        apiService.deleteIndentHadComment(indentId,1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBean result) {
                        Log.i(TAG, "onNext: result>>>>>"+result);
                        callBack.deleteIndentHadCommentSuccess(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: e>>>>>"+e.toString());
                        callBack.deleteIndentHadCommentFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void ensureAcceptGoods(String finishTime,int indentId, String price, PublishFragmentEnsureAcceptGoodsCallBack callBack) {
        apiService.ensureAcceptGoods(finishTime,indentId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBean result) {
                        Log.i(TAG, "onNext: result>>>>>"+result);
                        callBack.ensureAcceptGoodsSuccess(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: e>>>>>"+e.toString());
                        callBack.ensureAcceptGoodsFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void giveRating(int acceptId,int indentId,int increasement, String happenTime, PublishFragmentGiveRatingCallBack callBack) {
        apiService.giveRating(indentId,acceptId,increasement,happenTime)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBean result) {
                        Log.i(TAG, "onNext: result>>>>>"+result);
                        callBack.giveRatingSuccess(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: e>>>>>"+e.toString());
                        callBack.giveRatingFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
