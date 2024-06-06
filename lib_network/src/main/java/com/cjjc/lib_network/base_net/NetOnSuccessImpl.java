package com.cjjc.lib_network.base_net;

import android.text.TextUtils;

import com.cjjc.lib_network.base_net.bean.BaseResponse;

/**
 * 请求成功回调处理
 */
public class NetOnSuccessImpl {


    /**
     * 成功请求处理
     *
     * @param response 数据
     * @param callBack 回调
     */
    public static void onSuccess(BaseResponse response, CallBack callBack) {
        switch (response.reqCode) {
            case ServerCode.CODE_SUCCESS://成功
                if (TextUtils.isEmpty(response.bCode)) {//成功
                    callBack.onSuccess(response);
                } else {//失败：bCode不为空
                    callBack.onError(response.msg, response.reqCode, response.bCode);
                }
                break;
            default:
                callBack.onError("网络异常", response.reqCode, response.bCode);
//                callBack.onError(response.msg, response.reqCode, response.bCode);
                break;
        }
    }

    interface CallBack {
        //成功--状态码处理
        void onSuccess(BaseResponse response);

        //失败--状态码处理
        void onError(String tipsMsg, int reqCode, String bCode);
    }
}
