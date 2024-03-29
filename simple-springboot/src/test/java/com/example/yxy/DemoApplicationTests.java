package com.example.yxy;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ReflectUtil;
import com.alibaba.fastjson.JSON;
import com.example.yxy.entity.TestAutoIdEntity;
import com.example.yxy.entity.TestAutoIdEntity2;
import com.example.yxy.entity.TestEntity;
import com.example.yxy.entity.TestJsonEntity;
import com.example.yxy.entity.mrule.CustActResultVO;
import com.example.yxy.entity.mrule.CustInfo;
import com.example.yxy.entity.mrule.CustProdInfo;
import com.example.yxy.service.TestServiceImpl;
import com.example.yxy.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
//import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.MethodResolver;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.*;

@SpringBootTest
class DemoApplicationTests {
 
 
    @Autowired
    private TestServiceImpl testService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

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
    void testRedis() {
        redisTemplate.opsForValue().set("abc", "yxy");
    }

    /**
     * 查询数据
     */
    @Test
    void testRedis2() throws InterruptedException {

        //得到当前的秒
        long start = System.currentTimeMillis();
        Random r=new Random();

        int threadNum = 20;
        int sigThreadRunNum = 1000;
        int sumCount = threadNum * sigThreadRunNum;
        CountDownLatch cd = new CountDownLatch(sumCount);

        new Thread(()->{
            ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
            for (int j = 0; j < threadNum; j++) {
                executorService.execute(()->{
                    for(int i=0; i<sigThreadRunNum; i++){
                        //从jedis连接池获取资源
                        try {
                            // 定义Lua脚本
                            String luaScript = "redis.call(\"incrby\",\"abcd123\",1) return '123'";
                            DefaultRedisScript<Integer> stringDefaultRedisScript = new DefaultRedisScript<>(luaScript, Integer.class);
                            //                    String luaScript = "redis.call(\"SCRIPT FLUSH\")";
                            Integer aa = redisTemplate.execute(stringDefaultRedisScript, Arrays.asList("key"));

                            Thread.sleep(r.nextInt(10) + 10);
                        }catch (Exception e){
                            e.printStackTrace();
                        }finally {
                            cd.countDown();
                        }
                    }
                });
            }
        }).start();



        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(()->{
            while (cd.getCount() > 0) {
                //从jedis连接池获取资源
                try {
                    // 定义Lua脚本
                    redisTemplate.execute((RedisCallback<Object>) connection -> {
                        connection.scriptFlush();
                        return "ok";
                    });

                    Thread.sleep(r.nextInt(10) + 10);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                }
            }
        });

        cd.await();


        //结束时的秒数
        long end = System.currentTimeMillis();

        System.out.println("总耗时:"+(end-start));

    }


    /**
     * 查询数据
     */
    @Test
    void selectTimeOut() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        int N = 10000;
        CountDownLatch cd = new CountDownLatch(N);
        for (int i = 0; i < N; i++) {
            executorService.execute(()->{
                try {
                    List list = testService.selectTimeOut();
                    System.out.println(list);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    cd.countDown();
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });

        }
        cd.await();
    }

    @Test
    void queryDataMap() {
        List<Map> lists = testService.queryDataMap();

        String x = JSON.toJSONStringWithDateFormat(lists, "yyyy-MM-dd HH:mm:ss.SSS");

        List<TestJsonEntity> testJsonEntities = JSON.parseArray(x, TestJsonEntity.class);

        System.out.println(x);
//        JSON.toJsonSt

        System.out.println(testJsonEntities);

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

    @Test
    public void saveReturnPK2(){
        TestAutoIdEntity2 testAutoIdEntity = new TestAutoIdEntity2();
        testAutoIdEntity.setName("xx2");
        testAutoIdEntity.setAddress("重庆");
        testService.saveReturnPK2(testAutoIdEntity);
        System.out.println(JSON.toJSONString(testAutoIdEntity));
    }


    @Test
    public void testSpel2() throws NoSuchMethodException {
        Method parseDouble = Double.class.getDeclaredMethod("parseDouble", String.class);
        SpelParserConfiguration config = new SpelParserConfiguration(SpelCompilerMode.IMMEDIATE,
                this.getClass().getClassLoader());
        ExpressionParser parser = new SpelExpressionParser(config);

        System.out.println("-->name->>" + parseDouble.getName());
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.registerFunction("parseDouble", parseDouble);
        context.registerFunction("parseDouble", parseDouble);
        List<MethodResolver> methodResolvers = context.getMethodResolvers();

//        Person person = new Person("zhangsan", 1);
//        Double.parseDouble()
        context.setVariable("name", "zhangsan");
        context.setVariable("tradeAmont", "100.2");

        String result = parser.parseExpression(
                "#name.equals('zhangsan') && #name.indexOf('z')>=0 && #name.substring(0,1)=='z' && #tradeAmont != null && #parseDouble(#tradeAmont) > 10 ? '结果是1':'结果不是1'").getValue(context, String.class);
        System.out.println(result);

        Boolean result2 = parser.parseExpression(
                "#name=='zhangsan'").getValue(context, Boolean.class);
        System.out.println(result2);
    }

    @Test
    public void test() {

        SpelParserConfiguration config = new SpelParserConfiguration(SpelCompilerMode.IMMEDIATE,
                this.getClass().getClassLoader());
        ExpressionParser parser = new SpelExpressionParser(config);

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

    public static void main(String[] args) throws ParseException, InterruptedException {
        testSpel();
    }
    @Test
    public static void testSpel() throws ParseException, InterruptedException {
        long t1 = System.currentTimeMillis();
        int N = 100;

//        SpelParserConfiguration config = new SpelParserConfiguration(SpelCompilerMode.IMMEDIATE,
//                DemoApplicationTests.getClass().getClassLoader());
        ExpressionParser parser = new SpelExpressionParser();

        Expression expression = parser.parseExpression(
                "#cust.level=='1' && #cust.age >= 28 && #amap['num']==1 && #cp.prodNo=='prod1' && #checkDate('13:00:00','23:00:00')");

        List<Boolean> flagSet = new CopyOnWriteArrayList<>();
        CountDownLatch cd = new CountDownLatch(N);
        for (int i = 0; i < N; i++) {
            int finalI = i;
            new Thread(() -> {
                CustProdInfo cp = new CustProdInfo();
                cp.setCustNo("xx2");
                cp.setProdNo("prod1");
                cp.setBal(BigDecimal.valueOf(11.23));

                CustInfo p2 = new CustInfo();
                p2.setAge(finalI);
                p2.setLevel("1");
                p2.setCustNo("xx2");
                p2.setLoginTime(new Date());

                CustActResultVO vo = new CustActResultVO();

                Map param = new HashMap<>();
                param.put("num",1);

                StandardEvaluationContext context = new StandardEvaluationContext();
                context.setVariable("cust", p2);
                context.setVariable("cp", cp);
                context.setVariable("amap", param);
                context.setVariable("checkDate", ReflectUtil.getMethodByName(DateUtil.class,"checkDate"));

                boolean bol = expression
                        .getValue(context, Boolean.class);

                System.out.println("bol-->" + bol);

                if(bol){
                    flagSet.add(bol);
                }
                cd.countDown();
            }).start();
        }

        cd.await();

        long t2 = System.currentTimeMillis();
        long cha = ((t2 - t1));
        System.out.println("多少是true->"+ flagSet.size() +",次数->" + N + "，耗时->" + (t2 - t1) + "，平均->" + ((double)cha / N));


//        if($cust.getLevel() == "1" && $cust.getAge() >= 28 && $cp.getProdNo() == "prod1" && DateUtil.checkDate("13:00:00","23:00:00"))
    }

    @Test
    public void testCron(){
        CronSequenceGenerator cronSequenceGenerator = new CronSequenceGenerator("0/30 * * * * ?");
        Date date = new Date();
        Date time1 = cronSequenceGenerator.next(date);
        Date time2 = cronSequenceGenerator.next(time1);
        System.out.println(time1);
        System.out.println(time2);

        //下次执行时间
    }

    @Test
    public void testExecSql() throws InterruptedException {
        int N = 1000000;
        CountDownLatch cd = new CountDownLatch(N);
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        for (int i = 0; i < N; i++) {
            int finalI = i;
            executorService.execute(()->{
                try {
                    if (finalI % 2 == 0) {
                        testService.execSql("update test_num set num=num-10 where id=177 ");
                    } else {
                        testService.execSql("update test_num set num=num+10 where id=177 ");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    cd.countDown();
                }
            });
        }
        cd.await();
        executorService.shutdown();
        System.out.println("执行完成=======>");
    }


}

@AllArgsConstructor
@NoArgsConstructor
@Data
class Person {
    String name;
    Integer type;
}