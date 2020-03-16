package com.example.studentagency.mvp.model.Callback;

import com.example.studentagency.bean.ResponseBean;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/24
 * desc:
 */
public interface AddressActivityDeleteAddressCallBack {
    void deleteAddressSuccess(ResponseBean responseBean);
    void deleteAddressFail();
}
