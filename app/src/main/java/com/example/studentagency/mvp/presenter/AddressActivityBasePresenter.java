package com.example.studentagency.mvp.presenter;

import com.example.studentagency.bean.ResponseBean;
import com.example.studentagency.mvp.model.AddressActivityBaseModel;
import com.example.studentagency.mvp.model.Callback.AddressActivityAddAddressCallBack;
import com.example.studentagency.mvp.model.Callback.AddressActivityDeleteAddressCallBack;
import com.example.studentagency.mvp.model.Callback.AddressActivityEditAddressCallBack;
import com.example.studentagency.mvp.model.Callback.AddressActivityGetAddressCallBack;
import com.example.studentagency.mvp.view.AddressActivityBaseView;

import java.lang.ref.WeakReference;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/24
 * desc:
 */
public class AddressActivityBasePresenter extends IPresenter {

    public AddressActivityBasePresenter(AddressActivityBaseView view) {
        this.mIModel = new AddressActivityBaseModel();
        this.mViewRef = new WeakReference<>(view);
    }

    public void getAddress(){
        if (null != mViewRef && null != mViewRef.get() && null != mIModel){
            ((AddressActivityBaseModel)mIModel).getAddress(new AddressActivityGetAddressCallBack() {
                @Override
                public void getAddressSuccess(ResponseBean responseBean) {
                    if (null != mViewRef.get()){
                        ((AddressActivityBaseView)mViewRef.get()).getAddressSuccess(responseBean);
                    }
                }

                @Override
                public void getAddressFail() {
                    if (null != mViewRef.get()){
                        ((AddressActivityBaseView)mViewRef.get()).getAddressFail();
                    }
                }
            });
        }
    }

    public void addAddress(String tag, String address) {
        if (null != mViewRef && null != mViewRef.get() && null != mIModel){
            ((AddressActivityBaseModel)mIModel).addAddress(tag,address,new AddressActivityAddAddressCallBack() {
                @Override
                public void addAddressSuccess(ResponseBean responseBean) {
                    if (null != mViewRef.get()){
                        ((AddressActivityBaseView)mViewRef.get()).addAddressSuccess(responseBean);
                    }
                }

                @Override
                public void addAddressFail() {
                    if (null != mViewRef.get()){
                        ((AddressActivityBaseView)mViewRef.get()).addAddressFail();
                    }
                }
            });
        }
    }

    public void editAddress(int addressId, String tag, String address) {
        if (null != mViewRef && null != mViewRef.get() && null != mIModel){
            ((AddressActivityBaseModel)mIModel).editAddress(addressId,tag,address,new AddressActivityEditAddressCallBack() {
                @Override
                public void editAddressSuccess(ResponseBean responseBean) {
                    if (null != mViewRef.get()){
                        ((AddressActivityBaseView)mViewRef.get()).editAddressSuccess(responseBean);
                    }
                }

                @Override
                public void editAddressFail() {
                    if (null != mViewRef.get()){
                        ((AddressActivityBaseView)mViewRef.get()).editAddressFail();
                    }
                }
            });
        }
    }

    public void deleteAddress(int addressId) {
        if (null != mViewRef && null != mViewRef.get() && null != mIModel){
            ((AddressActivityBaseModel)mIModel).deleteAddress(addressId,new AddressActivityDeleteAddressCallBack() {
                @Override
                public void deleteAddressSuccess(ResponseBean responseBean) {
                    if (null != mViewRef.get()){
                        ((AddressActivityBaseView)mViewRef.get()).deleteAddressSuccess(responseBean);
                    }
                }

                @Override
                public void deleteAddressFail() {
                    if (null != mViewRef.get()){
                        ((AddressActivityBaseView)mViewRef.get()).deleteAddressFail();
                    }
                }
            });
        }
    }
}
