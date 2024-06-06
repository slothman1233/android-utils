package cjjc.plugin.cjjcMvp.libraryDemo

import com.android.tools.idea.wizard.template.*
import com.android.tools.idea.wizard.template.impl.activities.common.MIN_API

val libraryDemoTemplate
    get() = template {
        name = "MVP Library Common Template"
        description = "一键创建MVP模式所有关联文件"
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
        //模块名
        val mLibraryName = stringParameter {
            name = "Library Name"
            constraints = listOf(Constraint.NONEMPTY)
            default = "Test"
            visible = { true }
            help = "模块名 用于给Common中文件命名使用"
        }
        widgets(
            PackageNameWidget(mRootPackageName),
            TextFieldWidget(mLibraryName),
            )

        recipe = { data: TemplateData ->
            libraryDemoRecipe(
                data as ModuleTemplateData,
                mRootPackageName.value,
                mLibraryName.value,
            )
        }
    }

