package cjjc.plugin.cjjcMvp.widget.mvp

fun mvpPresenterKt(
    mRootPackageName: String?,
    mActivityPackageName: String,
    mPageName: String
) = """

package ${mRootPackageName}.${mActivityPackageName};

import com.cjjc.base_lib.view.widget.BaseWidgetPresenter;

/**
 * 业务调度控制器->${mPageName}
 */
public class ${mPageName}Presenter extends BaseWidgetPresenter<${mPageName}Interface.Model,${mPageName}Interface.View> implements ${mPageName}Interface.Presenter{

    public ${mPageName}Presenter(${mPageName}Interface.Model mModel) {
        super(mModel);
    }

    @Override
    public void onInit() {
    }

}

"""