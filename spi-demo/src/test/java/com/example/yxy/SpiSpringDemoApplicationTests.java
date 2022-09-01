package com.example.yxy;

import com.example.yxy.service.one.UploadCDNService;
import com.example.yxy.service.three.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.util.ClassUtils;

import java.util.List;
import java.util.ServiceLoader;

@SpringBootTest
class SpiSpringDemoApplicationTests {

    @Autowired
    private StudentService studentService;

    @Test
    void contextLoads() {
    }

    @Test
    void testSPI(){
        List<UploadCDNService> uploadCDN = SpringFactoriesLoader.loadFactories(UploadCDNService.class, ClassUtils.getDefaultClassLoader());
//        ServiceLoader<UploadCDNService> uploadCDN = ServiceLoader.load(UploadCDNService.class);
        for (UploadCDNService u : uploadCDN) {
            u.upload("filePath");
        }
        studentService.sayHello();
    }



}
