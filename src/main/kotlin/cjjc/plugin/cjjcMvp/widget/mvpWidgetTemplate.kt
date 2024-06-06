package cjjc.plugin.cjjcMvp.widget

import com.android.tools.idea.wizard.template.*
import com.android.tools.idea.wizard.template.impl.activities.common.MIN_API

val mvpWidgetTemplate
    get() = template {
//          revision = 1
        name = "MVP Widget Template"
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

        //创建文件位置的相对全路径(例如：com.xxx.xxx)
        val mRootPackageName = stringParameter {
            name = "Root Package Name"
            constraints = listOf(Constraint.PACKAGE)
            default = "com.cjjc."
            visible = { !isNewModule }
            help = "文件创建位置,此处为默认为选中包的全路径"
        }

        //组件名称
        val mPageName = stringParameter {
            name = "Create Page Name"
            constraints = listOf(Constraint.UNIQUE, Constraint.NONEMPTY)
            default = "Main"
            help = "需要生成页面的名字,不需要再写 名字后缀:如Activity、Fragment、Widget,会自动生成,以及对应文件名后缀"
        }

        //是否生成Widget 布局
        val mIsGenerateWidgetLayout = booleanParameter {
            name = "Generate Fragment Layout"
            default = true
            visible = {true }
            help = "默认勾选,如果使用已存在布局 则无需勾选,若不勾选,创建后记得修改Act或 Fragment 绑定的视图文件！！！"
        }

        //Widget 布局名
        val mWidgetLayoutName = stringParameter {
            name = "Fragment Layout Name"
            default = "widget_}"
            constraints = listOf(Constraint.LAYOUT, Constraint.NONEMPTY)
            suggest = { fragmentToLayout(mPageName.value.toLowerCase(),"widget")}
            visible = { true }
        }

        //组件包名
        val mWidgetPackageName = stringParameter {
            name = "Widget Package Name"
            constraints = listOf(Constraint.NONEMPTY)
            default = "ui"
            visible = { true }
            help = "Fragment 将被输出到此包下,请认真核实此包名是否是你需要输出的目标包名 (基于 Root Package Name )"
        }

        widgets(
            PackageNameWidget(mRootPackageName),
            TextFieldWidget(mPageName),
            CheckBoxWidget(mIsGenerateWidgetLayout),
            TextFieldWidget(mWidgetLayoutName),
            TextFieldWidget(mWidgetPackageName),
        )

        recipe = { data: TemplateData ->
            mvpWidgetRecipe(
                data as ModuleTemplateData,
                mRootPackageName.value,
                mPageName.value,
                mWidgetPackageName.value,
                mIsGenerateWidgetLayout.value,
                mWidgetLayoutName.value
            )
        }
    }

fun createLayoutName(className: String): String {
    val array = className.toCharArray()
    val string = StringBuilder()
    array.forEach {
        if (it.isUpperCase()) {
            //第一个首字母大写的话 不加下划线
            if (string.isNotEmpty()) {
                string.append("_")
            }
            string.append(it.toLowerCase())
        } else {
            string.append(it)
        }
    }
    return string.toString()
}


