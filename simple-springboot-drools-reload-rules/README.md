#### 手动添加drools规则

### 添加规则
```
post http://127.0.0.1:8080/rule/addRule/ck-rule-test-group

参数：

package com_rules
import com.secbro.drools.model.mrule.CustActResultVO;
import com.secbro.drools.model.mrule.CustInfo;
import com.secbro.drools.model.mrule.CustProdInfo;

//引入类库
import java.text.SimpleDateFormat
import java.util.*

//处理时间的function
function boolean checkDate(String st ,String et){
	 Calendar calendar = Calendar.getInstance();
     SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
     Date startTime = sdf.parse(st);
     Date endTime = sdf.parse(et);
     Date nowCal = sdf.parse(sdf.format(calendar.getTime()));
     if(nowCal.after(startTime)  && nowCal.before(endTime)){
        return true;
     }else{
         return false;
     }
}

rule "ck-rule-test2"
    agenda-group "ck-rule-test-group"
    activation-group "ck-rule-test-group"
    when
        $vo : CustActResultVO()
        $cust : CustInfo()
        $cp: CustProdInfo()
        if($cust.getLevel() == "1" && $cust.getAge() >= 28 && $cp.getProdNo() == "prod1" && checkDate('13:00:00','23:00:00'))
            do[successDone]
    then
        System.out.println("不满足");
    then[successDone]
         modify($vo) { setJoinType( "success" ) };
         System.out.println("满足");
    end


```


### 执行规则
```
get http://127.0.0.1:8080/rule/exec/ck-rule-test-group
```

### 删除规则
```
get http://127.0.0.1:8080/rule/removeRule/ck-rule-test-group
```

