package com.qfhqfh.plugin

import java.util.jar.JarEntry
import java.util.jar.JarFile

class RouterMappingCollector {
    private final Set<String> mappingClassNames = new HashSet<>()

    private static final String PACKAGE_NAME = 'com\\qfh\\common\\mapping'
    private static final String CLASS_NAME_PREFIX = 'RouterMapping'
    private static final String CLASS_NAME_SUFFIX = '.class'

    //获取收集好的映射表类名
    Set<String> getMappingClassName() {
        return mappingClassNames;
    }
    //收集class文件或者class文件目录中的映射表类。
    void collect(File classFile) {
        if (classFile == null || !classFile.exists()) return
        if (classFile.isFile()) {
            println "qfh classFile.path = ${classFile.path}"
            println "qfh  PACKAGE_NAME ${classFile.absolutePath.contains(PACKAGE_NAME)}"
            println "qfh  classFile.name ${classFile.name}"
            println "qfh  CLASS_NAME_PREFIX ${classFile.name.startsWith(CLASS_NAME_PREFIX)}"
            println "qfh  CLASS_NAME_SUFFIX ${classFile.name.endsWith(CLASS_NAME_SUFFIX)}"
            println "qfh  total ${classFile.absolutePath.contains(PACKAGE_NAME) && classFile.name.startsWith(CLASS_NAME_PREFIX) && classFile.name.endsWith(CLASS_NAME_SUFFIX)}"
            if (classFile.absolutePath.contains(PACKAGE_NAME)
                    && classFile.name.startsWith(CLASS_NAME_PREFIX)
                    && classFile.name.endsWith(CLASS_NAME_SUFFIX)) {
                String className =
                        classFile.name.replace(CLASS_NAME_SUFFIX, "")
                mappingClassNames.add(className)
            }
        } else {
            classFile.listFiles().each {
                collect(it)
            }
        }
    }
    //收集JAR包中的目标类
    void collectFromJarFile(File jarFile) {
        println "qfh jarFile = $jarFile"
        Enumeration enumeration = new JarFile(jarFile).entries()
        String PACKAGE_NAME_JAR_FILE = PACKAGE_NAME.replace("\\","/")
        while (enumeration.hasMoreElements()) {
            JarEntry jarEntry = (JarEntry) enumeration.nextElement()
            String entryName = jarEntry.getName()
            println "qfh entryName = $entryName"
            println "qfh PACKAGE_NAME_JAR_FILE = ${entryName.contains(PACKAGE_NAME_JAR_FILE)}"
            println "qfh CLASS_NAME_PREFIX = ${entryName.contains(CLASS_NAME_PREFIX)}"
            println "qfh CLASS_NAME_SUFFIX = ${entryName.contains(CLASS_NAME_SUFFIX)}"
            if (entryName.contains(PACKAGE_NAME_JAR_FILE)
                    && entryName.contains(CLASS_NAME_PREFIX)
                    && entryName.contains(CLASS_NAME_SUFFIX)) {
                String className = entryName
                        .replace(PACKAGE_NAME_JAR_FILE, "")
                        .replace("/", "")
                        .replace(CLASS_NAME_SUFFIX, "")
                println "qfhqfh className = $className"
                mappingClassNames.add(className)
            }
        }

    }
}