package com.example.yxy.config.java.running;


import com.example.yxy.config.annotation.ESSearchTaskAnnotation;
import com.example.yxy.constant.ESConstant;
import com.example.yxy.service.java.running.JavaRunningService;
import groovy.lang.GroovyClassLoader;
import groovy.lang.Tuple2;
import groovy.lang.Tuple3;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class JavaRunningFactory {
    private static GroovyClassLoader groovyClassLoader = new GroovyClassLoader();
    private static ConcurrentMap<String, Class<?>> CLASS_CACHE = new ConcurrentHashMap<>();

    private static ConcurrentMap<String, Class<?>> CLASS_INSTANCE_CACHE = new ConcurrentHashMap<>();

    private static Class<?> getCodeSourceClass(String codeSource) {
        try {
            // md5
            byte[] md5 = MessageDigest.getInstance("MD5").digest(codeSource.getBytes());
            String md5Str = new BigInteger(1, md5).toString(16);

            Class<?> clazz = CLASS_CACHE.get(md5Str);
            if (clazz == null) {
                clazz = groovyClassLoader.parseClass(codeSource);
                CLASS_CACHE.putIfAbsent(md5Str, clazz);
            }
            return clazz;
        } catch (Exception e) {
            return groovyClassLoader.parseClass(codeSource);
        }
    }

    public static void loadNewInstance2Method(String codeSource) throws Exception {
        if (codeSource != null && codeSource.trim().length() > 0) {
            Class<?> clazz = getCodeSourceClass(codeSource);
            if (clazz == null) {
                return;
            }
            Object instance = clazz.newInstance();
            if (instance == null) {
                return;
            }
            Method[] methods = instance.getClass().getMethods();
            if (methods == null) {
                return;
            }
            for (int i = 0; i < methods.length; i++) {
                Method method = methods[i];
                ESSearchTaskAnnotation annotation = AnnotationUtils.findAnnotation(method, ESSearchTaskAnnotation.class);
                if (annotation == null) {
                    continue;
                }
                String taskName = annotation.searchId() + ESConstant.ESSEARCHID_AND_TYPE_SPLIT + annotation.type();
                Tuple2<Method,Object> t = Tuple2.tuple(method,instance);
                ESConstant.SCHEDULE_METHOD2_MAP.put(taskName,t);
            }
        }
    }

    public static JavaRunningService loadNewInstance(String codeSource) throws Exception {
        if (codeSource != null && codeSource.trim().length() > 0) {
            Class<?> clazz = getCodeSourceClass(codeSource);
            if (clazz != null) {
                Object instance = clazz.newInstance();
                if (instance != null) {
                    if (instance instanceof JavaRunningService) {
                        injectService(instance);
                        return (JavaRunningService) instance;
                    } else {
                        throw new IllegalArgumentException(">>>>>>>>>>> xxl-glue, loadNewInstance error, "
                                + "cannot convert from instance[" + instance.getClass() + "] to IJobHandler");
                    }
                }
            }
        }
        throw new IllegalArgumentException(">>>>>>>>>>> xxl-glue, loadNewInstance error, instance is null");
    }

    public static void injectService(Object instance) {

    }


}
