package com.example.yxy.config.log;

import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import cn.hutool.extra.spring.SpringUtil;
import com.example.yxy.entity.log.EasySlf4jLogging;
import com.example.yxy.service.EasySlf4jLoggingService;
import com.example.yxy.util.DateUtils;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.util.concurrent.*;


/**
 * 自定义 日期 过滤器
 * @author yxy
 * @date 2023/08/22
 */
public class LogFilter extends Filter<LoggingEvent> {
    RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.DiscardOldestPolicy();

    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(2);

    ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 10,
            TimeUnit.SECONDS, workQueue, rejectedExecutionHandler);

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
        if(DB_MARKER_SYNC_STR.equals(marker.getName())){
            saveLog2DB(event);
            return FilterReply.ACCEPT;
            //异步
        }else if(DB_MARKER_ASYNC_STR.equals(marker.getName())){
            executor.submit(()->{
                saveLog2DB(event);
            });
            return FilterReply.ACCEPT;
        }else{
            //非项目本身的日志不会入库
            return FilterReply.DENY;
        }
    }

    /**
     * 报错日志到db
     * @param event
     */
    private void saveLog2DB(LoggingEvent event) {
        String loggerName = event.getLoggerName();
        EasySlf4jLogging log = new EasySlf4jLogging();
        StackTraceElement[] callerData = event.getCallerData();
        if(callerData != null && callerData.length > 0){
            StackTraceElement callerDatum = callerData[0];
            if(callerDatum != null){
                int lineNumber = callerDatum.getLineNumber();
                String methodName = callerDatum.getMethodName();
                log.setLogLineNum(lineNumber);
                log.setLogMethod(methodName);
            }
        }
        log.setLogTime(DateUtils.getCurrentLocalDateTime());
        log.setLogThread(event.getThreadName());
        log.setLogClass(loggerName);
        log.setLogLevel(event.getLevel().levelStr);
        log.setTrackId(event.getMDCPropertyMap().get("TRACE_ID"));
        //日志内容
        log.setLogContent(event.getFormattedMessage());
        easySlf4jLoggingService = SpringUtil.getBean(EasySlf4jLoggingService.class);
        //日志入库
        easySlf4jLoggingService.asyncSave(log);
    }
}
