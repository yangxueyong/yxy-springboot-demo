package com.example.yxy;


import org.activiti.engine.*;
import org.activiti.engine.history.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

@SpringBootTest
class Test01 {


    /**
     * 部署流程
     */
    @Test
    void testDeploy() throws FileNotFoundException {

        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = defaultProcessEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment()
                //加载物理路径
//                .addInputStream("test01.bpmn20.xml",new FileInputStream(""))
                //加载相对路径
                .addClasspathResource("process/test01.bpmn20.xml")
                .name("测试流程1")
                .deploy();
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
        List<Deployment> deploymentList = repositoryService.createDeploymentQuery().list();
        System.out.println("部署信息=======================================");
        for (Deployment deploy : deploymentList) {
            System.out.printf("部署ID: %s\n", deploy.getId());
            System.out.printf("部署Name: %s\n", deploy.getName());
            System.out.printf("部署Key: %s\n", deploy.getKey());
        }

        System.out.println("定义信息=======================================");
        List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery().list();
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
        ProcessInstance processInstance = runtimeService.startProcessInstanceById("test01:1:2e56c6df-142c-11ef-9a67-f2577737d854");
        //28d8f785-1433-11ef-8d91-f2577737d854
        System.out.printf("流程实例ID: %s\n", processInstance.getId());
        System.out.printf("流程实例Name: %s\n", processInstance.getName());
        System.out.printf("流程实例ProcessDefinitionId: %s\n", processInstance.getProcessDefinitionId());
    }

    @Test
    void testQueryTask(){
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = defaultProcessEngine.getTaskService();
        List<Task> taskList = taskService.createTaskQuery().list();
        for (Task task : taskList) {
            System.out.printf("任务ID: %s\n", task.getId());
            System.out.printf("任务名称: %s\n", task.getName());
            System.out.printf("任务流程实例ID: %s\n", task.getProcessInstanceId());
            System.out.printf("任务流程定义ID: %s\n", task.getProcessDefinitionId());
            System.out.printf("任务创建时间: %s\n", task.getCreateTime());
            System.out.printf("任务办理人: %s\n", task.getAssignee());
        }
    }

    /**
     * 完成任务
     */
    @Test
    void completeTask(){
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = defaultProcessEngine.getTaskService();
        String taskId = "28daa539-1433-11ef-8d91-f2577737d854";
        taskService.complete(taskId);
    }

    /**
     * 历史流程实例查询
     */
    @Test
    void testQueryHistoryProcessInstance(){
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        HistoryService historyService = defaultProcessEngine.getHistoryService();
        HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery();
        List<HistoricProcessInstance> processInstanceList = historicProcessInstanceQuery.list();
        for (HistoricProcessInstance processInstance : processInstanceList) {
            System.out.printf("流程实例ID: %s\n", processInstance.getId());
            System.out.printf("流程实例Name: %s\n", processInstance.getName());
            System.out.printf("流程实例ProcessDefinitionId: %s\n", processInstance.getProcessDefinitionId());
            System.out.printf("流程实例结束时间: %s\n", processInstance.getEndTime());
        }
    }

    /**
     * 历史任务实例查询
     */
    @Test
    void testQueryHistoryTask() {
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        HistoryService historyService = defaultProcessEngine.getHistoryService();
        HistoricTaskInstanceQuery historicTaskInstanceQuery = historyService.createHistoricTaskInstanceQuery();
        List<HistoricTaskInstance> taskInstanceList = historicTaskInstanceQuery.list();
        for (HistoricTaskInstance taskInstance : taskInstanceList) {
            System.out.printf("任务ID: %s\n", taskInstance.getId());
            System.out.printf("任务名称: %s\n", taskInstance.getName());
            System.out.printf("任务流程实例ID: %s\n", taskInstance.getProcessInstanceId());
            System.out.printf("任务流程定义ID: %s\n", taskInstance.getProcessDefinitionId());
            System.out.printf("任务办理人: %s\n", taskInstance.getAssignee());
            System.out.printf("任务创建时间: %s\n", taskInstance.getCreateTime());
            System.out.printf("任务结束时间: %s\n", taskInstance.getEndTime());
            System.out.printf("任务结束原因: %s\n", taskInstance.getDeleteReason());
            System.out.println("=============================");
        }
    }

    /**
     * 历史活动实例查询
     */
    @Test
    void testQueryHistoryActivityInstance() {
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        HistoryService historyService = defaultProcessEngine.getHistoryService();
        List<HistoricActivityInstance> activityInstanceList = historyService.createHistoricActivityInstanceQuery().list();
        for (HistoricActivityInstance activityInstance : activityInstanceList) {
            System.out.printf("活动实例ID: %s\n", activityInstance.getId());
            System.out.printf("活动实例名称: %s\n", activityInstance.getActivityName());
            System.out.printf("活动实例类型: %s\n", activityInstance.getActivityType());
            System.out.printf("活动实例流程实例ID: %s\n", activityInstance.getProcessInstanceId());
            System.out.printf("活动实例流程定义ID: %s\n", activityInstance.getProcessDefinitionId());
            System.out.printf("活动实例执行者: %s\n", activityInstance.getAssignee());
            System.out.printf("活动实例开始时间: %s\n", activityInstance.getStartTime());
            System.out.printf("活动实例结束时间: %s\n", activityInstance.getEndTime());
            System.out.printf("活动实例持续时间: %s\n", activityInstance.getDurationInMillis());
            System.out.println("=");
        }
    }

}
