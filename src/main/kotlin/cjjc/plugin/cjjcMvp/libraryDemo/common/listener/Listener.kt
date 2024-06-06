package cjjc.plugin.cjjcMvp.libraryDemo.common.listener

fun listenerKt(
    mRootPackageName: String?,
    mActivityPackageName: String,
    mPageName: String
) = """
                    
package ${mRootPackageName}.${mActivityPackageName};

public interface $mPageName {
}
                    
"""