-- test_flink.t_p_cust_deposit_prod definition
drop table if exists test_flink.t_p_cust_deposit_prod;
CREATE TABLE test_flink.t_p_cust_deposit_prod (
                                                  auto_id bigint auto_increment NOT NULL  key COMMENT '主键',
                                                  cust_no varchar(100) NULL COMMENT '客户号',
                                                  main_prod_no varchar(100) NULL COMMENT '主产品号',
                                                  sub_prod_no varchar(100) NULL COMMENT '子产品号',
                                                  amount decimal(30,2) NULL COMMENT '金额',
                                                  begin_time datetime NULL COMMENT '开始时间',
                                                  end_time datetime NULL COMMENT '结束时间'
)
    ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci
COMMENT='客户存款数据';

drop table if exists test_flink.t_p_cust_loan_prod;
CREATE TABLE test_flink.t_p_cust_loan_prod (
                                               auto_id bigint auto_increment NOT NULL  key COMMENT '主键',
                                               cust_no varchar(100) NULL COMMENT '客户号',
                                               main_prod_no varchar(100) NULL COMMENT '主产品号',
                                               sub_prod_no varchar(100) NULL COMMENT '子产品号',
                                               amount decimal(30,2) NULL COMMENT '金额',
                                               begin_time datetime NULL COMMENT '开始时间',
                                               end_time datetime NULL COMMENT '结束时间'
)
    ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci
COMMENT='客户贷款数据';

drop table if exists test_flink.t_p_cust_prod;
CREATE TABLE test_flink.t_p_cust_prod (
                                          auto_id bigint auto_increment NOT NULL  key COMMENT '主键',
                                          cust_no varchar(100) NULL COMMENT '客户号',
                                          main_prod_no varchar(100) NULL COMMENT '主产品号',
                                          num decimal(30,2) COMMENT '数量',
                                          amount decimal(30,2) NULL COMMENT '金额'
)
    ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci
COMMENT='客户产品持有情况';

create unique index idx_unq_t_p_cust_prod_cust_no_main_prod on t_p_cust_prod(cust_no,main_prod_no);

insert into t_p_cust_deposit_prod(cust_no,main_prod_no,sub_prod_no,amount,begin_time,end_time)
values('zhangsan','A00001','A0000100001',10,'2022-01-01','2023-01-01');
insert into t_p_cust_deposit_prod(cust_no,main_prod_no,sub_prod_no,amount,begin_time,end_time)
values('zhangsan','A00001','A0000100001',10,'2022-02-01','2023-02-01');
insert into t_p_cust_deposit_prod(cust_no,main_prod_no,sub_prod_no,amount,begin_time,end_time)
values('zhangsan','B00001','B0000100001',100,'2022-04-01','2023-04-01');
insert into t_p_cust_deposit_prod(cust_no,main_prod_no,sub_prod_no,amount,begin_time,end_time)
values('lisi','A00001','A0000100001',10,'2022-01-11','2023-01-11');




insert into t_p_cust_loan_prod(cust_no,main_prod_no,sub_prod_no,amount,begin_time,end_time)
values('zhangsan','C00001','C0000100001',10,'2022-11-01','2023-11-01');
insert into t_p_cust_loan_prod(cust_no,main_prod_no,sub_prod_no,amount,begin_time,end_time)
values('wangwu','D00001','D0000100001',10,'2022-12-01','2023-12-01');
insert into t_p_cust_loan_prod(cust_no,main_prod_no,sub_prod_no,amount,begin_time,end_time)
values('wangwu','E00001','E0000100001',100,'2022-05-01','2023-05-01');


select * from t_p_cust_loan_prod;
select * from t_p_cust_deposit_prod;


drop table if exists test_flink.t_p_cust_main;
CREATE TABLE test_flink.t_p_cust_main (
                                          cust_no varchar(100) NULL COMMENT '客户号',
                                          cust_name varchar(100) NULL COMMENT '客户姓名',
                                          phone varchar(100) NULL COMMENT '电话'
)
    ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci
COMMENT='客户信息主档表';
insert into t_p_cust_main values('zhangsan','张三','130000');
insert into t_p_cust_main values('lisi','李四','130000');


drop table if exists test_flink.t_p_cust_red_money;
CREATE TABLE test_flink.t_p_cust_red_money (
                                               auto_id bigint auto_increment NOT NULL  key COMMENT '主键',
                                               cust_no varchar(100) NULL COMMENT '客户号',
                                               prod_no varchar(100) NULL COMMENT '产品号',
                                               red_money decimal(30,2) NULL COMMENT '金额',
                                               create_time datetime NULL COMMENT '开始时间'
)
    ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci
COMMENT='客户红包表';

--- kafka相关


