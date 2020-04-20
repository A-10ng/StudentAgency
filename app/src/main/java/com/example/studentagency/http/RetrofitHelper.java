package com.example.studentagency.http;

import android.util.Log;

import com.example.studentagency.utils.DateUtils;
import com.example.studentagency.utils.GsonTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
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
//    public static String BASE_URL = "http://192.168.43.73:8080/LongSh1z/";
//    public static String BASE_URL = "http://192.168.0.105:8080/LongSh1z/";
//    public static String BASE_URL = "http://39.106.86.42:8086/";
//    public static String BASE_URL = "http://192.168.1.107:8080/LongSh1z/";
//    public static String BASE_URL = "http://120.78.219.119:8080/LongSh1z/";
    public static String BASE_URL = "http://299s738j54.wicp.vip/";

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
////                .registerTypeAdapter(
////                        new TypeToken<Map<String,Object>>(){}.getType(),
////                        new MapTypeAdapter())
//                .create();

        //        Gson mGson = new GsonBuilder()
//                .registerTypeAdapter(
//                        new TypeToken<Map<String,Object>>(){}.getType(),
//                        new GsonTypeAdapter())
//                .create();

//        Gson mGson = new GsonBuilder()
//                .registerTypeAdapter(
//                        new TypeToken<TreeMap<String, Object>>(){}.getType(),
//                        new JsonDeserializer<TreeMap<String, Object>>() {
//                            @Override
//                            public TreeMap<String, Object> deserialize(
//                                    JsonElement json, Type typeOfT,
//                                    JsonDeserializationContext context) throws JsonParseException {
//
//                                TreeMap<String, Object> treeMap = new TreeMap<>();
//                                JsonObject jsonObject = json.getAsJsonObject();
//                                Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
//                                for (Map.Entry<String, JsonElement> entry : entrySet) {
//                                    treeMap.put(entry.getKey(), entry.getValue());
//                                }
//                                return treeMap;
//                            }
//                        }).create();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getOkHttpClient())
//                .addConverterFactory(GsonConverterFactory.create(mGson))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static class MapTypeAdapter extends TypeAdapter<Object> {

        @Override
        public Object read(JsonReader in) throws IOException {
            JsonToken token = in.peek();
            switch (token) {
                case BEGIN_ARRAY:
                    List<Object> list = new ArrayList<Object>();
                    in.beginArray();
                    while (in.hasNext()) {
                        list.add(read(in));
                    }
                    in.endArray();
                    return list;

                case BEGIN_OBJECT:
                    Map<String, Object> map = new LinkedTreeMap<String, Object>();
                    in.beginObject();
                    while (in.hasNext()) {
                        map.put(in.nextName(), read(in));
                    }
                    in.endObject();
                    return map;

                case STRING:
                    return in.nextString();

                case NUMBER:
                    /**
                     * 改写数字的处理逻辑，将数字值分为整型与浮点型。
                     */
                    double dbNum = in.nextDouble();

                    // 数字超过long的最大值，返回浮点类型
                    if (dbNum > Long.MAX_VALUE) {
                        return dbNum;
                    }

                    // 判断数字是否为整数值
                    long lngNum = (long) dbNum;
                    if (dbNum == lngNum) {
                        return lngNum;
                    } else {
                        return dbNum;
                    }

                case BOOLEAN:
                    return in.nextBoolean();

                case NULL:
                    in.nextNull();
                    return null;

                default:
                    throw new IllegalStateException();
            }
        }

        @Override
        public void write(JsonWriter out, Object value) throws IOException {
            // 序列化无需实现
        }

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
//                .addInterceptor(new LogInterceptor())
                .build();
        return okHttpClient;
    }

    /**
     * 请求拦截器,注意：不能调用两次chain.proceed，因为这样会同时发送两次请求
     */
    private class RqInterceptor implements Interceptor{

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request()
                    .newBuilder()
                    .addHeader("X-APP-TYPE","android")
                    .build();
            Response response = chain.proceed(request);

            //打印请求的具体信息
            String url = request.url().toString();
            String params = requestBodyToString(request.body());
            String responseString = response.body().string();//JsonHandleUtils.jsonHandle(response)
            String time = DateUtils.getCurrentDateByFormat("yyyy-MM-dd HH:mm:ss");
            String log =
                    "\n\n**************************请求时间**************************:\n" + time +
                            "\n****************************路径****************************:\n" + url +
                            "\n****************************参数****************************:\n" + params +
                            "\n****************************报文****************************:\n" + responseString+"\n \n";
            Log.d(TAG, log);

            return response.newBuilder().body(ResponseBody.create(response.body().contentType(), responseString)).build();
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
                    "\n\n**************************请求时间**************************:\n" + time +
                    "\n**************************路径**************************:\n" + url +
                    "\n**************************参数**************************:\n" + params +
                    "\n**************************报文**************************:\n" + responseString+"\n \n";
            Log.d(TAG, log);

            return response.newBuilder().body(ResponseBody.create(response.body().contentType(), responseString)).build();
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
