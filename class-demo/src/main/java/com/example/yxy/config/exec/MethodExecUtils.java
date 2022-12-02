package com.example.yxy.config.exec;//package com.cqrcb.cloud.service.equity;

import com.example.yxy.config.spring.SpringUtil;
import com.example.yxy.config.annotation.ESSearchTaskAnnotation;
import com.example.yxy.config.handle.ESSearchTaskHandle;
import com.example.yxy.constant.ESConstant;
import com.example.yxy.entity.bean.vo.MethodVO;
import groovy.lang.Tuple2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
public class MethodExecUtils {

    public static Map search(String searchId,String types,String param) {
        //最终的结果
        Map resultMap = new HashMap();
        //拆解type
        String[] typess = types.split(",");

        ESSearchTaskHandle handle = null;
        List<ESSearchTaskHandle> listHandles = new ArrayList<>();
        //异步执行所有type
        for (String type : typess) {
            String key = searchId + ESConstant.ESSEARCHID_AND_TYPE_SPLIT + type;
            List<MethodVO> methodVOS = ESConstant.SCHEDULE_METHOD_MAP.get(key);
            if (!CollectionUtils.isEmpty(methodVOS)) {
                for (MethodVO methodVO : methodVOS) {
                    Method method = methodVO.getMethod();
                    Object clsBean = methodVO.getClsBean();
                    handle = () -> {
                        try {
                            method.invoke(clsBean, resultMap, param);
                        } catch (Exception e) {
                            log.error("执行代理方法报错了1->",e);
                        }
                    };
                    //这里装入list是为了后面执行的时候使用CountDownLatch
                    listHandles.add(handle);
                }
            }
            Tuple2<Method, Object> methodObjectTuple2 = ESConstant.SCHEDULE_METHOD2_MAP.get(key);
            if(methodObjectTuple2 != null){
                Method method = methodObjectTuple2.getV1();
                Object clsBean = methodObjectTuple2.getV2();
                handle = () -> {
                    try {
                        method.invoke(clsBean, resultMap, param);
                    } catch (Exception e) {
                        log.error("执行代理方法报错了2->",e);
                    }
                };
                //这里装入list是为了后面执行的时候使用CountDownLatch
                listHandles.add(handle);
            }
        }

        CountDownLatch cd = new CountDownLatch(listHandles.size());
        for (ESSearchTaskHandle handle1 : listHandles) {
            ESConstant.ES_SEARCH_POOL_EXECUTOR.submit(() -> {
                handle1.doExecForCD(cd);
            });
        }
        try {
            //只等待10秒钟
            cd.await(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("等待执行失败->", e);
        }
        return resultMap;
    }

    /**
     * 获取所有task的bean方法
     */
    public static <T> Map<String, List<MethodVO>> getScheduleMethodMap(Class<T> tClass) {
        ApplicationContext context = SpringUtil.getApplicationContext();
        if (context == null) {
            return null;
        }
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        if (beanDefinitionNames == null) {
            return null;
        }
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = SpringUtil.getBean(beanDefinitionName);
            Method[] methods = bean.getClass().getMethods();
//            String name = bean.getClass().getName();
            for (Method method : methods) {
                String taskName = null;
                if (tClass == ESSearchTaskAnnotation.class) {
                    ESSearchTaskAnnotation annotation = AnnotationUtils.findAnnotation(method, ESSearchTaskAnnotation.class);
                    if (annotation != null) {
                        taskName = annotation.searchId() + ESConstant.ESSEARCHID_AND_TYPE_SPLIT + annotation.type();
                    }
                }
                if (!StringUtils.hasText(taskName)) {
                    continue;
                }
                if (!ESConstant.SCHEDULE_METHOD_MAP.containsKey(taskName)) {
                    ESConstant.SCHEDULE_METHOD_MAP.put(taskName, new ArrayList<>());
                }
                ESConstant.SCHEDULE_METHOD_MAP.get(taskName).add(new MethodVO(bean, method));
            }
        }
        return ESConstant.SCHEDULE_METHOD_MAP;
    }


}
