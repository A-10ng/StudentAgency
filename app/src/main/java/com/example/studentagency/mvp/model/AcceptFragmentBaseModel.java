package com.example.studentagency.mvp.model;

import android.util.Log;

import com.example.studentagency.bean.IndentBean;
import com.example.studentagency.http.ApiService;
import com.example.studentagency.http.RetrofitHelper;
import com.example.studentagency.mvp.model.Callback.AcceptFragmentCancelIndentHadTakenCallBack;
import com.example.studentagency.mvp.model.Callback.AcceptFragmentDeleteIndentHadCommentCallBack;
import com.example.studentagency.mvp.model.Callback.AcceptFragmentDeleteIndentNotCommentCallBack;
import com.example.studentagency.mvp.model.Callback.AcceptFragmentEnsureAcceptGoodsCallBack;
import com.example.studentagency.mvp.model.Callback.AcceptFragmentGetAcceptIndentsCallBack;
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
public class AcceptFragmentBaseModel implements IModel {

    private static final String TAG = "AcceptFragmentBaseModel";
    private ApiService apiService = RetrofitHelper.getInstance().getServer();

    public void getAcceptIndents(AcceptFragmentGetAcceptIndentsCallBack callBack){
        apiService.getAcceptIndents(MyApp.userId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<IndentBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<IndentBean> indentBeanList) {
                        Log.i(TAG, "onNext: indentBeanList.size>>>>>"+indentBeanList.size());
                        callBack.getAcceptIndentsSuccess(indentBeanList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: e>>>>>"+e.toString());
                        callBack.getAcceptIndentsFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void cancelIndentHadTaken(int indentId, String price, AcceptFragmentCancelIndentHadTakenCallBack callBack) {
        apiService.cancelIndentHadTakenInAcpFragment(MyApp.userId,indentId,price)
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

    public void deleteIndentNotComment(int indentId, String price, AcceptFragmentDeleteIndentNotCommentCallBack callBack) {
        apiService.deleteIndentNotCommentInAcpFragment(MyApp.userId,indentId,price)
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

    public void deleteIndentHadComment(int indentId, String price, AcceptFragmentDeleteIndentHadCommentCallBack callBack) {
        apiService.deleteIndentHadCommentInAcpFragment(MyApp.userId,indentId,price)
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

    public void ensureAcceptGoods(int indentId, String price, AcceptFragmentEnsureAcceptGoodsCallBack callBack) {
        apiService.ensureAcceptGoodsInAcpFragment(MyApp.userId,indentId,price)
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
}
