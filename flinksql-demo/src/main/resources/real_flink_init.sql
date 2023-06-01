CREATE TABLE kafka_cust_red_money (
    cust_no String,
    trade_channel String,
    trade_money decimal(30,2),
    proctime as proctime()
) with(
  'connector' = 'kafka',
  'topic' = 'test_kafka_cust_red_money',
  'properties.bootstrap.servers' = 'localhost:9092',
  'scan.startup.mode' = 'latest-offset',
  'format' = 'json'
);
CREATE TABLE t_p_cust_red_money (
    cust_no String,
    prod_no String,
    red_money decimal(30,2) ,
    create_time TIMESTAMP
) with(
   'connector' = 'jdbc',
   'url' = 'jdbc:mysql://127.0.0.1:3316/test_flink?useSSL=false&serverTimezone=Asia/Shanghai&allowMultiQueries=true&allowPublicKeyRetrieval=true',
   'table-name' = 't_p_cust_red_money',
   'username'='root',
   'password'='123456789'
);
CREATE TABLE t_p_cust_main (
   cust_no String,
   cust_name String,
   phone String
) with(
   'connector' = 'jdbc',
   'url' = 'jdbc:mysql://127.0.0.1:3316/test_flink?useSSL=false&serverTimezone=Asia/Shanghai&allowMultiQueries=true&allowPublicKeyRetrieval=true',
   'table-name' = 't_p_cust_main',
   'username'='root',
   'password'='123456789'
)