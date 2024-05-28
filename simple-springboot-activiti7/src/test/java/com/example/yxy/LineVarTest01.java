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

@SpringBootTest
class LineVarTest01 {


    /**
     * 部署流程
     */
    @Test
    void testDeploy() throws FileNotFoundException {

        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = defaultProcessEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment()
                .key("line-var-test1-1")
                //加载相对路径
                .addClasspathResource("process/line-var-test01.bpmn20.xml")
                .name("线变量测试流程-1")
                .deploy();
        /**
         * 部署ID: 8a148963-1c8e-11ef-ba33-c6b7d5de4e0a
         * 部署Name: 线变量测试流程-1
         * 部署Key: line-var-test1-1
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
                .deploymentName("线变量测试流程-1").list();
        System.out.println("部署信息=======================================");
        for (Deployment deploy : deploymentList) {
            System.out.printf("部署ID: %s\n", deploy.getId());
            System.out.printf("部署Name: %s\n", deploy.getName());
            System.out.printf("部署Key: %s\n", deploy.getKey());
        }

        System.out.println("定义信息=======================================");
        List<ProcessDefinition> processDefinitionList = repositoryService
                .createProcessDefinitionQuery()
                .deploymentId("8a148963-1c8e-11ef-ba33-c6b7d5de4e0a")
                .list();
        processDefinitionList.forEach(processDefinition -> {
            /**
             * 流程定义ID: line-var-test01:2:8a1e4d65-1c8e-11ef-ba33-c6b7d5de4e0a
             * 流程定义名称: line-var-test01
             * 流程定义DeploymentId: 8a148963-1c8e-11ef-ba33-c6b7d5de4e0a
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
                .startProcessInstanceById("line-var-test01:2:8a1e4d65-1c8e-11ef-ba33-c6b7d5de4e0a");
        /**
         * 流程实例ID: b0b79649-1c8e-11ef-ada6-c6b7d5de4e0a
         * 流程实例Name: null
         * 流程实例ProcessDefinitionId: line-var-test01:2:8a1e4d65-1c8e-11ef-ba33-c6b7d5de4e0a
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
                .processInstanceId("b0b79649-1c8e-11ef-ada6-c6b7d5de4e0a")
                .active()
                .list();
        for (Task task : taskList) {
            /**
             * 任务ID: b0b943fd-1c8e-11ef-ada6-c6b7d5de4e0a
             * 任务名称: 线-变量-员工请假
             * 任务流程实例ID: b0b79649-1c8e-11ef-ada6-c6b7d5de4e0a
             * 任务流程定义ID: line-var-test01:2:8a1e4d65-1c8e-11ef-ba33-c6b7d5de4e0a
             * 任务创建时间: Tue May 28 09:07:44 CST 2024
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
     * 必须要传变量day，day的值为请假天数，如：10
     */
    @Test
    void completeTask(){
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = defaultProcessEngine.getTaskService();
        String taskId = "b0b943fd-1c8e-11ef-ada6-c6b7d5de4e0a";
        Map<String, Object> variables = new HashMap<>();
        variables.put("day", 8);
        //完成任务之后，应该是部门经理审批
        //默认为全局变量，也就是说下一节点也能获取这个变量
        taskService.complete(taskId, variables);
    }


    /**
     * 完成任务 经理审批  节点2
     */
    @Test
    void completeTask2(){
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = defaultProcessEngine.getTaskService();
        String taskId = "d45ca64b-1c8e-11ef-8924-f25f8d1ceb9b";
//        Map<String, Object> variables = new HashMap<>();
//        variables.put("day", 8);
        //完成任务之后，应该是部门经理审批
        taskService.complete(taskId);
    }




}
