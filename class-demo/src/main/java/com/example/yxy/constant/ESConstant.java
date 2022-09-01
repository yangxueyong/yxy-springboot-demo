package com.example.yxy.constant;

import com.example.yxy.entity.bean.vo.MethodVO;
import groovy.lang.Tuple2;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ESConstant {
    public static Map<String, List<MethodVO>> SCHEDULE_METHOD_MAP = new HashMap<>();

    public static Map<String, Tuple2<Method,Object>> SCHEDULE_METHOD2_MAP = new HashMap<>();

    public final static ThreadPoolExecutor ES_SEARCH_POOL_EXECUTOR = new ThreadPoolExecutor(15, 50, 0, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<>(5000),
            Executors.defaultThreadFactory());

    public final static String ESSEARCHID_AND_TYPE_SPLIT = ":";
}
