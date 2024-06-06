package cjjc.plugin.cjjcMvp.libraryDemo.common.plugins

fun pluginsKt(
    mRootPackageName: String?,
    mActivityPackageName: String,
    mPageName: String
) = """
                    
package ${mRootPackageName}.${mActivityPackageName};

public class $mPageName {
}
                    
"""