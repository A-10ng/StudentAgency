package com.example.studentagency.http;

import com.example.studentagency.bean.ResponseBean;
import com.example.studentagency.bean.UserBean;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Response;
import retrofit2.http.DELETE;
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

    /**
     * @POST("GiveACommentSuccess") //    Observable<ResponseBean> loginByPassword(@Query("phoneNum") String phoneNum, @Query("password") String password);
     * //
     * //    @POST("GiveACommentSuccess")
     * //    Observable<ResponseBean> loginByVerifyCode(@Query("phoneNum") String phoneNum, @Query("verificationCode") String password);
     * //
     * //    @GET("GetBannerData")
     * //    Observable<List<NewsBean>> getBannerData();
     * //
     * ////    @GET("GetMoreIndentData")
     * //    @GET("indent/getIndentsByUser/{userid}")
     * ////    Observable<List<IndentBean>> getIndentData(@Path("userid") int userid);
     * //    Observable<ResponseBean> getIndentData(@Path("userid") int userid);
     * //
     * //    @GET("GetPublishInfo")
     * //    Observable<UserBean> getPublishInfoInIndentActivity();
     * //
     * //    @POST("GetIndentInfo")
     * //    Observable<IndentBean> getIndentInfoInIndentActivity(@Query("indentId") int indentId);
     * //
     * //    @POST("GetRatingStarsInfo")
     * //    Observable<CreditBean> getRatingStarsInfo(@Query("indentId") int indentId);
     * //
     * //    @POST("GetMoreCommentInfo")
     * //    Observable<List<CommentBean>> getCommentInfoInIndentActivity(@Query("indentId") int indentId);
     * //
     * //    @POST("AcceptIndentSuccess")
     * //    Observable<Integer> acceptIndent(@Query("indentId") int indentId, @Query("acceptedTime") String acceptedTime);
     * //
     * //    @POST("GiveACommentSuccess")
     * //    Observable<Integer> giveAComment(@Query("indentId") int indentId, @Query("userId") int userId,
     * //                                     @Query("content") String content, @Query("commentTime") String commentTime);
     * //
     * //    @POST("GetMoreIndentData")
     * //    Observable<List<IndentBean>> getIndentByType(@Query("type") int type);
     * //
     * //    @POST("PublishIndentSuccess")
     * //    Observable<Integer> publishIndent(@Query("publishId") int publishId, @Query("type") int type, @Query("price") float price,
     * //                                      @Query("description") String description, @Query("address") String address, @Query("publishTime") String publishTime,
     * //                                      @Query("planTime") String planTime);
     * //
     * //    @POST("GiveACommentSuccess")
     * //    Observable<Integer> getVerifyCode(@Query("phoneNum") String phoneNum);
     * //
     * //    @POST("GiveACommentSuccess")
     * //    Observable<Integer> register(@Query("username") String username, @Query("gender") int gender, @Query("password") String password,
     * //                                 @Query("school") String school, @Query("phoneNum") String phoneNum);
     * //
     * //    @POST("GetPublishInfo")
     * //    Observable<UserBean> getPersonFragmentInfo(@Query("userId") int userId);
     * //
     * //    @Multipart
     * //    @POST("UploadAvatar")
     * //    Observable<Integer> uploadAvatar(@Part MultipartBody.Part avatar, @Query("userId") int userId);
     * //
     * //    @POST("GiveACommentSuccess")
     * //    Observable<Integer> changePwd(@Query("userId") int userId, @Query("newPwd") String newPwd);
     * //
     * //    @POST("GetPublishInfo")
     * //    Observable<UserBean> getPersonalInfo(@Query("userId") int userId);
     * //
     * //    @POST("GiveACommentSuccess")
     * //    Observable<Integer> changePersonalInfo(@Query("userBean") UserBean userBean);
     * //
     * //    @POST("GetVerifyStateSuccess")
     * //    Observable<Integer> getVerifyState(@Query("userId") int userId);
     * //
     * //    @Multipart
     * //    @POST("UploadAvatar")
     * //    Observable<Integer> uploadVerifyPic(@Part MultipartBody.Part verifyPic, @Query("userId") int userId);
     * //
     * //    @GET("GetVerifyPic")
     * //    Observable<String> getVerifyPic(@Query("userId") int userId);
     * //
     * //    @GET("GetCreditScore")
     * //    Observable<Integer> getCreditScore(@Query("userId") int userId);
     * //
     * //    @GET("GetCreditAllRecord")
     * //    Observable<List<CreditBean>> getCreditAllRecord(@Query("userId") int userId);
     * //
     * //    @GET("GetCreditInputRecord")
     * //    Observable<List<CreditBean>> getCreditInputRecord(@Query("userId") int userId);
     * //
     * //    @GET("GetCreditOutputRecord")
     * //    Observable<List<CreditBean>> getCreditOutputRecord(@Query("userId") int userId);
     * //
     * //    @GET("GetPublishIndents")
     * //    Observable<List<IndentBean>> getPublishIndents(@Query("userId") int userId);
     * //
     * //    @GET("GetAcceptIndents")
     * //    Observable<List<IndentBean>> getAcceptIndents(@Query("userId") int userId);
     * //
     * //    @POST("GetVerifyStateSuccess")
     * //    Observable<Integer> cancelIndentNotTaken(@Query("userId") int userId,
     * //                                             @Query("indentId") int indentId,
     * //                                             @Query("price") String price);
     * //
     * //    @POST("GetVerifyStateSuccess")
     * //    Observable<Integer> cancelIndentHadTaken(@Query("userId") int userId,
     * //                                             @Query("indentId") int indentId,
     * //                                             @Query("price") String price);
     * //
     * //    @POST("GetVerifyStateSuccess")
     * //    Observable<Integer> deleteIndentNotComment(@Query("userId") int userId,
     * //                                               @Query("indentId") int indentId,
     * //                                               @Query("price") String price);
     * //
     * //    @POST("GetVerifyStateSuccess")
     * //    Observable<Integer> deleteIndentHadComment(@Query("userId") int userId,
     * //                                               @Query("indentId") int indentId,
     * //                                               @Query("price") String price);
     * //
     * //    @POST("GetVerifyStateSuccess")
     * //    Observable<Integer> ensureAcceptGoods(@Query("userId") int userId,
     * //                                          @Query("indentId") int indentId,
     * //                                          @Query("price") String price);
     * //
     * //    @POST("GetVerifyStateSuccess")
     * //    Observable<Integer> cancelIndentHadTakenInAcpFragment(@Query("userId") int userId,
     * //                                                          @Query("indentId") int indentId,
     * //                                                          @Query("price") String price);
     * //
     * //    @POST("GetVerifyStateSuccess")
     * //    Observable<Integer> deleteIndentNotCommentInAcpFragment(@Query("userId") int userId,
     * //                                                            @Query("indentId") int indentId,
     * //                                                            @Query("price") String price);
     * //
     * //    @POST("GetVerifyStateSuccess")
     * //    Observable<Integer> deleteIndentHadCommentInAcpFragment(@Query("userId") int userId,
     * //                                                            @Query("indentId") int indentId,
     * //                                                            @Query("price") String price);
     * //
     * //    @POST("GetVerifyStateSuccess")
     * //    Observable<Integer> ensureAcceptGoodsInAcpFragment(@Query("userId") int userId,
     * //                                                       @Query("indentId") int indentId,
     * //                                                       @Query("price") String price);
     * //
     * //    @POST("GetVerifyStateSuccess")
     * //    Observable<Integer> giveRating(@Query("userId") int userId,
     * //                                   @Query("increasement") int increasement,
     * //                                   @Query("happenTime") String happenTime);
     * //
     * //    @POST("GiveACommentSuccess")
     * //    Observable<Integer> modifyPhoneNum(@Query("userId") int userId,
     * //                                       @Query("newPhoneNum") String newPhoneNum,
     * //                                       @Query("verifyCode") String verifyCode);
     * //
     * //    @GET("GetAddress")
     * //    Observable<List<AddressBean>> getAddress(@Query("userId") int userId);
     * //
     * //    @POST("AddAddress")
     * //    Observable<AddressBean> addAddress(@Query("userId") int userId,
     * //                                       @Query("tag") String tag,
     * //                                       @Query("address") String address);
     * //
     * //    @POST("GiveACommentSuccess")
     * //    Observable<Integer> editAddress(@Query("userId") int userId,
     * //                                    @Query("addressId") int addressId,
     * //                                    @Query("tag") String tag,
     * //                                    @Query("address") String addresse);
     * //
     * //    @POST("GiveACommentSuccess")
     * //    Observable<Integer> deleteAddress(@Query("userId") int userId, @Query("addressId") int addressId);
     * //
     * //    @GET("GetCurrentUserInfo")
     * //    Observable<OtherPersonBean> getCurrentUserInfo(@Query("phoneNum") String phoneNum);
     * //
     * //    @GET("CookieVerify")
     * Observable<ResponseBean> cookieVerify(@Query("cookie") String cookie);
     **/
    //通过密码登录
    @POST("loginByPassword")
    Observable<Response<ResponseBean>> loginByPassword(@Query("phoneNum") String phoneNum, @Query("password") String password);

    //返回验证码
    @GET("getVerifyCode/{phoneNum}")
    Observable<ResponseBean> getVerifyCode(@Path("phoneNum") String phoneNum);

    //通过验证码登录
    @POST("loginByVerifyCode")
    Observable<Response<ResponseBean>> loginByVerifyCode(@Query("phoneNum") String phoneNum, @Query("verificationCode") String password);

    //验证token的有效性
    @GET("tokenVerify")
    Observable<ResponseBean> tokenVerify(@Header("Authorization") String token);

    //修改手机号
    @POST("modifyPhoneNum")
    Observable<ResponseBean> modifyPhoneNum(@Query("userId") int userId,
                                            @Query("newPhoneNum") String newPhoneNum,
                                            @Query("verifyCode") String verifyCode);

    //----------------------------------------------------------------------------------------------

    //首页获取新闻数据
    @GET("api-user/news/")
    Observable<ResponseBean> getBannerData();

    //其他人主页返回用户数据
    @GET("api-user/user/getCurrentUserInfo/{phoneNum}")
    Observable<ResponseBean> getCurrentUserInfo(@Path("phoneNum") String phoneNum);

    //注册用户
    @POST("api-user/user/")
    Observable<ResponseBean> register(@Query("username") String username, @Query("gender") int gender, @Query("password") String password,
                                      @Query("school") String school, @Query("phoneNum") String phoneNum);

    //上传头像
    @Multipart
    @POST("api-user/user/uploadAvatar")
    Observable<ResponseBean> uploadAvatar(@Part MultipartBody.Part avatar, @Query("userId") int userId);

    //上传认证照片
    @Multipart
    @POST("api-user/user/uploadVerifyPic")
    Observable<ResponseBean> uploadVerifyPic(@Part MultipartBody.Part verifyPic, @Query("userId") int userId);

    //修改密码
    @PUT("api-user/user/")
    Observable<ResponseBean> changePwd(@Query("userId") int userId, @Query("newPwd") String newPwd);

    //修改个人信息
    @PUT("api-user/user/")
    Observable<ResponseBean> changePersonalInfo(@Query("userBean") UserBean userBean);

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
    @PUT("api-user/user/")
    Observable<ResponseBean> recharge(@Query("userId") int userId, @Query("recharge") float recharge);

    //地址管理页面返回地址数据
    @GET("api-user/address/getAddress/{userId}")
    Observable<ResponseBean> getAddress(@Path("userId") int userId);

    //新增地址
    @POST("api-user/address/addAddress")
    Observable<ResponseBean> addAddress(@Query("userId") int userId,
                                        @Query("tag") String tag,
                                        @Query("address") String address);

    //修改地址
    @PUT("api-user/address/")
    Observable<ResponseBean> editAddress(@Query("userId") int userId,
                                         @Query("addressId") int addressId,
                                         @Query("tag") String tag,
                                         @Query("address") String address);

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
    @POST("api-indent/indent/")
    Observable<ResponseBean> publishIndent(@Query("publishId") int publishId, @Query("type") int type, @Query("price") float price,
                                           @Query("description") String description, @Query("address") String address, @Query("publishTime") String publishTime,
                                           @Query("planTime") String planTime);

    //发布方取消未接订单
    @PUT("api-indent/indent/cancelIndent")
    Observable<ResponseBean> cancelIndentNotTaken(@Query("indentId") int indentId);

    //发布方取消已接订单
    @PUT("api-indent/indent/cancelIndent")
    Observable<ResponseBean> cancelIndentHadTaken(@Query("indentId") int indentId);

    //发布方删除未评价订单
    @PUT("api-indent/indent/")
    Observable<ResponseBean> deleteIndentNotComment(@Query("indentId") int indentId);

    //发布方删除已评价订单
    @PUT("api-indent/indent/")
    Observable<ResponseBean> deleteIndentHadComment(@Query("indentId") int indentId);

    //发布方确认送达
    @PUT("api-indent/indent/finishIndent/")
    Observable<ResponseBean> ensureAcceptGoods(@Query("indentId") int indentId);

    //订单详情页面返回留言数据
    @GET("api-indent/comment/getCommentInfo/{indentId}")
    Observable<ResponseBean> getCommentInfoInIndentActivity(@Path("indentId") int indentId);

    //留言
    @POST("api-indent/comment/")
    Observable<ResponseBean> giveAComment(@Query("indentId") int indentId, @Query("userId") int userId,
                                          @Query("content") String content, @Query("commentTime") String commentTime);

    //----------------------------------------------------------------------------------------------

    //接单
    @POST("api-indent/accept/")
    Observable<ResponseBean> acceptIndent(@Query("acceptId") int acceptId,
                                          @Query("indentId") int indentId,
                                          @Query("acceptedTime") String acceptedTime);

    //接收方取消已接订单
    @PUT("api-indent/accept/cancelAccept")
    Observable<ResponseBean> cancelIndentHadTakenInAcpFragment(@Query("indentId") int indentId);

    //接收方删除未评价订单
    @PUT("api-indent/accept/deleteAccept")
    Observable<ResponseBean> deleteIndentNotCommentInAcpFragment(@Query("indentId") int indentId);

    //接收方删除已评价订单
    @PUT("api-indent/accept/deleteAccept")
    Observable<ResponseBean> deleteIndentHadCommentInAcpFragment(@Query("indentId") int indentId);

    //接收方确认送达
    @PUT("api-indent/accept/finishedAccept")
    Observable<ResponseBean> ensureAcceptGoodsInAcpFragment(@Query("deliveryTime") String deliveryTime, @Query("indentId") int indentId);

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
    @POST("api-indent/credit/star/")
    Observable<ResponseBean> giveRating(@Query("indentId") int indentId,
                                        @Query("userId") int userId,
                                        @Query("increasement") int increasement,
                                        @Query("happenTime") String happenTime);
}
