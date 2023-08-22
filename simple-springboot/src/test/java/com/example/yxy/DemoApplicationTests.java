package com.example.yxy;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.alibaba.fastjson2.JSON;
import com.example.yxy.entity.TestAutoIdEntity;
import com.example.yxy.entity.TestEntity;
import com.example.yxy.mapper.TestMapper;
import com.example.yxy.service.TestService;
import com.example.yxy.util.TestBean;
import com.github.pagehelper.PageHelper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
//import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class DemoApplicationTests {
 
 
    @Autowired
    private TestService testService;

    /**
     * 查询数据
     */
    @Test
    void selectInt() {
        testService.selectInt();
    }


    /**
     * 查询数据
     */
    @Test
    void selectTimeOut() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10000; i++) {
            executorService.execute(()->{
                try {
                    List list = testService.selectTimeOut();
                    System.out.println(list);
                }catch (Exception e){
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });

        }
    }

    @Test
    void copyTestBean() {
        TestEntity testBean1 = new TestEntity();
        TestEntity testBean2 = new TestEntity();
        testBean1.setId("xx1");
        testBean2.setName("张三");
        testBean2.setId("zs");
//        BeanUtils.copyProperties(testBean1,testBean2,);
//        BeanUtils.c
        BeanUtil.copyProperties(testBean1,testBean2, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
        System.out.println(JSON.toJSONString(testBean2));
    }

    @Test
    public void saveReturnPK(){
        TestAutoIdEntity testAutoIdEntity = new TestAutoIdEntity();
        testAutoIdEntity.setName("xx1");
        testAutoIdEntity.setAddress("重庆");
        testService.saveReturnPK(testAutoIdEntity);
        System.out.println(JSON.toJSONString(testAutoIdEntity));
    }

    ExpressionParser parser = new SpelExpressionParser();

    @Test
    public void test() {


        StandardEvaluationContext context = new StandardEvaluationContext();
        Person person = new Person("zhangsan", 1);
        context.setVariable("person", person);

        String result = parser.parseExpression(
                "#person.type==1?'结果是1':'结果不是1'").getValue(context, String.class);
        System.out.println(result);

        result = parser.parseExpression(
                "#person.name=='zhangsan' && #person.type==1 ?'结果是1':'结果不是1'").getValue(context, String.class);
        System.out.println(result);

        Boolean result2 = parser.parseExpression(
                "#person.type==1").getValue(context, Boolean.class);
        System.out.println(result2);

        // 另一种写法加上TemplateParserContext,以#{开始   }结束
        String message = "#{1==#person.type?'开启':'关闭'}申报#{#person.name}";
        result = parser.parseExpression(message, new TemplateParserContext()).getValue(context, String.class);
        System.out.println(result);
    }
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class Person {
    String name;
    Integer type;
}