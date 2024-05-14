
create database r2dbc;
use r2dbc;

CREATE TABLE `user` (
    `id` int NOT NULL,
    `name` varchar(200) DEFAULT NULL,
    `age` int DEFAULT NULL,
     PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;