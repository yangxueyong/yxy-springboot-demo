package com.example.yxy.service.java.running;


import groovy.lang.GroovyClassLoader;

public class JavaRunningService {
    private GroovyClassLoader groovyClassLoader = new GroovyClassLoader();

    private Class<?> getCodeSourceClass(String codeSource){
        try {
            return groovyClassLoader.parseClass(codeSource);
        } catch (Exception e) {
            return groovyClassLoader.parseClass(codeSource);
        }
    }
}
