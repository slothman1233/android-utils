package com.cjjc.lib_imgload.imgLoad;

import android.content.Context;
import android.widget.ImageView;

import java.io.File;


/**
 * 图片加载代理
 */
public class ImgLoad implements IImgLoad {

    //持有当前代理的对象
    private static IImgLoad mImgLoad;

    public static void init(IImgLoad imgLoad) {
        mImgLoad = imgLoad;
    }

    private static ImgLoad instance;

    public static ImgLoad getInstance() {
        if (instance == null) {
            synchronized (ImgLoad.class) {
                if (instance == null) {
                    instance = new ImgLoad();
                }
            }
        }
        return instance;
    }

    private ImgLoad() {
    }

    @Override
    public void showImg(Context context, Object img_Url, ImageView view) {
        mImgLoad.showImg(context, img_Url, view);
    }

    @Override
    public void showImg(Context context, Object img_Url, ImageView view, int round) {
        mImgLoad.showImg(context, img_Url, view, round);
    }

    @Override
    public void showImg(Context context, Object img_Url, ImageView view, int placeholder, int error) {
        mImgLoad.showImg(context, img_Url, view, placeholder, error);
    }

    @Override
    public void loadSmollUrl(Context mContext, String url, int w, int h, int placeholder, int error, ImageView imageView) {
        mImgLoad.loadSmollUrl(mContext, url, w, h, placeholder, error, imageView);
    }

    @Override
    public void loadFile(Context mContext, File file, ImageView imageView) {
        mImgLoad.loadFile(mContext, file, imageView);
    }

    @Override
    public void loadFile(Context mContext, File file, int placeholder, int error, ImageView imageView) {
        mImgLoad.loadFile(mContext, file, placeholder, error, imageView);
    }
}
