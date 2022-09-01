# 启动class-demo
## 首先访问 http://127.0.0.1:8112/user/search
```
post参数
{
    "searchId":"user",
    "types":"queryUserInfo,queryUserAddress,queryUserOrder"
}
```

```
返回结果
{
    "userAddress": [
        {
            "userId": "xx1",
            "address": "重庆市九龙坡区xxx",
            "phone": "1300000000000"
        },
        {
            "userId": "xx1",
            "address": "重庆市大渡口区xxx",
            "phone": "1700000000000"
        }
    ],
    "userInfo": {
        "userId": "xx1",
        "userName": "张三"
    }
}
```

## 然后添加方法 http://127.0.0.1:8112/autoJava/insert
```
post参数

package com.example.yxy.service.one.impl;

import com.example.yxy.config.annotation.ESSearchTaskAnnotation;
import com.example.yxy.entity.user.vo.UserInfoAddressVO;
import com.example.yxy.entity.user.vo.UserInfoVO;
import com.example.yxy.service.one.UserService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class UserOrderServiceImpl { 
    @ESSearchTaskAnnotation(searchId = "user", type = "queryUserOrder")
    public void queryUserInfo(Map resultMap,String param) { 
        System.out.println("--->>>>>>>>>>>");
        resultMap.put("userOrder","买了羽绒服"); 
    } 
}

```


## 然后再访问 http://127.0.0.1:8112/user/search
```
post参数
{
    "searchId":"user",
    "types":"queryUserInfo,queryUserAddress,queryUserOrder"
}
```

```
返回结果
{
    "userAddress": [
        {
            "userId": "xx1",
            "address": "重庆市九龙坡区xxx",
            "phone": "1300000000000"
        },
        {
            "userId": "xx1",
            "address": "重庆市大渡口区xxx",
            "phone": "1700000000000"
        }
    ],
    "userInfo": {
        "userId": "xx1",
        "userName": "张三"
    },
    "userOrder": "买了羽绒服"  //可以看到多了这个玩意儿
}
```