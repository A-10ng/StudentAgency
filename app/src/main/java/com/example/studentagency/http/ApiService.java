package com.example.studentagency.http;

import com.example.studentagency.bean.AddressBean;
import com.example.studentagency.bean.CreditBean;
import com.example.studentagency.bean.NewsBean;
import com.example.studentagency.bean.CommentBean;
import com.example.studentagency.bean.IndentBean;
import com.example.studentagency.bean.OtherPersonBean;
import com.example.studentagency.bean.UserBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    @POST("GetRatingStarsInfo")
    Observable<CreditBean> getRatingStarsInfo(@Query("indentId") int indentId);

    @POST("GetMoreCommentInfo")
    Observable<List<CommentBean>> getCommentInfoInIndentActivity(@Query("indentId") int indentId);

    @POST("AcceptIndentSuccess")
    Observable<Integer> acceptIndent(@Query("indentId") int indentId, @Query("acceptedTime") String acceptedTime);

    @POST("GiveACommentSuccess")
    Observable<Integer> giveAComment(@Query("indentId") int indentId, @Query("userId") int userId,
                                     @Query("content") String content, @Query("commentTime") String commentTime);

    @POST("GetMoreIndentData")
    Observable<List<IndentBean>> getIndentByType(@Query("type") int type);

    @POST("PublishIndentSuccess")
    Observable<Integer> publishIndent(@Query("publishId") int publishId, @Query("type") int type, @Query("price") float price,
                                      @Query("description") String description, @Query("address") String address, @Query("publishTime") String publishTime,
                                      @Query("planTime") String planTime);

    @POST("GiveACommentSuccess")
    Observable<Integer> getVerifyCode(@Query("phoneNum") String phoneNum);

    @POST("GiveACommentSuccess")
    Observable<Integer> register(@Query("username") String username, @Query("gender") int gender, @Query("password") String password,
                                 @Query("school") String school, @Query("phoneNum") String phoneNum);

    @POST("GetPublishInfo")
    Observable<UserBean> getPersonFragmentInfo(@Query("userId") int userId);

    @Multipart
    @POST("UploadAvatar")
    Observable<Integer> uploadAvatar(@Part MultipartBody.Part avatar, @Query("userId") int userId);

    @POST("GiveACommentSuccess")
    Observable<Integer> changePwd(@Query("userId") int userId, @Query("newPwd") String newPwd);

    @POST("GetPublishInfo")
    Observable<UserBean> getPersonalInfo(@Query("userId") int userId);

    @POST("GiveACommentSuccess")
    Observable<Integer> changePersonalInfo(@Query("userBean") UserBean userBean);

    @POST("GetVerifyStateSuccess")
    Observable<Integer> getVerifyState(@Query("userId") int userId);

    @Multipart
    @POST("UploadAvatar")
    Observable<Integer> uploadVerifyPic(@Part MultipartBody.Part verifyPic, @Query("userId") int userId);

    @GET("GetVerifyPic")
    Observable<String> getVerifyPic(@Query("userId") int userId);

    @GET("GetCreditScore")
    Observable<Integer> getCreditScore(@Query("userId") int userId);

    @GET("GetCreditAllRecord")
    Observable<List<CreditBean>> getCreditAllRecord(@Query("userId") int userId);

    @GET("GetCreditInputRecord")
    Observable<List<CreditBean>> getCreditInputRecord(@Query("userId") int userId);

    @GET("GetCreditOutputRecord")
    Observable<List<CreditBean>> getCreditOutputRecord(@Query("userId") int userId);

    @GET("GetPublishIndents")
    Observable<List<IndentBean>> getPublishIndents(@Query("userId") int userId);

    @GET("GetAcceptIndents")
    Observable<List<IndentBean>> getAcceptIndents(@Query("userId") int userId);

    @POST("GetVerifyStateSuccess")
    Observable<Integer> cancelIndentNotTaken(@Query("userId") int userId,
                                             @Query("indentId") int indentId,
                                             @Query("price") String price);

    @POST("GetVerifyStateSuccess")
    Observable<Integer> cancelIndentHadTaken(@Query("userId") int userId,
                                             @Query("indentId") int indentId,
                                             @Query("price") String price);

    @POST("GetVerifyStateSuccess")
    Observable<Integer> deleteIndentNotComment(@Query("userId") int userId,
                                               @Query("indentId") int indentId,
                                               @Query("price") String price);

    @POST("GetVerifyStateSuccess")
    Observable<Integer> deleteIndentHadComment(@Query("userId") int userId,
                                               @Query("indentId") int indentId,
                                               @Query("price") String price);

    @POST("GetVerifyStateSuccess")
    Observable<Integer> ensureAcceptGoods(@Query("userId") int userId,
                                          @Query("indentId") int indentId,
                                          @Query("price") String price);

    @POST("GetVerifyStateSuccess")
    Observable<Integer> cancelIndentHadTakenInAcpFragment(@Query("userId") int userId,
                                                          @Query("indentId") int indentId,
                                                          @Query("price") String price);

    @POST("GetVerifyStateSuccess")
    Observable<Integer> deleteIndentNotCommentInAcpFragment(@Query("userId") int userId,
                                                            @Query("indentId") int indentId,
                                                            @Query("price") String price);

    @POST("GetVerifyStateSuccess")
    Observable<Integer> deleteIndentHadCommentInAcpFragment(@Query("userId") int userId,
                                                            @Query("indentId") int indentId,
                                                            @Query("price") String price);

    @POST("GetVerifyStateSuccess")
    Observable<Integer> ensureAcceptGoodsInAcpFragment(@Query("userId") int userId,
                                                       @Query("indentId") int indentId,
                                                       @Query("price") String price);

    @POST("GetVerifyStateSuccess")
    Observable<Integer> giveRating(@Query("userId") int userId,
                                   @Query("increasement") int increasement,
                                   @Query("happenTime") String happenTime);

    @POST("GiveACommentSuccess")
    Observable<Integer> modifyPhoneNum(@Query("userId") int userId,
                                       @Query("newPhoneNum") String newPhoneNum,
                                       @Query("verifyCode") String verifyCode);

    @GET("GetAddress")
    Observable<List<AddressBean>> getAddress(@Query("userId") int userId);

    @POST("AddAddress")
    Observable<AddressBean> addAddress(@Query("userId") int userId,
                                       @Query("tag") String tag,
                                       @Query("address") String address);

    @POST("GiveACommentSuccess")
    Observable<Integer> editAddress(@Query("userId") int userId,
                                    @Query("addressId") int addressId,
                                    @Query("tag") String tag,
                                    @Query("address") String addresse);

    @POST("GiveACommentSuccess")
    Observable<Integer> deleteAddress(@Query("userId") int userId, @Query("addressId") int addressId);

    @GET("GetCurrentUserInfo")
    Observable<OtherPersonBean> getCurrentUserInfo(@Query("phoneNum") String phoneNum);
}
