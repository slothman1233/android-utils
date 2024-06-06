package cjjc.plugin.cjjcMvp.businessActivity

import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.RecipeExecutor
import com.android.tools.idea.wizard.template.impl.activities.common.generateManifest
import cjjc.plugin.cjjcMvp.activity.mvp.mvpActivityPresenterKt
import cjjc.plugin.cjjcMvp.mvp.mvpInterfaceKt
import cjjc.plugin.cjjcMvp.mvp.mvpModelKt
import cjjc.plugin.cjjcMvp.comm.res.layout.mvpXml


fun RecipeExecutor.mvpBusinessActivityRecipe(
    moduleTemplateData: ModuleTemplateData,
    mRootPackageName: String,
    mPageName: String,
    mActivityLayoutName: String,
    mIsGenerateActivityLayout: Boolean,
    mActivityPackageName: String,
) {
    val (projectData, srcOut, resOut) = moduleTemplateData
    val ktOrJavaExt = projectData.language.extension


        val split = mRootPackageName.split(".")
        val mApplicationId= split[0]+"."+split[1]+"."+split[2]
        generateManifest(
            moduleData = moduleTemplateData,
            activityClass = "${mPageName}Activity",
            packageName = ".${split[split.lastIndex]}.${mActivityPackageName.replace("/", ".")}",
            isLauncher = false,
            hasNoActionBar = false,
            generateActivityTitle = false
        )

        val mvvmActivity =
            mvpBusinessActivityKt(mRootPackageName,mApplicationId, mActivityPackageName.replace("/", "."), mPageName,mActivityLayoutName)
        // 保存Activity
        save(
            mvvmActivity,
            srcOut.resolve("${mActivityPackageName}/${mPageName}Activity.${ktOrJavaExt}")
        )
        if (mIsGenerateActivityLayout) {
            // 保存xml
            save(mvpXml(), resOut.resolve("layout/${mActivityLayoutName}.xml"))
        }
        // 保存Model
        val mvpModel =
            mvpModelKt(mRootPackageName, mActivityPackageName.replace("/", "."), mPageName)
        save(
            mvpModel,
            srcOut.resolve("${mActivityPackageName}/${mPageName}Model.${ktOrJavaExt}")
        )
        // 保存Presenter
        val mvpPresenter =
            mvpActivityPresenterKt(mRootPackageName, mActivityPackageName.replace("/", "."), mPageName)
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

