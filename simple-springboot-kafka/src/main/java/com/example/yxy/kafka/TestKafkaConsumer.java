package com.example.yxy.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;

@Slf4j
@Configuration
@ConditionalOnProperty(value = "spring.kafka.producer.topic.topic-test-consumer")
public class TestKafkaConsumer {
    @KafkaListener(topics = "${spring.kafka.producer.topic.topic-test-consumer}",concurrency = "3")
    public void testConsumer(ConsumerRecord<?, String> consumerRecord, Acknowledgment acknowledgment){
        //立刻提交
        acknowledgment.acknowledge();
        try {
            System.out.println("开始消费");
            log.info(Thread.currentThread().getName() + "数据->" + consumerRecord.value());
            Thread.sleep(2000);
            System.out.println("消费完毕");
        }catch (Exception e){
            log.error("报错了->" , e);
        }finally {

        }
    }
}
