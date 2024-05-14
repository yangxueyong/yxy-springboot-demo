package com.example.yxy.util;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
public class SSEUtils {
    // timeout
    private static Long DEFAULT_TIME_OUT = 12*60*1000L;
    // 订阅表
    private static Map<String, SseEmitter> subscribeMap = new ConcurrentHashMap<>();
    /** 添加订阅 */
    public static SseEmitter addSub(String questionId) {
        if (null == questionId || "".equals(questionId)) {
            return null;
        }
        SseEmitter emitter = subscribeMap.get(questionId);
        if (null == emitter) {
            emitter = new SseEmitter(DEFAULT_TIME_OUT);
            subscribeMap.put(questionId, emitter);
        }
        return emitter;
    }
    /** 发消息 */
    public static void pubMsg(String questionId, String msg) {
        SseEmitter emitter = subscribeMap.get(questionId);
        if (null != emitter) {
            try {
                // 更规范的消息结构看源码
                emitter.send(SseEmitter.event().data(msg));
            } catch (Exception e) {
                // e.printStackTrace();
            }
        }
    }
    /**
     * 关闭订阅 
     * @param questionId
     */
    public static void closeSub(String questionId) {
        SseEmitter emitter = subscribeMap.get(questionId);
        if (null != emitter) {
            try {
                emitter.complete();
                subscribeMap.remove(questionId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}