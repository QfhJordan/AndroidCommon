package com.qfh.route.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class RouterPlugin implements Plugin<Project> {
    //实现apply方法，注入插件的逻辑
    @Override
    void apply(Project project) {
        println "Router apply ${project.name}"
        project.getExtensions().create("router", RouterExtension)
        project.afterEvaluate {
            RouterExtension routerExtension = project["router"]
            println "The WIKI path set by the user is:${routerExtension.wikiDir}"
        }
    }
}