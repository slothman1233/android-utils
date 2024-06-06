package cjjc.plugin.cjjcMvp.libraryDemo.common.serve.get.call

fun serveGetDataKt(
    mRootPackageName: String?,
    mActivityPackageName: String,
    mPageName: String,
) = """
                    
package ${mRootPackageName}.${mActivityPackageName};

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * 接口暴露--对外提供数据服务能力
 */
public interface $mPageName extends IProvider {
    int getAuthCodeToLogin(); //对外提供内部登录鉴权码
}
                    
"""