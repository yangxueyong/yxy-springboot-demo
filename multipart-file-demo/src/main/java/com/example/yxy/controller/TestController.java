package com.example.yxy.controller;

import com.alibaba.fastjson2.JSON;
import com.example.yxy.entity.BookIO;
import com.example.yxy.entity.BookIO2;
import com.example.yxy.utils.MultipartFileToFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    /**
     * 将MultipartFile放在实体类中
     * 不能使用@requestBody
     *
     * Content-Type multipart/form-data;
     * @param bookIO 书io
     * @return {@link String}
     */
    @PostMapping("/updateBook1")
    public String updateBook1(BookIO bookIO){
        if(bookIO != null && bookIO.getFiles() != null) {
            for (MultipartFile file : bookIO.getFiles()) {
                MultipartFileToFile.saveMultipartFile(file,"/Users/yxy/tmp/mfile");
            }
        }
        return "ok";
    }

//    @PostMapping(value = "/updateBook2")
//    public String updateBook2(@RequestParam("files") MultipartFile[] files, @RequestPart() BookIO2 bookIO2) {
//        log.info("bookIO2->{}",JSON.toJSONString(bookIO2));
//        for (MultipartFile file : files) {
//            MultipartFileToFile.saveMultipartFile(file,"/Users/yxy/tmp/mfile");
//        }
//        return "ok";
//    }

}
