package com.example.router_annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//说明当前注解可以修饰的元素，此处表示可以用于标记在类上面
@Target(ElementType.TYPE)
//说明当前注解可以被保留的时间
@Retention(RetentionPolicy.CLASS)
public @interface Destinatign {
    //当前页面的URL，不能为空
    String url();
    //对于当前页面的中文描述
    String description();
}
