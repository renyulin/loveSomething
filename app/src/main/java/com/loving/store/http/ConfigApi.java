package com.loving.store.http;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface ConfigApi {
    @POST("flyElephant/app/activity/search")
    @FormUrlEncoded
    Call<ResponseBody> searchBook(
            @Field("content") String content,
            @Field("page") int page, @Field("rows") int rows);

    @POST
    Call<ResponseBody> drawSearch(@Url String url, @Body Map<String, Object> map);

    @POST
    Call<ResponseBody> plash(@Url String url);
}
