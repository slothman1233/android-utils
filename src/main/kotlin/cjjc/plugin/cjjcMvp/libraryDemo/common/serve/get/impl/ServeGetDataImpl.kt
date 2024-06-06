package cjjc.plugin.cjjcMvp.libraryDemo.common.serve.get.impl

fun serveGetDataImplKt(
    mRootPackageName: String?,
    mActivityPackageName: String,
    mPageName: String,
    mLibraryName: String,
    mLibraryNameUp: String,
) = """
                    
package ${mRootPackageName}.${mActivityPackageName};
import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import ${mRootPackageName}.constant.ConstantCode${mLibraryName};
import ${mRootPackageName}.router.ARouterPath${mLibraryName};
import ${mRootPackageName}.serve.get.call.Serve${mLibraryName}GetData;

/**
 * 数据服务实现
 */
@Route(path = ARouterPath${mLibraryName}.APP_SERVE_${mLibraryNameUp}.PATH_GET_DATA, name = "Serve${mLibraryName}")
public class $mPageName implements Serve${mLibraryName}GetData {

    public Serve${mLibraryName}GetDataImpl() {
        ARouter.getInstance().inject(this);
    }

    @Override
    public void init(Context context) {

    }

    /**
     * 提供当前业务模块登录鉴权码
     *
     * @return
     */
    @Override
    public int getAuthCodeToLogin() {
        return ConstantCode${mLibraryName}.CODE_ALL_CHECK_LOGIN;
    }
}       

"""