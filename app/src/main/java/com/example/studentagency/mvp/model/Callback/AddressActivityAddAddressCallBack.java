package com.example.studentagency.mvp.model.Callback;

import com.example.studentagency.bean.AddressBean;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/24
 * desc:
 */
public interface AddressActivityAddAddressCallBack {
    void addAddressSuccess(AddressBean addressBean);
    void addAddressFail();
}
