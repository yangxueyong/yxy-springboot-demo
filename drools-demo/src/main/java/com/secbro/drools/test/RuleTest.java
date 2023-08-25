package com.secbro.drools.test;

import com.secbro.drools.BaseTest;
import com.secbro.drools.model.Person;
import com.secbro.drools.model.mrule.CustActResultVO;
import com.secbro.drools.model.mrule.CustInfo;
import com.secbro.drools.model.mrule.CustProdInfo;
import org.junit.Test;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 规则测试
 *
 * @author yxy
 * @date 2023/08/25
 */
public class RuleTest extends BaseTest {

    @Test
    public void ckRuleTest() {
        KieSession kieSession = this.getKieSession("ck-rule-test-group");

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
        int count = kieSession.fireAllRules();
        System.out.println("Fire " +count + " rule(s)!");
        kieSession.dispose();

        System.out.println(vo);
    }

}
