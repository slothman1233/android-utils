package cjjc.plugin.cjjcMvp.libraryDemo.common.router

fun routerKt(
    mRootPackageName: String?,
    mActivityPackageName: String,
    mPageName: String,
    mLibraryName: String,
    mLibraryNameUp: String,
    mLibraryNameDown: String,
) = """
                    
package ${mRootPackageName}.${mActivityPackageName};

/**
 * ${mLibraryName}路由管理
 */
public class $mPageName {
    //模块页面路由
    public static class APP_${mLibraryNameUp} {
        private static final String APP_${mLibraryNameUp} = "/${mLibraryNameDown}/";
        public static final String PATH_${mLibraryNameUp}_FRAGMENT = APP_${mLibraryNameUp} + "${mLibraryNameUp}Fragment";
    }

    //模块-接口服务路径
    public static class APP_SERVE_${mLibraryNameUp} {
        private static final String APP_SERVE_${mLibraryNameUp} = "/${mLibraryNameDown}/server/";
        public static final String PATH_GET_DATA = APP_SERVE_${mLibraryNameUp} + "getData";
    }
}

                    
"""