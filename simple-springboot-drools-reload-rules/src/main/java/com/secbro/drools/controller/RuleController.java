package com.secbro.drools.controller;

import com.alibaba.fastjson.JSON;
import com.secbro.drools.model.mrule.CustActResultVO;
import com.secbro.drools.model.mrule.CustInfo;
import com.secbro.drools.model.mrule.CustProdInfo;
import com.secbro.drools.utils.ExKieHelper;
import net.minidev.json.JSONValue;
import org.kie.api.KieBase;
import org.kie.api.definition.KiePackage;
import org.kie.api.definition.rule.Rule;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.*;


/**
 * 规则控制器
 *
 * @author yxy
 * @date 2023/08/25
 */
@RequestMapping("/rule")
@Controller
public class RuleController {

    KieHelper kieHelper = new ExKieHelper();

    /**
     * 添加规则
     *
     * @param name 文件名字
     * @param rule 规则
     * @return {@link String}
     */
    @ResponseBody
    @RequestMapping("/addRule/{name}")
    public String add(@PathVariable("name") String name, @RequestBody String rule){
        kieHelper.addContent(rule, name + "." + ResourceType.DRL.getDefaultExtension());
        return "ok";
    }

    /**
     * 删除规则
     *
     * @param name 文件名字
     * @return {@link String}
     */
    @ResponseBody
    @RequestMapping("/removeRule/{name}")
    public String remove(@PathVariable("name") String name){
        String path = ((ExKieHelper)kieHelper).getPath(name, ResourceType.DRL);
        kieHelper.kfs.delete(path);
        return "ok";
    }

    /**
     * 获取规则
     *
     * @param packageName 包名
     * @return {@link String}
     */
    @ResponseBody
    @RequestMapping("/getRule/{packageName}")
    public String get(@PathVariable("packageName") String packageName){
        Collection<Rule> rules = kieHelper.build().getKiePackage(packageName).getRules();
        for (Rule rule : rules) {
            return JSON.toJSONString(rule.getName());
        }
        return JSON.toJSONString(rules);
    }


    /**
     * 执行规则
     *
     * @param groupName 组名称
     * @return {@link CustActResultVO}
     */
    @ResponseBody
    @RequestMapping("/exec/{groupName}")
    public CustActResultVO exec(@PathVariable("groupName") String groupName){
        KieSession kieSession = kieHelper.build().newKieSession();
        CustProdInfo cp = new CustProdInfo();
        cp.setCustNo("xx2");
        cp.setProdNo("prod1");
        cp.setBal(BigDecimal.valueOf(11.23));

        CustInfo p2 = new CustInfo();
        p2.setAge(29);
        p2.setLevel("1");
        p2.setCustNo("xx2");
        p2.setLoginTime(new Date());

        CustActResultVO vo = new CustActResultVO();

        kieSession.insert(vo);
        kieSession.insert(cp);
        kieSession.insert(p2);

        kieSession.getAgenda().getAgendaGroup(groupName).setFocus();
        kieSession.fireAllRules();
        kieSession.dispose();
        kieSession.destroy();

        return vo;
    }


    /**
     * 性能测试
     *
     * @param groupName 组名称
     * @param N         n
     */
    @ResponseBody
    @RequestMapping("/multi/{groupName}/{N}")
    public void multi(@PathVariable("groupName") String groupName,@PathVariable("N") int N){
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
            exec(groupName);
        }
        long t2 = System.currentTimeMillis();
        long cha = ((t2 - t1));
        System.out.println("次数->" + N + "，耗时->" + (t2 - t1) + "，平均->" + (cha / N));
    }

}
