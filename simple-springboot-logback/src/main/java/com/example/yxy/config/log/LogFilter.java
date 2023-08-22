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

/**
 * <p>Slf4j日志入库-过滤器</p>
 *
 * @author 波波老师(微信:javabobo0513)
 */
public class LogFilter extends Filter<LoggingEvent> {

    public EasySlf4jLoggingService easySlf4jLoggingService = null;

    public static final String DB_Marker_str = "DB_Marker";
    public static final Marker DB_Marker = MarkerFactory.getMarker("DB_Marker");
    @Override
    public FilterReply decide(LoggingEvent event) {
        Marker marker = event.getMarker();
        String loggerName = event.getLoggerName();
        if(DB_Marker_str.equals(marker.getName())){
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
            return FilterReply.ACCEPT;
        }else{
            //非项目本身的日志不会入库
            return FilterReply.DENY;
        }
    }
}
