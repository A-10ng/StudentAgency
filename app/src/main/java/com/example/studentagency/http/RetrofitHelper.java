package com.example.studentagency.http;

import android.util.Log;

import com.example.studentagency.utils.DateUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2019/10/14
 * desc:
 */
public class RetrofitHelper {

//    public static String BASE_URL = "http://120.78.219.119:8080/StudentAgency/";
//    public static String BASE_URL = "http://192.168.1.116:8080/LongSh1z/";
    public static String BASE_URL = "http://192.168.43.73:8080/LongSh1z/";
//    public static String BASE_URL = "http://192.168.1.107:8080/LongSh1z/";
//    public static String BASE_URL = "http://120.78.219.119:8080/LongSh1z/";

    private static final String TAG = "RetrofitHelper";
    private long CONNECT_TIMEOUT = 3L;
    private long READ_TIMEOUT = 3L;
    private long WRITE_TIMEOUT = 3L;
    private static volatile RetrofitHelper mInstance = null;
    private Retrofit mRetrofit = null;

    public static RetrofitHelper getInstance(){
        if (mInstance == null){
            synchronized (RetrofitHelper.class){
                if (mInstance == null){
                    mInstance = new RetrofitHelper();
                }
            }
        }
        return mInstance;
    }

    //防止外部实例化
    private RetrofitHelper(){
        init();
    }

    private void init() {
        resetApp();
    }

    private void resetApp() {
//        Gson mGson = new GsonBuilder()
//                .setLenient()
//                .create();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getOkHttpClient())
//                .addConverterFactory(GsonConverterFactory.create(mGson))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /**
     * 获取OkHttpClient实例
     * @return
     */
    private OkHttpClient getOkHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT,TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT,TimeUnit.SECONDS)
                .addInterceptor(new RqInterceptor())
                .addInterceptor(new LogInterceptor())
                .build();
        return okHttpClient;
    }

    /**
     * 请求拦截器
     */
    private class RqInterceptor implements Interceptor{

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request()
                    .newBuilder()
                    .addHeader("X-APP-TYPE","android")
                    .build();
            Response response = chain.proceed(request);
            return response;
        }
    }

    /**
     * 日志拦截器
     */
    private class LogInterceptor implements Interceptor{

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            //打印请求的具体信息
            String url = request.url().toString();
            String params = requestBodyToString(request.body());
            Response response = chain.proceed(request);
            String responseString = response.body().string();//JsonHandleUtils.jsonHandle(response)
            String time = DateUtils.getCurrentDateByFormat("yyyy-MM-dd HH:mm:ss");
            String log =
                    "\n\n*****请求时间*****:\n" + time +
                    "\n*******路径*******:\n" + url +
                    "\n*******参数*******:\n" + params +
                    "\n*******报文*******:\n" + responseString+"\n \n";
            Log.d(TAG, log);

            return chain.proceed(request);
        }
    }

    private String requestBodyToString(RequestBody body) {
        try{
            RequestBody requestBody = body;
            Buffer buffer = new Buffer();
            if (requestBody != null){
                requestBody.writeTo(buffer);
            }else {
                return "";
            }
            return buffer.readUtf8();
        }catch (IOException e){
            return "did not work";
        }
    }

    public ApiService getServer(){
        return mRetrofit.create(ApiService.class);
    }
}
