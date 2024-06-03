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
import java.util.List;

/**
 * 启动另一个流程
 * @author yxy
 * @date 2024/05/30
 */
@SpringBootTest
class CallActivitiTest01 {


    /**
     * 部署流程
     */
    @Test
    void testDeploy() throws FileNotFoundException {

        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = defaultProcessEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment()
                .key("callActivit-test01")
                //加载相对路径
                .addClasspathResource("process/callActivit-test01.bpmn20.xml")
                .name("callActivit测试流程-1")
                .deploy();
        /**
         * 部署ID: 4eafd75d-1e23-11ef-a093-aa82f9a380a8
         * 部署Name: callActivit测试流程-1
         * 部署Key: callActivit-test01
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
                .deploymentId("4eafd75d-1e23-11ef-a093-aa82f9a380a8")
                .list();
        processDefinitionList.forEach(processDefinition -> {
            /**
             * 流程定义ID: callActivit-test01:1:4eba108f-1e23-11ef-a093-aa82f9a380a8
             * 流程定义名称: callActivit-test01
             * 流程定义DeploymentId: 4eafd75d-1e23-11ef-a093-aa82f9a380a8
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
                .startProcessInstanceById("callActivit-test01:1:4eba108f-1e23-11ef-a093-aa82f9a380a8");
        /**
         * 流程实例ID: 7d8e48ae-1e23-11ef-aec3-aa82f9a380a8
         * 流程实例Name: null
         * 流程实例ProcessDefinitionId: callActivit-test01:1:4eba108f-1e23-11ef-a093-aa82f9a380a8
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
                .processInstanceId("7d8e48ae-1e23-11ef-aec3-aa82f9a380a8")
                .active()
                .list();
        for (Task task : taskList) {
            /**
             * 任务ID: 7d8f0c02-1e23-11ef-aec3-aa82f9a380a8
             * 任务名称: callActivity流程测试-员工离职
             * 任务流程实例ID: 7d8e48ae-1e23-11ef-aec3-aa82f9a380a8
             * 任务流程定义ID: callActivit-test01:1:4eba108f-1e23-11ef-a093-aa82f9a380a8
             * 任务创建时间: Thu May 30 09:25:25 CST 2024
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
        String taskId = "7d8f0c02-1e23-11ef-aec3-aa82f9a380a8";
        taskService.complete(taskId);
    }

    /**
     * 根据流程实例id查询任务
     */
    @Test
    void testQueryTask2(){
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = defaultProcessEngine.getTaskService();
        List<Task> taskList = taskService.createTaskQuery()
                .processDefinitionKey("test01")
                .active()
                .list();
        for (Task task : taskList) {
            /**
             * 任务ID: a8a77544-1e23-11ef-93cd-aa82f9a380a8
             * 任务名称: 经理审批
             * 任务流程实例ID: a8a70010-1e23-11ef-93cd-aa82f9a380a8
             * 任务流程定义ID: test01:2:0c940397-1d5f-11ef-9751-aa82f9a380a8
             * 任务创建时间: Thu May 30 09:26:37 CST 2024
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



}
