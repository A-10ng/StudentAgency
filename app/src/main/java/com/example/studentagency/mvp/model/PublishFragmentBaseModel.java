package com.example.studentagency.mvp.model;

import android.util.Log;

import com.example.studentagency.bean.IndentBean;
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

import java.util.List;

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
                .subscribe(new Observer<List<IndentBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<IndentBean> indentBeanList) {
                        Log.i(TAG, "onNext: indentBeanList.size>>>>>"+indentBeanList.size());
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
        apiService.cancelIndentNotTaken(MyApp.userId,indentId,price)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer result) {
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
        apiService.cancelIndentHadTaken(MyApp.userId,indentId,price)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer result) {
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
        apiService.deleteIndentNotComment(MyApp.userId,indentId,price)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer result) {
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
        apiService.deleteIndentHadComment(MyApp.userId,indentId,price)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer result) {
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

    public void ensureAcceptGoods(int indentId, String price, PublishFragmentEnsureAcceptGoodsCallBack callBack) {
        apiService.ensureAcceptGoods(MyApp.userId,indentId,price)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer result) {
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

    public void giveRating(int increasement, String happenTime, PublishFragmentGiveRatingCallBack callBack) {
        apiService.giveRating(MyApp.userId,increasement,happenTime)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer result) {
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
