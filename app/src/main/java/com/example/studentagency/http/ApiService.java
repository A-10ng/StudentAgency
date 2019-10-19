package com.example.studentagency.http;

import com.example.studentagency.bean.Cat;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2019/10/14
 * desc:
 */
public interface ApiService {

    @POST("findCat")
    Observable<Cat> login(@Field("studentId") String studentId,@Field("password") String password);
}
