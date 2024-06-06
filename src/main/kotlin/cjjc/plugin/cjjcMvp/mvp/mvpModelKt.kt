package cjjc.plugin.cjjcMvp.mvp

fun mvpModelKt(
    mRootPackageName: String?,
    mActivityPackageName: String,
    mPageName: String
) = """

package ${mRootPackageName}.${mActivityPackageName};

import com.cjjc.lib_base_view.view.BaseModel;
import javax.inject.Inject;


/**
 * 数据获取实现层->${mPageName}
 */
public class ${mPageName}Model extends BaseModel implements ${mPageName}Interface.Model {

    @Inject
    public ${mPageName}Model() {
    }

}

"""