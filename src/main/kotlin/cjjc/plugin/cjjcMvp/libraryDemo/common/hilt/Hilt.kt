package cjjc.plugin.cjjcMvp.libraryDemo.common.hilt

fun hiltKt(
    mRootPackageName: String?,
    mActivityPackageName: String,
    mPageName: String
) = """
                    
package ${mRootPackageName}.${mActivityPackageName};

public class $mPageName {
}
                    
"""