package com.example.yxy;


import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TaskService测试
 * @author yxy
 * @date 2024/05/29
 */
@SpringBootTest
class TaskServiceTest01 {


    /**
     * 部署流程
     */
    @Test
    void testDeploy() throws FileNotFoundException {

        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = defaultProcessEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment()
                .key("service-task-test01")
                //加载相对路径
                .addClasspathResource("process/service-task-test01.bpmn20.xml")
                .name("测试流程-service-task")
                .deploy();
        /**
         * 部署ID: 7d6237ea-1d81-11ef-a2cc-aa82f9a380a8
         * 部署Name: 测试流程-service-task
         * 部署Key: service-task-test01
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
                .deploymentId("7d6237ea-1d81-11ef-a2cc-aa82f9a380a8")
                .list();
        processDefinitionList.forEach(processDefinition -> {
            /**
             * 流程定义ID: service-task-test01:2:7d6b389c-1d81-11ef-a2cc-aa82f9a380a8
             * 流程定义名称: service-task-test01
             * 流程定义DeploymentId: 7d6237ea-1d81-11ef-a2cc-aa82f9a380a8
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
                .startProcessInstanceById("service-task-test01:2:7d6b389c-1d81-11ef-a2cc-aa82f9a380a8");
        /**
         * 流程实例ID: a28d7205-1d81-11ef-8c79-aa82f9a380a8
         * 流程实例Name: null
         * 流程实例ProcessDefinitionId: service-task-test01:2:7d6b389c-1d81-11ef-a2cc-aa82f9a380a8
         */
        System.out.printf("流程实例ID: %s\n", processInstance.getId());
        System.out.printf("流程实例Name: %s\n", processInstance.getName());
        System.out.printf("流程实例ProcessDefinitionId: %s\n", processInstance.getProcessDefinitionId());
    }


    /**
     * 查询任务
     */
    @Test
    void testQueryTask(){
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = defaultProcessEngine.getTaskService();
        List<Task> taskList = taskService.createTaskQuery()
                .processInstanceId("a28d7205-1d81-11ef-8c79-aa82f9a380a8")
                .list();
        for (Task task : taskList) {
            /**
             * 任务ID: a28e5c69-1d81-11ef-8c79-aa82f9a380a8
             * 任务名称: service-task测试-用户请假
             * 任务流程实例ID: a28d7205-1d81-11ef-8c79-aa82f9a380a8
             * 任务流程定义ID: service-task-test01:2:7d6b389c-1d81-11ef-a2cc-aa82f9a380a8
             * 任务创建时间: Wed May 29 14:06:48 CST 2024
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
        String taskId = "a28e5c69-1d81-11ef-8c79-aa82f9a380a8";
        Map<String,Object> taskVariables = new HashMap<>();
        taskVariables.put("creator","manager");
        //其实不拾取也可以完成任务
        taskService.complete(taskId,taskVariables);
    }



}
