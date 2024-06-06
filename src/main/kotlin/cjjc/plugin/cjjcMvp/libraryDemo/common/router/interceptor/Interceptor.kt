package cjjc.plugin.cjjcMvp.libraryDemo.common.router.interceptor

fun interceptorKt(
    mRootPackageName: String?,
    mActivityPackageName: String,
    mPageName: String,
    mLibraryName: String,
    mLibraryNameDown: String,
) = """
                    
package ${mRootPackageName}.${mActivityPackageName};


import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;

/**
 * ${mLibraryName}组件-单独业务鉴权拦截器
 */
@Interceptor(priority = 2, name = "${mLibraryNameDown}Interceptor")
public class $mPageName implements IInterceptor {
    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        //业务判断
//        if(MMkvHelper.getInstance().getUserInfo()==null){
//            callback.onInterrupt(null);
//            ARouter.getInstance().build(ARouterPath.APP_LOGIN.LOGIN_ACTIVITY).greenChannel().navigation();
//        }
//        else{
//            callback.onContinue(postcard);
//        }
            callback.onContinue(postcard);
    }

    @Override
    public void init(Context context) {

    }
}

                    
"""