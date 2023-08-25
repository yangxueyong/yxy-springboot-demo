package com.example.yxy.config.log;

import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import cn.hutool.core.thread.NamedThreadFactory;
import cn.hutool.extra.spring.SpringUtil;
import com.example.yxy.entity.log.EasySlf4jLogging;
import com.example.yxy.service.EasySlf4jLoggingService;
import com.example.yxy.util.DateUtils;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 自定义 日期 过滤器
 *
 * @author yxy
 * @date 2023/08/22
 */
public class LogFilter extends Filter<LoggingEvent> {
    /**
     * 丢弃老的
     */
    RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.DiscardOldestPolicy();
    /**
     * 队列深度，超过这个深度，历史的会被抛弃掉
     */
    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(1000);
    /**
     * 线程池
     */
    ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 10, TimeUnit.SECONDS, workQueue, new ThreadFactory() {
        private final AtomicInteger counter = new AtomicInteger();
        @Override
        public Thread newThread(Runnable r) {
            final String threadName = String.format("custom_%s_%d", "log", counter.incrementAndGet());
            return new Thread(r, threadName);
        }
    }, rejectedExecutionHandler);

    public EasySlf4jLoggingService easySlf4jLoggingService = null;

    public static final String DB_MARKER_ASYNC_STR = "DB_Marker_async";
    public static final String DB_MARKER_SYNC_STR = "DB_Marker_sync";
    /**
     * 异步
     */
    public static final Marker DB_MARKER_ASYNC = MarkerFactory.getMarker(DB_MARKER_ASYNC_STR);
    /**
     * 同步
     */
    public static final Marker DB_MARKER_SYNC = MarkerFactory.getMarker(DB_MARKER_SYNC_STR);

    @Override
    public FilterReply decide(LoggingEvent event) {
        Marker marker = event.getMarker();
        //同步
        if (DB_MARKER_SYNC_STR.equals(marker.getName())) {
            saveLog2DB(event);
            return FilterReply.ACCEPT;
            //异步
        } else if (DB_MARKER_ASYNC_STR.equals(marker.getName())) {
            executor.submit(() -> {
                saveLog2DB(event);
            });
            return FilterReply.ACCEPT;
        } else {
            //非项目本身的日志不会入库
            return FilterReply.DENY;
        }
    }

    /**
     * 保存日志到db
     *
     * @param event
     */
    private void saveLog2DB(LoggingEvent event) {
        String loggerName = event.getLoggerName();
        EasySlf4jLogging log = new EasySlf4jLogging();
        StackTraceElement[] callerData = event.getCallerData();
        if (callerData != null && callerData.length > 0) {
            StackTraceElement callerDatum = callerData[0];
            if (callerDatum != null) {
                int lineNumber = callerDatum.getLineNumber();
                String methodName = callerDatum.getMethodName();
                //行号
                log.setLogLineNum(lineNumber);
                //方法
                log.setLogMethod(methodName);
            }
        }
        //时间
        log.setLogTime(DateUtils.getCurrentLocalDateTime());
        //线程名称
        log.setLogThread(event.getThreadName());
        //类
        log.setLogClass(loggerName);
        //日志级别
        log.setLogLevel(event.getLevel().levelStr);
        //记录日志跟踪号
        log.setTrackId(event.getMDCPropertyMap().get("TRACE_ID"));
        //日志内容
        log.setLogContent(event.getFormattedMessage());
        easySlf4jLoggingService = SpringUtil.getBean(EasySlf4jLoggingService.class);
        //日志入库
        easySlf4jLoggingService.asyncSave(log);
    }
}
