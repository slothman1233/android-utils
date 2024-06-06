package cjjc.plugin.cjjcMvp.fragment.mvp

fun mvpFragmentPresenterKt(
    mRootPackageName: String?,
    mActivityPackageName: String,
    mPageName: String
) = """
    
package ${mRootPackageName}.${mActivityPackageName};

import com.cjjc.lib_base_view.view.fragment.BaseFragmentPresenter;

import javax.inject.Inject;

/**
 * 业务调度控制器->${mPageName}
 */
public class ${mPageName}Presenter extends BaseFragmentPresenter<${mPageName}Interface.Model, ${mPageName}Interface.View> implements ${mPageName}Interface.Presenter{

    //初始化时注入M层
    @Inject
    public ${mPageName}Presenter(${mPageName}Interface.Model mModel) {
        super(mModel);
    }

    @Override
    public void onInit() {

    }

}

"""