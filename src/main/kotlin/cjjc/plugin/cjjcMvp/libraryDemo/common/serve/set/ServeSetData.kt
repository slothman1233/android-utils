package cjjc.plugin.cjjcMvp.libraryDemo.common.serve.set

fun serveSetDataKt(
    mRootPackageName: String?,
    mActivityPackageName: String,
    mPageName: String,
) = """
                    
package ${mRootPackageName}.${mActivityPackageName};

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * 暴露接口--向外部索要数据
 */
public interface $mPageName extends IProvider {
}

"""