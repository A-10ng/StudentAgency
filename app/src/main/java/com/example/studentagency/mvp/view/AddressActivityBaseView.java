package com.example.studentagency.mvp.view;

import com.example.studentagency.bean.AddressBean;

import java.util.List;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/24
 * desc:
 */
public interface AddressActivityBaseView extends IView {
    void getAddressSuccess(List<AddressBean> addressBeans);
    void getAddressFail();

    void addAddressSuccess(AddressBean addressBean);
    void addAddressFail();

    void editAddressSuccess(Integer result);
    void editAddressFail();

    void deleteAddressSuccess(Integer result);
    void deleteAddressFail();
}
