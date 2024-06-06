package com.cjjc.lib_tools.util;

import android.content.Context;

import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.XXPermissions;


/**
 * 权限请求工具
 */
public class PermissionsUtil {

    /**
     * 请求权限
     * @param context
     * @param callback 结果回调
     * @param permissions 权限集合
     */
    public static void requestPermissions(Context context, MyOnPermissionCallback callback, String... permissions) {
        XXPermissions.with(context)
                .permission(permissions)
                .request(callback);
    }

    /**
     * 请求权限(可以不写onDeny方法)
     * @param context
     * @param callback 结果回调
     * @param permissions 权限集合
     */
    public static void requestPermissions(Context context, OnPermissionCallback callback, String... permissions) {
        XXPermissions.with(context)
                .permission(permissions)
                .request(callback);
    }

    /**
     * 判断权限
     * @param context
     * @param permissions 权限集合
     */
    public static boolean checkPermissions(Context context, String... permissions) {
        return XXPermissions.isGranted(context, permissions);
    }
}
