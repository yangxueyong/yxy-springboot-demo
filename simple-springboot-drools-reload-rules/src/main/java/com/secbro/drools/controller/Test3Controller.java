package com.secbro.drools.controller;

import com.secbro.drools.component.ReloadDroolsRules;
import com.secbro.drools.model.Address;
import com.secbro.drools.model.Person;
import com.secbro.drools.model.fact.AddressCheckResult;
import com.secbro.drools.utils.KieUtils;
import org.kie.api.KieBase;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.utils.KieHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;


@RequestMapping("/test3")
@Controller
public class Test3Controller {

    @Resource
    private ReloadDroolsRules rules;

    @ResponseBody
    @RequestMapping("/test")
    public void test(){
        KieHelper kieHelper = new KieHelper();
        kieHelper.addResource(ResourceFactory.newClassPathResource("com/accumu/accumulate.drl"), ResourceType.DRL);
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {

            KieSession kieSession = kieHelper.build().newKieSession();

            Person p1 = new Person(1, "Tom", 19);
            Person p2 = new Person(2, "Lucy", 18);

            kieSession.insert(p1);
            kieSession.insert(p2);

            kieSession.fireAllRules();
            kieSession.dispose();
        }
        long t2 = System.currentTimeMillis();
        System.out.println("耗时->" + (t2 - t1));
    }


}
