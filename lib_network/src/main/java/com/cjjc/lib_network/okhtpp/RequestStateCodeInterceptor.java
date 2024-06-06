package com.cjjc.lib_network.okhtpp;

import com.cjjc.lib_network.util.RxHttpConfig;
import com.cjjc.lib_tools.util.GsonUtil;
import com.cjjc.lib_tools.util.log.LogUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 错误状态码拦截器
 */
public class RequestStateCodeInterceptor implements Interceptor {
    private final Map<String, Integer> map = new HashMap<>();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
//        Log.e("=_=","response-->"+response.code()+"\t"+response.message());
        stateCode(response.code());
        return response;
    }

    private void stateCode(int code) {
        //HTTP请求状态码异常
        if(RxHttpConfig.getInstance().getErrorCode().contains(String.valueOf(code))){
            map.put("netStateCode", code);
            EventBus.getDefault().post(map);
        }
    }


}
