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
 * 任务转办测试
 * 任务转办是将任务转给其他人处理，任务属于其他人
 * @author yxy
 * @date 2024/05/28
 */
@SpringBootTest
class TaskZhuanBanTest01 {


    /**
     * 部署流程
     */
    @Test
    void testDeploy() throws FileNotFoundException {

        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = defaultProcessEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment()
                .key("zhuanban-test01")
                //加载相对路径
                .addClasspathResource("process/test01.bpmn20.xml")
                .name("转办测试流程-1")
                .deploy();
        /**
         * 部署ID: ad0bd014-1d56-11ef-8197-6ed3dad6a163
         * 部署Name: 委派测试流程-1
         * 部署Key: weipai-test01
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
                .deploymentId("ad0bd014-1d56-11ef-8197-6ed3dad6a163")
                .list();
        processDefinitionList.forEach(processDefinition -> {
            /**
             * 流程定义ID: test01:1:ad16a586-1d56-11ef-8197-6ed3dad6a163
             * 流程定义名称: test01
             * 流程定义DeploymentId: ad0bd014-1d56-11ef-8197-6ed3dad6a163
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
                .startProcessInstanceById("test01:1:ad16a586-1d56-11ef-8197-6ed3dad6a163");
        /**
         * 流程实例ID: 4114defc-1d5f-11ef-b030-aa82f9a380a8
         * 流程实例Name: null
         * 流程实例ProcessDefinitionId: test01:1:ad16a586-1d56-11ef-8197-6ed3dad6a163
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
                .processInstanceId("4114defc-1d5f-11ef-b030-aa82f9a380a8")
                .active()
                .list();
        for (Task task : taskList) {
            /**
             * 任务ID: 41168cb0-1d5f-11ef-b030-aa82f9a380a8
             * 任务名称: 经理审批
             * 任务流程实例ID: 4114defc-1d5f-11ef-b030-aa82f9a380a8
             * 任务流程定义ID: test01:1:ad16a586-1d56-11ef-8197-6ed3dad6a163
             * 任务创建时间: Wed May 29 10:00:42 CST 2024
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
     * 转办任务
     */
    @Test
    void delegateTask(){
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = defaultProcessEngine.getTaskService();
        String taskId = "41168cb0-1d5f-11ef-b030-aa82f9a380a8";
        String loginName = "wangdada";
        taskService.setAssignee(taskId, loginName);
    }


    /**
     * 根据流程实例id查询任务
     */
    @Test
    void testQueryTask2(){
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = defaultProcessEngine.getTaskService();
        List<Task> taskList = taskService.createTaskQuery()
                .processInstanceId("4114defc-1d5f-11ef-b030-aa82f9a380a8")
                .taskAssignee("wangdada")
                .active()
                .list();
        for (Task task : taskList) {
            /**
             * 任务ID: 41168cb0-1d5f-11ef-b030-aa82f9a380a8
             * 任务名称: 经理审批
             * 任务流程实例ID: 4114defc-1d5f-11ef-b030-aa82f9a380a8
             * 任务流程定义ID: test01:1:ad16a586-1d56-11ef-8197-6ed3dad6a163
             * 任务创建时间: Wed May 29 10:00:42 CST 2024
             * 任务办理人: wangdada
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
     * 完成委托
     */
    @Test
    void completeTask2() throws InterruptedException {
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = defaultProcessEngine.getTaskService();
        String taskId = "41168cb0-1d5f-11ef-b030-aa82f9a380a8";
        taskService.complete(taskId);
    }

    /**
     * 完成节点3
     */
    @Test
    void completeTask3() throws InterruptedException {
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = defaultProcessEngine.getTaskService();
        String taskId = "80505a19-1d60-11ef-8ed5-aa82f9a380a8";
        taskService.complete(taskId);
    }





}
