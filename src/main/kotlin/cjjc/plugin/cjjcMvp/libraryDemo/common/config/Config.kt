package cjjc.plugin.cjjcMvp.libraryDemo.common.config

fun configKt(
    mRootPackageName: String?,
    mActivityPackageName: String,
    mPageName: String,
    mLibraryName: String
) = """
                    
package ${mRootPackageName}.${mActivityPackageName};


import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * ${mLibraryName}工程配置
 */
public class $mPageName {
    private static $mPageName instance;
    private ${mPageName}Info ${mPageName}Info; //${mLibraryName}工程配置信息

    private $mPageName() {
        ARouter.getInstance().inject(this);
        ${mPageName}Info=${mPageName}Info.getInstance();
    }

    public static $mPageName getInstance() {
        if (instance == null) {
            synchronized ($mPageName.class) {
                if (instance == null) {
                    instance = new $mPageName();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化
     */
    public void init(Context context) {
        ${mPageName}Info.init(context);
    }

    /**
     * ${mLibraryName}工程配置信息
     *
     * @return
     */
    public ${mPageName}Info get${mPageName}Info() {
        return ${mPageName}Info;
    }
}
                    
"""