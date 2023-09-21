package com.qfhqfh.plugin

import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager

class RouterMappingTransform extends Transform {
    // Transform 的名称
    @Override
    String getName() {
        return "RouterMappingTransform"
    }
    //告知编译器，Transform需要消费的输入类型
    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }
    //Transform需要收集的范围
    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }
    //是否支持增量
    @Override
    boolean isIncremental() {
        return false
    }
//    所有的class收集好以后，会被打包传入这个方法
    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        println "22222222222222222222222222222"
        //遍历Input
        //二次处理
        //将Input拷贝到目标目录
//        遍历所有的输入
        transformInvocation.inputs.each {
            //文件夹 类型，拷贝到目标目录
            it.directoryInputs.each {
                directoryInput ->
                    def testDir = transformInvocation.outputProvider.
                            getContentLocation(directoryInput.name,
                                    directoryInput.contentTypes,
                                    directoryInput.scopes, Format.DIRECTORY)
                    FileUtils.copyDirectory(directoryInput.file, testDir)
            }
            //把 JAR 类型，拷贝到目标目录
            it.jarInputs.each {
                jarInput ->
                    def testJar = transformInvocation.outputProvider.
                            getContentLocation(jarInput.name,
                                    jarInput.contentTypes,
                                    jarInput.scopes, Format.JAR)
                    FileUtils.copyFile(jarInput.file, testJar)
            }
        }
    }
}
