package com.example.studentagency.mvp.view;

import com.example.studentagency.bean.ResponseBean;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/19
 * desc:
 */
public interface AcceptFragmentBaseView extends IView {
//    void getAcceptIndentsSuccess(List<IndentBean> indentBeanList);
    void getAcceptIndentsSuccess(ResponseBean responseBean);
    void getAcceptIndentsFail();

    //取消别人已接的订单
//    void cancelIndentHadTakenSuccess(Integer result);
    void cancelIndentHadTakenSuccess(ResponseBean responseBean);
    void cancelIndentHadTakenFail();

    //删除已完成未评价的订单
//    void deleteIndentNotCommentSuccess(Integer result);
    void deleteIndentNotCommentSuccess(ResponseBean responseBean);
    void deleteIndentNotCommentFail();

    //删除已完成已评价的订单
//    void deleteIndentHadCommentSuccess(Integer result);
    void deleteIndentHadCommentSuccess(ResponseBean responseBean);
    void deleteIndentHadCommentFail();

    //确认送达
//    void ensureAcceptGoodsSuccess(Integer result);
    void ensureAcceptGoodsSuccess(ResponseBean responseBean);
    void ensureAcceptGoodsFail();
}
