package cjjc.plugin.cjjcMvp.libraryDemo.common.config

fun configInfoKt(
    mRootPackageName: String?,
    mActivityPackageName: String,
    mPageName: String,
) = """
                    
package ${mRootPackageName}.${mActivityPackageName};
import android.content.Context;

public class $mPageName {

    private static $mPageName instance;

    protected static $mPageName getInstance() {
        if (instance == null) {
            synchronized (${mPageName}.class) {
                if (instance == null) {
                    instance = new ${mPageName}();
                }
            }
        }
        return instance;
    }

    public void init(Context context){}

}
                    
"""