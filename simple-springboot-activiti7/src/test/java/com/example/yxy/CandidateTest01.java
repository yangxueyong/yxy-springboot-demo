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
class CandidateTest01 {


    /**
     * 部署流程
     */
    @Test
    void testDeploy() throws FileNotFoundException {

        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = defaultProcessEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment()
                .key("candidate-test1-1")
                //加载相对路径
                .addClasspathResource("process/candidate-test1-single.bpmn20.xml")
                .name("测试流程-候选人拾取-1")
                .deploy();
        /**
         * 部署ID: 64ec1de2-1c03-11ef-a452-428b074323c1
         * 部署Name: 测试流程-候选人拾取-1
         * 部署Key: candidate-test1-1
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
                .deploymentName("测试流程-候选人拾取-1").list();
        System.out.println("部署信息=======================================");
        for (Deployment deploy : deploymentList) {
            System.out.printf("部署ID: %s\n", deploy.getId());
            System.out.printf("部署Name: %s\n", deploy.getName());
            System.out.printf("部署Key: %s\n", deploy.getKey());
        }

        System.out.println("定义信息=======================================");
        List<ProcessDefinition> processDefinitionList = repositoryService
                .createProcessDefinitionQuery()
                .deploymentId("64ec1de2-1c03-11ef-a452-428b074323c1")
                .list();
        processDefinitionList.forEach(processDefinition -> {
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
                .startProcessInstanceById("candidate-test1-single:1:64fa75c4-1c03-11ef-a452-428b074323c1");
        /**
         * 流程实例ID: 8c1990f5-1c03-11ef-8503-428b074323c1
         * 流程实例Name: null
         * 流程实例ProcessDefinitionId: candidate-test1-single:1:64fa75c4-1c03-11ef-a452-428b074323c1
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
                .taskCandidateUser("zs")
                .list();
        for (Task task : taskList) {
            System.out.printf("任务ID: %s\n", task.getId());
            System.out.printf("任务名称: %s\n", task.getName());
            System.out.printf("任务流程实例ID: %s\n", task.getProcessInstanceId());
            System.out.printf("任务流程定义ID: %s\n", task.getProcessDefinitionId());
            System.out.printf("任务创建时间: %s\n", task.getCreateTime());
            System.out.printf("任务办理人: %s\n", task.getAssignee());
            System.out.printf("====================================\n");
        }
    }


    @Test
    void testQueryTaskByAssignee(){
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = defaultProcessEngine.getTaskService();
        List<Task> taskList = taskService.createTaskQuery()
                .taskAssignee("role-GRB-admin")
                //活动状态
                .active()
                .list();
        for (Task task : taskList) {
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
        String taskId = "54190321-1bf4-11ef-89ef-428b074323c1";
        taskService.complete(taskId);
    }


    @Test
    void testQueryTaskByAssignee2(){
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = defaultProcessEngine.getTaskService();
        List<Task> taskList = taskService.createTaskQuery()
                .taskAssignee("role-RS-admin")
                //活动状态
                .active()
                .list();
        for (Task task : taskList) {
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
     * 给节点2填加备注
     */
    @Test
    void addComment(){
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = defaultProcessEngine.getTaskService();
        String taskId = "4bc3145d-1bf8-11ef-9bbc-428b074323c1";
        taskService.addComment(taskId,"5417f1ab-1bf4-11ef-89ef-428b074323c1","备注信息");
        taskService.addComment(taskId,"5417f1ab-1bf4-11ef-89ef-428b074323c1","myType","备注信息");
    }

    /**
     * 给节点2填加备注
     */
    @Test
    void getComment(){
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        String taskId = "4bc3145d-1bf8-11ef-9bbc-428b074323c1";
        List<Comment> commentList = defaultProcessEngine.getTaskService().getTaskComments(taskId,"myType");
        for (Comment comment : commentList) {
            System.out.printf("评论Type: %s\n", comment.getType());
            System.out.printf("评论内容: %s\n", comment.getFullMessage());
        }
    }


    /**
     * 完成任务  节点2
     */
    @Test
    void completeTask2(){
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = defaultProcessEngine.getTaskService();
        String taskId = "4bc3145d-1bf8-11ef-9bbc-428b074323c1";
        taskService.complete(taskId);
    }



}
