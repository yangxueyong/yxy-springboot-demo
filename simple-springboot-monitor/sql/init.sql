create table my_user(
    `id` bigint  auto_increment primary key,
     name varchar(20) not null,
     phone varchar(20),
     gender varchar(2),
     address varchar(200),
     createTime datetime
)
