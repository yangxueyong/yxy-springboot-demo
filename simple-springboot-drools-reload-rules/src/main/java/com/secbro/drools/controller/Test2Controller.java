package com.secbro.drools.controller;

import com.secbro.drools.component.ReloadDroolsRules;
import com.secbro.drools.model.Address;
import com.secbro.drools.model.fact.AddressCheckResult;
import com.secbro.drools.utils.KieUtils;
import org.kie.api.KieBase;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.internal.utils.KieHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;


@RequestMapping("/test2")
@Controller
public class Test2Controller {

    KieHelper kieHelper = new KieHelper();


    @ResponseBody
    @RequestMapping("/add/{packageName}/{groupName}/{ruleName}/{msg}")
    public void add(@PathVariable("packageName") String packageName,
                    @PathVariable("groupName") String groupName,
                    @PathVariable("ruleName") String ruleName,
                    @PathVariable("msg") String msg){
        kieHelper.addContent("package "+packageName+"\n" +
                "\n" +
                "ruleflow-group \""+groupName+"\"\n" +
                "rule \""+ruleName+"\"\n" +
                "\n" +
                "when\n" +
                "\n" +
                "then\n" +
                "\n" +
                "System.out.println(\""+msg+"\");\n" +
                "end", ResourceType.DRL);
        KieBase kieBase = kieHelper.build();
        KieSession kieSession = kieBase.newKieSession();
        kieSession.fireAllRules();
        kieSession.dispose();
    }

    @ResponseBody
    @RequestMapping("/exec/{groupName}")
    public void exec(@PathVariable("groupName") String groupName){
        KieBase kieBase = kieHelper.build();
        KieSession kieSession = kieBase.newKieSession();
        kieSession.getAgenda().getAgendaGroup(groupName).setFocus();
        kieSession.fireAllRules();
        kieSession.dispose();
    }



    @ResponseBody
    @RequestMapping("/multi/{groupName}/{N}")
    public void multi(@PathVariable("groupName") String groupName,@PathVariable("N") int N){
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
            KieBase kieBase = kieHelper.build();
            KieSession kieSession = kieBase.newKieSession();
            kieSession.getAgenda().getAgendaGroup(groupName).setFocus();
            kieSession.fireAllRules();
            kieSession.dispose();
        }
        long t2 = System.currentTimeMillis();
        System.out.println("次数->" + N + "，耗时->" + (t2 - t1) + "，平均->" + (N / ((t2 - t1)/1000)));
    }

}
