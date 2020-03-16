package com.example.studentagency.mvp.model.Callback;

import com.example.studentagency.bean.ResponseBean;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/05
 * desc:
 */
public interface PersonFragmentUploadAvatarCallBack {
    void uploadAvatarSuccess(ResponseBean responseBean);
    void uploadAvatarFail();
}
