package cjjc.plugin.cjjcMvp.publicBusinessMvp

import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.RecipeExecutor
import cjjc.plugin.cjjcMvp.activity.mvp.mvpActivityPresenterKt
import cjjc.plugin.cjjcMvp.fragment.mvp.mvpFragmentPresenterKt
import cjjc.plugin.cjjcMvp.mvp.mvpInterfaceKt
import cjjc.plugin.cjjcMvp.mvp.mvpModelKt

fun RecipeExecutor.mvpBusinessActivityRecipe(
    moduleTemplateData: ModuleTemplateData,
    mRootPackageName: String,
    mPageName: String,
    mActivityPackageName: String,
    publicBusinessType: Boolean,
) {
    val (projectData, srcOut) = moduleTemplateData
    val ktOrJavaExt = projectData.language.extension

    // 保存Model
    val mvpModel =
        mvpModelKt(mRootPackageName, mActivityPackageName.replace("/", "."), mPageName)
    save(
        mvpModel,
        srcOut.resolve("${mActivityPackageName}/${mPageName}Model.${ktOrJavaExt}")
    )

    // 保存Presenter
    val mvpPresenter = if (publicBusinessType) {
        mvpActivityPresenterKt(mRootPackageName, mActivityPackageName.replace("/", "."), mPageName)
    } else {
        mvpFragmentPresenterKt(mRootPackageName, mActivityPackageName.replace("/", "."), mPageName)
    }
    save(
        mvpPresenter,
        srcOut.resolve("${mActivityPackageName}/${mPageName}Presenter.${ktOrJavaExt}")
    )

    // 保存Interface
    val mvpInterface =
        mvpInterfaceKt(mRootPackageName, mActivityPackageName.replace("/", "."), mPageName)
    save(
        mvpInterface,
        srcOut.resolve("${mActivityPackageName}/${mPageName}Interface.${ktOrJavaExt}")
    )
}

