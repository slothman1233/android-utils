package cjjc.plugin.cjjcMvp.libraryDemo

import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.RecipeExecutor
import cjjc.plugin.cjjcMvp.activity.mvp.mvpActivityPresenterKt
import cjjc.plugin.cjjcMvp.fragment.mvp.mvpFragmentPresenterKt
import cjjc.plugin.cjjcMvp.libraryDemo.common.adapter.testAdapterKt
import cjjc.plugin.cjjcMvp.libraryDemo.common.api.apiKt
import cjjc.plugin.cjjcMvp.libraryDemo.common.base.baseKt
import cjjc.plugin.cjjcMvp.libraryDemo.common.bean.testBeanKt
import cjjc.plugin.cjjcMvp.libraryDemo.common.config.configInfoKt
import cjjc.plugin.cjjcMvp.libraryDemo.common.config.configKt
import cjjc.plugin.cjjcMvp.libraryDemo.common.constant.constantKt
import cjjc.plugin.cjjcMvp.libraryDemo.common.hilt.hiltKt
import cjjc.plugin.cjjcMvp.libraryDemo.common.listener.listenerKt
import cjjc.plugin.cjjcMvp.libraryDemo.common.plugins.pluginsKt
import cjjc.plugin.cjjcMvp.libraryDemo.common.router.interceptor.interceptorKt
import cjjc.plugin.cjjcMvp.libraryDemo.common.router.routerKt
import cjjc.plugin.cjjcMvp.libraryDemo.common.serve.get.call.serveGetDataKt
import cjjc.plugin.cjjcMvp.libraryDemo.common.serve.get.impl.serveGetDataImplKt
import cjjc.plugin.cjjcMvp.libraryDemo.common.serve.set.serveSetDataKt
import cjjc.plugin.cjjcMvp.mvp.mvpInterfaceKt
import cjjc.plugin.cjjcMvp.mvp.mvpModelKt


fun RecipeExecutor.libraryDemoRecipe(
    moduleTemplateData: ModuleTemplateData,
    mRootPackageName: String,
    mLibraryName: String,
) {
    val (projectData, srcOut, resOut) = moduleTemplateData
    val ktOrJavaExt = projectData.language.extension
    val rootCommonName = "common/"
    //处理根路径 拼接 common 层级
    val mRootPackageNames = mRootPackageName + "." + rootCommonName.replace("/", "")
    // 保存Adapter
    val adapterCommon =
        testAdapterKt(mRootPackageNames, "adapter", "TestAdapter")
    save(
        adapterCommon,
        srcOut.resolve("${rootCommonName}adapter/TestAdapter.${ktOrJavaExt}")
    )

    // 保存Api
    val apiCommon =
        apiKt(mRootPackageNames, "api", "Api${mLibraryName}")
    save(
        apiCommon,
        srcOut.resolve("${rootCommonName}api/Api${mLibraryName}.${ktOrJavaExt}")
    )

    // 保存Base
    val baseCommon =
        baseKt(mRootPackageNames, "base", "${mLibraryName}Base")
    save(
        baseCommon,
        srcOut.resolve("${rootCommonName}base/${mLibraryName}Base.${ktOrJavaExt}")
    )

    // 保存Bean
    val beanCommon =
        testBeanKt(mRootPackageNames, "bean", "TestBean")
    save(
        beanCommon,
        srcOut.resolve("${rootCommonName}bean/TestBean.${ktOrJavaExt}")
    )

    // 保存Config
    val configCommon =
        configKt(mRootPackageNames, "config", "Config${mLibraryName}", mLibraryName)
    save(
        configCommon,
        srcOut.resolve("${rootCommonName}config/Config${mLibraryName}.${ktOrJavaExt}")
    )

    // 保存ConfigInfo
    val configInfoCommon =
        configInfoKt(mRootPackageNames, "config", "Config${mLibraryName}Info")
    save(
        configInfoCommon,
        srcOut.resolve("${rootCommonName}config/Config${mLibraryName}Info.${ktOrJavaExt}")
    )

    // 保存Constant
    val constantCommon =
        constantKt(mRootPackageNames, "constant", "ConstantCode${mLibraryName}")
    save(
        constantCommon,
        srcOut.resolve("${rootCommonName}constant/ConstantCode${mLibraryName}.${ktOrJavaExt}")
    )

    // 保存Hilt
    val hiltCommon =
        hiltKt(mRootPackageNames, "hilt", "I${mLibraryName}Module")
    save(
        hiltCommon,
        srcOut.resolve("${rootCommonName}hilt/I${mLibraryName}Module.${ktOrJavaExt}")
    )

    // 保存Listener
    val listenerCommon =
        listenerKt(mRootPackageNames, "listener", "${mLibraryName}Listener")
    save(
        listenerCommon,
        srcOut.resolve("${rootCommonName}listener/${mLibraryName}Listener.${ktOrJavaExt}")
    )

    // 保存Plugins
    val pluginsCommon =
        pluginsKt(mRootPackageNames, "plugins", "${mLibraryName}Plugins")
    save(
        pluginsCommon,
        srcOut.resolve("${rootCommonName}plugins/${mLibraryName}Plugins.${ktOrJavaExt}")
    )

    // 保存Model
    val mvpModel =
        mvpModelKt(mRootPackageNames, "public_business", "${mLibraryName}Public")
    save(
        mvpModel,
        srcOut.resolve("${rootCommonName}public_business/${mLibraryName}PublicModel.${ktOrJavaExt}")
    )

    // 保存Presenter
    val mvpPresenter =
        mvpActivityPresenterKt(mRootPackageNames,"public_business", "${mLibraryName}Public")
    save(
        mvpPresenter,
        srcOut.resolve("${rootCommonName}public_business/${mLibraryName}PublicPresenter.${ktOrJavaExt}")
    )

    // 保存Interface
    val mvpInterface =
        mvpInterfaceKt(mRootPackageNames, "public_business", "${mLibraryName}Public")
    save(
        mvpInterface,
        srcOut.resolve("${rootCommonName}public_business/${mLibraryName}PublicInterface.${ktOrJavaExt}")
    )

    // 保存Router
    val routerCommon =
        routerKt(mRootPackageNames, "router", "ARouterPath${mLibraryName}",mLibraryName,mLibraryName.toUpperCase(),mLibraryName.toLowerCase())
    save(
        routerCommon,
        srcOut.resolve("${rootCommonName}router/ARouterPath${mLibraryName}.${ktOrJavaExt}")
    )

    // 保存Interceptor
    val interceptorCommon =
        interceptorKt(mRootPackageNames, "router.interceptor", "${mLibraryName}Interceptor",mLibraryName,mLibraryName.toLowerCase())
    save(
        interceptorCommon,
        srcOut.resolve("${rootCommonName}router/interceptor/${mLibraryName}Interceptor.${ktOrJavaExt}")
    )

    // 保存ServeGetData
    val serveGetDataCommon =
        serveGetDataKt(mRootPackageNames, "serve.get.call", "Serve${mLibraryName}GetData")
    save(
        serveGetDataCommon,
        srcOut.resolve("${rootCommonName}serve/get/call/Serve${mLibraryName}GetData.${ktOrJavaExt}")
    )

    // 保存ServeGetDataImpl
    val serveGetDataImplCommon =
        serveGetDataImplKt(mRootPackageNames, "serve.get.impl", "Serve${mLibraryName}GetDataImpl",mLibraryName,mLibraryName.toUpperCase())
    save(
        serveGetDataImplCommon,
        srcOut.resolve("${rootCommonName}serve/get/impl/Serve${mLibraryName}GetDataImpl.${ktOrJavaExt}")
    )

    // 保存ServeSetData
    val serveSetDataCommon =
        serveSetDataKt(mRootPackageNames, "serve.set", "Serve${mLibraryName}SetData")
    save(
        serveSetDataCommon,
        srcOut.resolve("${rootCommonName}serve/set/Serve${mLibraryName}SetData.${ktOrJavaExt}")
    )



}


