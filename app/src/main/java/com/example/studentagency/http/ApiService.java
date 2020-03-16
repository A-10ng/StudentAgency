package com.example.studentagency.http;

import com.example.studentagency.bean.ResponseBean;
import com.example.studentagency.bean.UserBean;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Response;
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

    /**
     * @POST("GiveACommentSuccess")
     * //    Observable<ResponseBean> loginByPassword(@Query("phoneNum") String phoneNum, @Query("password") String password);
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
    @POST("GiveACommentSuccess")
    Observable<Response<ResponseBean>> loginByPassword(@Query("phoneNum") String phoneNum, @Query("password") String password);

    //通过验证码登录
    @POST("GiveACommentSuccess")
    Observable<Response<ResponseBean>> loginByVerifyCode(@Query("phoneNum") String phoneNum, @Query("verificationCode") String password);

    //首页获取新闻数据
    @GET("GetBannerData")
    Observable<ResponseBean> getBannerData();

    //首页获取订单数据
//    @GET("indent/getIndentsByUser/{phoneNum}")
//    Observable<ResponseBean> getIndentData(@Path("phoneNum") String phoneNum);
    @GET("GetMoreIndentData")
    Observable<ResponseBean> getIndentData(@Query("phoneNum") String phoneNum);

    //订单详情页面获取发布方信息
    @GET("GetPublishInfo")
    Observable<ResponseBean> getPublishInfoInIndentActivity(@Query("publishId") int publishId);

    //订单详情页面返回订单数据
    @POST("GetIndentInfo")
    Observable<ResponseBean> getIndentInfoInIndentActivity(@Query("indentId") int indentId);

    //订单详情页面返回评价数据
    @POST("GetRatingStarsInfo")
    Observable<ResponseBean> getRatingStarsInfo(@Query("indentId") int indentId);

    //订单详情页面返回留言数据
    @POST("GetMoreCommentInfo")
    Observable<ResponseBean> getCommentInfoInIndentActivity(@Query("indentId") int indentId);

    //接单
    @POST("AcceptIndentSuccess")
    Observable<ResponseBean> acceptIndent(@Query("indentId") int indentId, @Query("acceptedTime") String acceptedTime);

    //留言
    @POST("GiveACommentSuccess")
    Observable<ResponseBean> giveAComment(@Query("indentId") int indentId, @Query("userId") int userId,
                                     @Query("content") String content, @Query("commentTime") String commentTime);

    //根据类型返回分类订单数据
    @POST("GetMoreIndentData")
    Observable<ResponseBean> getIndentByType(@Query("type") int type,@Query("userId") int userId);

    //发布订单
    @POST("PublishIndentSuccess")
    Observable<ResponseBean> publishIndent(@Query("publishId") int publishId, @Query("type") int type, @Query("price") float price,
                                      @Query("description") String description, @Query("address") String address, @Query("publishTime") String publishTime,
                                      @Query("planTime") String planTime);

    //返回验证码
    @POST("GiveACommentSuccess")
    Observable<ResponseBean> getVerifyCode(@Query("phoneNum") String phoneNum);

    //注册用户
    @POST("GiveACommentSuccess")
    Observable<ResponseBean> register(@Query("username") String username, @Query("gender") int gender, @Query("password") String password,
                                 @Query("school") String school, @Query("phoneNum") String phoneNum);

    //个人页面返回用户数据
    @POST("GetPublishInfo")
    Observable<ResponseBean> getPersonFragmentInfo(@Query("userId") int userId);

    //上传头像
    @Multipart
    @POST("UploadAvatar")
    Observable<ResponseBean> uploadAvatar(@Part MultipartBody.Part avatar, @Query("userId") int userId);

    //修改密码
    @POST("GiveACommentSuccess")
    Observable<ResponseBean> changePwd(@Query("userId") int userId, @Query("newPwd") String newPwd);

    //修改个人信息页面返回用户信息
    @POST("GetPublishInfo")
    Observable<ResponseBean> getPersonalInfo(@Query("userId") int userId);

    //修改个人信息
    @POST("GiveACommentSuccess")
    Observable<ResponseBean> changePersonalInfo(@Query("userBean") UserBean userBean);

    //学生认证页面返回认证状态
    @POST("GetVerifyStateSuccess")
    Observable<ResponseBean> getVerifyState(@Query("userId") int userId);

    //上传认证照片
    @Multipart
    @POST("UploadAvatar")
    Observable<ResponseBean> uploadVerifyPic(@Part MultipartBody.Part verifyPic, @Query("userId") int userId);

    //学生认证页面返回已上传图片
    @GET("GetVerifyPic")
    Observable<ResponseBean> getVerifyPic(@Query("userId") int userId);

    //信誉积分页面返回信誉积分
    @GET("GetCreditScore")
    Observable<ResponseBean> getCreditScore(@Query("userId") int userId);

    //信誉积分页面返回信誉积分所有记录
    @GET("GetCreditAllRecord")
    Observable<ResponseBean> getCreditAllRecord(@Query("userId") int userId);

    //信誉积分页面返回信誉积分收入记录
    @GET("GetCreditInputRecord")
    Observable<ResponseBean> getCreditInputRecord(@Query("userId") int userId);

    //信誉积分页面返回信誉积分支出记录
    @GET("GetCreditOutputRecord")
    Observable<ResponseBean> getCreditOutputRecord(@Query("userId") int userId);

    //个人订单页面返回发布订单数据
    @GET("GetPublishIndents")
    Observable<ResponseBean> getPublishIndents(@Query("userId") int userId);

    //个人订单页面返回接收订单数据
    @GET("GetAcceptIndents")
    Observable<ResponseBean> getAcceptIndents(@Query("userId") int userId);

    //发布方取消未接订单
    @POST("GetVerifyStateSuccess")
    Observable<ResponseBean> cancelIndentNotTaken(@Query("userId") int userId,
                                             @Query("indentId") int indentId,
                                             @Query("price") String price);

    //发布方取消已接订单
    @POST("GetVerifyStateSuccess")
    Observable<ResponseBean> cancelIndentHadTaken(@Query("userId") int userId,
                                             @Query("indentId") int indentId,
                                             @Query("price") String price);

    //发布方删除未评价订单
    @POST("GetVerifyStateSuccess")
    Observable<ResponseBean> deleteIndentNotComment(@Query("userId") int userId,
                                               @Query("indentId") int indentId,
                                               @Query("price") String price);

    //发布方删除已评价订单
    @POST("GetVerifyStateSuccess")
    Observable<ResponseBean> deleteIndentHadComment(@Query("userId") int userId,
                                               @Query("indentId") int indentId,
                                               @Query("price") String price);

    //发布方确认送达
    @POST("GetVerifyStateSuccess")
    Observable<ResponseBean> ensureAcceptGoods(@Query("userId") int userId,
                                          @Query("indentId") int indentId,
                                          @Query("price") String price);

    //接收方取消已接订单
    @POST("GetVerifyStateSuccess")
    Observable<ResponseBean> cancelIndentHadTakenInAcpFragment(@Query("userId") int userId,
                                                          @Query("indentId") int indentId,
                                                          @Query("price") String price);

    //接收方删除未评价订单
    @POST("GetVerifyStateSuccess")
    Observable<ResponseBean> deleteIndentNotCommentInAcpFragment(@Query("userId") int userId,
                                                            @Query("indentId") int indentId,
                                                            @Query("price") String price);

    //接收方删除已评价订单
    @POST("GetVerifyStateSuccess")
    Observable<ResponseBean> deleteIndentHadCommentInAcpFragment(@Query("userId") int userId,
                                                            @Query("indentId") int indentId,
                                                            @Query("price") String price);

    //接收方确认送达
    @POST("GetVerifyStateSuccess")
    Observable<ResponseBean> ensureAcceptGoodsInAcpFragment(@Query("userId") int userId,
                                                       @Query("indentId") int indentId,
                                                       @Query("price") String price);

    //发布方发布评价
    @POST("GetVerifyStateSuccess")
    Observable<ResponseBean> giveRating(@Query("userId") int userId,
                                   @Query("increasement") int increasement,
                                   @Query("happenTime") String happenTime);

    //修改手机号
    @POST("GiveACommentSuccess")
    Observable<ResponseBean> modifyPhoneNum(@Query("userId") int userId,
                                       @Query("newPhoneNum") String newPhoneNum,
                                       @Query("verifyCode") String verifyCode);

    //地址管理页面返回地址数据
    @GET("GetAddress")
    Observable<ResponseBean> getAddress(@Query("userId") int userId);

    //新增地址
    @POST("AddAddress")
    Observable<ResponseBean> addAddress(@Query("userId") int userId,
                                       @Query("tag") String tag,
                                       @Query("address") String address);

    //修改地址
    @POST("GiveACommentSuccess")
    Observable<ResponseBean> editAddress(@Query("userId") int userId,
                                    @Query("addressId") int addressId,
                                    @Query("tag") String tag,
                                    @Query("address") String address);

    //删除地址
    @POST("GiveACommentSuccess")
    Observable<ResponseBean> deleteAddress(@Query("userId") int userId, @Query("addressId") int addressId);

    //其他人主页返回用户数据
    @GET("GetCurrentUserInfo")
    Observable<ResponseBean> getCurrentUserInfo(@Query("phoneNum") String phoneNum);

    //验证token的有效性
    @GET("GiveACommentSuccess")
    Observable<ResponseBean> tokenVerify(@Query("token") String token);

    //充值金额
    @GET("GiveACommentSuccess")
    Observable<ResponseBean> recharge(@Query("userId") int userId,@Query("recharge") String recharge);

}
