package com.cjjc.lib_imgload.imgLoad;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

/**
 * 接口注入实现隔离层
 */
@InstallIn(SingletonComponent.class)
@Module
public abstract class ImgLoadModule {

    @Binds
    abstract IImgLoad bindGlide(GlideImg img);
}
