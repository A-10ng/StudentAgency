package com.example.studentagency.mvp.view;

import com.example.studentagency.bean.CommentBean;
import com.example.studentagency.bean.CreditBean;
import com.example.studentagency.bean.IndentBean;
import com.example.studentagency.bean.UserBean;

import java.util.List;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/01/05
 * desc:
 */
public interface IndentActivityBaseView extends IView {
    //获取发布方的信息
    void getPublishInfoSuccess(UserBean userBean);
    void getPusblishInfoFail();

    //获取该订单的信息
    void getIndentInfoSuccess(IndentBean indentBean);
    void getIndentInfoFail();

    //获取留言的信息
    void getCommentInfoSuccess(List<CommentBean> commentBeans);
    void getCommentInfoFail();

    //获取评价星数
    void getRatingStarsInfoSuccess(CreditBean creditBean);
    void getRatingStarsInfoFail();

    //接单
    void acceptIndentSuccess(Integer result);
    void acceptIndentFail();

    //留言
    void giveACommentSuccess(Integer result);
    void giveACommentFail();
}
