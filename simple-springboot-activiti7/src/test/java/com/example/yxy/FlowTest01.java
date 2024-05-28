package com.example.yxy;


import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;
import java.util.List;

@SpringBootTest
class FlowTest01 {


    /**
     * 部署流程
     */
    @Test
    void testDeploy() throws FileNotFoundException {

        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = defaultProcessEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment()
                .key("flow-test1-1")
                //加载相对路径
                .addClasspathResource("process/flow-test01.bpmn20.xml")
                .name("测试流程-流程监听器-1")
                .deploy();
        /**
         * 部署ID: cefc996e-1c8a-11ef-a4f0-428b074323c1
         * 部署Name: 测试流程-流程监听器-1
         * 部署Key: flow-test1-1
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
        List<Deployment> deploymentList = repositoryService
                .createDeploymentQuery()
                .deploymentName("测试流程-流程监听器-1").list();
        System.out.println("部署信息=======================================");
        for (Deployment deploy : deploymentList) {
            System.out.printf("部署ID: %s\n", deploy.getId());
            System.out.printf("部署Name: %s\n", deploy.getName());
            System.out.printf("部署Key: %s\n", deploy.getKey());
        }

        System.out.println("定义信息=======================================");
        List<ProcessDefinition> processDefinitionList = repositoryService
                .createProcessDefinitionQuery()
                .deploymentId("cefc996e-1c8a-11ef-a4f0-428b074323c1")
                .list();
        processDefinitionList.forEach(processDefinition -> {
            /**
             * 流程定义ID: flow-test01:1:cf060f50-1c8a-11ef-a4f0-428b074323c1
             * 流程定义名称: flow-test01
             * 流程定义DeploymentId: cefc996e-1c8a-11ef-a4f0-428b074323c1
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
                .startProcessInstanceById("flow-test01:1:cf060f50-1c8a-11ef-a4f0-428b074323c1");
        /**
         * 流程实例ID: 0060cb4a-1c8b-11ef-9bd7-428b074323c1
         * 流程实例Name: null
         * 流程实例ProcessDefinitionId: flow-test01:1:cf060f50-1c8a-11ef-a4f0-428b074323c1
         */
        System.out.printf("流程实例ID: %s\n", processInstance.getId());
        System.out.printf("流程实例Name: %s\n", processInstance.getName());
        System.out.printf("流程实例ProcessDefinitionId: %s\n", processInstance.getProcessDefinitionId());
    }


    @Test
    void testQueryTask(){
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = defaultProcessEngine.getTaskService();
        List<Task> taskList = taskService.createTaskQuery()
                .active()
                .list();
        for (Task task : taskList) {
            /**
             * 任务ID: 006251ee-1c8b-11ef-9bd7-428b074323c1
             * 任务名称: 流程监听器-经理审批
             * 任务流程实例ID: 0060cb4a-1c8b-11ef-9bd7-428b074323c1
             * 任务流程定义ID: flow-test01:1:cf060f50-1c8a-11ef-a4f0-428b074323c1
             * 任务创建时间: Tue May 28 08:41:20 CST 2024
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
        String taskId = "006251ee-1c8b-11ef-9bd7-428b074323c1";
        taskService.complete(taskId);
    }



}
