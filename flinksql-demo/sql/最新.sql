create database if not exists test_flink;

CREATE TABLE `t_p_cust_main` (
    `cust_no` varchar(100) DEFAULT NULL COMMENT '客户号',
    `cust_name` varchar(100) DEFAULT NULL COMMENT '客户姓名',
    `phone` varchar(100) DEFAULT NULL COMMENT '电话',
    `level` varchar(100) DEFAULT NULL COMMENT '级别'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='客户信息主档表';

INSERT INTO test_flink.t_p_cust_main (cust_no, cust_name, phone, `level`) VALUES('zhangsan', '张三', '130000', '3');
INSERT INTO test_flink.t_p_cust_main (cust_no, cust_name, phone, `level`) VALUES('lisi', '李四', '130000', '2');
INSERT INTO test_flink.t_p_cust_main (cust_no, cust_name, phone, `level`) VALUES('wangwu', 'wangwu', '130000', '1');

CREATE TABLE `t_p_cust_label_all` (
    `cust_no` varchar(100) DEFAULT NULL COMMENT '客户号',
    `cust_name` varchar(100) DEFAULT NULL COMMENT '客户姓名',
    `phone` varchar(100) DEFAULT NULL COMMENT '电话',
    `c0010101` varchar(100) DEFAULT NULL COMMENT '用户等级',
    `c0010102` varchar(100) DEFAULT NULL COMMENT '信用卡等级',
    `aum` decimal(30,2) DEFAULT NULL COMMENT 'AUM',
    `lum` decimal(30,2) DEFAULT NULL COMMENT 'LUM'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='客户标签表';

INSERT INTO test_flink.t_p_cust_label_all (cust_no, cust_name, phone, c0010101, c0010102, aum, lum) VALUES('zhangsan', '张三', '130000', '1', '3', 100.00, 103.00);
INSERT INTO test_flink.t_p_cust_label_all (cust_no, cust_name, phone, c0010101, c0010102, aum, lum) VALUES('lisi', '李四', '130001', '2', '2', 101.00, 102.00);
INSERT INTO test_flink.t_p_cust_label_all (cust_no, cust_name, phone, c0010101, c0010102, aum, lum) VALUES('wangwu', '王五', '130002', '3', '1', 103.00, 100.00);


-- test_flink.t_p_cust_deposit_prod definition

CREATE TABLE `t_p_cust_deposit_prod` (
    `auto_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
    `cust_no` varchar(100) DEFAULT NULL COMMENT '客户号',
    `main_prod_no` varchar(100) DEFAULT NULL COMMENT '主产品号',
    `sub_prod_no` varchar(100) DEFAULT NULL COMMENT '子产品号',
    `amount` decimal(30,2) DEFAULT NULL COMMENT '金额',
    `begin_time` datetime DEFAULT NULL COMMENT '开始时间',
    `end_time` datetime DEFAULT NULL COMMENT '结束时间',
     PRIMARY KEY (`auto_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='客户存款数据';

INSERT INTO test_flink.t_p_cust_deposit_prod (cust_no, main_prod_no, sub_prod_no, amount, begin_time, end_time) VALUES('zhangsan', 'A00001', 'A0000100001', 10.00, '2022-01-01 00:00:00', '2023-01-01 00:00:00');
INSERT INTO test_flink.t_p_cust_deposit_prod (cust_no, main_prod_no, sub_prod_no, amount, begin_time, end_time) VALUES('zhangsan', 'A00001', 'A0000100001', 10.00, '2022-02-01 00:00:00', '2023-02-01 00:00:00');
INSERT INTO test_flink.t_p_cust_deposit_prod (cust_no, main_prod_no, sub_prod_no, amount, begin_time, end_time) VALUES('zhangsan', 'B00001', 'B0000100001', 100.00, '2022-04-01 00:00:00', '2023-04-01 00:00:00');
INSERT INTO test_flink.t_p_cust_deposit_prod (cust_no, main_prod_no, sub_prod_no, amount, begin_time, end_time) VALUES('lisi', 'A00001', 'A0000100001', 10.00, '2022-01-11 00:00:00', '2023-01-11 00:00:00');
INSERT INTO test_flink.t_p_cust_deposit_prod (cust_no, main_prod_no, sub_prod_no, amount, begin_time, end_time) VALUES('wangwu', 'C00001', 'A0000100001', 19.00, '2022-01-11 00:00:00', '2023-01-11 00:00:00');
INSERT INTO test_flink.t_p_cust_deposit_prod (cust_no, main_prod_no, sub_prod_no, amount, begin_time, end_time) VALUES('zhangsan', 'K00001', 'B0000100001', 11.00, '2022-02-01 00:00:00', '2023-02-01 00:00:00');
INSERT INTO test_flink.t_p_cust_deposit_prod (cust_no, main_prod_no, sub_prod_no, amount, begin_time, end_time) VALUES('zhangsan', 'Z00001', 'Z0000100001', 12.00, '2022-02-01 00:00:00', '2023-02-01 00:00:00');


