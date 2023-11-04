package com.qfhqfh.plugin

import com.android.build.api.transform.Context
import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.Format
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformInvocation
import com.android.build.api.transform.TransformOutputProvider
import com.android.build.gradle.internal.pipeline.TransformManager
import org.apache.commons.io.FileUtils
import org.gradle.api.Project

import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry

class RouterMappingTransform extends Transform {
    Project project

    RouterMappingTransform(Project project) {
        this.project = project
    }
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

    @Override
    void transform(Context context, Collection<TransformInput> inputs, Collection<TransformInput> referencedInputs,
                   TransformOutputProvider outputProvider,
                   boolean isIncremental) throws IOException, TransformException, InterruptedException {
        super.transform(context, inputs, referencedInputs, outputProvider, isIncremental)
//        project.logger.warn("register RouterMappingTransform")
        RouterMappingCollector collector = new RouterMappingCollector()
        // 1. 遍历所有的Input
        // 2. 对Input进行二次处理
        // 3. 将Input拷贝到目标目录
        inputs.each { TransformInput input ->
            // 把 JAR 类型的输入，拷贝到目标目录
            input.jarInputs.each { JarInput jarInput ->
//                println "jarInput.file.absolutePath:" + jarInput.file.absolutePath
                scanJar(jarInput.file)
                File src = jarInput.file
                File dest = getDestFile(jarInput, outputProvider)
                collector.collectFromJarFile(jarInput.file)
                FileUtils.copyFile(src, dest)
            }
            input.directoryInputs.each { DirectoryInput directoryInput ->
                File dest = outputProvider.getContentLocation(directoryInput.name,
                        directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY)
                collector.collect(directoryInput.file)

                FileUtils.copyDirectory(directoryInput.file, dest)
            }
        }
        println "${getName()} qfhall mapping class name = ${collector.mappingClassName}"
        File mappingJarFile = outputProvider.getContentLocation("router_mapping",
                getOutputTypes(), getScopes(), Format.JAR)
        println "${getName()} qfhall mappingJarFile  = ${mappingJarFile}"
        if (mappingJarFile.getParentFile().exists()) {
            mappingJarFile.getParentFile().mkdirs()
        }
        if (mappingJarFile.exists()) {
            mappingJarFile.delete()
        }
        //字节码写入本地文件
        FileOutputStream fileOutputStream = new FileOutputStream(mappingJarFile)
        JarOutputStream jarOutputStream = new JarOutputStream(fileOutputStream)
        ZipEntry zipEntry = new ZipEntry(RouterMappingByteCodeBuilder.CLASS_NAME + ".class")
        jarOutputStream.putNextEntry(zipEntry)
        jarOutputStream.write(RouterMappingByteCodeBuilder.get(collector.mappingClassName))
        jarOutputStream.closeEntry()
        jarOutputStream.close()
        fileOutputStream.close()
    }

    static File getDestFile(JarInput jarInput, TransformOutputProvider outputProvider) {
        def destName = jarInput.name
        /*  def hexName = DigestUtils.md5Hex(jarInput.file.absolutePath)
          if (destName.endsWith(".jar")) {
              destName = destName.substring(0, destName.length() - 4)
          }*/
        File dest = outputProvider.getContentLocation(jarInput.name, jarInput.contentTypes,
                jarInput.scopes, Format.JAR)
        return dest
    }
    //扫描jar包所有class文件
    private void scanJar(File file) {
        def jarFile = new JarFile(file)
        def enumeration = jarFile.entries()
        while (enumeration.hasMoreElements()) {
            def jarEntry = enumeration.nextElement()
            def entryName = jarEntry.name
            if (entryName.startsWith("android/") || entryName.startsWith("androidx/") || entryName.startsWith("META-INF/")) {
                break
            }
//            project.logger.error("jar----" + file.absolutePath + "class---- $entryName")
        }
        if (jarFile != null) {
            jarFile.close()
        }
    }
//    所有的class收集好以后，会被打包传入这个方法
    /*@Override
    void transform(TransformInvocation transformInvocation)
            throws TransformException, InterruptedException,
                    IOException {
        println "22222222222222222222222222222"
        //遍历Input
        //二次处理
        //将Input拷贝到目标目录
//        遍历所有的输入
        transformInvocation.inputs.each {
            it.directoryInputs.each {
                directoryInput ->
                    def destDit = transformInvocation.outputProvider
                            .getContentLocation(directoryInput.name,
                                    directoryInput.contentTypes,
                                    directoryInput.scopes, Format.DIRECTORY)
                    FileUtils.copyDirectory(directoryInput.file, destDit)
            }
            it.jarInputs.each {
                jarInput ->
                    def dest = transformInvocation.outputProvider
                            .getContentLocation(jarInput.name,
                                    jarInput.contentTypes,
                                    jarInput.scopes, Format.DIRECTORY)
                    FileUtils.copyFile(jarInput.file, dest)
            }
        }
    }*/
}
