////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by FernFlower decompiler)
////
//
//package com.example.yxy.config;
//
//import java.math.BigDecimal;
//
//import io.github.shelltea.warmup.WarmUpRequest;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.web.ServerProperties;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
//import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
//import org.springframework.context.ApplicationListener;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//@Component
//public class MyWarmUpListener implements ApplicationListener<ApplicationReadyEvent> {
//    private static final Logger log = LoggerFactory.getLogger(MyWarmUpListener.class);
//    @Autowired
//    private ServerProperties serverProperties;
//    @Value("${management.endpoint.warm-up.enable:true}")
//    private boolean enable;
//    @Value("${management.endpoint.warm-up.times:5}")
//    private int times;
//
//    public MyWarmUpListener() {
//    }
//
//    private static WarmUpRequest body() {
//        WarmUpRequest warmUpRequest = new WarmUpRequest();
//        warmUpRequest.setValidTrue(true);
//        warmUpRequest.setValidFalse(false);
//        warmUpRequest.setValidString("warm up");
//        warmUpRequest.setValidNumber(15);
//        warmUpRequest.setValidBigDecimal(BigDecimal.TEN);
//        return warmUpRequest;
//    }
//
//    public void onApplicationEvent(ApplicationReadyEvent event) {
//        if (this.enable && event.getApplicationContext() instanceof ServletWebServerApplicationContext) {
//            AnnotationConfigServletWebServerApplicationContext context = (AnnotationConfigServletWebServerApplicationContext)event.getApplicationContext();
//            String contextPath = this.serverProperties.getServlet().getContextPath();
//            int port = this.serverProperties.getPort() != null && this.serverProperties.getPort() != 0 ? this.serverProperties.getPort() : context.getWebServer().getPort();
//            if (contextPath == null) {
//                contextPath = "";
//            }
//
//            String url = "http://localhost:" + port + contextPath + "/warm/warm-up";
//            log.info("Starting warm up application. Endpoint: {}, {} times", url, this.times);
//            RestTemplate restTemplate = new RestTemplate();
//
//            for(int i = 0; i < this.times; ++i) {
//                ResponseEntity<String> response = restTemplate.postForEntity(url, body(), String.class, new Object[0]);
//                log.debug("Warm up response:{}", response);
//            }
//
//            log.info("Completed warm up application");
//        }
//
//    }
//}
