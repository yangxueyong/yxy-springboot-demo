package com.example.yxy;


import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * 候选人测试
 * @author yxy
 * @date 2024/05/29
 */
@SpringBootTest
class CandidateGroupTest04 {


    /**
     * 部署流程
     */
    @Test
    void testDeploy() throws FileNotFoundException {

        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = defaultProcessEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment()
                .key("candidate-group-test")
                //加载相对路径
                .addClasspathResource("process/userGroup-test01.bpmn20.xml")
                .name("测试流程-候选人组3")
                .deploy();
        /**
         * 部署ID: 28058b35-1d6c-11ef-be03-aa82f9a380a8
         * 部署Name: 测试流程-候选人组3
         * 部署Key: candidate-group-test
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
                .deploymentId("28058b35-1d6c-11ef-be03-aa82f9a380a8")
                .list();
        processDefinitionList.forEach(processDefinition -> {
            /**
             * 流程定义ID: userGroup-test01:1:280e3dc7-1d6c-11ef-be03-aa82f9a380a8
             * 流程定义名称: userGroup-test01
             * 流程定义DeploymentId: 28058b35-1d6c-11ef-be03-aa82f9a380a8
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
                .startProcessInstanceById("userGroup-test01:1:280e3dc7-1d6c-11ef-be03-aa82f9a380a8");
        /**
         * 流程实例ID: 1b9d40ec-1d7e-11ef-bcb7-aa82f9a380a8
         * 流程实例Name: null
         * 流程实例ProcessDefinitionId: userGroup-test01:1:280e3dc7-1d6c-11ef-be03-aa82f9a380a8
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
                .processInstanceId("1b9d40ec-1d7e-11ef-bcb7-aa82f9a380a8")
                .taskCandidateGroup("kjGroup")
                .list();
        for (Task task : taskList) {
            /**
             * 任务ID: 1b9ec790-1d7e-11ef-bcb7-aa82f9a380a8
             * 任务名称: 候选人组测试-员工请假
             * 任务流程实例ID: 1b9d40ec-1d7e-11ef-bcb7-aa82f9a380a8
             * 任务流程定义ID: userGroup-test01:1:280e3dc7-1d6c-11ef-be03-aa82f9a380a8
             * 任务创建时间: Wed May 29 13:41:33 CST 2024
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
        String taskId = "1b9ec790-1d7e-11ef-bcb7-aa82f9a380a8";

        //其实不拾取也可以完成任务
        taskService.complete(taskId);
    }



}
