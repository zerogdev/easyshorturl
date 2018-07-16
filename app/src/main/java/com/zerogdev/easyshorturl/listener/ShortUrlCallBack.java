package com.zerogdev.easyshorturl.listener;

import com.zerogdev.easyshorturl.data.ShortUrlData;

public interface ShortUrlCallBack {

    public void onShortUrlSuccess(ShortUrlData shortUrlData);
    public void onShortUrlFail();

}
