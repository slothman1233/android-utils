package com.cjjc.lib_imgload.imgLoad;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

import javax.inject.Inject;

public class GlideImg implements IImgLoad {

    @Inject
    public GlideImg() {
    }

    @Override
    public void showImg(Context context, Object img_Url, ImageView view) {
        if (img_Url == null || view == null || context == null) {
            return;
        }

        RequestOptions requestOptions = new RequestOptions();
//        requestOptions.placeholder(R.mipmap.ic_launcher); //图片加载出来前，显示的图片
//        requestOptions.error(R.mipmap.ic_launcher);   //当图片加载错误时的替代图
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);   //配置磁盘缓存策略

        Glide.with(context)  // 表示某个组件需要加载图片
                .load(img_Url)   //加载的图片地址
//                .error(R.mipmap.ic_launcher)   //当图片加载错误时的替代图
                .diskCacheStrategy(DiskCacheStrategy.ALL)   //配置磁盘缓存策略
                .apply(requestOptions)
//                .placeholder(R.mipmap.ic_launcher)//图片加载出来前，显示的图片
                .into(view);      // 指定显示在某个ImageView 上面
    }

    @Override
    public void showImg(Context context, Object img_Url, ImageView view, int round) {
        if (img_Url == null || view == null || context == null) {
            return;
        }

        //设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(round);
        RequestOptions requestOptions = RequestOptions.bitmapTransform(roundedCorners);
//        requestOptions.placeholder(R.mipmap.ic_launcher); //图片加载出来前，显示的图片
//        requestOptions.error(R.mipmap.ic_launcher);   //当图片加载错误时的替代图
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);   //配置磁盘缓存策略
        requestOptions.transform(new GlideRoundTransform(round));

        Glide.with(context)  // 表示某个组件需要加载图片
                .load(img_Url)   //加载的图片地址
                .apply(requestOptions)
                .into(view);      // 指定显示在某个ImageView 上面
    }

    @Override
    public void showImg(Context context, Object img_Url, ImageView view, int placeholder, int error) {
        if (img_Url == null || view == null || context == null) {
            return;
        }

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(placeholder); //图片加载出来前，显示的图片
        requestOptions.error(error);   //当图片加载错误时的替代图
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);   //配置磁盘缓存策略

        Glide.with(context)  // 表示某个组件需要加载图片
                .load(img_Url)   //加载的图片地址
                .apply(requestOptions)
                .into(view);      // 指定显示在某个ImageView 上面
    }

    /**
     * 加载图片Url
     * @param mContext
     * @param url
     * @param w
     * @param h
     * @param placeholder
     * @param error
     * @param imageView
     */
    @Override
    public void loadSmollUrl(Context mContext, String url, int w, int h, int placeholder, int error, ImageView imageView) {
        if (mContext != null) {
            Glide.with(mContext.getApplicationContext())
                    .load(url)
                    .override(w, h)
                    .placeholder(placeholder)
                    .error(error)
                    .format(DecodeFormat.PREFER_RGB_565)
                    // 取消动画，防止第一次加载不出来
                    .dontAnimate()
                    //加载缩略图
                    .thumbnail(0.3f)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }
    }

    /**
     * 加载文件
     * @param mContext
     * @param file
     * @param imageView
     */
    @Override
    public void loadFile(Context mContext, File file, ImageView imageView) {
        if (mContext != null) {
            Glide.with(mContext.getApplicationContext())
                    .load(file)
                    .format(DecodeFormat.PREFER_RGB_565)
                    // 取消动画，防止第一次加载不出来
                    .dontAnimate()
                    //加载缩略图
                    .thumbnail(0.3f)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }
    }

    /**
     * 加载文件
     * @param mContext
     * @param file
     * @param placeholder
     * @param error
     * @param imageView
     */
    @Override
    public void loadFile(Context mContext, File file, int placeholder, int error, ImageView imageView) {
        if (mContext != null) {
            Glide.with(mContext.getApplicationContext())
                    .load(file)
                    .placeholder(placeholder)
                    .error(error)
                    .format(DecodeFormat.PREFER_RGB_565)
                    // 取消动画，防止第一次加载不出来
                    .dontAnimate()
                    //加载缩略图
                    .thumbnail(0.3f)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }
    }


}
