package cjjc.plugin.cjjcMvp.widget

import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.RecipeExecutor
import cjjc.plugin.cjjcMvp.comm.res.layout.mvpXml
import cjjc.plugin.cjjcMvp.mvp.mvpModelKt
import cjjc.plugin.cjjcMvp.widget.mvp.mvpInterfaceKt
import cjjc.plugin.cjjcMvp.widget.mvp.mvpPresenterKt
import cjjc.plugin.cjjcMvp.widget.mvp.mvpWidgetKt
import cjjc.plugin.cjjcMvp.widget.mvp.mvpWidgetNoLayoutKt


fun RecipeExecutor.mvpWidgetRecipe(
    moduleTemplateData: ModuleTemplateData,
    mRootPackageName: String,
    mPageName: String,
    mWidgetPackageName: String,
    mIsGenerateWidgetLayout: Boolean,
    mWidgetLayoutName: String,
) {
    val (projectData, srcOut, resOut) = moduleTemplateData
    val ktOrJavaExt = projectData.language.extension

    val split = mRootPackageName.split(".")
    val mApplicationId= split[0]+"."+split[1]+"."+split[2]
    val mvpWidget = if(mIsGenerateWidgetLayout) {
        mvpWidgetKt(mRootPackageName,mApplicationId, mWidgetPackageName.replace("/", "."), mPageName,mWidgetLayoutName)
    } else {
        mvpWidgetNoLayoutKt(mRootPackageName,mApplicationId, mWidgetPackageName.replace("/", "."), mPageName)
    }
    // 保存Widget
    save(
        mvpWidget,
        srcOut.resolve("${mWidgetPackageName}/${mPageName}Widget.${ktOrJavaExt}")
    )
    if (mIsGenerateWidgetLayout) {
        // 保存xml
        save(mvpXml(), resOut.resolve("layout/${mWidgetLayoutName}.xml"))
    }
    // 保存Model
    val mvpModel =
        mvpModelKt(mRootPackageName, mWidgetPackageName.replace("/", "."), mPageName)
    save(
        mvpModel,
        srcOut.resolve("${mWidgetPackageName}/${mPageName}Model.${ktOrJavaExt}")
    )
    // 保存Presenter
    val mvpPresenter =
        mvpPresenterKt(mRootPackageName, mWidgetPackageName.replace("/", "."), mPageName)
    save(
        mvpPresenter,
        srcOut.resolve("${mWidgetPackageName}/${mPageName}Presenter.${ktOrJavaExt}")
    )
    // 保存Interface
    val mvpInterface =
        mvpInterfaceKt(mRootPackageName, mWidgetPackageName.replace("/", "."), mPageName)
    save(
        mvpInterface,
        srcOut.resolve("${mWidgetPackageName}/${mPageName}Interface.${ktOrJavaExt}")
    )
}