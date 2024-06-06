package cjjc.plugin.cjjcMvp.businessFragment

import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.RecipeExecutor
import cjjc.plugin.cjjcMvp.mvp.mvpInterfaceKt
import cjjc.plugin.cjjcMvp.mvp.mvpModelKt
import cjjc.plugin.cjjcMvp.comm.res.layout.mvpXml
import cjjc.plugin.cjjcMvp.fragment.mvp.mvpFragmentPresenterKt


fun RecipeExecutor.mvpBusinessFragmentRecipe(
    moduleTemplateData: ModuleTemplateData,
    mRootPackageName: String,
    mPageName: String,
    mFragmentLayoutName: String,
    mIsGenerateFragmentLayout: Boolean,
    mFragmentPackageName: String
) {
    val (projectData, srcOut, resOut) = moduleTemplateData
    val ktOrJavaExt = projectData.language.extension

    val split = mRootPackageName.split(".")
    val mApplicationId= split[0]+"."+split[1]+"."+split[2]
    val mvpFragment = mvpBusinessFragmentKt(mRootPackageName,mApplicationId, mFragmentPackageName.replace("/", "."), mPageName,mFragmentLayoutName)
    // 保存Fragment
    save(
        mvpFragment,
        srcOut.resolve("${mFragmentPackageName}/${mPageName}Fragment.${ktOrJavaExt}")
    )
    if (mIsGenerateFragmentLayout) {
        // 保存xml
        save(mvpXml(), resOut.resolve("layout/${mFragmentLayoutName}.xml"))
    }
    // 保存Model
    val mvpModel =
        mvpModelKt(mRootPackageName, mFragmentPackageName.replace("/", "."), mPageName)
    save(
        mvpModel,
        srcOut.resolve("${mFragmentPackageName}/${mPageName}Model.${ktOrJavaExt}")
    )
    // 保存Presenter
    val mvpPresenter =
        mvpFragmentPresenterKt(mRootPackageName, mFragmentPackageName.replace("/", "."), mPageName)
    save(
        mvpPresenter,
        srcOut.resolve("${mFragmentPackageName}/${mPageName}Presenter.${ktOrJavaExt}")
    )
    // 保存Interface
    val mvpInterface =
        mvpInterfaceKt(mRootPackageName, mFragmentPackageName.replace("/", "."), mPageName)
    save(
        mvpInterface,
        srcOut.resolve("${mFragmentPackageName}/${mPageName}Interface.${ktOrJavaExt}")
    )
}