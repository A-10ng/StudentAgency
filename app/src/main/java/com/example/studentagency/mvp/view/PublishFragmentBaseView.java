package com.example.studentagency.mvp.view;

import com.example.studentagency.bean.IndentBean;

import java.util.List;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/19
 * desc:
 */
public interface PublishFragmentBaseView extends IView {
    void getPublishIndentsSuccess(List<IndentBean> indentBeanList);
    void getPublishIndentsFail();

    //取消别人未接的订单
    void cancelIndentNotTakenSuccess(Integer result);
    void cancelIndentNotTakenFail();

    //取消别人已接的订单
    void cancelIndentHadTakenSuccess(Integer result);
    void cancelIndentHadTakenFail();

    //删除已完成未评价的订单
    void deleteIndentNotCommentSuccess(Integer result);
    void deleteIndentNotCommentFail();

    //删除已完成已评价的订单
    void deleteIndentHadCommentSuccess(Integer result);
    void deleteIndentHadCommentFail();

    //确认送达
    void ensureAcceptGoodsSuccess(Integer result);
    void ensureAcceptGoodsFail();

    //评分
    void giveRatingSuccess(Integer result);
    void giveRatingFail();
}
