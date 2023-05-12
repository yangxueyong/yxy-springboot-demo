create database market;

CREATE TABLE `T_P_R_M_AT` (
    `RED_ACT_NO` varchar(100) DEFAULT NULL,
    `ACCT_NO` varchar(100) not NULL,
    `ACT_TYPE` varchar(100) DEFAULT NULL,
    `MER_NO` varchar(100) DEFAULT NULL,
    `ACT_NO` varchar(100) DEFAULT NULL,
    `MAIN_PRO_NO` varchar(100) DEFAULT NULL,
    `SUB_PRO_NO` varchar(100) DEFAULT NULL,
    `PROD_QUOTA` decimal(30,4) DEFAULT NULL,
    `USE_QUOTA` decimal(30,4) DEFAULT NULL,
    `TRAN_DAY` varchar(100) not NULL,
    `CREATE_TIME` datetime DEFAULT NULL,
     PRIMARY KEY (`ACCT_NO`,`TRAN_DAY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



