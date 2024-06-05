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
 * 以消息事件启动流程测试
 *
 * @author yxy
 * @date 2024/05/29
 */
@SpringBootTest
class MsgEventStartTest01 {


    /**
     * 部署流程
     */
    @Test
    void testDeploy() throws FileNotFoundException, InterruptedException {

        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = defaultProcessEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment()
                .key("event-msg-start-test01")
                //加载相对路径
                .addClasspathResource("process/event-msg-start-test01.bpmn20.xml")
                .name("事件-消息启动流程-测试01")
                .deploy();
        String deployId = deploy.getId();
        System.out.printf("部署ID: %s\n", deployId);
        System.out.printf("部署Name: %s\n", deploy.getName());
        System.out.printf("部署Key: %s\n", deploy.getKey());
        queryDeploy(deployId);

        Thread.sleep(Integer.MAX_VALUE);
    }

    /**
     * 查询流程信息
     */
    @Test
    void queryDeploy(String deploymentId) throws FileNotFoundException {

        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = defaultProcessEngine.getRepositoryService();

        System.out.println("定义信息=======================================");
        List<ProcessDefinition> processDefinitionList = repositoryService
                .createProcessDefinitionQuery()
                .deploymentId(deploymentId)
                .list();
        processDefinitionList.forEach(processDefinition -> {
            String processDefinitionId = processDefinition.getId();
            System.out.printf("流程定义ID: %s\n", processDefinitionId);
            System.out.printf("流程定义名称: %s\n", processDefinition.getName());
            System.out.printf("流程定义DeploymentId: %s\n", processDefinition.getDeploymentId());
            try {
                startProcess(processDefinitionId);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }


    /**
     * 启动流程
     */
    @Test
    void startProcess(String processDefinitionId) throws FileNotFoundException {
        //使用流程定义id启动流程
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = defaultProcessEngine.getRuntimeService();
        //启动时 使用定义的消息name来启动
        ProcessInstance processInstance = runtimeService
                .startProcessInstanceByMessage("hello");
        /**
         *
         */
        String processInstanceId = processInstance.getId();
        System.out.printf("流程实例ID: %s\n", processInstanceId);
        System.out.printf("流程实例Name: %s\n", processInstance.getName());
        System.out.printf("流程实例ProcessDefinitionId: %s\n", processInstance.getProcessDefinitionId());

        testQueryTask(processInstanceId);
    }


    /**
     * 查询任务
     */
    @Test
    void testQueryTask(String processInstanceId){
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = defaultProcessEngine.getTaskService();
        List<Task> taskList = taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .list();
        for (Task task : taskList) {
            /**
             *
             */
            String taskId = task.getId();
            System.out.printf("任务ID: %s\n", taskId);
            System.out.printf("任务名称: %s\n", task.getName());
            System.out.printf("任务流程实例ID: %s\n", task.getProcessInstanceId());
            System.out.printf("任务流程定义ID: %s\n", task.getProcessDefinitionId());
            System.out.printf("任务创建时间: %s\n", task.getCreateTime());
            System.out.printf("任务办理人: %s\n", task.getAssignee());
            System.out.printf("====================================\n");

            taskService.complete(taskId);
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
