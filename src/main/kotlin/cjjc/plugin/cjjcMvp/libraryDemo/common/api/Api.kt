package cjjc.plugin.cjjcMvp.libraryDemo.common.api

fun apiKt(
    mRootPackageName: String?,
    mActivityPackageName: String,
    mPageName: String
) = """
                    
package ${mRootPackageName}.${mActivityPackageName};

public class $mPageName {

    //接口名
    public static final String API_NAME="memberMessage/unReadCount";
}
                    
"""