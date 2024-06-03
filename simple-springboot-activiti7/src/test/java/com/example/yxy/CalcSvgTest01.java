package com.example.yxy;


import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class CalcSvgTest01 {

    /**
     * 生成 svg 图片文件
     * activiti-image-generator 7 的版本生成 png 的图片无法打开，可以生成 svg 图片，或者采用 5.19.0.2 版本的 activiti-image-generator 生成 png 图片
     */
    @Test
    public void testGenerateSVG() throws IOException{
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = defaultProcessEngine.getRepositoryService();
        RuntimeService runtimeService = defaultProcessEngine.getRuntimeService();

        String PROCESSID = "275de693-2145-11ef-9bf7-aa82f9a380a8";

        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(PROCESSID)
//                .processDefinitionKey(PROCESSID)
                .singleResult();

        BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
        if(bpmnModel != null && bpmnModel.getLocationMap().size() > 0){
            DefaultProcessDiagramGenerator ge = new DefaultProcessDiagramGenerator();

            InputStream inputStream = ge.generateDiagram(bpmnModel, runtimeService.getActiveActivityIds(processInstance.getId()), new ArrayList<>(), "宋体", "宋体", null, false);

            FileUtils.copyInputStreamToFile(inputStream, new File("src/main/resources/bpmn/" + Instant.now().toString() + "_" + PROCESSID + ".svg"));
        } else {
            System.out.println("bpmnModel 为空！");
        }
    }




}
