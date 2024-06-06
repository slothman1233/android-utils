package cjjc.plugin.cjjcMvp.libraryDemo.common.bean

fun testBeanKt(
    mRootPackageName: String?,
    mActivityPackageName: String,
    mPageName: String
) = """
                    
package ${mRootPackageName}.${mActivityPackageName};

public class $mPageName {


}  
                    
"""