package com.yechaoa.plugin;


import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * GitHub : https://github.com/yechaoa
 * CSDN : http://blog.csdn.net/yechaoa
 * <p>
 * Created by yechao on 2023/8/8.
 * Describe :
 */
class DependenciesPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        System.out.println(">>>>>>>>  " + this.getClass().getName());
    }
}
