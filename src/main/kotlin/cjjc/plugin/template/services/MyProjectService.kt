package cjjc.plugin.template.services

import com.intellij.openapi.project.Project
import cjjc.plugin.template.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
