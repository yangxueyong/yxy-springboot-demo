package com.example.yxy.controller;

import com.alibaba.fastjson2.JSON;
import com.example.yxy.config.FromToValidator;
import com.example.yxy.entity.Test16Form;
import com.example.yxy.entity.TestEntity;
import com.example.yxy.service.TestService;
import com.example.yxy.util.TestBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@RestController
@RequestMapping("/testInitBinder")
public class TestInitBinderController {


    // 注入我们自定义的校验器
    @Resource
    private FromToValidator fromToValidator;

    @InitBinder
    public void formBinder(WebDataBinder binder) {

        // 只要是String类型,就去除字符串前后的空格
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));

        // 只有当属性名为birthday且为Date类型的才使用使用框架自带的CustomDateEditor编辑器将String处理为Date
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, "birthday", new CustomDateEditor(df, true));

        // 使用我们自定义的校验器
        binder.addValidators(fromToValidator);
    }

    @GetMapping("/init")
    public ModelAndView init() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("test16");
        return modelAndView;
    }

    @PostMapping("/receiveGet")
    @ResponseBody
    public void receiveGet(@Validated @RequestBody Test16Form form) {
        log.info("数据->{}", JSON.toJSONString(form));
    }
}
