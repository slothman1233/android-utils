package com.cjjc.lib_network.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

import static com.cjjc.lib_network.util.CacheModeIndex.ONLY_NETWORK;
import static com.cjjc.lib_network.util.CacheModeIndex.ONLY_CACHE;
import static com.cjjc.lib_network.util.CacheModeIndex.NETWORK_SUCCESS_WRITE_CACHE;
import static com.cjjc.lib_network.util.CacheModeIndex.READ_CACHE_FAILED_REQUEST_NETWORK;
import static com.cjjc.lib_network.util.CacheModeIndex.REQUEST_NETWORK_FAILED_READ_CACHE;

/**
 * @Author：   HUlq
 * @Date：     2023/9/27 17:33
 * @Version：  1.0
 * @Description：  http缓存模式
 */
@IntDef({ONLY_NETWORK, ONLY_CACHE, NETWORK_SUCCESS_WRITE_CACHE, READ_CACHE_FAILED_REQUEST_NETWORK, REQUEST_NETWORK_FAILED_READ_CACHE})
@Retention(RetentionPolicy.SOURCE)
public @interface CacheModeIndex {

    /**
     * 仅请求网络，默认模式  （不写缓存）
     */
    int ONLY_NETWORK = 0;

    /**
     * 仅读取缓存  （不写缓存）
     */
    int ONLY_CACHE = 1;

    /**
     * 请求成功后，写入缓存 跟{@link #ONLY_NETWORK } 默认模式相比，仅多了写缓存的操作
     */
    int NETWORK_SUCCESS_WRITE_CACHE = 2;

    /**
     * 先读取缓存，失败后再请求网络  (网络请求成功，写缓存)
     */
    int READ_CACHE_FAILED_REQUEST_NETWORK = 3;

    /**
     * 先请求网络，失败后再读取缓存  (网络请求成功，写缓存)
     */
    int REQUEST_NETWORK_FAILED_READ_CACHE = 4;
}
