package com.example.studentagency.http;

import com.example.studentagency.bean.ResponseBean;
import com.example.studentagency.bean.UserBean;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Response;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2019/10/14
 * desc:
 */
public interface ApiService {

    //通过密码登录
    @FormUrlEncoded
    @POST("loginByPassword")
    Observable<Response<ResponseBean>> loginByPassword(@Field("phoneNum") String phoneNum, @Field("password") String password);

    //返回验证码
    @GET("getVerifyCode/{phoneNum}")
    Observable<ResponseBean> getVerifyCode(@Path("phoneNum") String phoneNum);

    //通过验证码登录
    @FormUrlEncoded
    @POST("loginByVerifyCode")
    Observable<Response<ResponseBean>> loginByVerifyCode(@Field("phoneNum") String phoneNum, @Field("verificationCode") String password);

    //验证token的有效性
    @GET("tokenVerify")
    Observable<ResponseBean> tokenVerify(@Header("Authorization") String token);

    //修改手机号
    @FormUrlEncoded
    @POST("modifyPhoneNum")
    Observable<ResponseBean> modifyPhoneNum(@Field("userId") int userId,
                                            @Field("newPhoneNum") String newPhoneNum,
                                            @Field("verifyCode") String verifyCode);

    //----------------------------------------------------------------------------------------------

    //首页获取新闻数据
    @GET("api-user/news/")
    Observable<ResponseBean> getBannerData();

    //其他人主页返回用户数据
    @GET("api-user/user/getCurrentUserInfo/{phoneNum}")
    Observable<ResponseBean> getCurrentUserInfo(@Path("phoneNum") String phoneNum);

    //注册用户
    @FormUrlEncoded
    @POST("api-user/user/")
    Observable<ResponseBean> register(@Field("username") String username,
                                      @Field("gender") int gender,
                                      @Field("password") String password,
                                      @Field("school") String school,
                                      @Field("phoneNum") String phoneNum);

    //上传头像
    @FormUrlEncoded
    @Multipart
    @POST("api-user/user/uploadAvatar")
    Observable<ResponseBean> uploadAvatar(@Part MultipartBody.Part avatar, @Field("userId") int userId);

    //上传认证照片
    @FormUrlEncoded
    @Multipart
    @POST("api-user/user/uploadVerifyPic")
    Observable<ResponseBean> uploadVerifyPic(@Part MultipartBody.Part verifyPic, @Field("userId") int userId);

    //修改密码
    @FormUrlEncoded
    @PUT("api-user/user/")
    Observable<ResponseBean> changePwd(@Field("userId") int userId, @Field("newPwd") String newPwd);

    //修改个人信息
    @FormUrlEncoded
    @PUT("api-user/user/")
    Observable<ResponseBean> changePersonalInfo(@Field("userBean") UserBean userBean);

    //学生认证页面返回已上传图片
    @GET("api-user/user/getVerifyPic/{userId}")
    Observable<ResponseBean> getVerifyPic(@Path("userId") int userId);

    //订单详情页面返回发布方信息
    @GET("api-user/user/getUserData/{userId}")
    Observable<ResponseBean> getPublishInfoInIndentActivity(@Path("publishId") int publishId);

    //个人页面返回用户数据
    @GET("api-user/user/getUserData/{userId}")
    Observable<ResponseBean> getPersonFragmentInfo(@Path("userId") int userId);

    //修改个人信息页面返回用户信息
    @GET("api-user/user/getUserData/{userId}")
    Observable<ResponseBean> getPersonalInfo(@Path("userId") int userId);

    //学生认证页面返回认证状态
    @GET("api-user/user/getVerifyState/{userId}")
    Observable<ResponseBean> getVerifyState(@Path("userId") int userId);

    //信誉积分页面返回信誉积分
    @GET("api-user/user/getCreditScore/{userId}")
    Observable<ResponseBean> getCreditScore(@Path("userId") int userId);

    //充值金额
    @FormUrlEncoded
    @PUT("api-user/user/")
    Observable<ResponseBean> recharge(@Query("userId") int userId, @Field("recharge") float recharge);

    //地址管理页面返回地址数据
    @GET("api-user/address/getAddress/{userId}")
    Observable<ResponseBean> getAddress(@Path("userId") int userId);

    //新增地址
    @FormUrlEncoded
    @POST("api-user/address/addAddress")
    Observable<ResponseBean> addAddress(@Field("userId") int userId,
                                        @Field("tag") String tag,
                                        @Field("address") String address);

    //修改地址
    @FormUrlEncoded
    @PUT("api-user/address/")
    Observable<ResponseBean> editAddress(@Field("userId") int userId,
                                         @Field("addressId") int addressId,
                                         @Field("tag") String tag,
                                         @Field("address") String address);

    //删除地址
    @DELETE("api-user/address/{addressId}")
    Observable<ResponseBean> deleteAddress(@Path("addressId") int addressId);

    //----------------------------------------------------------------------------------------------

    //首页获取订单数据
    @GET("api-indent/indent/getIndentDataByPhoneNum/{phoneNum}")
    Observable<ResponseBean> getIndentData(@Path("phoneNum") String phoneNum);

    //根据类型返回分类订单数据
    @GET("api-indent/indent/getIndentByType")
    Observable<ResponseBean> getIndentByType(@Query("type") int type, @Query("userId") int userId);

    //订单详情页面返回订单数据
    @GET("api-indent/indent/getIndentDataById/{indentId}")
    Observable<ResponseBean> getIndentInfoInIndentActivity(@Path("indentId") int indentId);

    //个人订单页面返回发布订单数据
    @GET("api-indent/indent/getPublishIndents/{userId}")
    Observable<ResponseBean> getPublishIndents(@Path("userId") int userId);

    //个人订单页面返回接收订单数据
    @GET("api-indent/indent/getAcceptIndents/{userId}")
    Observable<ResponseBean> getAcceptIndents(@Path("userId") int userId);

    //发布订单
    @FormUrlEncoded
    @POST("api-indent/indent/")
    Observable<ResponseBean> publishIndent(@Field("publishId") int publishId,
                                           @Field("type") int type,
                                           @Field("price") float price,
                                           @Field("description") String description,
                                           @Field("address") String address,
                                           @Field("publishTime") String publishTime,
                                           @Field("planTime") String planTime);

    //发布方取消未接订单
    @FormUrlEncoded
    @PUT("api-indent/indent/cancelIndent")
    Observable<ResponseBean> cancelIndentNotTaken(@Field("indentId") int indentId);

    //发布方取消已接订单
    @FormUrlEncoded
    @PUT("api-indent/indent/cancelIndent")
    Observable<ResponseBean> cancelIndentHadTaken(@Field("indentId") int indentId);

    //发布方删除未评价订单
    @FormUrlEncoded
    @PUT("api-indent/indent/")
    Observable<ResponseBean> deleteIndentNotComment(@Field("indentId") int indentId);

    //发布方删除已评价订单
    @FormUrlEncoded
    @PUT("api-indent/indent/")
    Observable<ResponseBean> deleteIndentHadComment(@Field("indentId") int indentId);

    //发布方确认送达
    @FormUrlEncoded
    @PUT("api-indent/indent/finishIndent/")
    Observable<ResponseBean> ensureAcceptGoods(@Field("indentId") int indentId);

    //订单详情页面返回留言数据
    @GET("api-indent/comment/getCommentInfo/{indentId}")
    Observable<ResponseBean> getCommentInfoInIndentActivity(@Path("indentId") int indentId);

    //留言
    @FormUrlEncoded
    @POST("api-indent/comment/")
    Observable<ResponseBean> giveAComment(@Field("indentId") int indentId,
                                          @Field("userId") int userId,
                                          @Field("content") String content,
                                          @Field("commentTime") String commentTime);

    //----------------------------------------------------------------------------------------------

    //接单
    @FormUrlEncoded
    @POST("api-indent/accept/")
    Observable<ResponseBean> acceptIndent(@Field("acceptId") int acceptId,
                                          @Field("indentId") int indentId,
                                          @Field("acceptedTime") String acceptedTime);

    //接收方取消已接订单
    @FormUrlEncoded
    @PUT("api-indent/accept/cancelAccept")
    Observable<ResponseBean> cancelIndentHadTakenInAcpFragment(@Field("indentId") int indentId);

    //接收方删除未评价订单
    @FormUrlEncoded
    @PUT("api-indent/accept/deleteAccept")
    Observable<ResponseBean> deleteIndentNotCommentInAcpFragment(@Field("indentId") int indentId);

    //接收方删除已评价订单
    @FormUrlEncoded
    @PUT("api-indent/accept/deleteAccept")
    Observable<ResponseBean> deleteIndentHadCommentInAcpFragment(@Field("indentId") int indentId);

    //接收方确认送达
    @FormUrlEncoded
    @PUT("api-indent/accept/finishedAccept")
    Observable<ResponseBean> ensureAcceptGoodsInAcpFragment(@Field("deliveryTime") String deliveryTime,
                                                            @Field("indentId") int indentId);

    //订单详情页面返回评价数据
    @GET("api-indent/credit/getRatingStarsByIndent")
    Observable<ResponseBean> getRatingStarsInfo(@Query("userId") int userId, @Query("indentId") int indentId);

    //信誉积分页面返回信誉积分所有记录
    @GET("api-indent/credit/getCreditAllRecord/{userId}")
    Observable<ResponseBean> getCreditAllRecord(@Path("userId") int userId);

    //信誉积分页面返回信誉积分收入记录
    @GET("api-indent/credit/ getCreditInputRecord/{userId}")
    Observable<ResponseBean> getCreditInputRecord(@Path("userId") int userId);

    //信誉积分页面返回信誉积分支出记录
    @GET("api-indent/credit/getCreditOutputRecord/{userId}")
    Observable<ResponseBean> getCreditOutputRecord(@Path("userId") int userId);

    //发布方发布评价
    @FormUrlEncoded
    @POST("api-indent/credit/star/")
    Observable<ResponseBean> giveRating(@Field("indentId") int indentId,
                                        @Field("userId") int userId,
                                        @Field("increasement") int increasement,
                                        @Field("happenTime") String happenTime);
}
