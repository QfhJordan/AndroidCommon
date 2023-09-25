package com.example.router_processor;

import com.example.router_annotations.Destinatign;
import com.google.auto.service.AutoService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

@AutoService(Process.class)
public class MyClass extends AbstractProcessor {
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        System.out.println("Hello apt1111");
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Hello apt");
    }

    private static final String TAG = "DestinationProcessor";

    //告诉编译器，当前处理器支持的注解类型
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> hashSet = new HashSet<>();
        hashSet.add(Destinatign.class.getCanonicalName());
        return hashSet;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return processingEnv.getSourceVersion();
    }

    //编译器找到我们关心的注解后，会回调这个方法
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        //避免多次调用process
        if (roundEnv.processingOver()) {
            return false;
        }
        String rootDir = processingEnv.getOptions().get("root_project_dir");
        System.out.println("root_project_dir = " + rootDir);
        //        if (rootDir != null) {
//            throw new RuntimeException("root_project_dir = " + rootDir);
//        }
        //将要自动生成的类的类名
        String className = "RouterMapping_" + System.currentTimeMillis();
        StringBuilder builder = new StringBuilder();
        builder.append("package com.qfh.common.mapping;\n\n");
        builder.append("import java.util.HashMap;\n");
        builder.append("import java.util.Map;\n\n");
        builder.append("public class ").append(className).append(" {\n\n");
        builder.append("    public static Map<String, String> get() {\n\n");
        builder.append("        Map<String,String> mapping = new HashMap<>();\n\n");

        JsonArray jsonArray = new JsonArray();
        System.out.println(TAG + ">>>>>>>process.....");
        //获取所有标记了@Destination 注解的类的信息
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Destinatign.class);

        System.out.println(TAG + ">>>>>Number of annotations= " + elements.size());
        //当未收集到 @Destination 注解的时候，跳过后续流程
        if (elements.size() < 1) {
            return false;
        }
        //遍历所有 @Destination 注解信息，挨个获取详细信息
        for (Element element1 : elements) {
            TypeElement typeElement = (TypeElement) element1;
//            尝试在当前类上，获取 @Destination 的信息
            Destinatign destinatign = typeElement.getAnnotation(Destinatign.class);
            if (destinatign == null) {
                continue;
            }
            String url = destinatign.url();
            String description = destinatign.description();
            String realPath = typeElement.getQualifiedName().toString();
            System.out.println(TAG + "........url " + url + "....description " + description + ".....realPath " + realPath);
            builder.append("        ").append("mapping.put(").append("\"" + url + "\"")
                    .append(", ")
                    .append("\"" + realPath + "\"")
                    .append(");\n");
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("url", url);
            jsonObject.addProperty("description", description);
            jsonObject.addProperty("realPath", realPath);
            jsonArray.add(jsonObject);
        }
        builder.append("        return mapping;\n");
        builder.append("    }\n");
        builder.append("}\n");

        String mappningFullclassName = "com.qfh.common.mapping." + className;
        System.out.println(TAG + ".....mappningFullclassName = " + mappningFullclassName);
        System.out.println(TAG + "....class content = " + builder);
        try {
            //写入自动生成的类到本地文件中
            JavaFileObject javaFileObject = processingEnv.getFiler().createSourceFile(mappningFullclassName);
            Writer writer = javaFileObject.openWriter();
            writer.write(builder.toString());
            writer.flush();
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException("Error while create " + e);
        }
        //写入JSON到本地文件中
        File rootFile = new File(rootDir);
        if (!rootFile.exists()) {
            throw new RuntimeException("root_project_dir not exist" + rootFile);
        }
//        创建router_mapping 子目录
        File jsonFile = new File(rootFile, "router_mapping");
        if (!jsonFile.exists()) {
            jsonFile.mkdir();
        }
        File mappingFile = new File(jsonFile, className + "mapping_" + System.currentTimeMillis() + ".json");
        //写入json内容
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(mappingFile));
            String  json = jsonArray.toString();
            writer.write(json);
            writer.flush();
            writer.close();
        } catch (Throwable e) {
            throw new RuntimeException("Error while create " + e);
        }


        System.out.println(",,,,,,finish");
        return false;
    }

}