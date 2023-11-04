package com.qfhqfh.plugin

import org.objectweb.asm.ClassWriter
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class RouterMappingByteCodeBuilder implements Opcodes {
    public static final String CLASS_NAME =
            "com/qfh/router/mapping/generated/RouterMapping"

    static byte[] get(Set<String> names) {
        // 1. 创建一个类
        // 2. 创建构造方法
        // 3. 创建get方法
        //   （1）创建一个Map
        //   （2）塞入所有映射表的内容
        //   （3）返回map
        //        classWriter.visit(V11, ACC_PUBLIC | ACC_SUPER,
        //        "com/example/common/sample/RouterMapping", null, "java/lang/Object", null);
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS)
        writer.visit(V11, ACC_PUBLIC | ACC_SUPER, "com/example/common/sample/RouterMapping", null,
                "java/lang/Object", null)
        // 生成或者编辑方法
        MethodVisitor mv

        // 创建构造方法
        mv = writer.visitMethod(ACC_PUBLIC,
                "<init>", "()V", null, null)
        mv.visitCode()
        mv.visitVarInsn(ALOAD, 0)
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object",
                "<init>", "()V", false)
        mv.visitInsn(RETURN)
        mv.visitMaxs(1, 1)
        mv.visitEnd()

        // 创建get方法
        mv = writer.visitMethod(ACC_PUBLIC + ACC_STATIC,
                "get", "()Ljava/util/Map;",
                "()Ljava/util/Map<Ljava/lang/String;Ljava/lang/String;>;",
                null)
        mv.visitCode()
        mv.visitTypeInsn(NEW, "java/util/HashMap")
        mv.visitInsn(DUP)
        mv.visitMethodInsn(INVOKESPECIAL, "java/util/HashMap",
                "<init>", "()V", false)
        mv.visitVarInsn(ASTORE, 0)

        // 向Map中，逐个塞入所有映射表的内容
        names.each {
            mv.visitVarInsn(ALOAD, 0)
            mv.visitMethodInsn(INVOKESTATIC, "com/example/common/sample/$it",
                    "get", "()Ljava/util/Map;",
                    false)
            mv.visitMethodInsn(INVOKEINTERFACE,
                    "java/util/Map", "putAll", "(Ljava/util/Map;)V"
                    , true)
        }
        mv.visitVarInsn(ALOAD, 0)
        mv.visitInsn(ARETURN)
        mv.visitMaxs(2, 2)
        mv.visitEnd()
        return writer.toByteArray()

    }
}