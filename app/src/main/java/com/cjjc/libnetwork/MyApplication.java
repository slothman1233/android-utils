package com.cjjc.libnetwork;

import android.app.Application;

import com.cjjc.lib_network.annotation.BindRxHttp;
import com.cjjc.lib_network.base_net.call.IHttp;
import com.cjjc.lib_network.util.CacheModeIndex;
import com.cjjc.lib_network.util.RxHttpConfig;
import com.cjjc.lib_tools.util.log.LogUtil;
import com.cjjc.libnetwork.hilt.IApplication;

import javax.inject.Inject;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class MyApplication extends Application implements IApplication {
    //注入网络访问接口  一句话切换底层实现框架
    @BindRxHttp
    @Inject
    IHttp http;

    @Inject
    public MyApplication(){}

    @Override
    public IHttp getIHttp() {
        return http;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.init(true,"=_=");
        RxHttpConfig.getInstance()
                .setBaseUrl("https://app-api.yidaz.cn/yd-api-member/")
                .setIsDebug(false)
                .setCacheMode(CacheModeIndex.ONLY_CACHE)
                .setSkipNetError(false)
                .setRequestErrorTag("")
                .init();
    }
}
