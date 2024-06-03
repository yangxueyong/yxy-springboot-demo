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
 * 子流程测试
 * @author yxy
 * @date 2024/05/29
 */
@SpringBootTest
class SubProcessTest01 {


    /**
     * 部署流程
     */
    @Test
    void testDeploy() throws FileNotFoundException {

        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = defaultProcessEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment()
                .key("subProcess-test01")
                //加载相对路径
                .addClasspathResource("process/subProcess-test01.bpmn20.xml")
                .name("测试子流程-01")
                .deploy();
        /**
         * 部署ID: ef044e7c-1ee7-11ef-8cfc-aa82f9a380a8
         * 部署Name: 测试子流程-01
         * 部署Key: subProcess-test01
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
                .deploymentId("ef044e7c-1ee7-11ef-8cfc-aa82f9a380a8")
                .list();
        processDefinitionList.forEach(processDefinition -> {
            /**
             * 流程定义ID: subProcess-test01:1:ef0bef9e-1ee7-11ef-8cfc-aa82f9a380a8
             * 流程定义名称: subProcess-test01
             * 流程定义DeploymentId: ef044e7c-1ee7-11ef-8cfc-aa82f9a380a8
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
                .startProcessInstanceById("subProcess-test01:1:ef0bef9e-1ee7-11ef-8cfc-aa82f9a380a8");
        /**
         * 流程实例ID: 1a07cfa7-1ee8-11ef-bdf7-aa82f9a380a8
         * 流程实例Name: null
         * 流程实例ProcessDefinitionId: subProcess-test01:1:ef0bef9e-1ee7-11ef-8cfc-aa82f9a380a8
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
                .processInstanceId("1a07cfa7-1ee8-11ef-bdf7-aa82f9a380a8")
                .list();
        for (Task task : taskList) {
            /**
             * 任务ID: 1a0892fb-1ee8-11ef-bdf7-aa82f9a380a8
             * 任务名称: 测试子流程-员工请假
             * 任务流程实例ID: 1a07cfa7-1ee8-11ef-bdf7-aa82f9a380a8
             * 任务流程定义ID: subProcess-test01:1:ef0bef9e-1ee7-11ef-8cfc-aa82f9a380a8
             * 任务创建时间: Fri May 31 08:52:48 CST 2024
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
     * 拾取任务  节点1
     */
    @Test
    void completeTask(){
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = defaultProcessEngine.getTaskService();
        String taskId = "1a0892fb-1ee8-11ef-bdf7-aa82f9a380a8";
        //其实不拾取也可以完成任务
        taskService.complete(taskId);
    }



}
