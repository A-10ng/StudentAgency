package com.example.studentagency.mvp.view;

import com.example.studentagency.bean.ResponseBean;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/24
 * desc:
 */
public interface AddressActivityBaseView extends IView {
//    void getAddressSuccess(List<AddressBean> addressBeans);
    void getAddressSuccess(ResponseBean responseBean);
    void getAddressFail();

//    void addAddressSuccess(AddressBean addressBean);
    void addAddressSuccess(ResponseBean responseBean);
    void addAddressFail();

//    void editAddressSuccess(Integer result);
    void editAddressSuccess(ResponseBean responseBean);
    void editAddressFail();

//    void deleteAddressSuccess(Integer result);
    void deleteAddressSuccess(ResponseBean responseBean);
    void deleteAddressFail();
}
