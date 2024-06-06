package cjjc.plugin.cjjcMvp.libraryDemo.common.base

fun baseKt(
    mRootPackageName: String?,
    mActivityPackageName: String,
    mPageName: String
) = """
                    
package ${mRootPackageName}.${mActivityPackageName};

public class $mPageName {
}
                    
"""