package com.zerogdev.easyshorturl.service;

import com.zerogdev.easyshorturl.data.ShortUrlResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface NaverService {

    @Headers({
            "X-Naver-Client-Id: " + NaverConsts.CLIENT_ID,
            "X-Naver-Client-Secret: " + NaverConsts.CLIENT_SECRET
    })
    @GET("/v1/util/shorturl")
    Call<ShortUrlResult> getShortUrl(@Query("url") String url);
}
