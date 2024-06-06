package cjjc.plugin.cjjcMvp.publicBusinessMvp

import com.android.tools.idea.wizard.template.*
import com.android.tools.idea.wizard.template.impl.activities.common.MIN_API


val mvpPublicBusinessTemplate
    get() = template {
//          revision = 1
        name = "MVP PublicBusiness Template"
        description = "一键创建MVP公共业务网络请求所有关联文件"
        minApi = MIN_API
        minApi = MIN_API
        category = Category.Other
        formFactor = FormFactor.Mobile
        screens = listOf(
            WizardUiContext.ActivityGallery,
            WizardUiContext.MenuEntry,
            WizardUiContext.NewProject,
            WizardUiContext.NewModule
        )

        //选中的包名全路径
        val mRootPackageName = stringParameter {
            name = "Root Package Name"
            constraints = listOf(Constraint.PACKAGE)
            default = "com.cjjc."
            visible = { !isNewModule }
            help = "文件创建位置,此处为默认为选中包的全路径"
        }

        //公共业务名称
        val mPageName = stringParameter {
            name = "Create PublicBusiness Name"
            constraints = listOf(Constraint.UNIQUE, Constraint.NONEMPTY)
            default = "Main"
            help = "公共业务名称"
        }

        //公共业务文件夹名
        val mActivityPackageName = stringParameter {
            name = "Activity Package Name"
            constraints = listOf(Constraint.NONEMPTY)
            default = "public_business"
            visible = { true }
            help = "PublicBusiness 将被输出到此包下,请认真核实此包名是否是你需要输出的目标包名 (基于 Root Package Name )"
        }

        //公共业务类型
        val publicBusinessType = booleanParameter {
            name = "Check PublicBusiness Type"
            default = true
            visible = { true}
            help = "默认勾选,Presenter 默认继承 BaseActivityPresenter,不选中则继承BaseFragmentPresenter"
        }

        widgets(
            PackageNameWidget(mRootPackageName),
            TextFieldWidget(mPageName),
            TextFieldWidget(mActivityPackageName),
            CheckBoxWidget(publicBusinessType),
        )

        recipe = { data: TemplateData ->
            mvpBusinessActivityRecipe(
                data as ModuleTemplateData,
                mRootPackageName.value,
                mPageName.value,
                mActivityPackageName.value,
                publicBusinessType.value,
            )
        }
    }

