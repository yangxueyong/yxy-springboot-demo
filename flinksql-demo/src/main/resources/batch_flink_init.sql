CREATE TABLE t_p_cust_deposit_prod (
   cust_no String,
   main_prod_no String,
   sub_prod_no String,
   amount decimal(30,2) ,
   begin_time TIMESTAMP ,
   end_time TIMESTAMP
) with(
   'connector' = 'jdbc',
   'url' = 'jdbc:mysql://127.0.0.1:3316/test_flink?useSSL=false&serverTimezone=Asia/Shanghai&allowMultiQueries=true&allowPublicKeyRetrieval=true',
   'table-name' = 't_p_cust_deposit_prod',
   'username'='root',
   'password'='123456789'
);
CREATE TABLE t_p_cust_loan_prod (
    cust_no String,
    main_prod_no String,
    sub_prod_no String,
    amount decimal(30,2) ,
    begin_time TIMESTAMP ,
    end_time TIMESTAMP
) with(
   'connector' = 'jdbc',
   'url' = 'jdbc:mysql://127.0.0.1:3316/test_flink?useSSL=false&serverTimezone=Asia/Shanghai&allowMultiQueries=true&allowPublicKeyRetrieval=true',
   'table-name' = 't_p_cust_loan_prod',
   'username'='root',
   'password'='123456789'
);
CREATE TABLE t_p_cust_prod (
   cust_no String,
   main_prod_no String,
   num decimal(30,2),
   amount decimal(30,2) ,primary key(cust_no,main_prod_no) not enforced
) with(
   'connector' = 'jdbc',
   'url' = 'jdbc:mysql://127.0.0.1:3316/test_flink?useSSL=false&serverTimezone=Asia/Shanghai&allowMultiQueries=true&allowPublicKeyRetrieval=true',
   'table-name' = 't_p_cust_prod',
   'username'='root',
   'password'='123456789'
)