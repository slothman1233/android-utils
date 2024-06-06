package cjjc.plugin.cjjcMvp.libraryDemo.common.constant

fun constantKt(
    mRootPackageName: String?,
    mActivityPackageName: String,
    mPageName: String
) = """
                    
package ${mRootPackageName}.${mActivityPackageName};

/**
 * 全局业务操作状态码
 */
public class $mPageName {
    public static final int CODE_ALL_CHECK_LOGIN = 30000; //登录鉴权码
}
                    
"""