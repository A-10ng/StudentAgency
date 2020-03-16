package com.example.studentagency.mvp.model;

import android.util.Log;

import com.example.studentagency.bean.ResponseBean;
import com.example.studentagency.http.ApiService;
import com.example.studentagency.http.RetrofitHelper;
import com.example.studentagency.mvp.model.Callback.AddressActivityAddAddressCallBack;
import com.example.studentagency.mvp.model.Callback.AddressActivityDeleteAddressCallBack;
import com.example.studentagency.mvp.model.Callback.AddressActivityEditAddressCallBack;
import com.example.studentagency.mvp.model.Callback.AddressActivityGetAddressCallBack;
import com.example.studentagency.ui.activity.MyApp;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/24
 * desc:
 */
public class AddressActivityBaseModel implements IModel {

    private static final String TAG = "AddressActivityBaseMode";
    private ApiService apiService = RetrofitHelper.getInstance().getServer();

    public void getAddress(AddressActivityGetAddressCallBack callBack){
        apiService.getAddress(MyApp.userId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBean addressBeans) {
                        callBack.getAddressSuccess(addressBeans);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: e>>>>>"+e.toString());
                        callBack.getAddressFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void addAddress(String tag, String address, AddressActivityAddAddressCallBack callBack) {
        apiService.addAddress(MyApp.userId,tag,address)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBean addressBean) {
                        callBack.addAddressSuccess(addressBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: e>>>>>"+e.toString());
                        callBack.addAddressFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void editAddress(int addressId, String tag, String address, AddressActivityEditAddressCallBack callBack) {
        apiService.editAddress(MyApp.userId,addressId,tag,address)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBean result) {
                        callBack.editAddressSuccess(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: e>>>>>"+e.toString());
                        callBack.editAddressFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void deleteAddress(int addressId, AddressActivityDeleteAddressCallBack callBack) {
        apiService.deleteAddress(MyApp.userId,addressId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBean result) {
                        Log.i(TAG, "onNext: result>>>>>"+result);
                        callBack.deleteAddressSuccess(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: e>>>>>"+e.toString());
                        callBack.deleteAddressFail();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
