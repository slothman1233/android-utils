package cjjc.plugin.cjjcMvp.widget.mvp

fun mvpInterfaceKt(
    mRootPackageName: String?,
    mActivityPackageName: String,
    mPageName: String
) = """
                    
package ${mRootPackageName}.${mActivityPackageName};

import com.cjjc.base_lib.call.mvp.IViewInterface;
import com.cjjc.base_lib.call.mvp.IModelInterface;
import com.cjjc.base_lib.call.mvp.IPresenterInterface;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;

/**
 * 业务接口声明->${mPageName}
 */
public interface ${mPageName}Interface {
    /**
     * View层接口定义
     */
    interface View extends IViewInterface {
    }

    /**
     * Model层接口定义
     */
    interface Model extends IModelInterface {
    }
    
    /**
     * Presenter层接口定义
     */
    interface Presenter extends IPresenterInterface {
    }
}
                    
"""