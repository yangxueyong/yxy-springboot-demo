package com.secbro.drools.component;

import com.secbro.drools.utils.KieUtils;
import org.drools.core.impl.KnowledgeBaseImpl;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.KnowledgeBase;
import org.kie.internal.KnowledgeBaseFactory;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.utils.KieHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;


@Component
public class ReloadDroolsRules {

    public void reload() throws UnsupportedEncodingException {
        KieServices kieServices = getKieServices();
        KieFileSystem kfs = kieServices.newKieFileSystem();
        kfs.write("src/main/resources/rules/temp.drl", loadRules());
        KieBuilder kieBuilder = kieServices.newKieBuilder(kfs).buildAll();
        Results results = kieBuilder.getResults();
        if (results.hasMessages(Message.Level.ERROR)) {
            System.out.println(results.getMessages());
            throw new IllegalStateException("### errors ###");
        }
        KieUtils.setKieContainer(kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId()));
        System.out.println("reload新规则重载成功");
    }

    private String loadRules() {
        // 从数据库加载的规则
        return "package plausibcheck.adress\n\n rule \"Postcode 6 numbers\"\n\n    when\n  then\n        System.out.println(\"规则1中打印日志：校验通过!\");\n end";

    }

    private String loadRules2() {
        // 从数据库加载的规则
        return "package plausibcheck.adress\n\n  agenda-group \"test-activity\" \n\n  rule \"Postcode 7 numbers\"\n\n    when\n  then\n        System.out.println(\"规则2中打印日志：校验通过!\");\n end";

    }

    private String loadRules3() {
        // 从数据库加载的规则
        return "package plausibcheck.adress\n\n agenda-group \"test-123\" \n\n  rule \"Postcode 8 numbers\"\n\n    when\n  then\n        System.out.println(\"规则3中打印日志：校验通过!\");\n end";

    }


    private KieServices getKieServices() {
        return KieServices.Factory.get();
    }

    public void reloadByHelper() throws UnsupportedEncodingException {

        KieHelper kieHelper = new KieHelper();
        kieHelper.addContent(loadRules(),ResourceType.DRL);

        Results results = kieHelper.verify();
        if (results.hasMessages(Message.Level.ERROR)) {
            System.out.println(results.getMessages());
            throw new IllegalStateException("### errors ###");
        }

//        KieBase kieBase = kieHelper.build();
        KieContainer kieContainer = kieHelper.getKieContainer();


        KieUtils.setKieContainer(kieContainer);
        System.out.println("新规则重载成功");
    }

    public void addRule() throws UnsupportedEncodingException {
        KnowledgeBaseImpl kieBase = (KnowledgeBaseImpl)KieUtils.getKieContainer().getKieBase();
        KnowledgeBuilder knowledgeBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        knowledgeBuilder.add(ResourceFactory.newByteArrayResource(loadRules2().getBytes("utf-8")), ResourceType.DRL);
        knowledgeBuilder.add(ResourceFactory.newByteArrayResource(loadRules3().getBytes("utf-8")), ResourceType.DRL);
        kieBase.addKnowledgePackages(knowledgeBuilder.getKnowledgePackages());

        System.out.println("reload2新规则重载成功");
    }



}
