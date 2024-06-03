package com.example.yxy;


import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 任务监听
 * @author yxy
 * @date 2024/06/03
 */
@SpringBootTest
class ReceiveTaskTest01 {


    /**
     * 部署流程
     */
    @Test
    void testDeploy() throws FileNotFoundException {

        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = defaultProcessEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment()
                .key("receive-task-test01")
                //加载相对路径
                .addClasspathResource("process/receiveTask-test01.bpmn20.xml")
                .name("receive-task测试流程-1")
                .deploy();
        /**
         * 部署ID: 4b4f0097-1e20-11ef-a4a0-aa82f9a380a8
         * 部署Name: receive-task测试流程-1
         * 部署Key: receive-task-test01
         */
        System.out.printf("部署ID: %s\n", deploy.getId());
        System.out.printf("部署Name: %s\n", deploy.getName());
        System.out.printf("部署Key: %s\n", deploy.getKey());
    }

    /**
     * 查询流程信息
     */
    @Test
    void queryDeploy() throws FileNotFoundException {
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = defaultProcessEngine.getRepositoryService();

        System.out.println("定义信息=======================================");
        List<ProcessDefinition> processDefinitionList = repositoryService
                .createProcessDefinitionQuery()
                .deploymentId("4b4f0097-1e20-11ef-a4a0-aa82f9a380a8")
                .list();
        processDefinitionList.forEach(processDefinition -> {
            /**
             * 流程定义ID: receiveTask-test01:3:4b562c89-1e20-11ef-a4a0-aa82f9a380a8
             * 流程定义名称: receiveTask-test01
             * 流程定义DeploymentId: 4b4f0097-1e20-11ef-a4a0-aa82f9a380a8
             */
            System.out.printf("流程定义ID: %s\n", processDefinition.getId());
            System.out.printf("流程定义名称: %s\n", processDefinition.getName());
            System.out.printf("流程定义DeploymentId: %s\n", processDefinition.getDeploymentId());
        });
    }


    /**
     * 启动流程
     */
    @Test
    void startProcess() throws FileNotFoundException {
        //使用流程定义id启动流程
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = defaultProcessEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService
                .startProcessInstanceById("receiveTask-test01:3:4b562c89-1e20-11ef-a4a0-aa82f9a380a8");
        /**
         * 流程实例ID: 81c2d292-1e20-11ef-9148-aa82f9a380a8
         * 流程实例Name: null
         * 流程实例ProcessDefinitionId: receiveTask-test01:3:4b562c89-1e20-11ef-a4a0-aa82f9a380a8
         */
        System.out.printf("流程实例ID: %s\n", processInstance.getId());
        System.out.printf("流程实例Name: %s\n", processInstance.getName());
        System.out.printf("流程实例ProcessDefinitionId: %s\n", processInstance.getProcessDefinitionId());
    }


    /**
     * 根据流程实例id查询任务
     */
    @Test
    void testQueryTask(){
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = defaultProcessEngine.getTaskService();
        List<Task> taskList = taskService.createTaskQuery()
                .processInstanceId("81c2d292-1e20-11ef-9148-aa82f9a380a8")
                .active()
                .list();
        for (Task task : taskList) {
            /**
             * 任务ID: 81c36ed6-1e20-11ef-9148-aa82f9a380a8
             * 任务名称: receiveTask测试-统计开始
             * 任务流程实例ID: 81c2d292-1e20-11ef-9148-aa82f9a380a8
             * 任务流程定义ID: receiveTask-test01:3:4b562c89-1e20-11ef-a4a0-aa82f9a380a8
             * 任务创建时间: Thu May 30 09:04:03 CST 2024
             * 任务办理人: null
             */
            System.out.printf("任务ID: %s\n", task.getId());
            System.out.printf("任务名称: %s\n", task.getName());
            System.out.printf("任务流程实例ID: %s\n", task.getProcessInstanceId());
            System.out.printf("任务流程定义ID: %s\n", task.getProcessDefinitionId());
            System.out.printf("任务创建时间: %s\n", task.getCreateTime());
            System.out.printf("任务办理人: %s\n", task.getAssignee());
            System.out.printf("====================================\n");
        }
    }




    /**
     * 完成任务  节点1
     */
    @Test
    void completeTask(){
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = defaultProcessEngine.getTaskService();
        String taskId = "1b4f35f0-1e1f-11ef-a0b2-aa82f9a380a8";
        taskService.complete(taskId);
    }



    @Test
    void triggerTask(){
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        HistoryService historyService = defaultProcessEngine.getHistoryService();
        RuntimeService runtimeService = defaultProcessEngine.getRuntimeService();
        String processInstanceId = "1b4e4b8c-1e1f-11ef-a0b2-aa82f9a380a8";
//        historyService.createHistoricActivityInstanceQuery().processInstanceId("1b4e4b8c-1e1f-11ef-a0b2-aa82f9a380a8").list().forEach(historicProcessInstance -> {
//            String activityId = historicProcessInstance.getActivityId();
//            System.out.println("activityId->" + activityId);
//        });

        Execution e2 = runtimeService
              .createExecutionQuery()
              .processInstanceId(processInstanceId) //每个流程的唯一标识
              .activityId("receiveTask测试-发送")    //每个活动的唯一标识
              .singleResult();
        //触发节点
        runtimeService.trigger(e2.getId());
//        也可以传参
//        runtimeService.trigger(e2.getId(), new HashMap<String, Object>());
    }

}
