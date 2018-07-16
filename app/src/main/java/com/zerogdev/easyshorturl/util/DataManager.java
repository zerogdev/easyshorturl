package com.zerogdev.easyshorturl.util;

import android.util.Log;

import com.zerogdev.easyshorturl.data.ShortUrlResult;
import com.zerogdev.easyshorturl.listener.ShortUrlCallBack;
import com.zerogdev.easyshorturl.service.NaverService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataManager {

    public static final String NAVER_SERVICE_URL = "https://openapi.naver.com";

    private static OkHttpClient mClient;
    private NaverService mNaverService;

    public DataManager() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        mClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        createNaverApi();
    }

    private void createNaverApi() {
        mNaverService = new Retrofit.Builder()
                .baseUrl(NAVER_SERVICE_URL)
                .client(mClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NaverService.class);
    }

    public void loadShortUrl(String url, final ShortUrlCallBack callBack) {
        Call<ShortUrlResult> call = mNaverService.getShortUrl(url);
        call.enqueue(new Callback<ShortUrlResult>() {
            @Override
            public void onResponse(Call<ShortUrlResult> call, Response<ShortUrlResult> response) {
                if (callBack != null) {
                    if (response.body() != null) {
                        callBack.onShortUrlSuccess(response.body().getResult());
                    } else {
                        callBack.onShortUrlFail();
                    }
                }
            }

            @Override
            public void onFailure(Call<ShortUrlResult> call, Throwable t) {
                if (callBack != null) {
                    callBack.onShortUrlFail();
                }
            }
        });
    }
}
