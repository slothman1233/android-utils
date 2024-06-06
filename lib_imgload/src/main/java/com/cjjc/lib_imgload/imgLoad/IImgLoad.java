package com.cjjc.lib_imgload.imgLoad;

import android.content.Context;
import android.widget.ImageView;

import java.io.File;

/**
 * 图片加载
 */
public interface IImgLoad {

    void showImg(Context context, Object img_Url, ImageView view);

    void showImg(Context context, Object img_Url, ImageView view, int round);

    void showImg(Context context, Object img_Url, ImageView view, int placeholder, int error);

    void loadSmollUrl(Context mContext, String url,
                      int w, int h, int placeholder, int error, ImageView imageView);

    void loadFile(Context mContext, File file,ImageView imageView);

    void loadFile(Context mContext, File file, int placeholder, int error, ImageView imageView);


}
