package com.example.yxy;


import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会签测试
 *
 * 会签是通过多实例Multi Instance来设置的：
 *
 * Sequential：执行顺序，true表示多实例顺序执行，false表示多实例并行。
 * Loop Cardinality：循环基数，选填，会签人数。
 * Completion Condition：完成条件，Activiti预定义了3个变量，可以在UEL表达式中直接使用，可以根据表达式设置按数量、按比例、一票通过、一票否定等条件。
 *      nrOfInstances：总实例数，Collection中的数量。
 *      nrOfCompletedInstances：已经完成的实例数。
 *      nrOfActiveInstances：还没有完成的实例数。
 * Collection：Assignee集合，可以在启动实例时赋值变量。
 * Element Variable：元素变量，必须和Assignee一样。
 * Assignee：负责人占位符，会通过Collection自动赋值的。
 *
 * @author yxy
 * @date 2024/05/29
 */
@SpringBootTest
class HuiqianTest01 {


    /**
     * 部署流程
     */
    @Test
    void testDeploy() throws FileNotFoundException {

        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = defaultProcessEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment()
                .key("huiqian-test1")
                //加载相对路径
                .addClasspathResource("process/huiqian-test01.bpmn20.xml")
                .name("会签-test1")
                .deploy();
        /**
         * 部署ID: 06def639-2145-11ef-ae80-aa82f9a380a8
         * 部署Name: 会签-test1
         * 部署Key: huiqian-test1
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
                .deploymentId("06def639-2145-11ef-ae80-aa82f9a380a8")
                .list();
        processDefinitionList.forEach(processDefinition -> {
            /**
             * 流程定义ID: huiqian-test01:2:06e81dfb-2145-11ef-ae80-aa82f9a380a8
             * 流程定义名称: huiqian-test01
             * 流程定义DeploymentId: 06def639-2145-11ef-ae80-aa82f9a380a8
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

        Map<String, Object> varMap = new HashMap<>();
        varMap.put("sprList", Arrays.asList("zhangsan","lisi","wangwu","zhaoliu"));

        ProcessInstance processInstance = runtimeService
                .startProcessInstanceById("huiqian-test01:2:06e81dfb-2145-11ef-ae80-aa82f9a380a8",varMap);
        /**
         * 流程实例ID: 275de693-2145-11ef-9bf7-aa82f9a380a8
         * 流程实例Name: null
         * 流程实例ProcessDefinitionId: huiqian-test01:2:06e81dfb-2145-11ef-ae80-aa82f9a380a8
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
                .processInstanceId("275de693-2145-11ef-9bf7-aa82f9a380a8")
                .list();
        for (Task task : taskList) {
            /**
             * 任务ID: 275f9463-2145-11ef-9bf7-aa82f9a380a8
             * 任务名称: 会签测试-多人任务
             * 任务流程实例ID: 275de693-2145-11ef-9bf7-aa82f9a380a8
             * 任务流程定义ID: huiqian-test01:2:06e81dfb-2145-11ef-ae80-aa82f9a380a8
             * 任务创建时间: Mon Jun 03 09:03:56 CST 2024
             * 任务办理人: zhangsan
             *
             * ============================================
             *
             * 任务ID: 276009a2-2145-11ef-9bf7-aa82f9a380a8
             * 任务名称: 会签测试-多人任务
             * 任务流程实例ID: 275de693-2145-11ef-9bf7-aa82f9a380a8
             * 任务流程定义ID: huiqian-test01:2:06e81dfb-2145-11ef-ae80-aa82f9a380a8
             * 任务创建时间: Mon Jun 03 09:03:56 CST 2024
             * 任务办理人: lisi
             * ====================================
             * 任务ID: 276030c1-2145-11ef-9bf7-aa82f9a380a8
             * 任务名称: 会签测试-多人任务
             * 任务流程实例ID: 275de693-2145-11ef-9bf7-aa82f9a380a8
             * 任务流程定义ID: huiqian-test01:2:06e81dfb-2145-11ef-ae80-aa82f9a380a8
             * 任务创建时间: Mon Jun 03 09:03:56 CST 2024
             * 任务办理人: wangwu
             * ====================================
             * 任务ID: 27607ef0-2145-11ef-9bf7-aa82f9a380a8
             * 任务名称: 会签测试-多人任务
             * 任务流程实例ID: 275de693-2145-11ef-9bf7-aa82f9a380a8
             * 任务流程定义ID: huiqian-test01:2:06e81dfb-2145-11ef-ae80-aa82f9a380a8
             * 任务创建时间: Mon Jun 03 09:03:56 CST 2024
             * 任务办理人: zhaoliu
             * ====================================
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
        String taskId = "276009a2-2145-11ef-9bf7-aa82f9a380a8";
        Map<String, Object> varMap = new HashMap<>();
        varMap.put("spr","lisi");
        //其实不拾取也可以完成任务
        taskService.complete(taskId,varMap);
    }



}
