package com.example.studentagency.http;

import com.example.studentagency.bean.NewsBean;
import com.example.studentagency.bean.CommentBean;
import com.example.studentagency.bean.IndentBean;
import com.example.studentagency.bean.UserBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2019/10/14
 * desc:
 */
public interface ApiService {

    @POST("GiveACommentSuccess")
    Observable<Integer> loginByPassword(@Query("phoneNum") String phoneNum, @Query("password") String password);

    @POST("GiveACommentSuccess")
    Observable<Integer> loginByVerifyCode(@Query("phoneNum") String phoneNum, @Query("verificationCode") String password);

    @GET("GetBannerData")
    Observable<List<NewsBean>> getBannerData();

    @GET("GetMoreIndentData")
    Observable<List<IndentBean>> getIndentData();

    @GET("GetPublishInfo")
    Observable<UserBean> getPublishInfoInIndentActivity();

    @POST("GetIndentInfo")
    Observable<IndentBean> getIndentInfoInIndentActivity(@Query("indentId") int indentId);

    @POST("GetMoreCommentInfo")
    Observable<List<CommentBean>> getCommentInfoInIndentActivity(@Query("indentId") int indentId);

    @POST("AcceptIndentFail")
    Observable<Integer> acceptIndent(@Query("indentId") int indentId, @Query("acceptedTime") String acceptedTime);

    @POST("GiveACommentSuccess")
    Observable<Integer> giveAComment(@Query("indentId") int indentId, @Query("userId") int userId,
                                     @Query("content") String content, @Query("commentTime") String commentTime);

    @POST("GetMoreIndentData")
    Observable<List<IndentBean>> getIndentByType(@Query("type") int type);

    @POST("PublishIndentFail")
    Observable<Integer> publishIndent(@Query("publishId") int publishId,@Query("type") int type,@Query("price") float price,
                                      @Query("description") String description,@Query("address") String address,@Query("publishTime") String publishTime,
                                      @Query("planTime") String planTime);

    @POST("GiveACommentSuccess")
    Observable<Integer> getVerifyCode(@Query("phoneNum") String phoneNum);
}
