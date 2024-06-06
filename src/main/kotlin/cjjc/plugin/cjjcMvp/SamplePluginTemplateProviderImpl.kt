package cjjc.plugin.cjjcMvp

import com.android.tools.idea.wizard.template.Template
import com.android.tools.idea.wizard.template.WizardTemplateProvider
import cjjc.plugin.cjjcMvp.activity.mvpActivityTemplate
import cjjc.plugin.cjjcMvp.businessActivity.mvpActivityBusinessTemplate
import cjjc.plugin.cjjcMvp.businessFragment.mvpFragmentBusinessTemplate
import cjjc.plugin.cjjcMvp.fragment.mvpFragmentTemplate
import cjjc.plugin.cjjcMvp.libraryDemo.libraryDemoTemplate
import cjjc.plugin.cjjcMvp.publicBusinessMvp.mvpPublicBusinessTemplate
import cjjc.plugin.cjjcMvp.widget.mvpWidgetTemplate
//https://www.jianshu.com/p/fa9b1357ebe7 文档
class SamplePluginTemplateProviderImpl: WizardTemplateProvider() {
    override fun getTemplates(): List<Template> = listOf(
        mvpActivityTemplate,
        mvpActivityBusinessTemplate,
        mvpFragmentTemplate,
        mvpFragmentBusinessTemplate,
        mvpWidgetTemplate,
        mvpPublicBusinessTemplate,
        libraryDemoTemplate
    )
}