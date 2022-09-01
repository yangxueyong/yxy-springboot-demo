package com.example.yxy;

import com.example.yxy.service.one.UploadCDNService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.util.ServiceLoader;

@SpringBootTest
class SpiDemoApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testSPI(){
        ServiceLoader<UploadCDNService> uploadCDN = ServiceLoader.load(UploadCDNService.class);
        for (UploadCDNService u : uploadCDN) {
            u.upload("filePath");
        }
    }



}
