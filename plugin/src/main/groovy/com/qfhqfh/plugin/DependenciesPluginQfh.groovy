package com.qfhqfh.plugin

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin;
import org.gradle.api.Plugin
import org.gradle.api.Project
import groovy.json.JsonSlurper

class DependenciesPluginQfh implements Plugin<Project> {
    //实现apply方法，注入插件的逻辑
    //自动帮助用户传递路径参数到注解处理器中
    //实现旧的构建产物的自动清理
    //在javac任务后，汇总生成文档
    @Override
    void apply(Project project) {
        //注册
        println "------------------Transform----------------------"
        def isApp = project.plugins.hasPlugin(AppPlugin)
        if (isApp) {
            println "------------------isApp----------------------"
            def android = project.extensions.getByType(AppExtension)
            android.registerTransform(new RouterMappingTransform(project))
        }
        /*
         AppExtension android = project.extensions.getByType(AppExtension)
         android.registerTransform(new RouterMappingTransform(project))
 //*/
//        if (project.plugins.hasPlugin(AppPlugin)){
//            println "33333333333333333333333333"
//            AppExtension appExtension = project.extensions.getByType(BaseExtension::class.java)
//            Transform transform = new RouterMappingTransform()
//            appExtension.registerTransform(transform)
//        }
//        def androidComponents = project.extensions.getByType(AndroidComponentsExtension::class.java)
//        androidComponents.onVariants {
//            variant ->
//                variant.transformClassesWith(PrivacyClassVisitorFactory::class.java,
//                        InstrumentationScope.ALL) {}
//                variant.setAsmFramesComputationMode(FramesComputationMode.COPY_FRAMES)
//        }

        if (project.extensions.findByName("kapt") != null) {
            project.extensions.findByName("kapt").
                    arguments {
                        arg("root_project_dir", project.rootProject.projectDir.absolutePath)
                    }
        }
//      //删除 上一次构建生成的 router_mapping
        project.clean.doFirst {
            File mappingFile = new File(project.rootProject.projectDir, "router_mapping")
            if (mappingFile.exists()) {
                println ">>>>>>>>>>>>>>>>  mappingFile.exists()"
                mappingFile.deleteDir()
            }
        }
        if (!project.plugins.hasPlugin(AppPlugin)) {
            return
        }
        project.getExtensions().create("router", RouterExtension)

        project.afterEvaluate {
            RouterExtension extension = project["router"]
            println "qfh WIKI path:  ${extension.wikiDir}"
            //javac任务compileDebugJavaWithJavac执行后，
            project.tasks.findAll {
                task ->
                    task.name.startsWith('compile') &&
                            task.name.endsWith('JavaWithJavac')
            }.each {
                task ->
                    task.doLast {
                        File mappingFile = new File(project.rootProject.projectDir, "router_mapping")
                        if (!mappingFile.exists()) {
                            return
                        }
                        File[] allChildFiles = mappingFile.listFiles()
                        if (allChildFiles.length < 1) {
                            return
                        }
                        StringBuilder stringBuilder = new StringBuilder()
                        stringBuilder.append("# 文档\n\n")
                        allChildFiles.each {
                            child ->
                                if (child.name.endsWith(".json")) {
                                    JsonSlurper slurper = new JsonSlurper()
                                    def content = slurper.parse(child)
                                    content.each {
                                        innerContent ->
                                            def url = innerContent['url']
                                            def description = innerContent['description']
                                            def realPath = innerContent['realPath']
                                            stringBuilder.append("## $description \n")
                                            stringBuilder.append("--- url $url \n")
                                            stringBuilder.append("--- realPath $realPath \n\n")

                                    }
                                }
                        }
                        File fileDir = new File(extension.wikiDir)
                        if (!fileDir.exists()) {
                            fileDir.mkdir()
                        }
                        File file = new File(fileDir, "页面文档.md")
                        if (file.exists()) {
                            file.delete()
                        }
                        file.write(stringBuilder.toString())
                    }
            }
        }
        println ">>>>>>>>>>>>>>>>  " + this.getClass().getName()
    }
}
