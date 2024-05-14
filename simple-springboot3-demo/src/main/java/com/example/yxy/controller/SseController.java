package com.example.yxy.controller;

import com.example.yxy.entity.User;
import com.example.yxy.entity.res.ApiResponse;
import com.example.yxy.service.UserService;
import com.example.yxy.util.SSEUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;


@CrossOrigin
@Tag(name = "sse测试", description = "sse测试")
@RestController
@RequestMapping("/sse")
public class SseController {

    @Operation(summary = "sse数据持续输出", description = "sse数据持续输出")
    @GetMapping(value = "sse",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> sse() {
        return Flux.range(1,10)
                .map(i -> {
                    return ServerSentEvent.builder("yxy-" + i)
                            .id(i + "")
                            .comment("我是-" + i)
                            .event("myEvent")
                            .build() ;
                }).delayElements(Duration.ofSeconds(2));
    }

    @GetMapping(path = "/sub/{questionId}",produces = MediaType.TEXT_EVENT_STREAM_VALUE )
    public SseEmitter subscribe(@PathVariable("questionId") String questionId) {
        // 简单异步发消息 ====
        //questionId 订阅id，id对应了sse对象
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                for (int i = 0; i < 10; i++) {
                    Thread.sleep(1500);
                    SSEUtils.pubMsg(questionId, questionId + " - kingtao come " + i);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // 消息发送完关闭订阅
                SSEUtils.closeSub(questionId);
            }
        }).start();
        // =================
        return SSEUtils.addSub(questionId);
    }
}