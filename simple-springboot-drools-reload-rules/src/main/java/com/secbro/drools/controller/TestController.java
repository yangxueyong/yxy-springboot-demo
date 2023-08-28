package com.secbro.drools.controller;

import com.secbro.drools.component.ReloadDroolsRules;
import com.secbro.drools.model.fact.AddressCheckResult;
import com.secbro.drools.model.Address;
import com.secbro.drools.utils.KieUtils;
import org.kie.api.KieBase;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;


@RequestMapping("/test")
@Controller
public class TestController {

    @Resource
    private ReloadDroolsRules rules;

    @ResponseBody
    @RequestMapping("/exec")
    public void exec(){
        KieSession kieSession = KieUtils.getKieContainer().newKieSession();

        Address address = new Address();
        address.setPostcode("994251");

        kieSession.insert(address);

        int ruleFiredCount = kieSession.fireAllRules();
        System.out.println("触发了" + ruleFiredCount + "条规则");

        kieSession.dispose();
    }

    @ResponseBody
    @RequestMapping("/execGroup/{groupName}")
    public void execGroup(@PathVariable("groupName") String groupName){
        KieSession kieSession = KieUtils.getKieContainer().newKieSession();
        kieSession.getAgenda().getAgendaGroup(groupName).setFocus();
        Address address = new Address();
        address.setPostcode("994251");
        kieSession.insert(address);
        int ruleFiredCount = kieSession.fireAllRules();
        System.out.println("触发了" + ruleFiredCount + "条规则");
        kieSession.dispose();
    }



    @ResponseBody
    @RequestMapping("/multiGroup/{groupName}/{N}")
    public void multiGroup(@PathVariable("groupName") String groupName,@PathVariable("N") int N){
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
            KieSession kieSession = KieUtils.getKieContainer().newKieSession();
            kieSession.getAgenda().getAgendaGroup(groupName).setFocus();
            Address address = new Address();
            address.setPostcode("994251");
            kieSession.insert(address);
            int ruleFiredCount = kieSession.fireAllRules();
            System.out.println("触发了" + ruleFiredCount + "条规则");
        }
        long t2 = System.currentTimeMillis();
        System.out.println("次数->" + N + "，耗时->" + (t2 - t1) + "，平均->" + (N / ((t2 - t1)/1000)));
    }

    @ResponseBody
    @RequestMapping("/multi/{N}")
    public void multi(@PathVariable("N") int N){
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {

            KieSession kieSession = KieUtils.getKieContainer().newKieSession();

            Address address = new Address();
            address.setPostcode("994251");

            kieSession.insert(address);

            int ruleFiredCount = kieSession.fireAllRules();

            kieSession.dispose();

//            kieHelper.ad
        }
        long t2 = System.currentTimeMillis();
        System.out.println("次数->" + N + "，耗时->" + (t2 - t1) + "，平均->" + (N / ((t2 - t1)/1000)));
    }


    @ResponseBody
    @RequestMapping("/remove/{pack}/{rule}")
    public void remove(@PathVariable("pack") String pack,@PathVariable("rule") String rule){
        KieContainer kieContainer = KieUtils.getKieContainer();
        KieBase kieBase = kieContainer.getKieBase();
        kieBase.removeRule(pack, rule);
        System.out.println("删掉了");
    }

    @ResponseBody
    @RequestMapping("/reload")
    public String reload() throws IOException {
        rules.reload();
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/addRule")
    public String addRule() throws IOException {
        rules.addRule();
        return "ok";
    }
}
