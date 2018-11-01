package com.loving.store.http;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loving.store.base.MApplication;
import com.loving.store.utils.WifiUtils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OKManager {
    private OKManager() {
    }

    private static OKManager okManager = new OKManager();

    public static OKManager getInstance() {
        return okManager;
    }

    private OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();

    //构建Retrofit对象
    //然后将刚才设置好的okHttp对象,通过retrofit.client()方法 设置到retrofit中去
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(com.loving.store.http.URLUtil.SERVER)
            .client(client)
            .addConverterFactory(new MyCoverFactor())
            .build();

    private void requestHttp(final CallBack callBack, String bookName) {
        ConfigApi configApi = retrofit.create(ConfigApi.class);
        Call<ResponseBody> call = configApi.searchBook(bookName, 1, 20);
        // 用法和OkHttp的call如出一辙,
        // 不同的是如果是Android系统回调方法执行在主线程
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Gson gson = new Gson();
                    Map<String, Object> map = gson.fromJson(response.body().string(), new TypeToken<Map<String, Object>>() {
                    }.getType());
                    callBack.doSuccess(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                callBack.doFail(t.toString());
            }
        });
    }

    public void requestHttp(String url, Map<String, Object> data, final CallBack callBack) {
        ConfigApi configApi = retrofit.create(ConfigApi.class);
        Call<ResponseBody> call = configApi.drawSearch(url, data);
        call.enqueue(new Callback<ResponseBody>() {//回调在主线程
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    Map<String, Object> map = null;
                    try {
                        map = gson.fromJson(response.body().string(), new TypeToken<Map<String, Object>>() {
                        }.getType());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Map<String, Object> head = (Map<String, Object>) map.get("head");
                    String status = (String) head.get("status");
                    if (callBack == null) return;
                    if ("200".equals(status)) {
                        callBack.doSuccess(map.get("body"));
                    } else {
                        callBack.doFail(head.get("message"));
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                if (callBack == null) return;
                if (TextUtils.isEmpty(WifiUtils.getNetworkType(MApplication.mApplication))) {
                    callBack.doFail("您的网络状况不佳，请检查网络连接");
                } else
                    callBack.doFail(t.toString());
            }
        });
    }

    public void requestHttp(String url, final CallBack callBack) {
        ConfigApi configApi = retrofit.create(ConfigApi.class);
        Call<ResponseBody> call = configApi.plash(url);
        call.enqueue(new Callback<ResponseBody>() {//回调在主线程
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    Map<String, Object> map = null;
                    try {
                        map = gson.fromJson(response.body().string(), new TypeToken<Map<String, Object>>() {
                        }.getType());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    callBack.doSuccess(map);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                if (callBack == null) return;
                if (TextUtils.isEmpty(WifiUtils.getNetworkType(MApplication.mApplication))) {
                    callBack.doFail("您的网络状况不佳，请检查网络连接");
                } else
                    callBack.doFail(t.toString());
            }
        });
    }

    public interface CallBack {
        void doSuccess(Object o);

        void doFail(Object o);
    }

    @SuppressLint("MissingPermission")
    private static String getDeviceId(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return "";
        }
        return telephonyManager.getDeviceId();
    }
}


