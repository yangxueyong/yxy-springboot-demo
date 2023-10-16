#### kafka消费规则- 常见参数设置

#### spring.kafka.consumer.enable-auto-commit
#### spring.kafka.listener.ack-mode
```
当设置spring.kafka.consumer.enable-auto-commit=false时
并不是说就不会自动提交消费位移
还得判断spring.kafka.listener.ack-mode的取值
```
#### spring.kafka.listener.ack-mode的取值
| 取值         | 含义  |
|------------|-----|
| BATCH(默认值) | 表示当每一批poll()的数据被消费者监听器（ListenerConsumer）处理之后提交|
| MANUAL        | poll()拉取一批消息，处理完业务后，手动调用Acknowledgment.acknowledge()先将offset存放到map本地缓存，在下一次poll之前从缓存拿出来批量提交 |
| MANUAL_IMMEDIATE        | 每处理完业务手动调用Acknowledgment.acknowledge()后立即提交 |
| RECORD        | 当每一条记录被消费者监听器（ListenerConsumer）处理之后提交 |
| BATCH        | 当每一批poll()的数据被消费者监听器（ListenerConsumer）处理之后提交 |
| TIME        | 当每一批poll()的数据被消费者监听器（ListenerConsumer）处理之后，距离上次提交时间大于TIME时提交 |
| COUNT        | 当每一批poll()的数据被消费者监听器（ListenerConsumer）处理之后，被处理record数量大于等于COUNT时提交 |
| COUNT_TIME        | TIME或COUNT满足其中一个时提交 |

### 生产上建议设置
```
spring.kafka.consumer.enable-auto-commit=false
spring.kafka.listener.ack-mode=manual_immediate
```


#### spring.kafka.properties.session.timeout.ms
```
spring.kafka.properties.session.timeout.ms 默认值45秒  心跳超时时间，当超过这个时间没有提交心跳，则kafka会发起消费组再平衡
```

#### spring.kafka.properties.max.poll.interval.ms
```
spring.kafka.properties.max.poll.interval.ms 默认值300秒  消费者超过这个时间没有poll消费数据，则kafka会发起消费组再平衡
```
