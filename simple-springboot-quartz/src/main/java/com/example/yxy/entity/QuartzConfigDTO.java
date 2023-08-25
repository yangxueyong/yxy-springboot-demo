package com.example.yxy.entity;
 
import lombok.Data;
 
import java.io.Serializable;
import java.util.Map;
 
/**
 * @Author: 
 * @Description:
 * @Date Create in 14:16 2022/5/16
 * @Modified By:
 */
@Data
public class QuartzConfigDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 任务名称
     */
    private String jobName;
 
    /**
     * 任务所属组
     */
    private String groupName;
 
    /**
     * 任务执行类
     */
    private String jobClass;
 
    /**
     * 任务调度时间表达式
     */
    private String cronExpression;
 
    /**
     * 附加参数
     */
    private Map<String, Object> param;
}